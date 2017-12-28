package gdoc.gestor_documentos.persistence.mapping

import slick.jdbc.JdbcProfile
import gdoc.gestor_documentos.model.RecibidoDTO


object RecibidoTable extends JdbcProfile{
  import api._
  private[persistence] class RecibidoMapa(tag: Tag) extends Table[RecibidoDTO](tag, "GDOC_RECIBIDO") {
    def id = column[Option[Long]]("ID", O.PrimaryKey, O.AutoInc)

    def categoriaId = column[Option[Long]]("ID_CATEGORIA")
    def tipoRemitente = column[String]("TIPO_REMITENTE")
    def remitenteId = column[Option[Long]]("ID_REMITENTE")
    def tipoDestinatario = column[String]("TIPO_DESTINATARIO")
    def destinatarioId = column[Option[Long]]("ID_DESTINATARIO")
    def comentario = column[String]("COMENTARIO")

    def * = (id, categoriaId, tipoRemitente, remitenteId, tipoDestinatario, destinatarioId, comentario) <> (RecibidoDTO.tupled, RecibidoDTO.unapply)

    def categoriaFK = foreignKey("GDOC_RECIBIDO_FK1", categoriaId, CategoriaTable.categoriaTableQuery)(_.id)
  }

  val recibidoTableQuery = TableQuery[RecibidoMapa]
}