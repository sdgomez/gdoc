package gdoc.gestor_documentos.service

import scala.concurrent.{ExecutionContext, Future}

trait RadicacionService[A,B,C,D,E,F] {
  def radicarInterno(interno:A)(implicit ec:ExecutionContext):Future[Option[B]]
  def radicarExterno(externo:C)(implicit ec:ExecutionContext):Future[Option[D]]
  def radicarRecibido(recibido:E)(implicit ec:ExecutionContext):Future[Option[F]]
}

