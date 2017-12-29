package gdoc.gestor_documentos.persistence.repository

import cats.data.Reader

import scala.concurrent.{ExecutionContext, Future}

trait RadicacionRepository [A,B,C, D, E] extends Repository{
  def radicarInterno(interno:B)(implicit ec: ExecutionContext):Reader[A, Future[Option[E]]]
  def radicarExterno(externo:C)(implicit ec: ExecutionContext):Reader[A, Future[Option[E]]]
  def radicarRecibido(recibido:D)(implicit ec: ExecutionContext):Reader[A, Future[Option[E]]]
}
