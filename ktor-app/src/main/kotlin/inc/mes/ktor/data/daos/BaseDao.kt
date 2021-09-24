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

/***
 * Provides the generic data access functions for DAOS
 */
interface BaseDao<T> {
    /***
     * Add an instance of model [T] to the Database.
     * @param item[T]
     * @return [Int]
     */
    suspend fun insert(item: T): Int

    /***
     * Add argument instances of model [T] to the Database.
     * @param items[T]
     * @return [List] of [T]
     */
    suspend fun insert(vararg items: T): List<Int>

    /***
     * Add argument instances of model [T] to the Database.
     * @param items [List] of [T]
     * @return [List] of [T]
     */
    suspend fun insert(items: List<T>): List<Int>

    /***
     * Mutate instances of model [T] in the Database.
     * @param item[T]
     * @return [Boolean]
     */
    suspend fun update(item: T): Boolean

    /***
     * Mutate instance of model [T] in the Database.
     * @param items [List] of [T]
     * @return [List] of [Boolean]
     */
    suspend fun update(items: List<T>): List<Boolean>

    /***
     * Remove instance of model [T] from the Database.
     * @param item[T]
     */
    suspend fun delete(item: T)
}
