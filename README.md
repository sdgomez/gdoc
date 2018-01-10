Modulo de [Radicación de documentos]:

Se pueden radicar 3 tipos de documentos:

* Internos
* Externos
* Recibidos

El destinatario de un documento INTERNO y de un RECIBIDO es un DestinatarioGestion

DestinatarioGestion: puede ser una dependencia o persona natural o una ruta
RemitenteGestion: puede ser una dependencia o persona natural


El remitente de un RECIBIDO es un remitente gestíon
El remitente de un INTERNO es una persona natural
El remitente y destinatario de un EXTERNO puede ser una persona natural o jurídica

* sbt 0.13.5
* Cross build against Scala 2.10.4, 2.11.2
* [ScalaTest](http://www.scalatest.org/)
* [ScalaCheck](http://www.scalacheck.org/)


Comandos:

sbt:
* "reStart" para iniciar la aplicación

* "test" para correr las pruebas

* Para tirar las pruebas unitarias con el análisis de coberetura:
"clean coverage test"

* Para tirar las pruebas de integración con el análisis de coberetura:
sbt clean coverage it:test

* Para generar el reporte html de la cobertura, con anterioridad debe haber
ejecutado los comandos anteriores:
sbt coverageReport