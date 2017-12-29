package gdoc.gestor_documentos.app.api

import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.{HttpEntity, HttpResponse, StatusCode}
import gdoc.gestor_documentos.app.api.helpers.RadicacionMarshallers
import gdoc.gestor_documentos.model._
import gdoc.gestor_documentos.service.interpreter.radicacionServiceImpl
import play.api.libs.json.{JsValue, Json}

import scala.concurrent.{ExecutionContext, Future}

trait RadicacionService extends RadicacionMarshallers{

  def radicarInterno(internoDTO: InternoDTO)
    (implicit ec:ExecutionContext):Future[Option[Documento]] = {
    radicacionServiceImpl.radicarInterno(internoDTO)
  }

  def radicarExterno(externoDTO: ExternoDTO)
    (implicit ec:ExecutionContext):Future[Option[Documento]] = {
    radicacionServiceImpl.radicarExterno(externoDTO)
  }

  def radicarRecibido(recibidoDTO: RecibidoDTO)
    (implicit ec:ExecutionContext):Future[Option[Documento]] = {
    radicacionServiceImpl.radicarRecibido(recibidoDTO)
  }

  def toHttpResponse(documento: Documento, httpCode:StatusCode):HttpResponse = {
    val jsValue: JsValue = Json.toJson(documento)
    HttpResponse(httpCode, entity = HttpEntity(`application/json`, jsValue.toString))
  }

}
