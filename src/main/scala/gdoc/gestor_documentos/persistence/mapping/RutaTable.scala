package gdoc.gestor_documentos.persistence.mapping

import slick.jdbc.JdbcProfile
import gdoc.gestor_documentos.model.{MapaTabla, Ruta}

object RutaTable extends JdbcProfile{
  import api._

  private[persistence] class RutaMapa(tag: Tag) extends Table[Ruta](tag, "GDOC_RUTA") with MapaTabla{
    def id = column[Option[Long]]("ID", O.PrimaryKey)

    def descripcion = column[String]("DESCRIPCION")

    def * = (id, descripcion) <> (Ruta.tupled, Ruta.unapply)
  }

  val rutaTableQuery = TableQuery[RutaMapa]

}
