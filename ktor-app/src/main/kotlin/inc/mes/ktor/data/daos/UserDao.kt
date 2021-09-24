package inc.mes.ktor.data.daos

import inc.mes.ktor.data.entities.User
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

    override suspend fun insert(vararg items: User): List<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(items: List<User>): List<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: User): Int {
        TODO("Not yet implemented")
    }

    override suspend fun update(items: List<User>): Int {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: User) {
        TODO("Not yet implemented")
    }
}