package gdoc.gestor_documentos.persistence.mapping

import slick.jdbc.JdbcProfile
import gdoc.gestor_documentos.model.Dependencia

object DependenciaTable extends JdbcProfile{
  import api._

  class DependenciaMapa(tag: Tag) extends Table[Dependencia](tag, "GDOC_DEPENDENCIA") {
    def id = column[Option[Long]]("ID", O.PrimaryKey)

    def codigo = column[String]("CODIGO")

    def descripcion = column[String]("DESCRIPCION")

    def * = (id, codigo, descripcion) <> (Dependencia.tupled, Dependencia.unapply)
  }

  val dependencia = TableQuery[DependenciaMapa]
}
