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
    private val outputParser: HarvesterOutputParser,
    private val scansRepository: CompletedScansRepository,
): ResultCallback<Frame> {

    private var stdOut = ""

    override fun close() { }

    override fun onStart(closeable: Closeable?) { }

    override fun onError(throwable: Throwable?) { }

    override fun onComplete() {
        scansRepository.addScan(outputParser.parseOutput(stdOut))
    }

    override fun onNext(`object`: Frame) {
        stdOut += `object`.payload.toString(Charset.defaultCharset())
    }

}
