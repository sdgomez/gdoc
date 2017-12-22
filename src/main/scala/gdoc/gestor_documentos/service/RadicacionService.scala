package gdoc.gestor_documentos.service

import scala.concurrent.Future

trait RadicacionService[A,B,C] {
  def radicarInterno(interno:A):Future[A]
  def radicarExterno(externo:B):Future[B]
  def radicarRecibido(recibido:C):Future[C]
}

