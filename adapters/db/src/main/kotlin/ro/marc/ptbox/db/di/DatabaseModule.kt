package ro.marc.ptbox.db.di

import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module
import ro.marc.ptbox.db.ScansRepositoryImpl
import ro.marc.ptbox.db.exposed.DatabaseFactory
import ro.marc.ptbox.shared.GlobalConfig
import ro.marc.ptbox.shared.domain.ports.ScansRepository

fun getDatabaseModule() = module {
    single<Database>(createdAtStart = true) {
        DatabaseFactory.provide(
            get<GlobalConfig>(),
        )
    }
    single<ScansRepository> {
        ScansRepositoryImpl()
    }
}
