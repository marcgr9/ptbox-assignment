package ro.marc.ptbox.theharvester

import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.core.DockerClientBuilder
import org.koin.core.component.KoinComponent
import org.koin.core.parameter.parametersOf
import ro.marc.ptbox.shared.domain.model.Scan
import ro.marc.ptbox.shared.domain.ports.ScannerPort

class TheHarvesterAdapterImpl: ScannerPort, KoinComponent {

    private val dockerClient = DockerClientBuilder.getInstance().build()

    private fun callbackFactory(scan: Scan): TheHarvesterResultCallback {
        return getKoin().get<TheHarvesterResultCallback> {
            parametersOf(scan)
        }
    }

    override fun processWebsite(scan: Scan) {
        val container = dockerClient
            .createContainerCmd("theharvester")
            .withEntrypoint("/root/.local/bin/theHarvester")
            .withCmd("-b", "bing", "-d", scan.website)
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
