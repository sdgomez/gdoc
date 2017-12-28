package gdoc.gestor_documentos.persistence.mapping

import gdoc.gestor_documentos.model.ExternoDTO
import slick.jdbc.JdbcProfile

object ExternoTable extends JdbcProfile{
  import api._
  private[persistence] class ExternoMapa(tag: Tag) extends Table[ExternoDTO](tag, "GDOC_EXTERNO") {
    def id = column[Option[Long]]("ID", O.PrimaryKey, O.AutoInc)

    def categoriaId = column[Option[Long]]("ID_CATEGORIA")
    def tipoRemitente = column[String]("TIPO_REMITENTE")
    def remitenteId = column[Option[Long]]("ID_REMITENTE")
    def tipoDestinatario = column[String]("TIPO_DESTINATARIO")
    def destinatarioId = column[Option[Long]]("ID_DESTINATARIO")
    def comentario = column[String]("COMENTARIO")

    def * = (id, categoriaId, tipoRemitente, remitenteId, tipoDestinatario, destinatarioId, comentario) <> (ExternoDTO.tupled, ExternoDTO.unapply)

    def categoriaFK = foreignKey("GDOC_EXTERNO_FK1", categoriaId, CategoriaTable.categoriaTableQuery)(_.id)
  }

  val externoTableQuery = TableQuery[ExternoMapa]
}
