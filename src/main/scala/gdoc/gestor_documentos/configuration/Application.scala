package gdoc.gestor_documentos.configuration

import com.typesafe.config.{Config, ConfigFactory}

object ApplicationConf {

 private val conf: Config = ConfigFactory.load()

 /*
 * Variables de arranque de aplicación
 * */
  val server: String = conf.getString("server")
  val port: Int = conf.getInt("port")


 /*
 * Clasificacion de errores de postgres
  */
 val postgresError: Config = conf.getConfig("postgresError")
 val foreign_key_violation: String = postgresError.getString("foreign_key_violation")

 /*
 * mensajes que entrega este microservicio
 */
 val mensajes = conf.getConfig("mensajes")
 val errorMensajes = conf.getConfig("error")
 val destinatarioNotFound = errorMensajes.getString("destinatario_not_found")
 val remitenteNotFound = errorMensajes.getString("remitente_not_found")
 val categoriaNotFound = errorMensajes.getString("categoria_not_found")
}
