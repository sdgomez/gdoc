package gdoc.gestor_documentos.persistence.repository.interpreter

import cats.data.Reader
import gdoc.gestor_documentos.model.{BDConfiguration, _}
import gdoc.gestor_documentos.persistence.mapping.InternoTable._
import gdoc.gestor_documentos.persistence.mapping.ExternoTable._
import gdoc.gestor_documentos.persistence.mapping.RecibidoTable._
import gdoc.gestor_documentos.persistence.repository.RadicacionRepository
import gdoc.gestor_documentos.persistence.repository.interpreter.helpers.{RadicacionExternoHelper, RadicacionInternoHelper, RadicacionRecibidoHelper}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

trait RadicacionRepositoryImpl
  extends RadicacionRepository[BDConfiguration, InternoDTO, Interno[DestinatarioGestion], ExternoDTO, Externo, RecibidoDTO, Recibido[DestinatarioGestion, RemitenteGestion]]
    with RadicacionInternoHelper with RadicacionExternoHelper with RadicacionRecibidoHelper{

  override def radicarInterno(interno: InternoDTO)(implicit ec: ExecutionContext):
    Reader[BDConfiguration, Future[Option[Interno[DestinatarioGestion]]]] = Reader{
    dbConfiguration =>
      import dbConfiguration.profile.api._

      for {
        existsDestinatario <- existsDestinatarioInterno(interno.tipoDestinatario, interno.destinatarioId, dbConfiguration)
        if existsDestinatario
        internoDTO <- dbConfiguration.db.run((internoTableQuery returning internoTableQuery.map(_.id)
          into ((in, newId) => in.copy(id = newId))
          ) += interno)
        internoR <- getInterno(internoDTO, dbConfiguration)
      } yield internoR

  }

  override def radicarExterno(externo: ExternoDTO)(implicit ec: ExecutionContext):
    Reader[BDConfiguration, Future[Option[Externo]]] = Reader{
    dbConfiguration =>
      import dbConfiguration.profile.api._

      for {
        existsDestinatario <- existsDestinatarioExterno(externo.tipoDestinatario, externo.destinatarioId, dbConfiguration)
        existsRemitente    <- existsRemitenteExterno(externo.tipoRemitente, externo.remitenteId, dbConfiguration)
        if existsDestinatario && existsRemitente
        futureExternoDTO <- dbConfiguration.db.run((externoTableQuery returning externoTableQuery.map(_.id)
          into ((in,newId) => in.copy(id=newId))
          ) += externo)
        externoR <- getExterno(futureExternoDTO, dbConfiguration)

      } yield externoR
  }

  override def radicarRecibido(recibido: RecibidoDTO)(implicit ec: ExecutionContext):
    Reader[BDConfiguration, Future[Option[Recibido[DestinatarioGestion, RemitenteGestion]]]] = Reader{
    dbConfiguration =>
      import dbConfiguration.profile.api._

      for {
        existsDestinatario <- existsDestinatarioRecibido(recibido.tipoDestinatario, recibido.destinatarioId, dbConfiguration)
        existRemitente <- existsRemitenteRecibido(recibido.tipoRemitente, recibido.remitenteId, dbConfiguration)
        recibidoDTO <- dbConfiguration.db.run((recibidoTableQuery returning recibidoTableQuery.map(_.id)
          into ((in,newId) => in.copy(id=newId))
          ) += recibido)
        recibidoR <- getRecibido(recibidoDTO, dbConfiguration)
      } yield recibidoR

  }

}

object radicacionRepositoryImpl extends RadicacionRepositoryImpl
