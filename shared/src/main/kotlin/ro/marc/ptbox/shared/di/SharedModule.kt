package ro.marc.ptbox.shared.di

import org.koin.dsl.module
import ro.marc.ptbox.shared.GlobalConfig

fun getSharedModule(kv: Map<String, *>) = module {
    single {
        GlobalConfig(kv)
    }
}
