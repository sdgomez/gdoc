package gdoc.gestor_documentos.persistence.mapping
import gdoc.gestor_documentos.model.InternoDTO
import slick.jdbc.JdbcProfile

object InternoTable extends JdbcProfile{
  import api._
  private[persistence] class InternoMapa(tag: Tag) extends Table[InternoDTO](tag, "GDOC_INTERNO") {
    def id = column[Option[Long]]("ID", O.PrimaryKey)

    def categoriaId = column[Option[Long]]("ID_CATEGORIA")
    def remitenteId = column[Option[Long]]("ID_REMITENTE")
    def destinatarioId = column[Option[Long]]("ID_DESTINATARIO")
    def comentario = column[String]("COMENTARIO")

    def * = (id, categoriaId, remitenteId, destinatarioId, comentario) <> (InternoDTO.tupled, InternoDTO.unapply)

    def categoriaFK = foreignKey("GDOC_INTERNO_FK1", categoriaId, CategoriaTable.categoria)(_.id)
    def personaNaturalFK = foreignKey("GDOC_INTERNO_FK2", remitenteId, PersonaNaturalTable.personaNatural)(_.id)
  }

  val interno = TableQuery[InternoMapa]
}

