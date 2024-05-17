package ro.marc.ptbox.theharvester

import com.github.dockerjava.api.async.ResultCallback
import com.github.dockerjava.api.model.Frame
import ro.marc.ptbox.shared.domain.CompletedScansRepository
import ro.marc.ptbox.shared.domain.Scan
import java.io.Closeable
import java.nio.charset.Charset
import java.util.UUID

class AmassResultCallback(
    private val scansRepository: CompletedScansRepository,
    private val scan: Scan,
): ResultCallback<Frame> {

    private var stdOut = ""

    override fun close() { }

    override fun onStart(closeable: Closeable?) { }

    override fun onError(throwable: Throwable?) { }

    override fun onComplete() {
        scansRepository.addScan(scan.copy(results = parseStdOut(stdOut)))
    }

    override fun onNext(`object`: Frame?) {
        stdOut += `object`!!.payload.toString(Charset.defaultCharset()) + "\n"
    }

    private fun parseStdOut(stdOut: String): List<String> {
        val lines = stdOut.split("\n")
        if (lines.first() == "No root domain names were provided") {
            return listOf()
        }

        return lines.filterNot { it.isNotBlank() }
    }

}
