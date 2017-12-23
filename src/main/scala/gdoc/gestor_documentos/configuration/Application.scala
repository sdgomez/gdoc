package gdoc.gestor_documentos.configuration

import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

object ApplicationConf {
  val db: PostgresProfile.backend.Database = Database.forConfig("gdoc")
}
