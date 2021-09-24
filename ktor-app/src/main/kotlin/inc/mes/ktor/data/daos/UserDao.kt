/*
 * Copyright 2021 Mes Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package inc.mes.ktor.data.daos

import inc.mes.ktor.data.models.User
import inc.mes.ktor.data.userStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

/***
 * A data access object for manipulating the [User] model in the database.
 *
 */
class UserDao : BaseDao<User> {
    fun get(id: Int): Flow<User?> = flow {
        emit(
            userStorage[id]
        )
    }

    fun userExists(username: String): Flow<Boolean> = getByUsername(username).map {
        it != null
    }

    fun getByUsername(username: String): Flow<User?> = flow {
        for (user in userStorage.entries) {
            if (user.value.username == username) {
                emit(user.value)
                break
            }
        }
        emit(null)
    }

    override suspend fun insert(item: User): Int {
        if (item.id != 0 || userStorage.containsValue(item)) {
            return 0
        }
        val userId = userStorage.size + 1
        userStorage[userId] = item.apply {
            id = userId
        }
        return userId
    }

    override suspend fun insert(vararg items: User): List<Int> = mutableListOf<Int>().apply {
        items.forEach {
            add(insert(it))
        }
    }

    override suspend fun insert(items: List<User>): List<Int> = mutableListOf<Int>().apply {
        items.forEach {
            add(insert(it))
        }
    }

    override suspend fun update(item: User): Boolean {
        if (item.id == 0 || !userStorage.containsKey(item.id)) {
            return false
        }
        userStorage[item.id] = item
        return true
    }

    override suspend fun update(items: List<User>): List<Boolean> = mutableListOf<Boolean>().apply {
        items.forEach {
            add(update(it))
        }
    }

    override suspend fun delete(item: User) {
        userStorage.remove(item.id)
    }
}
