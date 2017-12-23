package gdoc.gestor_documentos.repository

import gdoc.gestor_documentos.configuration.ApplicationConf._
import gdoc.gestor_documentos.model.Categoria
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object TestDB extends App{
  object CategoriaTable extends JdbcProfile {

    import api._

    class CategoriaMapa(tag: Tag) extends Table[Categoria](tag, "GDOC_CATEGORIA") {
      def id = column[Option[Long]]("ID", O.PrimaryKey)

      def nombre = column[String]("NOMBRE")

      def descripcion = column[String]("DESCRIPCION")

      def * = (id, nombre, descripcion) <> (Categoria.tupled, Categoria.unapply)
    }

    val categoria = TableQuery[CategoriaMapa]
  }

  import CategoriaTable._
  import slick.jdbc.PostgresProfile.api._

  val q = for (c <- categoria) yield c.nombre
  val a = q.result
  val f: Future[Seq[String]] = db.run(a)

  f.onSuccess { case s => println(s"Result: $s") }
  //#materialize
  Await.result(f, Duration.Inf)
}
