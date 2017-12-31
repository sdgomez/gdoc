package gdoc.gestor_documentos.configuration

import com.typesafe.config.{Config, ConfigFactory}

object ApplicationConf {
 private val conf: Config = ConfigFactory.load()
 val postgresError: Config = conf.getConfig("postgresError")
  val server: String = conf.getString("server")
  val port: Int = conf.getInt("port")

 val foreign_key_violation: String = postgresError.getString("foreign_key_violation")
}
