package gdoc.gestor_documentos.persistence.mapping

import gdoc.gestor_documentos.model.ExternoDTO
import slick.jdbc.JdbcProfile

object ExternoTable extends JdbcProfile{
  import api._
  private[persistence] class ExternoMapa(tag: Tag) extends Table[ExternoDTO](tag, "GDOC_EXTERNO") {
    def id = column[Option[Long]]("ID", O.PrimaryKey)

    def categoriaId = column[Option[Long]]("ID_CATEGORIA")
    def remitenteId = column[Option[Long]]("ID_REMITENTE")
    def destinatarioId = column[Option[Long]]("ID_DESTINATARIO")
    def comentario = column[String]("COMENTARIO")

    def * = (id, categoriaId, remitenteId, destinatarioId, comentario) <> (ExternoDTO.tupled, ExternoDTO.unapply)

    def categoriaFK = foreignKey("GDOC_EXTERNO_FK1", categoriaId, CategoriaTable.categoria)(_.id)
  }

  val externo = TableQuery[ExternoMapa]
}
