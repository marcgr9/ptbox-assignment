package ro.marc.ptbox.theharvester

import com.github.dockerjava.api.async.ResultCallback
import com.github.dockerjava.api.model.Frame
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.core.DockerClientBuilder
import org.koin.core.component.KoinComponent
import ro.marc.ptbox.shared.domain.AmassAdapter

class AmassAdapterImpl: AmassAdapter, KoinComponent {

    private val callback: ResultCallback<Frame>
        get() = getKoin().get<ResultCallback<Frame>>()

    override fun processWebsite(website: String) {
        val dockerClient = DockerClientBuilder.getInstance().build()
        val container = dockerClient
            .createContainerCmd("caffix/amass")
            .withCmd("intel", "-whois", website)
            .withHostConfig(HostConfig().withAutoRemove(true))
            .exec()

        dockerClient
            .startContainerCmd(container.id)
            .exec()

        dockerClient.logContainerCmd(container.id)
            .withStdOut(true)
            .withStdErr(true)
            .withFollowStream(true)
            .exec(callback)

    }

}
