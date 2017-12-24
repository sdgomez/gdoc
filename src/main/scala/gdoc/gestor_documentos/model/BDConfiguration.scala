package gdoc.gestor_documentos.model

import slick.jdbc.{JdbcBackend, JdbcProfile}

final case class BDConfiguration(profile: JdbcProfile, db: JdbcBackend#DatabaseDef)

trait MapaTabla
