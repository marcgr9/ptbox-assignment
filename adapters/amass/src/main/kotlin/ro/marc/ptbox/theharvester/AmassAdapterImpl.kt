package ro.marc.ptbox.theharvester

import com.github.dockerjava.api.async.ResultCallback
import com.github.dockerjava.api.model.Frame
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.core.DockerClientBuilder
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import ro.marc.ptbox.shared.domain.model.Scan
import ro.marc.ptbox.shared.domain.ports.ScannerPort

class AmassAdapterImpl: ScannerPort, KoinComponent {

    private val dockerClient = DockerClientBuilder.getInstance().build()

    private fun callbackFactory(scan: Scan): ResultCallback<Frame> {
        return getKoin().get<ResultCallback<Frame>> {
            parametersOf(scan)
        }
    }

    override fun processWebsite(scan: Scan) {
        val container = dockerClient
            .createContainerCmd("caffix/amass")
            .withCmd("intel", "-whois", "-d", scan.website)
            .withHostConfig(HostConfig().withAutoRemove(true))
            .exec()

        dockerClient
            .startContainerCmd(container.id)
            .exec()

        dockerClient.logContainerCmd(container.id)
            .withStdOut(true)
            .withStdErr(true)
            .withFollowStream(true)
            .exec(callbackFactory(scan))
    }

}
