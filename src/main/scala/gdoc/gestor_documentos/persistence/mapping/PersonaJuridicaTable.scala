package gdoc.gestor_documentos.persistence.mapping

import slick.jdbc.JdbcProfile
import gdoc.gestor_documentos.model.PersonaJuridica

object PersonaJuridicaTable extends JdbcProfile{
  import api._

  private[persistence] class PersonaJuridicaMapa(tag: Tag) extends Table[PersonaJuridica](tag, "GDOC_PERSONA_JURIDICA") {
    def id = column[Option[Long]]("ID", O.PrimaryKey, O.AutoInc)

    def nit = column[String]("NIT")

    def nombre = column[String]("NOMBRE")

    def * = (id, nit, nombre) <> (PersonaJuridica.tupled, PersonaJuridica.unapply)
  }

  val personaJuridicaTableQuery = TableQuery[PersonaJuridicaMapa]
}
