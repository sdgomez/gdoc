package gdoc.gestor_documentos.app.api

import akka.http.scaladsl.server.Directives.{entity, _}
import gdoc.gestor_documentos.model._

import scala.concurrent.ExecutionContext.Implicits.global

trait RadicacionRoute extends RadicacionService{
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
                    toHttpResponse(interno, OK)
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
                      toHttpResponse(externo, OK)
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
                        toHttpResponse(recibido, OK)
                    }
                }
              }
          }

        }
      }

  val route = internoRoute ~ externoRoute ~ recibidoRoute
}
