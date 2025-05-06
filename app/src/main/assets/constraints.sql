-- alter table ACCESOUSUARIO
add constraint FK_ACCESOUS_ACCESO_USUARIO foreign key (IDUSUARIO)
  references USUARIO (IDUSUARIO);

-- alter table ACCESOUSUARIO
add constraint FK_ACCESOUS_OPCIONES_OPCIONCR foreign key (IDOPCION)
  references OPCIONCRUD (IDOPCION);

-- alter table ARTICULO
add constraint FK_ARTICULO_ADMINISTR_VIAADMIN foreign key (IDVIAADMINISTRACION)
  references VIAADMINISTRACION (IDVIAADMINISTRACION);

-- alter table ARTICULO
add constraint FK_ARTICULO_ASOCIADO__MARCA foreign key (IDMARCA)
  references MARCA (IDMARCA);

-- alter table ARTICULO
add constraint FK_ARTICULO_DADO_POR_SUBCATEG foreign key (IDSUBCATEGORIA)
  references SUBCATEGORIA (IDSUBCATEGORIA);

-- alter table ARTICULO
add constraint FK_ARTICULO_DETALLA2_DETALLEE foreign key (DET_IDARTICULO, IDDETALLEEXISTENCIA)
  references DETALLEEXISTENCIA (IDARTICULO, IDDETALLEEXISTENCIA);

-- alter table ARTICULO
add constraint FK_ARTICULO_PRESENTAD_FORMAFAR foreign key (IDFORMAFARMACEUTICA)
  references FORMAFARMACEUTICA (IDFORMAFARMACEUTICA);

-- alter table ARTICULO
add constraint FK_ARTICULO_RELATIONS_MARCA foreign key (MAR_IDMARCA)
  references MARCA (IDMARCA);

-- alter table DEPARTAMENTO
add constraint FK_DEPARTAM_RELATIONS_DISTRITO foreign key (IDDISTRITO)
  references DISTRITO (IDDISTRITO);

-- alter table DETALLECOMPRA
add constraint FK_DETALLEC_ESPECIFIC_FACTURAC foreign key (IDCOMPRA)
  references FACTURACOMPRA (IDCOMPRA);

-- alter table DETALLECOMPRA
add constraint FK_DETALLEC_TIENE_ASO_ARTICULO foreign key (IDARTICULO)
  references ARTICULO (IDARTICULO);

-- alter table DETALLEEXISTENCIA
add constraint FK_DETALLEE_DETALLA_ARTICULO foreign key (IDARTICULO)
  references ARTICULO (IDARTICULO);

-- alter table DETALLEEXISTENCIA
add constraint FK_DETALLEE_TIENE_UN_SUCURSAL foreign key (IDFARMACIA)
  references SUCURSALFARMACIA (IDFARMACIA);

-- alter table DETALLEVENTA
add constraint FK_DETALLEV_ASOCIADO__ARTICULO foreign key (IDARTICULO)
  references ARTICULO (IDARTICULO);

-- alter table DETALLEVENTA
add constraint FK_DETALLEV_ESTA_ESPE_FACTURAV foreign key (IDCLIENTE, IDVENTA)
  references FACTURAVENTA (IDCLIENTE, IDVENTA);

-- alter table DIRECCION
add constraint FK_DIRECCIO_UBICADA_E_DISTRITO foreign key (IDDISTRITO)
  references DISTRITO (IDDISTRITO);

-- alter table DISTRITO
add constraint FK_DISTRITO_SE_DIVIDE_MUNICIPI foreign key (IDMUNICIPIO)
  references MUNICIPIO (IDMUNICIPIO);

-- alter table FACTURACOMPRA
add constraint FK_FACTURAC_ESTA_ASOC_SUCURSAL foreign key (IDFARMACIA)
  references SUCURSALFARMACIA (IDFARMACIA);

-- alter table FACTURACOMPRA
add constraint FK_FACTURAC_LISTADO_PROVEEDO foreign key (IDPROVEEDOR)
  references PROVEEDOR (IDPROVEEDOR);

-- alter table FACTURAVENTA
add constraint FK_FACTURAV_DADA_EN_SUCURSAL foreign key (IDFARMACIA)
  references SUCURSALFARMACIA (IDFARMACIA);

-- alter table FACTURAVENTA
add constraint FK_FACTURAV_RELACIONA_CLIENTE foreign key (IDCLIENTE)
  references CLIENTE (IDCLIENTE);

-- alter table MUNICIPIO
add constraint FK_MUNICIPI_FORMADO_P_DEPARTAM foreign key (DEP_IDDEPARTAMENTO)
  references DEPARTAMENTO (IDDEPARTAMENTO);

-- alter table MUNICIPIO
add constraint FK_MUNICIPI_RELATIONS_DEPARTAM foreign key (IDDEPARTAMENTO)
  references DEPARTAMENTO (IDDEPARTAMENTO);

-- alter table RECETA
add constraint FK_RECETA_ASOCIADO_CLIENTE foreign key (IDCLIENTE)
  references CLIENTE (IDCLIENTE);

-- alter table RECETA
add constraint FK_RECETA_RECETA_DOCTOR foreign key (IDDOCTOR)
  references DOCTOR (IDDOCTOR);

-- alter table RECETA
add constraint FK_RECETA_RELATIONS_DOCTOR foreign key (DOC_IDDOCTOR)
  references DOCTOR (IDDOCTOR);

-- alter table SUBCATEGORIA
add constraint FK_SUBCATEG_TIENE_CATEGORI foreign key (IDCATEGORIA)
  references CATEGORIA (IDCATEGORIA);

-- alter table SUCURSALFARMACIA
add constraint FK_SUCURSAL_SE_UBICA__DIRECCIO foreign key (IDDIRECCION)
  references DIRECCION (IDDIRECCION);
