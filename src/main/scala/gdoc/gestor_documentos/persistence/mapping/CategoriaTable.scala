package gdoc.gestor_documentos.persistence.mapping

import gdoc.gestor_documentos.model.Categoria
import slick.jdbc.JdbcProfile

object CategoriaTable extends JdbcProfile{
  import api._

  private[persistence] class CategoriaMapa(tag: Tag) extends Table[Categoria](tag, "GDOC_CATEGORIA") {
    def id = column[Option[Long]]("ID", O.PrimaryKey)

    def nombre = column[String]("NOMBRE")

    def descripcion = column[String]("DESCRIPCION")

    def * = (id, nombre, descripcion) <> (Categoria.tupled, Categoria.unapply)
  }

  val categoria = TableQuery[CategoriaMapa]
}
