package gdoc.gestor_documentos.persistence.repository.interpreter.helpers

import gdoc.gestor_documentos.model._
import gdoc.gestor_documentos.persistence.mapping.CategoriaTable.categoriaTableQuery
import gdoc.gestor_documentos.persistence.mapping.ExternoTable._
import gdoc.gestor_documentos.persistence.mapping.PersonaJuridicaTable._
import gdoc.gestor_documentos.persistence.mapping.PersonaNaturalTable.personaNaturalTableQuery

import scala.concurrent.{ExecutionContext, Future}

trait RadicacionExternoHelper {

  private[interpreter] def getExterno(externoDTO: ExternoDTO, dbConfiguration:BDConfiguration)
    (implicit ec: ExecutionContext): Future[Option[Externo]] = {

    /*futureExternoDTO.map{
      externoDTO => */
        externoDTO.tipoDestinatario match {
          case "GDOC_PERSONA_JURIDICA" =>
            externoDTO.tipoRemitente match {
              case "GDOC_PERSONA_NATURAL" => getExternoCaseDestJuridico(externoDTO, dbConfiguration)
              case "GDOC_PERSONA_JURIDICA" =>  getExternoCaseJuridico(externoDTO, dbConfiguration)
            }

          case "GDOC_PERSONA_NATURAL" =>
            externoDTO.tipoRemitente match {
              case "GDOC_PERSONA_NATURAL"   => getExternoCaseNatural(externoDTO, dbConfiguration)
              case "GDOC_PERSONA_JURIDICA"  => getExternoCaseDestNatural(externoDTO, dbConfiguration)
            }

        }
    //}
  }

  private[interpreter] def existsDestinatarioExterno(
      tipoDestinatario:String, destinatarioId:Option[Long], dbConfiguration:BDConfiguration)
      (implicit ec: ExecutionContext): Future[Boolean] = {

        import dbConfiguration.profile.api._
        tipoDestinatario match {
          case "GDOC_PERSONA_NATURAL" =>
            dbConfiguration.db.run(personaNaturalTableQuery.filter(_.id === destinatarioId).exists.result)

          case "GDOC_PERSONA_JURIDICA" =>
            dbConfiguration.db.run(personaJuridicaTableQuery.filter(_.id === destinatarioId).exists.result)
        }
  }

  private[interpreter] def existsRemitenteExterno(
    tipoRemitente:String, remitenteId:Option[Long], dbConfiguration:BDConfiguration)
    (implicit ec: ExecutionContext): Future[Boolean] = {

    import dbConfiguration.profile.api._
    tipoRemitente match {
      case "GDOC_PERSONA_NATURAL" =>
        dbConfiguration.db.run(personaNaturalTableQuery.filter(_.id === remitenteId).exists.result)

      case "GDOC_PERSONA_JURIDICA" =>
        dbConfiguration.db.run(personaJuridicaTableQuery.filter(_.id === remitenteId).exists.result)
    }
  }

  private[interpreter] def getExternoCaseDestJuridico(externoDTO: ExternoDTO, dbConfiguration:BDConfiguration)
                                                    (implicit ec: ExecutionContext): Future[Option[Externo]] = {
    import dbConfiguration.profile.api._
    val query = for {
      c <- categoriaTableQuery
      d <- personaNaturalTableQuery
      r <- personaJuridicaTableQuery
      i <- externoTableQuery
      if c.id === i.categoriaId && d.id === i.destinatarioId && r.id === i.remitenteId && i.id === externoDTO.id
    } yield (i.id, c, r, d, i.comentario)
    dbConfiguration.db.run(query.result.headOption)
      .map(
        _.map(
          InternoTupla =>
            Externo(
              id = InternoTupla._1,
              categoria = InternoTupla._2,
              remitente = InternoTupla._3,
              destinatario = InternoTupla._4,
              comentario = Some(InternoTupla._5)
            )
        )
      )
  }

  private[interpreter] def getExternoCaseDestNatural(externoDTO: ExternoDTO, dbConfiguration:BDConfiguration)
                                                (implicit ec: ExecutionContext): Future[Option[Externo]] = {
    import dbConfiguration.profile.api._
    val query = for {
      c <- categoriaTableQuery
      d <- personaNaturalTableQuery
      r <- personaJuridicaTableQuery
      i <- externoTableQuery
      if c.id === i.categoriaId && d.id === i.destinatarioId && r.id === i.remitenteId && i.id === externoDTO.id
    } yield (i.id, c, r, d, i.comentario)
    dbConfiguration.db.run(query.result.headOption)
      .map(
        _.map(
          InternoTupla =>
            Externo(
              id = InternoTupla._1,
              categoria = InternoTupla._2,
              remitente = InternoTupla._3,
              destinatario = InternoTupla._4,
              comentario = Some(InternoTupla._5)
            )
        )
      )
  }

  private[interpreter] def getExternoCaseNatural(externoDTO: ExternoDTO, dbConfiguration:BDConfiguration)
   (implicit ec: ExecutionContext): Future[Option[Externo]] = {
    import dbConfiguration.profile.api._
    val query = for {
      c <- categoriaTableQuery
      d <- personaNaturalTableQuery
      r <- personaNaturalTableQuery
      i <- externoTableQuery
      if c.id === i.categoriaId && d.id === i.destinatarioId && r.id === i.remitenteId && i.id === externoDTO.id
    } yield (i.id, c, r, d, i.comentario)
    dbConfiguration.db.run(query.result.headOption)
      .map(
        _.map(
          InternoTupla =>
            Externo(
              id = InternoTupla._1,
              categoria = InternoTupla._2,
              remitente = InternoTupla._3,
              destinatario = InternoTupla._4,
              comentario = Some(InternoTupla._5)
            )
        )
      )
  }

  private[interpreter] def getExternoCaseJuridico(externoDTO: ExternoDTO, dbConfiguration:BDConfiguration)
                                                (implicit ec: ExecutionContext): Future[Option[Externo]] = {
    import dbConfiguration.profile.api._
    val query = for {
      c <- categoriaTableQuery
      d <- personaJuridicaTableQuery
      r <- personaJuridicaTableQuery
      i <- externoTableQuery
      if c.id === i.categoriaId && d.id === i.destinatarioId && r.id === i.remitenteId && i.id === externoDTO.id
    } yield (i.id, c, r, d, i.comentario)
    dbConfiguration.db.run(query.result.headOption)
      .map(
        _.map(
          InternoTupla =>
            Externo(
              id = InternoTupla._1,
              categoria = InternoTupla._2,
              remitente = InternoTupla._3,
              destinatario = InternoTupla._4,
              comentario = Some(InternoTupla._5)
            )
        )
      )
  }
}
