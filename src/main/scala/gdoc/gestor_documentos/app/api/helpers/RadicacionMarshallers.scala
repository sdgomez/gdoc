package gdoc.gestor_documentos.app.api.helpers
import akka.http.scaladsl.unmarshalling.{Unmarshaller, _}
import gdoc.gestor_documentos.model.{DestinatarioGestion, _}
import play.api.libs.json._
import play.api.libs.functional.syntax._

trait RadicacionMarshallers {
 implicit val internoDtoMarshaller = Json.format[InternoDTO]
 implicit val externoDtoMarshaller = Json.format[ExternoDTO]
 implicit val recibidoDtoMarshaller = Json.format[RecibidoDTO]
 implicit val personaJuridicaMarshaller = Json.format[PersonaJuridica]
 implicit val categoriaMarshaller = Json.format[Categoria]

  implicit val writeDependencia: Writes[Dependencia] = (
    (JsPath \ "id").writeNullable[Long] and
      (JsPath \ "codigo").write[String] and
      (JsPath \ "descripcion").write[String]
    )(unlift(Dependencia.unapply))

  implicit val writePersonaNatural: Writes[PersonaNatural] = (
    (JsPath \ "id").writeNullable[Long] and
      (JsPath \ "tipoIdentificacion").write[String] and
      (JsPath \ "identificacion").write[String] and
      (JsPath \ "nombres").write[String]
    )(unlift(PersonaNatural.unapply))

  implicit val writeRuta: Writes[Ruta] = (
    (JsPath \ "id").writeNullable[Long] and
      (JsPath \ "descripcion").write[String]
    )(unlift(Ruta.unapply))


  implicit val destinatarioGestionWrites: Writes[DestinatarioGestion] = new Writes[DestinatarioGestion]{
    override def writes(o: DestinatarioGestion): JsValue = o match {
      case s1: Dependencia => writeDependencia.writes(s1)
      case s2: PersonaNatural => writePersonaNatural.writes(s2)
      case s3: Ruta => writeRuta.writes(s3)
    }

    override def transform(transformer: JsValue => JsValue): Writes[DestinatarioGestion] = super.transform(transformer)

    override def transform(transformer: Writes[JsValue]): Writes[DestinatarioGestion] = super.transform(transformer)
  }

  implicit val implicitInterno1Writes = new Writes[Interno[DestinatarioGestion]] {
    def writes(interno: Interno[DestinatarioGestion]): JsValue = {
      Json.obj(
        "id" -> interno.id,
        "categoria" -> interno.categoria,
        "remitente" -> Json.toJson(interno.remitente),
        "destinatario" -> Json.toJson(interno.destinatario),
        "comentario" -> interno.comentario
      )
    }
  }

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


