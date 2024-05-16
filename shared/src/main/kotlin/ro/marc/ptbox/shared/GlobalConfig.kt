package ro.marc.ptbox.shared

data class GlobalConfig(
    private val kv: Map<String, Any?>
) {

    fun getString(key: String): String {
        return getObject(key).toString()
    }

    fun getLong(key: String): Long {
        return getObject(key).toString().toLong()
    }

    private fun getObject(key: String): Any? {
        val parts = key.split(".")

        var result: Any? = null
        var subtree: Any? = kv
        parts.forEachIndexed { index, part ->
            if (index == parts.size - 1) {
                result = (subtree as Map<*, *>)[part]
                return@forEachIndexed
            }

            subtree = (subtree as Map<*, *>)[part]
        }

        return result
    }

}
