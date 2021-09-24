package inc.mes.ktor.data.daos

import inc.mes.ktor.data.models.User
import inc.mes.ktor.data.userStorage

class UserDao : BaseDao<User> {
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