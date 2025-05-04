/*==============================================================*/
/* DBMS name:      SQLITE3 FROM ORACLE Version 9i               */
/* Created on:     4/5/2025 09:46:25                            */
/*==============================================================*/


drop table ACCESOUSUARIO ;

drop table ARTICULO ;

drop table CATEGORIA ;

drop table CLIENTE ;

drop table DEPARTAMENTO ;

drop table DETALLECOMPRA ;

drop table DETALLEEXISTENCIA ;

drop table DETALLEVENTA ;

drop table DIRECCION ;

drop table DISTRITO ;

drop table DOCTOR ;

drop table FACTURACOMPRA ;

drop table FACTURAVENTA ;

drop table FORMAFARMACEUTICA ;

drop table MARCA ;

drop table MUNICIPIO ;

drop table OPCIONCRUD ;

drop table PROVEEDOR ;

drop table RECETA ;

drop table SUBCATEGORIA ;

drop table SUCURSALFARMACIA ;

drop table USUARIO ;

drop table VIAADMINISTRACION ;

/*==============================================================*/
/* Table: ACCESOUSUARIO                                         */
/*==============================================================*/
create table ACCESOUSUARIO  (
   IDUSUARIO            TEXT(2)                         not null,
   IDOPCION             TEXT(3)                         not null,
   constraint PK_ACCESOUSUARIO primary key (IDUSUARIO, IDOPCION)
);

/*==============================================================*/
/* Index: ACCESO_FK                                             */
/*==============================================================*/
create index ACCESO_FK on ACCESOUSUARIO (
   IDUSUARIO ASC
);

/*==============================================================*/
/* Index: OPCIONES_FK                                           */
/*==============================================================*/
create index OPCIONES_FK on ACCESOUSUARIO (
   IDOPCION ASC
);

/*==============================================================*/
/* Table: ARTICULO                                              */
/*==============================================================*/
create table ARTICULO  (
   IDARTICULO           INTEGER                         not null,
   IDMARCA              INTEGER                         not null,
   IDVIAADMINISTRACION  INTEGER                         not null,
   IDSUBCATEGORIA       INTEGER                         not null,
   DET_IDARTICULO       INTEGER,
   IDDETALLEEXISTENCIA  INTEGER,
   MAR_IDMARCA          INTEGER,
   IDFORMAFARMACEUTICA  INTEGER                         not null,
   NOMBREARTICULO       TEXT(50)                    not null,
   DESCRIPCIONARTICULO  TEXT                            not null,
   RESTRINGIDOARTICULO  INTEGER                        not null,
   PRECIOARTICULO       REAL,
   constraint PK_ARTICULO primary key (IDARTICULO)
);

/*==============================================================*/
/* Index: RELATIONSHIP_1_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_1_FK on ARTICULO (
   MAR_IDMARCA ASC
);

/*==============================================================*/
/* Index: ASOCIADO_UN_FK                                        */
/*==============================================================*/
create index ASOCIADO_UN_FK on ARTICULO (
   IDMARCA ASC
);

/*==============================================================*/
/* Index: DADO_POR_FK                                           */
/*==============================================================*/
create index DADO_POR_FK on ARTICULO (
   IDSUBCATEGORIA ASC
);

/*==============================================================*/
/* Index: ADMINISTRADO_POR_FK                                   */
/*==============================================================*/
create index ADMINISTRADO_POR_FK on ARTICULO (
   IDVIAADMINISTRACION ASC
);

/*==============================================================*/
/* Index: PRESENTADO_COMO_FK                                    */
/*==============================================================*/
create index PRESENTADO_COMO_FK on ARTICULO (
   IDFORMAFARMACEUTICA ASC
);

/*==============================================================*/
/* Index: DETALLA2_FK                                           */
/*==============================================================*/
create index DETALLA2_FK on ARTICULO (
   DET_IDARTICULO ASC,
   IDDETALLEEXISTENCIA ASC
);

/*==============================================================*/
/* Table: CATEGORIA                                             */
/*==============================================================*/
create table CATEGORIA  (
   IDCATEGORIA          INTEGER                         not null,
   NOMBRECATEGORIA      TEXT(50)                    not null,
   constraint PK_CATEGORIA primary key (IDCATEGORIA)
);

/*==============================================================*/
/* Table: CLIENTE                                               */
/*==============================================================*/
create table CLIENTE  (
   IDCLIENTE            INTEGER                         not null,
   NOMBRECLIENTE        TEXT(100)                   not null,
   TELEFONOCLIENTE      TEXT(8)                         not null,
   CORREOCLIENTE        TEXT(50)                    not null,
   constraint PK_CLIENTE primary key (IDCLIENTE)
);

/*==============================================================*/
/* Table: DEPARTAMENTO                                          */
/*==============================================================*/
create table DEPARTAMENTO  (
   IDDEPARTAMENTO       INTEGER                         not null,
   IDDISTRITO           INTEGER,
   NOMBREDEPARTAMENTO   TEXT(30)                    not null,
   constraint PK_DEPARTAMENTO primary key (IDDEPARTAMENTO)
);

/*==============================================================*/
/* Index: RELATIONSHIP_4_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_4_FK on DEPARTAMENTO (
   IDDISTRITO ASC
);

/*==============================================================*/
/* Table: DETALLECOMPRA                                         */
/*==============================================================*/
create table DETALLECOMPRA  (
   IDCOMPRA             INTEGER                         not null,
   IDARTICULO           INTEGER                         not null,
   IDDETALLECOMPRA      INTEGER                         not null,
   FECHADECOMPRA        TEXT                            not null,
   PRECIOUNITARIOCOMPRA REAL                    not null,
   CANTIDADCOMPRA       INTEGER                         not null,
   TOTALDETALLECOMPRA   REAL,
   constraint PK_DETALLECOMPRA primary key (IDCOMPRA, IDARTICULO, IDDETALLECOMPRA)
);

/*==============================================================*/
/* Index: ESPECIFICADO_FK                                       */
/*==============================================================*/
create index ESPECIFICADO_FK on DETALLECOMPRA (
   IDCOMPRA ASC
);

/*==============================================================*/
/* Index: TIENE_ASOCIADO_FK                                     */
/*==============================================================*/
create index TIENE_ASOCIADO_FK on DETALLECOMPRA (
   IDARTICULO ASC
);

/*==============================================================*/
/* Table: DETALLEEXISTENCIA                                     */
/*==============================================================*/
create table DETALLEEXISTENCIA  (
   IDARTICULO           INTEGER                         not null,
   IDDETALLEEXISTENCIA  INTEGER                         not null,
   IDFARMACIA           INTEGER                         not null,
   CANTIDADEXISTENCIA   INTEGER,
   FECHADEVENCIMIENTO   TEXT,
   constraint PK_DETALLEEXISTENCIA primary key (IDARTICULO, IDDETALLEEXISTENCIA)
);

-- comment on table DETALLEEXISTENCIA is

/*==============================================================*/
/* Index: DETALLA_FK                                            */
/*==============================================================*/
create index DETALLA_FK on DETALLEEXISTENCIA (
   IDARTICULO ASC
);

/*==============================================================*/
/* Index: TIENE_UN_FK                                           */
/*==============================================================*/
create index TIENE_UN_FK on DETALLEEXISTENCIA (
   IDFARMACIA ASC
);

/*==============================================================*/
/* Table: DETALLEVENTA                                          */
/*==============================================================*/
create table DETALLEVENTA  (
   IDCLIENTE            INTEGER                         not null,
   IDVENTA              INTEGER                         not null,
   IDARTICULO           INTEGER                         not null,
   IDVENTADETALLE       INTEGER                         not null,
   CANTIDADVENTA        INTEGER                         not null,
   PRECIOUNITARIOVENTA  REAL,
   FECHADEVENTA         TEXT,
   TOTALDETALLEVENTA    REAL,
   constraint PK_DETALLEVENTA primary key (IDCLIENTE, IDVENTA, IDARTICULO, IDVENTADETALLE)
);

/*==============================================================*/
/* Index: ESTA_ESPECIFICADO_FK                                  */
/*==============================================================*/
create index ESTA_ESPECIFICADO_FK on DETALLEVENTA (
   IDCLIENTE ASC,
   IDVENTA ASC
);

/*==============================================================*/
/* Index: ASOCIADO_A_VARIOS_FK                                  */
/*==============================================================*/
create index ASOCIADO_A_VARIOS_FK on DETALLEVENTA (
   IDARTICULO ASC
);

/*==============================================================*/
/* Table: DIRECCION                                             */
/*==============================================================*/
create table DIRECCION  (
   IDDIRECCION          INTEGER                         not null,
   IDDISTRITO           INTEGER                         not null,
   DIRECCIONEXACTA      TEXT(200)                   not null,
   constraint PK_DIRECCION primary key (IDDIRECCION)
);

/*==============================================================*/
/* Index: UBICADA_EN_FK                                         */
/*==============================================================*/
create index UBICADA_EN_FK on DIRECCION (
   IDDISTRITO ASC
);

/*==============================================================*/
/* Table: DISTRITO                                              */
/*==============================================================*/
create table DISTRITO  (
   IDDISTRITO           INTEGER                         not null,
   IDMUNICIPIO          INTEGER                         not null,
   NOMBREDISTRITO       TEXT(30)                    not null,
   constraint PK_DISTRITO primary key (IDDISTRITO)
);

/*==============================================================*/
/* Index: SE_DIVIDE_EN_FK                                       */
/*==============================================================*/
create index SE_DIVIDE_EN_FK on DISTRITO (
   IDMUNICIPIO ASC
);

/*==============================================================*/
/* Table: DOCTOR                                                */
/*==============================================================*/
create table DOCTOR  (
   IDDOCTOR             INTEGER                         not null,
   NOMBREDOCTOR         TEXT(100)                   not null,
   ESPECIALIDADDOCTOR   TEXT(100)                   not null,
   JVPM                 TEXT(20)                    not null,
   constraint PK_DOCTOR primary key (IDDOCTOR)
);

/*==============================================================*/
/* Table: FACTURACOMPRA                                         */
/*==============================================================*/
create table FACTURACOMPRA  (
   IDCOMPRA             INTEGER                         not null,
   IDFARMACIA           INTEGER                         not null,
   IDPROVEEDOR          INTEGER                         not null,
   FECHACOMPRA          TEXT                            not null,
   TOTALCOMPRA          REAL                    not null,
   constraint PK_FACTURACOMPRA primary key (IDCOMPRA)
);

/*==============================================================*/
/* Index: LISTADO_FK                                            */
/*==============================================================*/
create index LISTADO_FK on FACTURACOMPRA (
   IDPROVEEDOR ASC
);

/*==============================================================*/
/* Index: ESTA_ASOCIADA_FK                                      */
/*==============================================================*/
create index ESTA_ASOCIADA_FK on FACTURACOMPRA (
   IDFARMACIA ASC
);

/*==============================================================*/
/* Table: FACTURAVENTA                                          */
/*==============================================================*/
create table FACTURAVENTA  (
   IDCLIENTE            INTEGER                         not null,
   IDVENTA              INTEGER                         not null,
   IDFARMACIA           INTEGER                         not null,
   FECHAVENTA           TEXT                            not null,
   TOTALVENTA           REAL                    not null,
   constraint PK_FACTURAVENTA primary key (IDCLIENTE, IDVENTA)
);

/*==============================================================*/
/* Index: RELACIONADO_FK                                        */
/*==============================================================*/
create index RELACIONADO_FK on FACTURAVENTA (
   IDCLIENTE ASC
);

/*==============================================================*/
/* Index: DADA_EN_FK                                            */
/*==============================================================*/
create index DADA_EN_FK on FACTURAVENTA (
   IDFARMACIA ASC
);

/*==============================================================*/
/* Table: FORMAFARMACEUTICA                                     */
/*==============================================================*/
create table FORMAFARMACEUTICA  (
   IDFORMAFARMACEUTICA  INTEGER                         not null,
   TIPOFORMAFARMACEUTICA TEXT(50)                    not null,
   constraint PK_FORMAFARMACEUTICA primary key (IDFORMAFARMACEUTICA)
);

/*==============================================================*/
/* Table: MARCA                                                 */
/*==============================================================*/
create table MARCA  (
   IDMARCA              INTEGER                         not null,
   NOMBREMARCA          TEXT(50)                    not null,
   constraint PK_MARCA primary key (IDMARCA)
);

/*==============================================================*/
/* Table: MUNICIPIO                                             */
/*==============================================================*/
create table MUNICIPIO  (
   IDMUNICIPIO          INTEGER                         not null,
   IDDEPARTAMENTO       INTEGER,
   DEP_IDDEPARTAMENTO   INTEGER                         not null,
   NOMBREMUNICIPIO      TEXT(30)                    not null,
   constraint PK_MUNICIPIO primary key (IDMUNICIPIO)
);

-- comment on table MUNICIPIO is

/*==============================================================*/
/* Index: RELATIONSHIP_3_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_3_FK on MUNICIPIO (
   IDDEPARTAMENTO ASC
);

/*==============================================================*/
/* Index: FORMADO_POR_FK                                        */
/*==============================================================*/
create index FORMADO_POR_FK on MUNICIPIO (
   DEP_IDDEPARTAMENTO ASC
);

/*==============================================================*/
/* Table: OPCIONCRUD                                            */
/*==============================================================*/
create table OPCIONCRUD  (
   IDOPCION             TEXT(3)                         not null,
   DESOPCION            TEXT(30)                    not null,
   NUMCRUD              INTEGER                         not null,
   constraint PK_OPCIONCRUD primary key (IDOPCION)
);

/*==============================================================*/
/* Table: PROVEEDOR                                             */
/*==============================================================*/
create table PROVEEDOR  (
   IDPROVEEDOR          INTEGER                         not null,
   NOMBREPROVEEDOR      TEXT(30)                    not null,
   TELEFONOPROVEEDOR    TEXT(8)                         not null,
   DIRECCIONPROVEEDOR   TEXT(100)                   not null,
   RUBROPROVEEDOR       TEXT(50)                    not null,
   NUMREGPROVEEDOR      TEXT(10)                    not null,
   NIT                  TEXT(10)                    not null,
   GIROPROVEEDOR        TEXT(50)                    not null,
   constraint PK_PROVEEDOR primary key (IDPROVEEDOR)
);

/*==============================================================*/
/* Table: RECETA                                                */
/*==============================================================*/
create table RECETA  (
   IDDOCTOR             INTEGER                         not null,
   IDCLIENTE            INTEGER                         not null,
   IDRECETA             INTEGER                         not null,
   DOC_IDDOCTOR         INTEGER,
   FECHAEXPEDIDA        TEXT                            not null,
   constraint PK_RECETA primary key (IDDOCTOR, IDCLIENTE, IDRECETA)
);

/*==============================================================*/
/* Index: RELATIONSHIP_7_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_7_FK on RECETA (
   DOC_IDDOCTOR ASC
);

/*==============================================================*/
/* Index: RECETA_FK                                             */
/*==============================================================*/
create index RECETA_FK on RECETA (
   IDDOCTOR ASC
);

/*==============================================================*/
/* Index: ASOCIADO_FK                                           */
/*==============================================================*/
create index ASOCIADO_FK on RECETA (
   IDCLIENTE ASC
);

/*==============================================================*/
/* Table: SUBCATEGORIA                                          */
/*==============================================================*/
create table SUBCATEGORIA  (
   IDSUBCATEGORIA       INTEGER                         not null,
   IDCATEGORIA          INTEGER                         not null,
   NOMBRESUBCATEGORIA   TEXT(50)                    not null,
   constraint PK_SUBCATEGORIA primary key (IDSUBCATEGORIA)
);

/*==============================================================*/
/* Index: TIENE_FK                                              */
/*==============================================================*/
create index TIENE_FK on SUBCATEGORIA (
   IDCATEGORIA ASC
);

/*==============================================================*/
/* Table: SUCURSALFARMACIA                                      */
/*==============================================================*/
create table SUCURSALFARMACIA  (
   IDFARMACIA           INTEGER                         not null,
   IDDIRECCION          INTEGER                         not null,
   NOMBREFARMACIA       TEXT(100)                   not null,
   constraint PK_SUCURSALFARMACIA primary key (IDFARMACIA)
);

/*==============================================================*/
/* Index: SE_UBICA_EN_FK                                        */
/*==============================================================*/
create index SE_UBICA_EN_FK on SUCURSALFARMACIA (
   IDDIRECCION ASC
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO  (
   IDUSUARIO            TEXT(2)                         not null,
   NOMBREUSUARIO        TEXT(30)                    not null,
   CLAVE                TEXT(5)                         not null,
   constraint PK_USUARIO primary key (IDUSUARIO)
);

/*==============================================================*/
/* Table: VIAADMINISTRACION                                     */
/*==============================================================*/
create table VIAADMINISTRACION  (
   IDVIAADMINISTRACION  INTEGER                         not null,
   TIPOADMINISTRACION   TEXT(50)                    not null,
   constraint PK_VIAADMINISTRACION primary key (IDVIAADMINISTRACION)
);