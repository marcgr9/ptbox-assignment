package ro.marc.ptbox.theharvester

import ro.marc.ptbox.shared.domain.model.Scan

internal class HarvesterOutputParserImpl(
    private val scan: Scan,
    private val numberOfModulesUsed: Int,
): HarvesterOutputParser {

    companion object {
        private const val DATA_POINT_PREFIX = "[*]"
    }

    /**
     * Our check is that a line is a data point if it starts with [DATA_POINT_PREFIX].
     * There are [numberOfModulesUsed] data points that indicate the modules used, but those lines actually start
     * with a color character followed by [DATA_POINT_PREFIX]
     *
     * So, the IPs data point is always the 2nd one, using the aforementioned check
     */
    private val ipsDataPoint = 1 + 1
    private val emailsDataPoint = ipsDataPoint + 1
    private val hostsDataPoint = emailsDataPoint + 1

    override fun parseOutput(output: String): Scan {
        val lines = output.lines()
        val ipsDataPointIndex = lines.findNthDataPointIndex(ipsDataPoint)
        val emailsDataPointIndex = lines.findNthDataPointIndex(emailsDataPoint)
        val hostsDataPointIndex = lines.findNthDataPointIndex(hostsDataPoint)

        val ips = lines.extractResults(ipsDataPointIndex)
        val emails = lines.extractResults(emailsDataPointIndex)
        val hosts = lines.extractResults(hostsDataPointIndex)

        return scan.copy(
            results = scan.results.copy(
                ips = ips,
                emails = emails,
                subdomains = hosts,
            )
        )
    }

    private fun List<String>.extractResults(dataPointIndex: Int): List<String> {
        if (get(dataPointIndex + 1).isBlank()) return listOf()

        return subList(dataPointIndex + 2, lastIndex).takeWhile { it.isNotBlank() }
    }

    private fun List<String>.findNthDataPointIndex(n: Int): Int {
        return withIndex().filter { it.value.startsWith(DATA_POINT_PREFIX) }.getOrNull(n - 1)?.index!!
    }

}
