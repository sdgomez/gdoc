package util
import org.scalatest.{BeforeAndAfterAll, FlatSpec}

import scala.concurrent.Await
import scala.concurrent.duration.Duration
trait DBTest extends FlatSpec with BeforeAndAfterAll{
  /*override def afterAll(): Unit = {

  }

  def createTable() = {
    val setup = BDIO.seq()
  }*/
}
object DataBaseProviderTest{

  import gdoc.gestor_documentos.persistence.mapping.CategoriaTable._
  import gdoc.gestor_documentos.persistence.mapping.DependenciaTable._
  import gdoc.gestor_documentos.persistence.mapping.ExternoTable._
  import gdoc.gestor_documentos.persistence.mapping.InternoTable._
  import gdoc.gestor_documentos.persistence.mapping.PersonaJuridicaTable._
  import gdoc.gestor_documentos.persistence.mapping.PersonaNaturalTable._
  import gdoc.gestor_documentos.persistence.mapping.RecibidoTable._
  import gdoc.gestor_documentos.persistence.mapping.RutaTable._
  import slick.jdbc.H2Profile.api._

  private val db = Database.forConfig("gdoctest")
  /*private val profile = H2Profile
  val dataBaseConfiguration: BDConfiguration = BDConfiguration(profile, db)*/

  def createTable() = {
    val setup = DBIO.seq(
      (
        rutaTableQuery.schema
        ++ dependenciaTableQuery.schema
        ++ personaJuridicaTableQuery.schema
        ++ personaNaturalTableQuery.schema
        ++ categoriaTableQuery.schema
        ++ internoTableQuery.schema
        ++ externoTableQuery.schema
        ++ recibidoTableQuery.schema
      )
        .create
    )
    Await.result(db.run(setup), Duration.Inf)
  }

  def truncateTable(): Unit = {
    val setup = DBIO.seq(
      (
        rutaTableQuery.schema
          ++ dependenciaTableQuery.schema
          ++ personaJuridicaTableQuery.schema
          ++ personaNaturalTableQuery.schema
          ++ categoriaTableQuery.schema
          ++ internoTableQuery.schema
          ++ externoTableQuery.schema
          ++ recibidoTableQuery.schema
        )
        .truncate
    )
    Await.result(db.run(setup), Duration.Inf)
  }

}
