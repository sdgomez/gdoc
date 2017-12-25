package gdoc.gestor_documentos.service.interpreter

import gdoc.gestor_documentos.model._
import gdoc.gestor_documentos.persistence.repository.interpreter.radicacionRepositoryImpl
import gdoc.gestor_documentos.service.RadicacionService
import gdoc.gestor_documentos.util.DataBaseProvider._

import scala.concurrent.{ExecutionContext, Future}

trait RadicacionServiceImpl
  extends RadicacionService[InternoDTO, Interno[DestinatarioGestion], ExternoDTO, Externo, RecibidoDTO, Recibido[DestinatarioGestion, RemitenteGestion]]{

  override def radicarInterno(internoDTO: InternoDTO)
     (implicit ec:ExecutionContext):Future[Option[Interno[DestinatarioGestion]]] = {
    radicacionRepositoryImpl.radicarInterno(internoDTO).run(dataBaseConfiguration)
  }

  override def radicarExterno(externo: ExternoDTO)
   (implicit ec:ExecutionContext): Future[Option[Externo]] =
    radicacionRepositoryImpl.radicarExterno(externo).run(dataBaseConfiguration)

  override def radicarRecibido(recibido: RecibidoDTO)
    (implicit ec:ExecutionContext): Future[Option[Recibido[DestinatarioGestion, RemitenteGestion]]] =
    radicacionRepositoryImpl.radicarRecibido(recibido).run(dataBaseConfiguration)
}

object radicacionServiceImpl extends RadicacionServiceImpl

/*
* TODO
* pool de conexiones
* encriptar las contraseñas
* falta logback
* el execution context
* falta los servicios rest
* falta las pruebas unitarias
*
 */