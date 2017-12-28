package gdoc.gestor_documentos.persistence.mapping

import slick.jdbc.JdbcProfile
import gdoc.gestor_documentos.model.{MapaTabla, PersonaNatural}

object PersonaNaturalTable extends JdbcProfile{
  import api._

  private[persistence] class PersonaNaturalMapa(tag: Tag) extends Table[PersonaNatural](tag, "GDOC_PERSONA_NATURAL") with MapaTabla{
    def id = column[Option[Long]]("ID", O.PrimaryKey, O.AutoInc)

    def tipoIdentificacion = column[String]("TIPO_IDENTIFICACION")
    def identificacion = column[String]("IDENTIFICACION")
    def nombres = column[String]("NOMBRES")

    def * = (id, tipoIdentificacion, identificacion, nombres) <> (PersonaNatural.tupled, PersonaNatural.unapply)
  }

  val personaNaturalTableQuery = TableQuery[PersonaNaturalMapa]

  trait pnMapa extends PersonaNaturalMapa
}
