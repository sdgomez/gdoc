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
 val errorMensajes: Config = mensajes.getConfig("error")
 val destinatarioNotFound = errorMensajes.getString("destinatario_not_found")
 val remitenteNotFound = errorMensajes.getString("remitente_not_found")
 val categoriaNotFound = errorMensajes.getString("categoria_not_found")

 /*
 * variables para pattern maching
  */
 val matchVariables: Config = conf.getConfig("matchVariables")
 val idDestinatario: String = matchVariables.getString("id_destinatario")
 val idRemitente: String = matchVariables.getString("id_remitente")
 val idCategoria: String = matchVariables.getString("id_categoria")

 val dependenciaType: String = matchVariables.getString("dependencia_type")
 val rutaType: String = matchVariables.getString("ruta_type")
 val personaNaturalType: String = matchVariables.getString("persona_natural_type")
 val personaJuridicaType: String = matchVariables.getString("persona_juridica_type")
}
