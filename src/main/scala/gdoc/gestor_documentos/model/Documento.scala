package gdoc.gestor_documentos.model


sealed trait Documento

final case class Interno[A<:DestinatarioGestion]
(
  id:Option[Long],
  categoria: Categoria,
  remitente:PersonaNatural,
  destinatario:A,
  comentario:Option[String]
) extends Documento

final case class InternoDTO(id:Option[Long], categoriaId:Option[Long], remitenteId:Option[Long], destinatarioId:Option[Long], comentario:String)

final case class Externo
(
  id:Option[Long],
  categoria: Categoria,
  remitente: Persona,
  destinatario:Persona,
  comentario:Option[String]
) extends Documento

final case class ExternoDTO(id:Option[Long], categoriaId:Option[Long], remitenteId:Option[Long], destinatarioId:Option[Long], comentario:String)

final case class Recibido[A<:DestinatarioGestion, B<:RemitenteGestion]
(
  id:Option[Long],
  categoria: Categoria,
  remitente:B,
  destinatario: A,
  comentario:Option[String]
) extends Documento

final case class RecibidoDTO(id:Option[Long], categoriaId:Option[Long], remitenteId:Option[Long], destinatarioId:Option[Long], comentario:String)

