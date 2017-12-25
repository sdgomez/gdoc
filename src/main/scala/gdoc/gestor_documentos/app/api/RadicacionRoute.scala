package gdoc.gestor_documentos.app.api

import akka.http.scaladsl.model.ContentTypes.`application/json`
import akka.http.scaladsl.model.{HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Directives.{entity, _}
import gdoc.gestor_documentos.app.api.helpers.RadicacionMarshallers
import gdoc.gestor_documentos.model._
import play.api.libs.json.{JsValue, Json}

trait RadicacionRoute extends RadicacionMarshallers with RadicacionService{
  import akka.http.scaladsl.model.StatusCodes._

  val internoRoute = pathPrefix("radicarInterno") {
    post {
      entity(as[InternoDTO]){
        internoDTO =>
          complete{
            radicarInterno(internoDTO).map{
              optionInterno =>
                optionInterno.map{
                  interno =>
                    val jsValue: JsValue = Json.toJson(interno)
                    HttpResponse(OK, entity = HttpEntity(`application/json`, jsValue.toString))
                }

            }
          }
      }
    }
  }

  val externoRoute =
    pathPrefix("radicarExterno") {
      post {
        entity(as[ExternoDTO]){
          externoDTO =>
            complete{
              radicarExterno(externoDTO).map{
                optionEXterno =>
                  optionEXterno.map{
                    externo =>
                      val jsValue: JsValue = Json.toJson(externo)
                      HttpResponse(OK, entity = HttpEntity(`application/json`, jsValue.toString))
                  }

              }
            }
        }

      }
    }

    val recibidoRoute =
      pathPrefix("radicarRecibido") {
        post {
          entity(as[RecibidoDTO]){
            recibidoDTO =>
              complete{
                radicarRecibido(recibidoDTO).map{
                  optionRecibido =>
                    optionRecibido.map{
                      recibido =>
                        val jsValue: JsValue = Json.toJson(recibido)
                        HttpResponse(OK, entity = HttpEntity(`application/json`, jsValue.toString))
                    }
                }
              }
          }

        }
      }

  val route = internoRoute ~ externoRoute ~ recibidoRoute
}
