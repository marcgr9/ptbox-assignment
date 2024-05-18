package ro.marc.ptbox.theharvester

import com.github.dockerjava.api.async.ResultCallback
import com.github.dockerjava.api.model.Frame
import com.github.dockerjava.api.model.StreamType
import ro.marc.ptbox.shared.domain.ports.CompletedScansRepository
import ro.marc.ptbox.shared.domain.model.Scan
import java.io.Closeable
import java.nio.charset.Charset
import java.util.concurrent.ExecutionException

class AmassResultCallback(
    private val scansRepository: CompletedScansRepository,
    private val scan: Scan,
): ResultCallback<Frame> {

    private var stdOut = ""
    private var completed = false

    override fun close() { }

    override fun onStart(closeable: Closeable?) { }

    override fun onError(throwable: Throwable?) {
        if (completed) return

        scansRepository.addScan(
            scan.copy(
                status = Scan.Status.FAILED,
            )
        )
        completed = true
    }

    override fun onComplete() {
        if (completed) return

        scansRepository.addScan(
            scan.copy(
                status = Scan.Status.COMPLETED,
                results = parseStdOut(stdOut)
            )
        )
        completed = true
    }

    override fun onNext(`object`: Frame) {
        if (`object`.streamType == StreamType.STDERR) {
            throw ExecutionException("Unexpected stream type ${`object`.streamType}", Exception())
        }

        stdOut += `object`.payload.toString(Charset.defaultCharset()) + "\n"
    }

    private fun parseStdOut(stdOut: String): List<String> =
        stdOut
            .split("\n")
            .filter { it.isNotBlank() }

}
