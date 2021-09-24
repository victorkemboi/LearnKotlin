package inc.mes.ktor.data.daos

interface BaseDao<T> {

    suspend fun insert(item: T): Int

    suspend fun insert(vararg items: T): List<Int>

    suspend fun insert(items: List<T>): List<Int>

    suspend fun update(item: T): Int

    suspend fun update(items: List<T>): Int

    suspend fun delete(item: T)
}