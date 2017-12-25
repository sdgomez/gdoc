package gdoc.gestor_documentos.persistence.repository.interpreter.helpers

import gdoc.gestor_documentos.model._
import gdoc.gestor_documentos.persistence.mapping.CategoriaTable.categoriaTableQuery
import gdoc.gestor_documentos.persistence.mapping.DependenciaTable.dependenciaTableQuery
import gdoc.gestor_documentos.persistence.mapping.InternoTable.internoTableQuery
import gdoc.gestor_documentos.persistence.mapping.PersonaNaturalTable.personaNaturalTableQuery
import gdoc.gestor_documentos.persistence.mapping.RutaTable.rutaTableQuery

import scala.concurrent.{ExecutionContext, Future}

trait RadicacionInternoHelper {

  private[interpreter] def getInterno(internoDTO: InternoDTO, dbConfiguration:BDConfiguration)
    (implicit ec: ExecutionContext): Future[Option[Interno[DestinatarioGestion]]] = {

    internoDTO.tipoDestinatario match {
          case "GDOC_DEPENDENCIA" =>
            queryWithDependencia(internoDTO, dbConfiguration)

          case "GDOC_PERSONA_NATURAL" =>
            queryWithPersonaNatural(internoDTO, dbConfiguration)

          case "GDOC_RUTA" =>
            queryWithRuta(internoDTO, dbConfiguration)
        }
  }

  private[interpreter] def existsDestinatarioInterno(tipoDestinatario:String, destinatarioId:Option[Long], dbConfiguration:BDConfiguration)
   (implicit ec: ExecutionContext): Future[Boolean] = {
    import dbConfiguration.profile.api._
    tipoDestinatario match {
      case "GDOC_DEPENDENCIA" =>
        dbConfiguration.db.run(dependenciaTableQuery.filter(_.id === destinatarioId).exists.result)

      case "GDOC_PERSONA_NATURAL" =>
        dbConfiguration.db.run(personaNaturalTableQuery.filter(_.id === destinatarioId).exists.result)

      case "GDOC_RUTA" =>
        dbConfiguration.db.run(rutaTableQuery.filter(_.id === destinatarioId).exists.result)
    }
  }

  private[interpreter] def queryWithDependencia(internoDto: InternoDTO, dbConfiguration:BDConfiguration)
   (implicit ec: ExecutionContext): Future[Option[Interno[DestinatarioGestion]]] = {
    import dbConfiguration.profile.api._
    val query = for {
      c <- categoriaTableQuery
      d <- dependenciaTableQuery
      p <- personaNaturalTableQuery
      i <- internoTableQuery
      if c.id === i.categoriaId && d.id === i.destinatarioId && p.id === i.remitenteId && i.id === internoDto.id
    } yield (i.id, c, p, d, i.comentario)
    dbConfiguration.db.run(query.result.headOption)
      .map(
        _.map(
          InternoTupla =>
            Interno[DestinatarioGestion](
              id = InternoTupla._1,
              categoria = InternoTupla._2,
              remitente = InternoTupla._3,
              destinatario = InternoTupla._4,
              comentario = Some(InternoTupla._5)
            )
        )
      )
  }

  private[interpreter] def queryWithPersonaNatural(internoDto: InternoDTO, dbConfiguration:BDConfiguration)
    (implicit ec: ExecutionContext): Future[Option[Interno[DestinatarioGestion]]] = {
    import dbConfiguration.profile.api._
    val query = for {
      c <- categoriaTableQuery
      d <- personaNaturalTableQuery
      p <- personaNaturalTableQuery
      i <- internoTableQuery
      if c.id === i.categoriaId && d.id === i.destinatarioId && p.id === i.remitenteId && i.id === internoDto.id
    } yield (i.id, c, p, d, i.comentario)
    dbConfiguration.db.run(query.result.headOption)
      .map(
        _.map(
          InternoTupla =>
            Interno[DestinatarioGestion](
              id = InternoTupla._1,
              categoria = InternoTupla._2,
              remitente = InternoTupla._3,
              destinatario = InternoTupla._4,
              comentario = Some(InternoTupla._5)
            )
        )
      )
  }

  private[interpreter] def queryWithRuta(internoDto: InternoDTO, dbConfiguration:BDConfiguration)
    (implicit ec: ExecutionContext): Future[Option[Interno[DestinatarioGestion]]] = {

    import dbConfiguration.profile.api._

    val query = for {
      c <- categoriaTableQuery
      d <- rutaTableQuery
      p <- personaNaturalTableQuery
      i <- internoTableQuery
      if c.id === i.categoriaId && d.id === i.destinatarioId && p.id === i.remitenteId && i.id === internoDto.id
    } yield (i.id, c, p, d, i.comentario)
    dbConfiguration.db.run(query.result.headOption)
      .map(
        _.map(
          InternoTupla =>
            Interno[DestinatarioGestion](
              id = InternoTupla._1,
              categoria = InternoTupla._2,
              remitente = InternoTupla._3,
              destinatario = InternoTupla._4,
              comentario = Some(InternoTupla._5)
            )
        )
      )
  }
}
