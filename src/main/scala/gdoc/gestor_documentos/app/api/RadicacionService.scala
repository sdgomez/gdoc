package gdoc.gestor_documentos.app.api

import gdoc.gestor_documentos.model._
import gdoc.gestor_documentos.service.interpreter.radicacionServiceImpl

import scala.concurrent.{ExecutionContext, Future}

trait RadicacionService {

  def radicarInterno(internoDTO: InternoDTO)
    (implicit ec:ExecutionContext):Future[Option[Interno[DestinatarioGestion]]] = {
    radicacionServiceImpl.radicarInterno(internoDTO)
  }

  def radicarExterno(externoDTO: ExternoDTO)
    (implicit ec:ExecutionContext):Future[Option[Externo]] = {
    radicacionServiceImpl.radicarExterno(externoDTO)
  }

  def radicarRecibido(recibidoDTO: RecibidoDTO)
    (implicit ec:ExecutionContext):Future[Option[Recibido[DestinatarioGestion, RemitenteGestion]]] = {
    radicacionServiceImpl.radicarRecibido(recibidoDTO)
  }

}
