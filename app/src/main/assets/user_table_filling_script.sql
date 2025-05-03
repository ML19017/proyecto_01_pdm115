-- Insertar usuarios
INSERT INTO USUARIO (IDUSUARIO, NOMBREUSUARIO, CLAVE) VALUES
('gerente', 'Gerente Farmacéutico', 'gerente123'),
('farmaceutico', 'Farmacéutico', 'farmaceutico123'),
('cajero', 'Cajero', 'cajero123'),
('admin', 'Administrador', '1234');

-- Insertar opciones en OPCIONCRUD
INSERT INTO OPCIONCRUD (IDOPCION, DESOPCION, NUMCRUD) VALUES
('direction', 'Direction', 1),
('branch', 'Branch', 2),
('existence_detail', 'Existence Detail', 3),
('sales', 'Sale', 4),
('sale_detail', 'Sales Details', 5),
('sale_invoice', 'Sales Invoice', 6),
('client', 'Client', 7),
('purchase', 'Purchase', 8),
('purchase_detail', 'Purchase Details', 9),
('purchase_invoice', 'Purchase Invoice', 10),
('supplier', 'Supplier', 11),
('pharmaceutical_form', 'Pharmaceutical Form', 12),
('brand', 'Brand', 13),
('route_of_administration', 'Route of Administration', 14),
('prescription', 'Prescription', 15),
('doctor', 'Doctor', 16),
('item', 'Item', 17),
('category', 'Category', 18),
('subcategory', 'Subcategory', 19);

-- Asignar permisos al Gerente Farmacéutico
INSERT INTO ACCESOUSUARIO (IDUSUARIO, IDOPCION) VALUES
('gerente', 'direction'),
('gerente', 'branch'),
('gerente', 'existence_detail'),
('gerente', 'sales'),
('gerente', 'sale_detail'),
('gerente', 'sale_invoice'),
('gerente', 'client'),
('gerente', 'purchase'),
('gerente', 'purchase_detail'),
('gerente', 'purchase_invoice'),
('gerente', 'supplier'),
('gerente', 'pharmaceutical_form'),
('gerente', 'brand'),
('gerente', 'route_of_administration'),
('gerente', 'prescription'),
('gerente', 'doctor'),
('gerente', 'item'),
('gerente', 'category'),
('gerente', 'subcategory');

-- Asignar permisos al Farmacéutico
INSERT INTO ACCESOUSUARIO (IDUSUARIO, IDOPCION) VALUES
('farmaceutico', 'existence_detail'),
('farmaceutico', 'sales'),
('farmaceutico', 'sale_detail'),
('farmaceutico', 'sale_invoice'),
('farmaceutico', 'client'),
('farmaceutico', 'purchase'),
('farmaceutico', 'purchase_detail'),
('farmaceutico', 'purchase_invoice'),
('farmaceutico', 'supplier'),
('farmaceutico', 'pharmaceutical_form'),
('farmaceutico', 'brand'),
('farmaceutico', 'route_of_administration'),
('farmaceutico', 'prescription'),
('farmaceutico', 'doctor'),
('farmaceutico', 'item');

-- Asignar permisos al Cajero
INSERT INTO ACCESOUSUARIO (IDUSUARIO, IDOPCION) VALUES
('cajero', 'sales'),
('cajero', 'sale_detail'),
('cajero', 'sale_invoice'),
('cajero', 'client');

-- Asignar permisos al Administrador
INSERT INTO ACCESOUSUARIO (IDUSUARIO, IDOPCION) VALUES
('admin', 'direction'),
('admin', 'branch'),
('admin', 'existence_detail'),
('admin', 'sale'),
('admin', 'sale_detail'),
('admin', 'sale_invoice'),
('admin', 'client'),
('admin', 'purchase'),
('admin', 'purchase_detail'),
('admin', 'purchase_invoice'),
('admin', 'supplier'),
('admin', 'pharmaceutical_form'),
('admin', 'brand'),
('admin', 'route_of_administration'),
('admin', 'prescription'),
('admin', 'doctor'),
('admin', 'item'),
('admin', 'category'),
('admin', 'subcategory');
