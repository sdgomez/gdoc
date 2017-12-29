package gdoc.gestor_documentos.configuration

import com.typesafe.config.ConfigFactory

object ApplicationConf {
 private val conf = ConfigFactory.load()
  val server = conf.getString("server")
  val port = conf.getInt("port")
}
