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

import inc.mes.ktor.data.customerStorage
import inc.mes.ktor.data.models.Customer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/***
 * A data access object for manipulating the [Customer] model in the database.
 */
class CustomerDao : BaseDao<Customer> {
    fun get(id: Int): Flow<Customer?> = flow {
        emit(
            customerStorage[id]
        )
    }

    fun getByEmail(email: String): Flow<Customer?> = flow {
        for (customer in customerStorage.entries) {
            if (customer.value.email == email) {
                emit(customer.value)
                break
            }
        }
        emit(null)
    }

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
