package gdoc.gestor_documentos.model

trait DestinatarioGestion
trait RemitenteGestion

sealed trait Persona
final case class Dependencia(id:Option[Long], codigo:String, descripcion:String)
  extends DestinatarioGestion with RemitenteGestion

final case class PersonaNatural(
   id:Option[Long], tipoIdentificacion:String, identificacion:String,
   nombres: String
) extends Persona with DestinatarioGestion with RemitenteGestion

final case class PersonaJuridica(id:Option[Long], nit:String, nombre:String) extends Persona

final case class Ruta(id:Option[Long], descripcion:String) extends DestinatarioGestion

final case class Categoria(id:Option[Long], nombre:String, descripcion:String)
