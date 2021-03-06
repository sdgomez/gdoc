CREATE TABLE "GDOC_DEPENDENCIA" (
        "ID" serial,
        "CODIGO" varchar(20),
        "DESCRIPCION" varchar(150),
        PRIMARY KEY("ID")
);

CREATE TABLE "GDOC_PERSONA_NATURAL" (
        "ID" serial,
        "TIPO_IDENTIFICACION" varchar(40),
        "IDENTIFICACION" varchar(12),
        "NOMBRES" varchar(100),
        PRIMARY KEY("ID")    
);

CREATE TABLE "GDOC_PERSONA_JURIDICA" (
        "ID" serial,
        "NIT" varchar(20),
        "NOMBRE" varchar(150),
        PRIMARY KEY("ID")    
);

CREATE TABLE "GDOC_RUTA" (
        "ID" serial,
        "DESCRIPCION" varchar(150),
        PRIMARY KEY("ID")    
);

CREATE TABLE "GDOC_CATEGORIA" (
        "ID" serial,
        "NOMBRE" varchar(20),
        "DESCRIPCION" varchar(150),
        PRIMARY KEY("ID")    
);

CREATE TABLE "GDOC_INTERNO" (
        "ID" serial,
        "ID_CATEGORIA" integer,
        "ID_REMITENTE" integer,
        "ID_DESTINATARIO" integer,
        "COMENTARIO" varchar(300),
        PRIMARY KEY("ID")    
);

ALTER TABLE "GDOC_INTERNO" ADD CONSTRAINT "GDOC_INTERNO_FK1" FOREIGN KEY ("ID_CATEGORIA") 
REFERENCES "GDOC_CATEGORIA" ("ID") MATCH FULL;

ALTER TABLE "GDOC_INTERNO" ADD CONSTRAINT "GDOC_INTERNO_FK2" FOREIGN KEY ("ID_REMITENTE") 
REFERENCES "GDOC_PERSONA_NATURAL" ("ID") MATCH FULL;

CREATE TABLE "GDOC_EXTERNO" (
        "ID" serial,
        "ID_CATEGORIA" integer,
        "ID_REMITENTE" integer,
        "ID_DESTINATARIO" integer,
        "COMENTARIO" varchar(300),
        PRIMARY KEY("ID")    
);

ALTER TABLE "GDOC_EXTERNO" ADD CONSTRAINT "GDOC_EXTERNO_FK1" FOREIGN KEY ("ID_CATEGORIA") 
REFERENCES "GDOC_CATEGORIA" ("ID") MATCH FULL;

CREATE TABLE "GDOC_RECIBIDO" (
        "ID" serial,
        "ID_CATEGORIA" integer,
        "ID_REMITENTE" integer,
        "ID_DESTINATARIO" integer,
        "COMENTARIO" varchar(300),
        PRIMARY KEY("ID")    
);

ALTER TABLE "GDOC_RECIBIDO" ADD CONSTRAINT "GDOC_RECIBIDO_FK1" FOREIGN KEY ("ID_CATEGORIA") 
REFERENCES "GDOC_CATEGORIA" ("ID") MATCH FULL;

INSERT INTO "GDOC_CATEGORIA"(
            "NOMBRE", "DESCRIPCION")
    VALUES ('MEMORANDO', 'Documento interno para comunicar en forma breve asuntos de carácter administrativo a personas');

    INSERT INTO public."GDOC_CATEGORIA"(
             "NOMBRE", "DESCRIPCION")
    VALUES ('PQR', 'Peticiones, Quejas o recursos');

    INSERT INTO public."GDOC_CATEGORIA"(
             "NOMBRE", "DESCRIPCION")
    VALUES ('Notificacion', 'Informacion que se brinda a persona o entidad interna o externa');

    INSERT INTO public."GDOC_DEPENDENCIA"(
            "CODIGO", "DESCRIPCION")
    VALUES ('Recursos Humanos', 'Encargada de la gestión del personal');

INSERT INTO public."GDOC_DEPENDENCIA"(
            "CODIGO", "DESCRIPCION")
    VALUES ('Administracion', 'Encargada de la toma de desiciones');

INSERT INTO public."GDOC_PERSONA_JURIDICA"(
            "NIT", "NOMBRE")
    VALUES ('12345678', 'GDOC S.A');

INSERT INTO public."GDOC_PERSONA_NATURAL"(
            "TIPO_IDENTIFICACION", "IDENTIFICACION", "NOMBRES")
    VALUES ('CC', '1234567890', 'PEPITO PEREZ');

INSERT INTO public."GDOC_RUTA"(
            "DESCRIPCION")
    VALUES ('Atención PQR');



ALTER TABLE "GDOC_INTERNO" ADD COLUMN "TIPO_DESTINATARIO" varchar(30);

ALTER TABLE "GDOC_EXTERNO" ADD COLUMN "TIPO_DESTINATARIO" varchar(30);

ALTER TABLE "GDOC_RECIBIDO" ADD COLUMN "TIPO_DESTINATARIO" varchar(30);

ALTER TABLE "GDOC_EXTERNO" ADD COLUMN "TIPO_REMITENTE" varchar(30);

ALTER TABLE "GDOC_RECIBIDO" ADD COLUMN "TIPO_REMITENTE" varchar(30);

INSERT INTO public."GDOC_PERSONA_NATURAL"(
            "TIPO_IDENTIFICACION", "IDENTIFICACION", "NOMBRES")
    VALUES ('C.C', '1068904805', 'PEPITO PEREZ');


{
    "id": 1,
    "categoria": {
        "id": 1,
        "nombre": "MEMORANDO",
        "descripcion": "Documento interno para comunicar en forma breve asuntos de carácter administrativo a personas"
    },
    "remitente": {
        "id": 1,
        "tipoIdentificacion": "CC",
        "identificacion": "1234567890",
        "nombres": "PEPITO PEREZ"
    },
    "destinatario": {
        "id": 1,
        "codigo": "Recursos Humanos",
        "descripcion": "Encargada de la gestión del personal"
    },
    "comentario": "memorando"
}