package gdoc.gestor_documentos.persistence.repository

import cats.data.Reader

import scala.concurrent.{ExecutionContext, Future}

trait RadicacionRepository [A,B,C, D, E, F, G] extends Repository{
  def radicarInterno(interno:B)(implicit ec: ExecutionContext):Reader[A, Future[Option[C]]]
  def radicarExterno(externo:D)(implicit ec: ExecutionContext):Reader[A, Future[Option[E]]]
  def radicarRecibido(recibido:F)(implicit ec: ExecutionContext):Reader[A, Future[Option[G]]]
}
