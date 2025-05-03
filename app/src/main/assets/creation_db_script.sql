/*==============================================================*/
/* DBMS name:      SQLite 3                                     */
/* Created on:     2/5/2025 18:13:38                            */
/*==============================================================*/


drop table if exists ACCESOUSUARIO;

drop table if exists ARTICULO;

drop table if exists CATEGORIA;

drop table if exists CLIENTE;

drop table if exists DEPARTAMENTO;

drop table if exists DETALLECOMPRA;

drop table if exists DETALLEEXISTENCIA;

drop table if exists DETALLEVENTA;

drop table if exists DIRECCION;

drop table if exists DISTRITO;

drop table if exists DOCTOR;

drop table if exists FACTURACOMPRA;

drop table if exists FACTURAVENTA;

drop table if exists FORMAFARMACEUTICA;

drop table if exists MARCA;

drop table if exists MUNICIPIO;

drop table if exists OPCIONCRUD;

drop table if exists PROVEEDOR;

drop table if exists RECETA;

drop table if exists SUBCATEGORIA;

drop table if exists SUCURSALFARMACIA;

drop table if exists USUARIO;

drop table if exists VIAADMINISTRACION;

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO (
   IDUSUARIO text not null,
   NOMBREUSUARIO text not null,
   CLAVE text not null,
   constraint PK_USUARIO primary key (IDUSUARIO)
);

/*==============================================================*/
/* Table: OPCIONCRUD                                            */
/*==============================================================*/
create table OPCIONCRUD (
   IDOPCION text not null,
   DESOPCION text not null,
   NUMCRUD integer not null,
   constraint PK_OPCIONCRUD primary key (IDOPCION)
);

/*==============================================================*/
/* Table: ACCESOUSUARIO                                         */
/*==============================================================*/
create table ACCESOUSUARIO (
   IDUSUARIO text not null,
   IDOPCION text not null,
   constraint PK_ACCESOUSUARIO primary key (IDUSUARIO, IDOPCION),
   constraint FK_ACCESOUS_ACCESO_USUARIO foreign key (IDUSUARIO)
         references USUARIO (IDUSUARIO),
   constraint FK_ACCESOUS_OPCIONES_OPCIONCR foreign key (IDOPCION)
         references OPCIONCRUD (IDOPCION)
);

/*==============================================================*/
/* Table: MARCA                                                 */
/*==============================================================*/
create table MARCA (
   IDMARCA integer not null,
   NOMBREMARCA text not null,
   constraint PK_MARCA primary key (IDMARCA)
);

/*==============================================================*/
/* Table: CATEGORIA                                             */
/*==============================================================*/
create table CATEGORIA (
   IDCATEGORIA integer not null,
   NOMBRECATEGORIA text not null,
   constraint PK_CATEGORIA primary key (IDCATEGORIA)
);

/*==============================================================*/
/* Table: SUBCATEGORIA                                          */
/*==============================================================*/
create table SUBCATEGORIA (
   IDSUBCATEGORIA integer not null,
   IDCATEGORIA integer not null,
   NOMBRESUBCATEGORIA text not null,
   constraint PK_SUBCATEGORIA primary key (IDSUBCATEGORIA),
   constraint FK_SUBCATEG_TIENE_CATEGORI foreign key (IDCATEGORIA)
         references CATEGORIA (IDCATEGORIA)
);

/*==============================================================*/
/* Table: VIAADMINISTRACION                                     */
/*==============================================================*/
create table VIAADMINISTRACION (
   IDVIAADMINISTRACION integer not null,
   TIPOADMINISTRACION text not null,
   constraint PK_VIAADMINISTRACION primary key (IDVIAADMINISTRACION)
);

/*==============================================================*/
/* Table: FORMAFARMACEUTICA                                     */
/*==============================================================*/
create table FORMAFARMACEUTICA (
   IDFORMAFARMACEUTICA integer not null,
   TIPOFORMAFARMACEUTICA text not null,
   constraint PK_FORMAFARMACEUTICA primary key (IDFORMAFARMACEUTICA)
);

/*==============================================================*/
/* Table: DEPARTAMENTO                                          */
/*==============================================================*/
create table DEPARTAMENTO (
   IDDEPARTAMENTO integer not null,
   IDDISTRITO integer,
   NOMBREDEPARTAMENTO text not null,
   constraint PK_DEPARTAMENTO primary key (IDDEPARTAMENTO),
   constraint FK_DEPARTAM_RELATIONS_DISTRITO foreign key (IDDISTRITO)
         references DISTRITO (IDDISTRITO)
);

/*==============================================================*/
/* Table: MUNICIPIO                                             */
/*==============================================================*/
create table MUNICIPIO (
   IDMUNICIPIO integer not null,
   IDDEPARTAMENTO integer,
   DEP_IDDEPARTAMENTO integer not null,
   NOMBREMUNICIPIO text not null,
   constraint PK_MUNICIPIO primary key (IDMUNICIPIO),
   constraint FK_MUNICIPI_RELATIONS_DEPARTAM foreign key (IDDEPARTAMENTO)
         references DEPARTAMENTO (IDDEPARTAMENTO),
   constraint FK_MUNICIPI_FORMADO_P_DEPARTAM foreign key (DEP_IDDEPARTAMENTO)
         references DEPARTAMENTO (IDDEPARTAMENTO)
);

/*==============================================================*/
/* Table: DISTRITO                                              */
/*==============================================================*/
create table DISTRITO (
   IDDISTRITO integer not null,
   IDMUNICIPIO integer not null,
   NOMBREDISTRITO text not null,
   constraint PK_DISTRITO primary key (IDDISTRITO),
   constraint FK_DISTRITO_SE_DIVIDE_MUNICIPI foreign key (IDMUNICIPIO)
         references MUNICIPIO (IDMUNICIPIO)
);

/*==============================================================*/
/* Table: DIRECCION                                             */
/*==============================================================*/
create table DIRECCION (
   IDDIRECCION integer not null,
   IDDISTRITO integer not null,
   DIRECCIONEXACTA text not null,
   constraint PK_DIRECCION primary key (IDDIRECCION),
   constraint FK_DIRECCIO_UBICADA_E_DISTRITO foreign key (IDDISTRITO)
         references DISTRITO (IDDISTRITO)
);

/*==============================================================*/
/* Table: SUCURSALFARMACIA                                      */
/*==============================================================*/
create table SUCURSALFARMACIA (
   IDFARMACIA integer not null,
   IDDIRECCION integer not null,
   NOMBREFARMACIA text not null,
   constraint PK_SUCURSALFARMACIA primary key (IDFARMACIA),
   constraint FK_SUCURSAL_SE_UBICA__DIRECCIO foreign key (IDDIRECCION)
         references DIRECCION (IDDIRECCION)
);

/*==============================================================*/
/* Table: DETALLEEXISTENCIA                                     */
/*==============================================================*/
create table DETALLEEXISTENCIA (
   IDARTICULO integer not null,
   IDDETALLEEXISTENCIA integer not null,
   IDFARMACIA integer not null,
   CANTIDADEXISTENCIA integer,
   FECHADEVENCIMIENTO text,
   constraint PK_DETALLEEXISTENCIA primary key (IDARTICULO, IDDETALLEEXISTENCIA),
   constraint FK_DETALLEE_DETALLA_ARTICULO foreign key (IDARTICULO)
         references ARTICULO (IDARTICULO),
   constraint FK_DETALLEE_TIENE_UN_SUCURSAL foreign key (IDFARMACIA)
         references SUCURSALFARMACIA (IDFARMACIA)
);

/*==============================================================*/
/* Table: ARTICULO                                              */
/*==============================================================*/
create table ARTICULO (
   IDARTICULO integer not null,
   IDMARCA integer not null,
   IDVIAADMINISTRACION integer not null,
   IDSUBCATEGORIA integer not null,
   DET_IDARTICULO integer,
   IDDETALLEEXISTENCIA integer,
   MAR_IDMARCA integer,
   IDFORMAFARMACEUTICA integer not null,
   NOMBREARTICULO text not null,
   DESCRIPCIONARTICULO text not null,
   RESTRINGIDOARTICULO integer not null,
   PRECIOARTICULO numeric,
   constraint PK_ARTICULO primary key (IDARTICULO),
   constraint FK_ARTICULO_RELATIONS_MARCA foreign key (MAR_IDMARCA)
         references MARCA (IDMARCA),
   constraint FK_ARTICULO_ASOCIADO__MARCA foreign key (IDMARCA)
         references MARCA (IDMARCA),
   constraint FK_ARTICULO_DADO_POR_SUBCATEG foreign key (IDSUBCATEGORIA)
         references SUBCATEGORIA (IDSUBCATEGORIA),
   constraint FK_ARTICULO_ADMINISTR_VIAADMIN foreign key (IDVIAADMINISTRACION)
         references VIAADMINISTRACION (IDVIAADMINISTRACION),
   constraint FK_ARTICULO_PRESENTAD_FORMAFAR foreign key (IDFORMAFARMACEUTICA)
         references FORMAFARMACEUTICA (IDFORMAFARMACEUTICA),
   constraint FK_ARTICULO_DETALLA2_DETALLEE foreign key (DET_IDARTICULO, IDDETALLEEXISTENCIA)
         references DETALLEEXISTENCIA (IDARTICULO, IDDETALLEEXISTENCIA)
);

/*==============================================================*/
/* Table: CLIENTE                                               */
/*==============================================================*/
create table CLIENTE (
   IDCLIENTE integer not null,
   NOMBRECLIENTE text not null,
   TELEFONOCLIENTE text not null,
   CORREOCLIENTE text not null,
   constraint PK_CLIENTE primary key (IDCLIENTE)
);

/*==============================================================*/
/* Table: PROVEEDOR                                             */
/*==============================================================*/
create table PROVEEDOR (
   IDPROVEEDOR integer not null,
   NOMBREPROVEEDOR text not null,
   TELEFONOPROVEEDOR text not null,
   DIRECCIONPROVEEDOR text not null,
   RUBROPROVEEDOR text not null,
   NUMREGPROVEEDOR text not null,
   NIT text not null,
   GIROPROVEEDOR text not null,
   constraint PK_PROVEEDOR primary key (IDPROVEEDOR)
);

/*==============================================================*/
/* Table: FACTURACOMPRA                                         */
/*==============================================================*/
create table FACTURACOMPRA (
   IDCOMPRA integer not null,
   IDFARMACIA integer not null,
   IDPROVEEDOR integer not null,
   FECHACOMPRA text not null,
   TOTALCOMPRA numeric not null,
   constraint PK_FACTURACOMPRA primary key (IDCOMPRA),
   constraint FK_FACTURAC_LISTADO_PROVEEDO foreign key (IDPROVEEDOR)
         references PROVEEDOR (IDPROVEEDOR),
   constraint FK_FACTURAC_ESTA_ASOC_SUCURSAL foreign key (IDFARMACIA)
         references SUCURSALFARMACIA (IDFARMACIA)
);

/*==============================================================*/
/* Table: DETALLECOMPRA                                         */
/*==============================================================*/
create table DETALLECOMPRA (
   IDCOMPRA integer not null,
   IDARTICULO integer not null,
   IDDETALLECOMPRA integer not null,
   FECHADECOMPRA text not null,
   PRECIOUNITARIOCOMPRA numeric not null,
   CANTIDADCOMPRA integer not null,
   TOTALDETALLECOMPRA numeric,
   constraint PK_DETALLECOMPRA primary key (IDCOMPRA, IDARTICULO, IDDETALLECOMPRA),
   constraint FK_DETALLEC_ESPECIFIC_FACTURAC foreign key (IDCOMPRA)
         references FACTURACOMPRA (IDCOMPRA),
   constraint FK_DETALLEC_TIENE_ASO_ARTICULO foreign key (IDARTICULO)
         references ARTICULO (IDARTICULO)
);

/*==============================================================*/
/* Table: FACTURAVENTA                                          */
/*==============================================================*/
create table FACTURAVENTA (
   IDCLIENTE integer not null,
   IDVENTA integer not null,
   IDFARMACIA integer not null,
   FECHAVENTA text not null,
   TOTALVENTA numeric not null,
   constraint PK_FACTURAVENTA primary key (IDCLIENTE, IDVENTA),
   constraint FK_FACTURAV_RELACIONA_CLIENTE foreign key (IDCLIENTE)
         references CLIENTE (IDCLIENTE),
   constraint FK_FACTURAV_DADA_EN_SUCURSAL foreign key (IDFARMACIA)
         references SUCURSALFARMACIA (IDFARMACIA)
);

/*==============================================================*/
/* Table: DETALLEVENTA                                          */
/*==============================================================*/
create table DETALLEVENTA (
   IDCLIENTE integer not null,
   IDVENTA integer not null,
   IDARTICULO integer not null,
   IDVENTADETALLE integer not null,
   CANTIDADVENTA integer not null,
   PRECIOUNITARIOVENTA numeric,
   FECHADEVENTA text,
   TOTALDETALLEVENTA numeric,
   constraint PK_DETALLEVENTA primary key (IDCLIENTE, IDVENTA, IDARTICULO, IDVENTADETALLE),
   constraint FK_DETALLEV_ESTA_ESPE_FACTURAV foreign key (IDCLIENTE, IDVENTA)
         references FACTURAVENTA (IDCLIENTE, IDVENTA),
   constraint FK_DETALLEV_ASOCIADO__ARTICULO foreign key (IDARTICULO)
         references ARTICULO (IDARTICULO)
);

/*==============================================================*/
/* Table: DOCTOR                                                */
/*==============================================================*/
create table DOCTOR (
   IDDOCTOR integer not null,
   NOMBREDOCTOR text not null,
   ESPECIALIDADDOCTOR text not null,
   JVPM text not null,
   constraint PK_DOCTOR primary key (IDDOCTOR)
);

/*==============================================================*/
/* Table: RECETA                                                */
/*==============================================================*/
create table RECETA (
   IDDOCTOR integer not null,
   IDCLIENTE integer not null,
   IDRECETA integer not null,
   DOC_IDDOCTOR integer,
   FECHAEXPEDIDA text not null,
   constraint PK_RECETA primary key (IDDOCTOR, IDCLIENTE, IDRECETA),
   constraint FK_RECETA_RELATIONS_DOCTOR foreign key (DOC_IDDOCTOR)
         references DOCTOR (IDDOCTOR),
   constraint FK_RECETA_RECETA_DOCTOR foreign key (IDDOCTOR)
         references DOCTOR (IDDOCTOR),
   constraint FK_RECETA_ASOCIADO_CLIENTE foreign key (IDCLIENTE)
         references CLIENTE (IDCLIENTE)
);

