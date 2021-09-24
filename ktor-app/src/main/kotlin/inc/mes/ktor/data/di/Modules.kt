package inc.mes.ktor.data.di

import inc.mes.ktor.data.daos.CustomerDao
import inc.mes.ktor.data.daos.UserDao
import org.koin.core.module.Module
import org.koin.dsl.module

val daoModule: Module = module {
    single {
        UserDao()
    }
    single {
        CustomerDao()
    }
}

val dbModules: List<Module> = listOf(
        daoModule
)