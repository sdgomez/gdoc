package gdoc.gestor_documentos.app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import gdoc.gestor_documentos.app.api.RadicacionRoute
import gdoc.gestor_documentos.configuration.ApplicationConf._

import scala.io.StdIn

object ServerConf{
    implicit val system = ActorSystem("my-system")
    implicit val executionContext = system.dispatcher
}

object WebServer extends App with RadicacionRoute{
    import gdoc.gestor_documentos.app.ServerConf._

    implicit val materializer = ActorMaterializer()

    val bindingFuture = Http().bindAndHandle(route, server, port)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
}
