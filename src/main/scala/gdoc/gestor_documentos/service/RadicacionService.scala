package gdoc.gestor_documentos.service

import scala.concurrent.{ExecutionContext, Future}

trait RadicacionService[A,B,C,D] {
  def radicarInterno(interno:A)(implicit ec:ExecutionContext):Future[Option[D]]
  def radicarExterno(externo:B)(implicit ec:ExecutionContext):Future[Option[D]]
  def radicarRecibido(recibido:C)(implicit ec:ExecutionContext):Future[Option[D]]
}

