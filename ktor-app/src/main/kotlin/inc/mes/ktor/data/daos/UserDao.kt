package inc.mes.ktor.data.daos

import inc.mes.ktor.data.models.User
import inc.mes.ktor.data.userStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserDao : BaseDao<User> {
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