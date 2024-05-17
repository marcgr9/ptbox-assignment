package ro.marc.ptbox.db.exposed

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.util.PSQLException
import ro.marc.ptbox.shared.GlobalConfig

object DatabaseFactory {

    fun provide(config: GlobalConfig): Database {
        val hkConfig = HikariConfig().apply {
            driverClassName = config.getString("database.driver")
            jdbcUrl = config.getString("database.url")
            username = config.getString("database.username")
            password = config.getString("database.password")
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        val ds = HikariDataSource(hkConfig)
        val db = Database.connect(ds)

        val tables: Array<Table> = arrayOf(
            ScansTable,
        )

        transaction(db = db) {
            try {
                exec(ScansTable.pgCreateEnumSql)
            } catch (ex: Exception) {
                //
            }
        }

        transaction(db = db) {
            SchemaUtils.createMissingTablesAndColumns(*tables)
        }

        return db
    }

}
