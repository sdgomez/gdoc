package gdoc.gestor_documentos.app.api

import akka.http.scaladsl.server.Directives._
import gdoc.gestor_documentos.model.InternoDTO

trait RadicacionRoute {
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
      entity(as[InternoDTO]){
        internoDTO =>
          complete(
            "ok"
          )
      }

    }
  } ~ pathPrefix("radicarRecibido") {
    post {
      entity(as[InternoDTO]){
        internoDTO =>
          complete(
            "ok"
          )
      }

    }
  }
}
