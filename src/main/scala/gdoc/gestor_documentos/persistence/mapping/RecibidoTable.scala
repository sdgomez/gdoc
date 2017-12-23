package gdoc.gestor_documentos.persistence.mapping

import slick.jdbc.JdbcProfile
import gdoc.gestor_documentos.model.RecibidoDTO


object RecibidoTable extends JdbcProfile{
  import api._
  private[persistence] class RecibidoMapa(tag: Tag) extends Table[RecibidoDTO](tag, "GDOC_RECIBIDO") {
    def id = column[Option[Long]]("ID", O.PrimaryKey)

    def categoriaId = column[Option[Long]]("ID_CATEGORIA")
    def remitenteId = column[Option[Long]]("ID_REMITENTE")
    def destinatarioId = column[Option[Long]]("ID_DESTINATARIO")
    def comentario = column[String]("COMENTARIO")

    def * = (id, categoriaId, remitenteId, destinatarioId, comentario) <> (RecibidoDTO.tupled, RecibidoDTO.unapply)

    def categoriaFK = foreignKey("GDOC_RECIBIDO_FK1", categoriaId, CategoriaTable.categoria)(_.id)
  }

  val recibido = TableQuery[RecibidoMapa]
}