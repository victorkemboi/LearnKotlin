package inc.mes.ktor.data.daos

import inc.mes.ktor.data.customerStorage
import inc.mes.ktor.data.models.Customer

class CustomerDao : BaseDao<Customer> {
    override suspend fun insert(item: Customer): Int {
        if (item.id != 0 || customerStorage.containsValue(item)) {
            return 0
        }
        val customerId = customerStorage.size + 1
        customerStorage[customerId] = item.apply {
            id = customerId
        }
        return customerId
    }

    override suspend fun insert(vararg items: Customer): List<Int> = mutableListOf<Int>().apply {
        items.forEach {
            add(insert(it))
        }
    }

    override suspend fun insert(items: List<Customer>): List<Int> = mutableListOf<Int>().apply {
        items.forEach {
            add(insert(it))
        }
    }

    override suspend fun update(item: Customer): Boolean {
        if (item.id == 0 || !customerStorage.containsKey(item.id)) {
            return false
        }
        customerStorage[item.id] = item
        return true
    }

    override suspend fun update(items: List<Customer>): List<Boolean> =
        mutableListOf<Boolean>().apply {
            items.forEach {
                add(update(it))
            }
        }

    override suspend fun delete(item: Customer) {
        customerStorage.remove(item.id)
    }
}