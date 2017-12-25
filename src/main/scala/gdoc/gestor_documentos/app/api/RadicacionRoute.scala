package gdoc.gestor_documentos.app.api

import akka.http.scaladsl.server.Directives._
import gdoc.gestor_documentos.app.api.helpers.RadicacionMarshallers
import gdoc.gestor_documentos.model.{ExternoDTO, InternoDTO, RecibidoDTO}

trait RadicacionRoute extends RadicacionMarshallers{
  val route =
    pathPrefix("radicarInterno") {
      post {
        entity(as[InternoDTO]){
          internoDTO =>
            complete(
              "ok"
            )
        }

      }
    } ~ pathPrefix("radicarExterno") {
    post {
      entity(as[ExternoDTO]){
        internoDTO =>
          complete(
            "ok"
          )
      }

    }
  } ~ pathPrefix("radicarRecibido") {
    post {
      entity(as[RecibidoDTO]){
        internoDTO =>
          complete(
            "ok"
          )
      }

    }
  }
}
