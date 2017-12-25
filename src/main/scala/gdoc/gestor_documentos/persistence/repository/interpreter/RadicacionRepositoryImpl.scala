package gdoc.gestor_documentos.persistence.repository.interpreter

import cats.data.Reader
import gdoc.gestor_documentos.model.{BDConfiguration, _}
import gdoc.gestor_documentos.persistence.mapping.InternoTable._
import gdoc.gestor_documentos.persistence.repository.RadicacionRepository
import gdoc.gestor_documentos.persistence.repository.interpreter.helpers.RadicacionInternoHelper

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

trait RadicacionRepositoryImpl
  extends RadicacionRepository[BDConfiguration, InternoDTO, Interno[DestinatarioGestion], ExternoDTO, Externo, RecibidoDTO, Recibido[DestinatarioGestion, RemitenteGestion]]
    with RadicacionInternoHelper{

  override def radicarInterno(interno: InternoDTO)(implicit ec: ExecutionContext):
    Reader[BDConfiguration, Future[Option[Interno[DestinatarioGestion]]]] = Reader{
    dbConfiguration =>
      import dbConfiguration.profile.api._
      //validar que exista ese idDestinatario en la tabla que corresponde
      val futureInternoDTO: Future[InternoDTO] = dbConfiguration.db.run((internoTableQuery returning internoTableQuery.map(_.id)
        into ((in,newId) => in.copy(id=newId))
        ) += interno)

      getInterno(futureInternoDTO, dbConfiguration)
  }

  override def radicarExterno(externo: ExternoDTO)(implicit ec: ExecutionContext):
    Reader[BDConfiguration, Future[Externo]] = Reader{
    ???
  }

  override def radicarRecibido(recibido: RecibidoDTO)(implicit ec: ExecutionContext):
    Reader[BDConfiguration, Future[Recibido[DestinatarioGestion, RemitenteGestion]]] = Reader{
    ???
  }

}
