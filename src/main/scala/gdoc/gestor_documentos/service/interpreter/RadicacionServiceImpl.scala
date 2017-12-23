package gdoc.gestor_documentos.service.interpreter

import gdoc.gestor_documentos.model._
import gdoc.gestor_documentos.service.RadicacionService

import scala.concurrent.Future

trait RadicacionServiceImpl extends RadicacionService[Interno[DestinatarioGestion], Externo, Recibido[DestinatarioGestion, RemitenteGestion]]{
  override def radicarInterno(interno: Interno[DestinatarioGestion]):Future[Interno[DestinatarioGestion]] = {
    // pool de conexiones
    // encriptar las contraseñas
    // falta la parte del repository
    // falta los servicios rest
    // falta las pruebas unitarias
    Future.successful(Interno(
      None,
      Categoria(None, "Memorando", ""),
      PersonaNatural(None, "cedula", "123456789", "pepito perez"),
      PersonaNatural(None, "cedula", "123456789", "pepito perez"),
      None
    ))
  }

  override def radicarExterno(externo: Externo): Future[Externo] = ???

  override def radicarRecibido(recibido: Recibido[DestinatarioGestion, RemitenteGestion]): Future[Recibido[DestinatarioGestion, RemitenteGestion]] = ???
}
