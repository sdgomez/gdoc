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