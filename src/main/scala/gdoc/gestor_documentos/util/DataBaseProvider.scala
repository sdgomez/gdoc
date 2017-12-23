package gdoc.gestor_documentos.util

import gdoc.gestor_documentos.model.BDConfiguration
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

object DataBaseProvider {
  private val db: PostgresProfile.backend.Database = Database.forConfig("gdoc")
  private val profile = PostgresProfile
  val dataBaseConfiguration: BDConfiguration = BDConfiguration(profile, db)
}
