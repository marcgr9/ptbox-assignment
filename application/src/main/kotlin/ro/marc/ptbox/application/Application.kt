package ro.marc.ptbox.application

import ro.marc.ptbox.db.Db
import ro.marc.ptbox.theharvester.Harvester

class Application {

    init {
        Db()
        Harvester()
    }

}
