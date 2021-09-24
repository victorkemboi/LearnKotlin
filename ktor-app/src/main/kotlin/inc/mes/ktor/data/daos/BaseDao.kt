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

interface BaseDao<T> {

    suspend fun insert(item: T): Int

    suspend fun insert(vararg items: T): List<Int>

    suspend fun insert(items: List<T>): List<Int>

    suspend fun update(item: T): Boolean

    suspend fun update(items: List<T>): List<Boolean>

    suspend fun delete(item: T)
}
