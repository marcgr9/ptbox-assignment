package ro.marc.ptbox.shared.domain.validator

object WebsiteValidator {

    fun validate(website: String): Boolean {
        // TODO check for email regex, length (max 50 as in the db) and for command param injection - when executing amass for example
        return true
    }

}
