package gdoc.gestor_documentos.persistence.repository.interpreter

import cats.data.Reader
import gdoc.gestor_documentos.model.{BDConfiguration, _}
import gdoc.gestor_documentos.persistence.mapping.CategoriaTable._
import gdoc.gestor_documentos.persistence.mapping.{DependenciaTable, PersonaNaturalTable}
import gdoc.gestor_documentos.persistence.mapping.DependenciaTable._
import gdoc.gestor_documentos.persistence.mapping.InternoTable._
import gdoc.gestor_documentos.persistence.mapping.PersonaNaturalTable._
import gdoc.gestor_documentos.persistence.mapping.RutaTable._
import gdoc.gestor_documentos.persistence.repository.RadicacionRepository
import slick.lifted.TableQuery

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

trait RadicacionRepositoryImpl
  extends RadicacionRepository[BDConfiguration, InternoDTO, Interno[DestinatarioGestion], ExternoDTO, Externo, RecibidoDTO, Recibido[DestinatarioGestion, RemitenteGestion]]{

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

 /* private def genericQuery[A<:MapaTabla]
    (t:TableQuery[A], internoDto: InternoDTO, dbConfiguration:BDConfiguration):
      Future[Option[Interno[DestinatarioGestion]]] = {
      var tq: TableQuery[_ >: PersonaNaturalMapa with RutaMapa with DependenciaMapa <: (PersonaNaturalTable.Table[_$1] with MapaTabla) forSome {type _$1 >: PersonaNatural with Ruta with Dependencia}] = if(t==1) dependenciaTableQuery else if(t==2) personaNaturalTableQuery else rutaTableQuery
        import dbConfiguration.profile.api._
        val query = for {
          c <- categoriaTableQuery
          d <- tq.shaped
          p <- personaNaturalTableQuery
          i <- internoTableQuery
          if c.id === i.categoriaId && d.id === i.destinatarioId && p.id === i.remitenteId && i.id === internoDto.id
        } yield (i.id, c, p, d, i.comentario)
        dbConfiguration.db.run(query.result.headOption)
          .map(
            _.map(
              InternoTupla =>
                Interno[DestinatarioGestion](
                  id = InternoTupla._1,
                  categoria = InternoTupla._2,
                  remitente = InternoTupla._3,
                  destinatario = InternoTupla._4,
                  comentario = Some(InternoTupla._5)
                )
            )
          )
  } */

  private def getInterno(futureInternoDTO: Future[InternoDTO], dbConfiguration:BDConfiguration): Future[Option[Interno[DestinatarioGestion]]] = {
    import dbConfiguration.profile.api._
    futureInternoDTO.flatMap{
      internoDto =>
        internoDto.tipoDestinatario match {
          case "GDOC_DEPENDENCIA" =>
            val query = for {
              c <- categoriaTableQuery
              d <- dependenciaTableQuery
              p <- personaNaturalTableQuery
              i <- internoTableQuery
              if c.id === i.categoriaId && d.id === i.destinatarioId && p.id === i.remitenteId && i.id === internoDto.id
            } yield (i.id, c, p, d, i.comentario)
            dbConfiguration.db.run(query.result.headOption)
            .map(
              _.map(
                InternoTupla =>
                  Interno[DestinatarioGestion](
                    id = InternoTupla._1,
                    categoria = InternoTupla._2,
                    remitente = InternoTupla._3,
                    destinatario = InternoTupla._4,
                    comentario = Some(InternoTupla._5)
                  )
              )
            )
            // genericQuery(dependenciaTableQuery, internoDto, dbConfiguration)

          case "GDOC_PERSONA_NATURAL" =>
            val query = for {
              c <- categoriaTableQuery
              d <- personaNaturalTableQuery
              p <- personaNaturalTableQuery
              i <- internoTableQuery
              if c.id === i.categoriaId && d.id === i.destinatarioId && p.id === i.remitenteId && i.id === internoDto.id
            } yield (i.id, c, p, d, i.comentario)
            dbConfiguration.db.run(query.result.headOption)
              .map(
                _.map(
                  InternoTupla =>
                    Interno[DestinatarioGestion](
                      id = InternoTupla._1,
                      categoria = InternoTupla._2,
                      remitente = InternoTupla._3,
                      destinatario = InternoTupla._4,
                      comentario = Some(InternoTupla._5))
                )
              )
            // genericQuery(personaNaturalTableQuery, internoDto, dbConfiguration)

          case "GDOC_RUTA" =>
            val query = for {
              c <- categoriaTableQuery
              d <- rutaTableQuery
              p <- personaNaturalTableQuery
              i <- internoTableQuery
              if c.id === i.categoriaId && d.id === i.destinatarioId && p.id === i.remitenteId && i.id === internoDto.id
            } yield (i.id, c, p, d, i.comentario)
            dbConfiguration.db.run(query.result.headOption)
              .map(
                _.map(
                  InternoTupla =>
                    Interno[DestinatarioGestion](
                      id = InternoTupla._1,
                      categoria = InternoTupla._2,
                      remitente = InternoTupla._3,
                      destinatario = InternoTupla._4,
                      comentario = Some(InternoTupla._5))
                )
              )
            // genericQuery(rutaTableQuery, internoDto, dbConfiguration)

        }
    }
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
