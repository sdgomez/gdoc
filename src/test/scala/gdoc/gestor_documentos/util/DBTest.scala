package gdoc.gestor_documentos.util
import gdoc.gestor_documentos.model._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, FlatSpec}
import slick.jdbc.H2Profile

import scala.concurrent.Await
import scala.concurrent.duration.Duration
trait DBTest extends FlatSpec with BeforeAndAfterAll with ScalaFutures{
  import slick.jdbc.H2Profile.api._
  private val db = Database.forConfig("gdoctest")
  private val profile = H2Profile
  val dataBaseConfiguration: BDConfiguration = BDConfiguration(profile, db)
  override def beforeAll():Unit = {
    DataBaseProviderTest
  }

  override def afterAll(): Unit = {
    //DataBaseProviderTest.truncateTable()
  }
}
object DataBaseProviderTest extends DBTest{

  import H2Profile.api._
  import gdoc.gestor_documentos.persistence.mapping.CategoriaTable._
  import gdoc.gestor_documentos.persistence.mapping.DependenciaTable._
  import gdoc.gestor_documentos.persistence.mapping.ExternoTable._
  import gdoc.gestor_documentos.persistence.mapping.InternoTable._
  import gdoc.gestor_documentos.persistence.mapping.PersonaJuridicaTable._
  import gdoc.gestor_documentos.persistence.mapping.PersonaNaturalTable._
  import gdoc.gestor_documentos.persistence.mapping.RecibidoTable._
  import gdoc.gestor_documentos.persistence.mapping.RutaTable._

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
      ).create

    )

    val setup2 = DBIO.seq(
      dependenciaTableQuery += Dependencia(Some(1), codigo = "002", descripcion = "gerencia"),
      personaNaturalTableQuery += PersonaNatural(Some(1), "Cedula", "123", "juanito"),
      personaJuridicaTableQuery += PersonaJuridica(Some(1), "5455", "asociados s.a"),
      categoriaTableQuery += Categoria(Some(1), "categoria1", "esta es una categ")
    )

    val s3 = setup andThen setup2

    Await.result(dataBaseConfiguration.db.run(s3), Duration.Inf)
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
    Await.result(dataBaseConfiguration.db.run(setup), Duration.Inf)
  }

  createTable()
}
