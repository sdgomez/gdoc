package gdoc.gestor_documentos.app

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import gdoc.gestor_documentos.app.api.RadicacionRoute
import gdoc.gestor_documentos.configuration.ApplicationConf._

object ServerConf{
    implicit val system = ActorSystem("my-system")
    implicit val executionContext = system.dispatcher
}

object WebServer extends App with RadicacionRoute{
    import gdoc.gestor_documentos.app.ServerConf._

    implicit val materializer = ActorMaterializer()

    val bindingFuture = Http().bindAndHandle(route, server, port)

    bindingFuture.map { serverBinding =>
        println(s"RestApi bound to ${serverBinding.localAddress} ")
    }.onFailure {
        case ex: Exception =>
            println(ex, "Failed to bind to {}:{}!", server, port)
            system.terminate()
    }
}
