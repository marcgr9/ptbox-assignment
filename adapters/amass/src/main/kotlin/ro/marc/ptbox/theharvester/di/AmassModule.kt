package ro.marc.ptbox.theharvester.di

import com.github.dockerjava.api.async.ResultCallback
import com.github.dockerjava.api.model.Frame
import org.koin.dsl.module
import ro.marc.ptbox.shared.domain.ScanAdapter
import ro.marc.ptbox.shared.domain.CompletedScansRepository
import ro.marc.ptbox.shared.domain.Scan
import ro.marc.ptbox.theharvester.AmassAdapterImpl
import ro.marc.ptbox.theharvester.AmassResultCallback
import java.util.*

fun getAmassModule() = module {
    factory<ResultCallback<Frame>> {
        AmassResultCallback(
            get<CompletedScansRepository>(),
            it.get<Scan>(),
        )
    }
    single<ScanAdapter> {
        AmassAdapterImpl()
    }
}