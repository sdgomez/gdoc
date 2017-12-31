package gdoc.gestor_documentos.model.exception

import gdoc.gestor_documentos.model.Documento

case object NoExisteDestinatario extends Exception
case object NoExisteRemitente extends Exception

case class GdocError(codigo:Long = 0, mensaje:String, mensajeTecnico:String) extends Documento
