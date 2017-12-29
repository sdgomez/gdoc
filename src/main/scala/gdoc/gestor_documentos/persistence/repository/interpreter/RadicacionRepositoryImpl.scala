package gdoc.gestor_documentos.persistence.repository.interpreter

import cats.data.Reader
import gdoc.gestor_documentos.model.exception.{GdocError, NoExisteDestinatario, NoExisteRemitente}
import gdoc.gestor_documentos.model.{BDConfiguration, _}
import gdoc.gestor_documentos.persistence.mapping.ExternoTable._
import gdoc.gestor_documentos.persistence.mapping.InternoTable._
import gdoc.gestor_documentos.persistence.mapping.RecibidoTable._
import gdoc.gestor_documentos.persistence.repository.RadicacionRepository
import gdoc.gestor_documentos.persistence.repository.interpreter.helpers.{RadicacionExternoHelper, RadicacionInternoHelper, RadicacionRecibidoHelper}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

trait RadicacionRepositoryImpl
  extends RadicacionRepository[BDConfiguration, InternoDTO, ExternoDTO, RecibidoDTO, Documento]
    with RadicacionInternoHelper with RadicacionExternoHelper with RadicacionRecibidoHelper{

  override def radicarInterno(interno: InternoDTO)(implicit ec: ExecutionContext):
    Reader[BDConfiguration, Future[Option[Documento]]] = Reader{
    dbConfiguration =>
      import dbConfiguration.profile.api._

      val internoRespuesta: Future[Option[Interno[DestinatarioGestion]]] =  for {
        existsDestinatario <- existsDestinatarioInterno(interno.tipoDestinatario, interno.destinatarioId, dbConfiguration)
        if existsDestinatario
        internoDTO <- dbConfiguration.db.run((internoTableQuery returning internoTableQuery.map(_.id)
          into ((in, newId) => in.copy(id = newId))
          ) += interno)
        i <- getInterno(internoDTO, dbConfiguration)
      } yield i
      internoRespuesta.recover{
          case NoExisteDestinatario => setError("El destinatario no existe en la base de datos")
          case NoExisteRemitente    => setError("El remitente no existe en la base de datos")
          case ex:Exception         =>
            setError(s"Ha ocurrido un error al intentar registrar el documento. " +
              s"Descripcion tecnica => ${ex.getMessage} tipoExcepción => ${ex.getClass} ")
      }
  }

  override def radicarExterno(externo: ExternoDTO)(implicit ec: ExecutionContext):
    Reader[BDConfiguration, Future[Option[Documento]]] = Reader{
    dbConfiguration =>
      import dbConfiguration.profile.api._

      val externoRespuesta: Future[Option[Externo]] = for {
        existsDestinatario <- existsDestinatarioExterno(externo.tipoDestinatario, externo.destinatarioId, dbConfiguration)
        existsRemitente    <- existsRemitenteExterno(externo.tipoRemitente, externo.remitenteId, dbConfiguration)
        if existsDestinatario && existsRemitente
        futureExternoDTO <- dbConfiguration.db.run((externoTableQuery returning externoTableQuery.map(_.id)
          into ((in,newId) => in.copy(id=newId))
          ) += externo)
        externoR <- getExterno(futureExternoDTO, dbConfiguration)

      } yield externoR

      externoRespuesta.recover{
        case NoExisteDestinatario => setError("El destinatario no existe en la base de datos")
        case NoExisteRemitente    => setError("El remitente no existe en la base de datos")
        case ex:Exception         =>
          setError(s"Ha ocurrido un error al intentar registrar el documento. " +
            s"Descripcion tecnica => ${ex.getMessage} tipoExcepción => ${ex.getClass} ")
      }
  }

  override def radicarRecibido(recibido: RecibidoDTO)(implicit ec: ExecutionContext):
    Reader[BDConfiguration, Future[Option[Documento]]] = Reader{
    dbConfiguration =>
      import dbConfiguration.profile.api._

      val recibidoRespuesta: Future[Option[Recibido[DestinatarioGestion, RemitenteGestion]]] = for {
        existsDestinatario <- existsDestinatarioRecibido(recibido.tipoDestinatario, recibido.destinatarioId, dbConfiguration)
        existRemitente <- existsRemitenteRecibido(recibido.tipoRemitente, recibido.remitenteId, dbConfiguration)
        recibidoDTO <- dbConfiguration.db.run((recibidoTableQuery returning recibidoTableQuery.map(_.id)
          into ((in,newId) => in.copy(id=newId))
          ) += recibido)
        recibidoR <- getRecibido(recibidoDTO, dbConfiguration)
      } yield recibidoR

      recibidoRespuesta.recover{
        case NoExisteDestinatario => setError("El destinatario no existe en la base de datos")
        case NoExisteRemitente    => setError("El remitente no existe en la base de datos")
        case ex:Exception         =>
          setError(s"Ha ocurrido un error al intentar registrar el documento. " +
            s"Descripcion tecnica => ${ex.getMessage} tipoExcepción => ${ex.getClass}")
      }
  }

  def setError(error: String):Option[GdocError] = Some{
    GdocError(mensaje = error)
  }

}

object radicacionRepositoryImpl extends RadicacionRepositoryImpl
