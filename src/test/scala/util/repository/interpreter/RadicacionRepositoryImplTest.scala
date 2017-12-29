package util.repository.interpreter

import gdoc.gestor_documentos.model.{Documento, ExternoDTO, InternoDTO, RecibidoDTO}
import org.scalatest.{Matchers, RecoverMethods}
import util.DBTest
import gdoc.gestor_documentos.persistence.repository.interpreter.radicacionRepositoryImpl

import scala.concurrent.ExecutionContext.Implicits.global

trait RadicacionRepositoryImplTest extends DBTest with Matchers with RecoverMethods{

  val internoDTO:InternoDTO = InternoDTO(
    id = Some(1),
    categoriaId = Some(1),
    remitenteId = Some(1),
    tipoDestinatario = "GDOC_DEPENDENCIA",
    destinatarioId = Some(1),
    comentario = "memo"
  )

  val externoDTO = ExternoDTO(
    id = Some(1),
    categoriaId = Some(1),
    tipoRemitente = "GDOC_PERSONA_JURIDICA",
    remitenteId = Some(1),
    tipoDestinatario = "GDOC_PERSONA_NATURAL",
    destinatarioId = Some(1),
    comentario = ""
  )

  val recibidoDTO = RecibidoDTO(
    id = Some(1),
    categoriaId = Some(1),
    tipoRemitente = "GDOC_DEPENDENCIA",
    remitenteId = Some(1),
    tipoDestinatario = "GDOC_RUTA",
    destinatarioId = Some(1),
    comentario = "PQR"
  )

  "RadicacionRepositoryImpl" should "Radicar un interno correctamente" in {

    whenReady(radicacionRepositoryImpl.radicarInterno(internoDTO).run(dataBaseConfiguration)) {
      res =>
        res shouldBe a [Documento]
    }
  }

  "RadicacionRepositoryImpl" should "Radicar un externo correctamente" in {

    whenReady(radicacionRepositoryImpl.radicarExterno(externoDTO).run(dataBaseConfiguration)) {
      res =>
        res shouldBe a [Documento]
    }
  }

  "RadicacionRepositoryImpl" should "Radicar un recibido correctamente" in {

    whenReady(radicacionRepositoryImpl.radicarRecibido(recibidoDTO).run(dataBaseConfiguration)) {
      res =>
        res shouldBe a [Documento]
    }
  }

}
