package gdoc.gestor_documentos.persistence.repository.interpreter.helpers

import gdoc.gestor_documentos.model._
import gdoc.gestor_documentos.model.exception.{NoExisteDestinatario, NoExisteRemitente}
import gdoc.gestor_documentos.persistence.mapping.CategoriaTable.categoriaTableQuery
import gdoc.gestor_documentos.persistence.mapping.DependenciaTable._
import gdoc.gestor_documentos.persistence.mapping.PersonaNaturalTable.personaNaturalTableQuery
import gdoc.gestor_documentos.persistence.mapping.RecibidoTable._
import gdoc.gestor_documentos.persistence.mapping.RutaTable._
import gdoc.gestor_documentos.configuration.ApplicationConf._

import scala.concurrent.{ExecutionContext, Future}

trait RadicacionRecibidoHelper {

  private[interpreter] def getRecibido(recibidoDTO: RecibidoDTO, dbConfiguration:BDConfiguration)
    (implicit ec: ExecutionContext): Future[Option[Recibido[DestinatarioGestion, RemitenteGestion]]] = {
    recibidoDTO.tipoDestinatario match {
          case dependenciaType =>
            recibidoDTO.tipoRemitente match {
              case personaNaturalType =>  getRcWithDestDpRemPn(recibidoDTO, dbConfiguration)
              case dependenciaType     =>  getRcWithDestDpRemDp(recibidoDTO, dbConfiguration)
            }

          case personaNaturalType =>
            recibidoDTO.tipoRemitente match {
              case personaNaturalType   => getRcWithDestPnRemPn(recibidoDTO, dbConfiguration)
              case dependenciaType       => getRcWithDestPnRemDp(recibidoDTO, dbConfiguration)
            }

          case rutaType =>
            recibidoDTO.tipoRemitente match {
              case personaNaturalType   => getRcWithDestRtRemPn(recibidoDTO, dbConfiguration)
              case dependenciaType       => getRcWithDestRtRemDp(recibidoDTO, dbConfiguration)
            }

        }
    }

  private[interpreter] def existsDestinatarioRecibido(
    tipoDestinatario:String, destinatarioId:Option[Long], dbConfiguration:BDConfiguration)
    (implicit ec: ExecutionContext): Future[Boolean] = {

    import dbConfiguration.profile.api._
    val futureExists: Future[Boolean] = tipoDestinatario match {
      case personaNaturalType =>
        dbConfiguration.db.run(personaNaturalTableQuery.filter(_.id === destinatarioId).exists.result)

      case rutaType =>
        dbConfiguration.db.run(rutaTableQuery.filter(_.id === destinatarioId).exists.result)

      case dependenciaType =>
        dbConfiguration.db.run(dependenciaTableQuery.filter(_.id === destinatarioId).exists.result)
    }
    futureExists.map(if(_) true else throw NoExisteDestinatario)
  }

  private[interpreter] def existsRemitenteRecibido(
   tipoRemitente:String, remitenteId:Option[Long], dbConfiguration:BDConfiguration)
   (implicit ec: ExecutionContext): Future[Boolean] = {

    import dbConfiguration.profile.api._
    val futureExists: Future[Boolean] = tipoRemitente match {
      case dependenciaType =>
        dbConfiguration.db.run(dependenciaTableQuery.filter(_.id === remitenteId).exists.result)

      case personaNaturalType =>
        dbConfiguration.db.run(personaNaturalTableQuery.filter(_.id === remitenteId).exists.result)
    }
    futureExists.map(if(_) true else throw NoExisteRemitente)
  }

  /*
  * Rc:   Recibido
  * Dp:   Dependencia
  * Pn:   Persona Natural
  * Dest: Destinatario
  * Rem:  Remitente
  * Obtener un documento recibido cuyo destinatario es una dependencia
  * y el remitente es una persona natural
   */
  private[interpreter] def getRcWithDestDpRemPn(RecibidoDTO: RecibidoDTO, dbConfiguration:BDConfiguration)
    (implicit ec: ExecutionContext): Future[Option[Recibido[DestinatarioGestion, RemitenteGestion]]] = {
    import dbConfiguration.profile.api._

    val query = for {
      c <- categoriaTableQuery
      d <- dependenciaTableQuery
      r <- personaNaturalTableQuery
      i <- recibidoTableQuery
      if c.id === i.categoriaId && d.id === i.destinatarioId && r.id === i.remitenteId && i.id === RecibidoDTO.id
    } yield (i.id, c, r, d, i.comentario)
    dbConfiguration.db.run(query.result.headOption)
      .map(
        _.map(
          InternoTupla =>
            Recibido(
              id = InternoTupla._1,
              categoria = InternoTupla._2,
              remitente = InternoTupla._3,
              destinatario = InternoTupla._4,
              comentario = Some(InternoTupla._5)
            )
        )
      )
  }

  /*
  * Rc:   Recibido
  * Dp:   Dependencia
  * Dest: Destinatario
  * Rem:  Remitente
  * Obtener un documento recibido cuyo destinatario es una dependencia
  * y el remitente es una dependencia
   */
  private[interpreter] def getRcWithDestDpRemDp(RecibidoDTO: RecibidoDTO, dbConfiguration:BDConfiguration)
                                               (implicit ec: ExecutionContext): Future[Option[Recibido[DestinatarioGestion, RemitenteGestion]]] = {
    import dbConfiguration.profile.api._

    val query = for {
      c <- categoriaTableQuery
      d <- dependenciaTableQuery
      r <- dependenciaTableQuery
      i <- recibidoTableQuery
      if c.id === i.categoriaId && d.id === i.destinatarioId && r.id === i.remitenteId && i.id === RecibidoDTO.id
    } yield (i.id, c, r, d, i.comentario)
    dbConfiguration.db.run(query.result.headOption)
      .map(
        _.map(
          InternoTupla =>
            Recibido(
              id = InternoTupla._1,
              categoria = InternoTupla._2,
              remitente = InternoTupla._3,
              destinatario = InternoTupla._4,
              comentario = Some(InternoTupla._5)
            )
        )
      )
  }

  /*
  * Rc:   Recibido
  * Pn:   Persona Natural
  * Dest: Destinatario
  * Rem:  Remitente
  * Obtener un documento recibido cuyo destinatario es una Persona Natural
  * y el remitente es una Persona Natural
   */
  private[interpreter] def getRcWithDestPnRemPn(RecibidoDTO: RecibidoDTO, dbConfiguration:BDConfiguration)
                                               (implicit ec: ExecutionContext): Future[Option[Recibido[DestinatarioGestion, RemitenteGestion]]] = {
    import dbConfiguration.profile.api._

    val query = for {
      c <- categoriaTableQuery
      d <- personaNaturalTableQuery
      r <- personaNaturalTableQuery
      i <- recibidoTableQuery
      if c.id === i.categoriaId && d.id === i.destinatarioId && r.id === i.remitenteId && i.id === RecibidoDTO.id
    } yield (i.id, c, r, d, i.comentario)
    dbConfiguration.db.run(query.result.headOption)
      .map(
        _.map(
          InternoTupla =>
            Recibido(
              id = InternoTupla._1,
              categoria = InternoTupla._2,
              remitente = InternoTupla._3,
              destinatario = InternoTupla._4,
              comentario = Some(InternoTupla._5)
            )
        )
      )
  }

  /*
  * Rc:   Recibido
  * Pn:   Persona Natural
  * Dp:   Dependencia
  * Dest: Destinatario
  * Rem:  Remitente
  * Obtener un documento recibido cuyo destinatario es una Persona Natural
  * y el remitente es una Dependencia
   */
  private[interpreter] def getRcWithDestPnRemDp(RecibidoDTO: RecibidoDTO, dbConfiguration:BDConfiguration)
                                               (implicit ec: ExecutionContext): Future[Option[Recibido[DestinatarioGestion, RemitenteGestion]]] = {
    import dbConfiguration.profile.api._

    val query = for {
      c <- categoriaTableQuery
      d <- personaNaturalTableQuery
      r <- dependenciaTableQuery
      i <- recibidoTableQuery
      if c.id === i.categoriaId && d.id === i.destinatarioId && r.id === i.remitenteId && i.id === RecibidoDTO.id
    } yield (i.id, c, r, d, i.comentario)
    dbConfiguration.db.run(query.result.headOption)
      .map(
        _.map(
          InternoTupla =>
            Recibido(
              id = InternoTupla._1,
              categoria = InternoTupla._2,
              remitente = InternoTupla._3,
              destinatario = InternoTupla._4,
              comentario = Some(InternoTupla._5)
            )
        )
      )
  }

  /*
  * Rc:   Recibido
  * Pn:   Persona Natural
  * Rt:   Ruta
  * Dest: Destinatario
  * Rem:  Remitente
  * Obtener un documento recibido cuyo destinatario es una Ruta
  * y el remitente es una Persona Natural
   */
  private[interpreter] def getRcWithDestRtRemPn(RecibidoDTO: RecibidoDTO, dbConfiguration:BDConfiguration)
                                               (implicit ec: ExecutionContext): Future[Option[Recibido[DestinatarioGestion, RemitenteGestion]]] = {
    import dbConfiguration.profile.api._

    val query = for {
      c <- categoriaTableQuery
      d <- rutaTableQuery
      r <- personaNaturalTableQuery
      i <- recibidoTableQuery
      if c.id === i.categoriaId && d.id === i.destinatarioId && r.id === i.remitenteId && i.id === RecibidoDTO.id
    } yield (i.id, c, r, d, i.comentario)
    dbConfiguration.db.run(query.result.headOption)
      .map(
        _.map(
          InternoTupla =>
            Recibido(
              id = InternoTupla._1,
              categoria = InternoTupla._2,
              remitente = InternoTupla._3,
              destinatario = InternoTupla._4,
              comentario = Some(InternoTupla._5)
            )
        )
      )
  }

  /*
  * Rc:   Recibido
  * Dp:   Dependencia
  * Rt:   Ruta
  * Dest: Destinatario
  * Rem:  Remitente
  * Obtener un documento recibido cuyo destinatario es una Ruta
  * y el remitente es una Persona Natural
   */
  private[interpreter] def getRcWithDestRtRemDp(RecibidoDTO: RecibidoDTO, dbConfiguration:BDConfiguration)
                                               (implicit ec: ExecutionContext): Future[Option[Recibido[DestinatarioGestion, RemitenteGestion]]] = {
    import dbConfiguration.profile.api._

    val query = for {
      c <- categoriaTableQuery
      d <- rutaTableQuery
      r <- dependenciaTableQuery
      i <- recibidoTableQuery
      if c.id === i.categoriaId && d.id === i.destinatarioId && r.id === i.remitenteId && i.id === RecibidoDTO.id
    } yield (i.id, c, r, d, i.comentario)
    dbConfiguration.db.run(query.result.headOption)
      .map(
        _.map(
          InternoTupla =>
            Recibido(
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
