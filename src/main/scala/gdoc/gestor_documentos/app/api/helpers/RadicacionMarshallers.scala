package gdoc.gestor_documentos.app.api.helpers
import akka.http.scaladsl.unmarshalling.{Unmarshaller, _}
import gdoc.gestor_documentos.model.{ExternoDTO, InternoDTO, RecibidoDTO}
import play.api.libs.json._
import play.api.libs.functional.syntax._

trait RadicacionMarshallers {
 implicit val internoDtoMarshaller = Json.format[InternoDTO]
 implicit val externoDtoMarshaller = Json.format[ExternoDTO]
 implicit val recibidoDtoMarshaller = Json.format[RecibidoDTO]

 /* implicit val internoDtoWrites: Writes[InternoDTO] = Json.writes[InternoDTO]

  implicit val internoDtoReads: Reads[InternoDTO] =
    ((JsPath \ "id").readNullable[Long] and
      (JsPath \ "categoriaId").readNullable[Long] and
      (JsPath \ "remitenteId").readNullable[Long] and
      (JsPath \ "tipoDestinatario").read[String] and
      (JsPath \ "destinatarioId").readNullable[Long] and
      (JsPath \ "comentario").read[String])(InternoDTO.apply _)
  */
  implicit val internoDtoUnmarshaller: FromEntityUnmarshaller[InternoDTO] = {
    Unmarshaller.stringUnmarshaller.map(json => Json.parse(json).as[InternoDTO])
  }

  implicit val externoDtoUnmarshaller: FromEntityUnmarshaller[ExternoDTO] = {
    Unmarshaller.stringUnmarshaller.map(json => Json.parse(json).as[ExternoDTO])
  }

  implicit val recibidoDtoUnmarshaller: FromEntityUnmarshaller[RecibidoDTO] = {
    Unmarshaller.stringUnmarshaller.map(json => Json.parse(json).as[RecibidoDTO])
  }
}
