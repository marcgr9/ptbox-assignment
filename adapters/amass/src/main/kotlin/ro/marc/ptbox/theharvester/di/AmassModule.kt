package ro.marc.ptbox.theharvester.di

import com.github.dockerjava.api.async.ResultCallback
import com.github.dockerjava.api.model.Frame
import kotlinx.coroutines.flow.MutableSharedFlow
import org.koin.dsl.module
import ro.marc.ptbox.shared.domain.AmassAdapter
import ro.marc.ptbox.shared.domain.CompletedScansRepository
import ro.marc.ptbox.theharvester.AmassAdapterImpl
import java.io.Closeable
import java.nio.charset.Charset

fun getAmassModule() = module {
    factory<ResultCallback<Frame>> {
        val scansRepository = get<CompletedScansRepository>()

        object: ResultCallback<Frame> {
            var stdOut = ""

            override fun close() { }

            override fun onStart(closeable: Closeable?) { }

            override fun onError(throwable: Throwable?) { }

            override fun onComplete() {
                println("done ${Thread.currentThread().name}")
                println(stdOut)
                scansRepository.addScan("it is done")
            }

            override fun onNext(`object`: Frame?) {
                stdOut = stdOut + `object`!!.payload.toString(Charset.defaultCharset()) + "\n"
            }

        }
    }
    single<AmassAdapter> {
        AmassAdapterImpl()
    }
}