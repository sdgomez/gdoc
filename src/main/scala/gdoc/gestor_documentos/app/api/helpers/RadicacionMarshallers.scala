package gdoc.gestor_documentos.app.api.helpers
import akka.http.scaladsl.unmarshalling.{Unmarshaller, _}
import gdoc.gestor_documentos.model.{DestinatarioGestion, _}
import play.api.libs.json._
import play.api.libs.functional.syntax._

trait RadicacionMarshallers {
 implicit val internoDtoMarshaller = Json.format[InternoDTO]
 implicit val externoDtoMarshaller = Json.format[ExternoDTO]
 implicit val recibidoDtoMarshaller = Json.format[RecibidoDTO]
 // implicit val dependenciaMarshaller = Json.format[Dependencia]
 //implicit val personaNaturalMarshaller = Json.format[PersonaNatural]
 implicit val personaJuridicaMarshaller = Json.format[PersonaJuridica]
 // implicit val rutaMarshaller = Json.format[Ruta]
 implicit val categoriaMarshaller = Json.format[Categoria]
  // implicit val destinatarioGestionMarshaller = Json.format[DestinatarioGestion]
 /*implicit val internoMarshaller = Json.format[Interno[DestinatarioGestion]]
 implicit val recibidoMarshaller = Json.format[Recibido[DestinatarioGestion, RemitenteGestion]]
 implicit val externoMarshaller = Json.format[Externo] */

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

  /* implicit val implicitInterno2Writes = new Writes[Interno[PersonaNatural]] {
    def writes(interno: Interno[PersonaNatural]): JsValue = {
      Json.obj(
        "id" -> interno.id,
        "categoria" -> interno.categoria,
        "remitente" -> Json.toJson(interno.remitente),
        "destinatario" -> Json.toJson(interno.destinatario),
        "comentario" -> interno.comentario
      )
    }
  }

  implicit val implicitInterno3Writes = new Writes[Interno[Ruta]] {
    def writes(interno: Interno[Ruta]): JsValue = {
      Json.obj(
        "id" -> interno.id,
        "categoria" -> interno.categoria,
        "remitente" -> Json.toJson(interno.remitente),
        "destinatario" -> Json.toJson(interno.destinatario),
        "comentario" -> interno.comentario
      )
    }
  } */

  /* implicit val internoReads: Reads[Interno[DestinatarioGestion]] = (
    (JsPath \ "id").readNullable[Long] and
      (JsPath \ "categoria").read[Categoria] and
      (JsPath \ "remitente").read[PersonaNatural] and
      (JsPath \ "destinatario").read[DestinatarioGestion] and
      (JsPath \ "comentario").readNullable[String]
    )(Interno[DestinatarioGestion].apply _)

  implicit val internoWrites: Writes[Interno[Dependencia]] = (
    (JsPath \ "id").writeNullable[Long] and
      (JsPath \ "categoria").write[Categoria] and
      (JsPath \ "remitente").write[PersonaNatural] and
      (JsPath \ "destinatario").write[Dependencia] and
      (JsPath \ "comentario").writeNullable[String]
    )(unlift(Interno[Dependencia].unapply))
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


