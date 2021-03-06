package gdoc.gestor_documentos.app.api.helpers
import akka.http.scaladsl.unmarshalling.{Unmarshaller, _}
import gdoc.gestor_documentos.model.exception.GdocError
import gdoc.gestor_documentos.model.{DestinatarioGestion, _}
import play.api.libs.json._
import play.api.libs.functional.syntax._

trait RadicacionMarshallers {
 implicit val internoDtoMarshaller = Json.format[InternoDTO]
 implicit val externoDtoMarshaller = Json.format[ExternoDTO]
 implicit val recibidoDtoMarshaller = Json.format[RecibidoDTO]
 implicit val categoriaMarshaller = Json.format[Categoria]
 //implicit val gdocErrorMarshaller = Json.format[GdocError]

  implicit val writeError: Writes[GdocError] = (
    (JsPath \ "codigo").write[Long] and
    (JsPath \ "mensaje").write[String] and
    (JsPath \ "mensajeTecnico").write[String]
    )(unlift(GdocError.unapply))

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

  implicit val writePersonaJuridica: Writes[PersonaJuridica] = (
    (JsPath \ "id").writeNullable[Long] and
      (JsPath \ "nit").write[String] and
      (JsPath \ "nombre").write[String]
    )(unlift(PersonaJuridica.unapply))

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

  implicit val remitenteGestionWrites: Writes[RemitenteGestion] = new Writes[RemitenteGestion]{
    override def writes(o: RemitenteGestion): JsValue = o match {
      case s2: PersonaNatural => writePersonaNatural.writes(s2)
      case s1: Dependencia => writeDependencia.writes(s1)
    }

    override def transform(transformer: JsValue => JsValue): Writes[RemitenteGestion] = super.transform(transformer)

    override def transform(transformer: Writes[JsValue]): Writes[RemitenteGestion] = super.transform(transformer)
  }

  implicit val personaWrites: Writes[Persona] = new Writes[Persona]{
    override def writes(o: Persona): JsValue = o match {
      case s1: PersonaJuridica => writePersonaJuridica.writes(s1)
      case s2: PersonaNatural => writePersonaNatural.writes(s2)
    }

    override def transform(transformer: JsValue => JsValue): Writes[Persona] = super.transform(transformer)

    override def transform(transformer: Writes[JsValue]): Writes[Persona] = super.transform(transformer)
  }

  implicit val documentoWrites: Writes[Documento] = new Writes[Documento]{
    override def writes(o: Documento): JsValue = o match {
      case s1: Interno[DestinatarioGestion] => implicitInterno1Writes.writes(s1)
      case s2: Externo => implicitExterno1Writes.writes(s2)
      case s3: Recibido[DestinatarioGestion, RemitenteGestion] => implicitRecibido1Writes.writes(s3)
      case s4: GdocError => writeError.writes(s4)
    }

    override def transform(transformer: JsValue => JsValue): Writes[Documento] = super.transform(transformer)

    override def transform(transformer: Writes[JsValue]): Writes[Documento] = super.transform(transformer)
  }

  implicit val implicitInterno1Writes = new Writes[Interno[DestinatarioGestion]] {
    def writes(interno: Interno[DestinatarioGestion]): JsValue = {
      Json.obj(
        "id" -> interno.id,
        "categoria" -> interno.categoria,
        "remitente" -> personaWrites.writes(interno.remitente),
        "destinatario" -> Json.toJson(interno.destinatario),
        "comentario" -> interno.comentario
      )
    }

    override def transform(transformer: JsValue => JsValue): Writes[Interno[DestinatarioGestion]] = super.transform(transformer)

    override def transform(transformer: Writes[JsValue]): Writes[Interno[DestinatarioGestion]] = super.transform(transformer)
  }

  implicit val implicitRecibido1Writes = new Writes[Recibido[DestinatarioGestion, RemitenteGestion]] {
    def writes(recibido: Recibido[DestinatarioGestion, RemitenteGestion]): JsValue = {
      Json.obj(
        "id" -> recibido.id,
        "categoria" -> recibido.categoria,
        "remitente" -> Json.toJson(recibido.remitente),
        "destinatario" -> Json.toJson(recibido.destinatario),
        "comentario" -> recibido.comentario
      )
    }

    override def transform(transformer: JsValue => JsValue): Writes[Recibido[DestinatarioGestion, RemitenteGestion]] = super.transform(transformer)

    override def transform(transformer: Writes[JsValue]): Writes[Recibido[DestinatarioGestion, RemitenteGestion]] = super.transform(transformer)
  }

  implicit val implicitExterno1Writes = new Writes[Externo] {
    def writes(externo: Externo): JsValue = {
      Json.obj(
        "id" -> externo.id,
        "categoria" -> externo.categoria,
        "remitente" -> Json.toJson(externo.remitente),
        "destinatario" -> Json.toJson(externo.destinatario),
        "comentario" -> externo.comentario
      )
    }

    override def transform(transformer: JsValue => JsValue): Writes[Externo] = super.transform(transformer)

    override def transform(transformer: Writes[JsValue]): Writes[Externo] = super.transform(transformer)
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


