package gdoc.gestor_documentos.service

import gdoc.gestor_documentos.model._

import scala.concurrent.Future

trait RadicacionServiceImpl extends RadicacionService[Interno[_], Externo, Recibido[_, _]]{
  override def radicarInterno(interno: Interno[_]):Interno[_] = {

    // falta versionarlo
    // falta REALIZAR LOS INSERT INICIALES EN LA BD
    // falta la parte del repository
    // falta los servicios rest
    Interno(
      None,
      Categoria(None, "Memorando", ""),
      PersonaNatural(None, "cedula", "123456789", "pepito perez"),
      Dependencia(None, "1234", "gerencia"),
      None
    )
  }

  override def radicarExterno(externo: Externo): Future[Externo] = ???

  override def radicarRecibido(recibido: Recibido[_, _]): Future[Recibido[_, _]] = ???
}
