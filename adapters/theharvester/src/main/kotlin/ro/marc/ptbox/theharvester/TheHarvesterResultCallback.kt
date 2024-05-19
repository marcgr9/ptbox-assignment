package ro.marc.ptbox.theharvester

import com.github.dockerjava.api.async.ResultCallback
import com.github.dockerjava.api.model.Frame
import com.github.dockerjava.api.model.StreamType
import ro.marc.ptbox.shared.domain.ports.CompletedScansRepository
import ro.marc.ptbox.shared.domain.model.Scan
import java.io.Closeable
import java.nio.charset.Charset
import java.util.concurrent.ExecutionException

class TheHarvesterResultCallback(
//    private val scansRepository: CompletedScansRepository,
//    private val scan: Scan,
): ResultCallback<Frame> {

    override fun close() { }

    override fun onStart(closeable: Closeable?) { }

    override fun onError(throwable: Throwable?) { }

    override fun onComplete() {
        println("done")
    }

    override fun onNext(`object`: Frame) {
        println(`object`)
    }

}
