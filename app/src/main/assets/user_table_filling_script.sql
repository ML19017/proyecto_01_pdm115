-- Insertar usuarios
INSERT INTO USUARIO (IDUSUARIO, NOMBREUSUARIO, CLAVE) VALUES
('01', 'gerente', 'gerente123'),
('02', 'farmaceutico', 'farmaceutico123'),
('03', 'cajero', 'cajero123'),
('04', 'admin', '1234');

-- Insertar opciones en OPCIONCRUD
INSERT INTO OPCIONCRUD (IDOPCION, DESOPCION, NUMCRUD) VALUES
('001', 'Menu Direction', 0),
('002', 'Direction Create', 1),
('003', 'Direction Read', 2),
('004', 'Direction Update', 3),
('005', 'Direction Delete', 4),
('006', 'Menu Branch', 0),
('007', 'Branch Create', 1),
('008', 'Branch Read', 2),
('009', 'Branch Update', 3),
('010', 'Branch Delete', 4),
('011', 'Menu Existence Detail', 0),
('012', 'Existence Detail Add', 1),
('013', 'Existence Detail Read', 2),
('014', 'Existence Detail Update', 3),
('015', 'Existence Detail Delete', 4),
('016', 'Menu Sales', 0),
('017', 'Sales Create', 1),
('018', 'Sales Read', 2),
('019', 'Sales Update', 3),
('020', 'Sales Delete', 4),
('021', 'Menu Sales Details', 0),
('022', 'Sales Details Create', 1),
('023', 'Sales Details Read', 2),
('024', 'Sales Details Update', 3),
('025', 'Sales Details Delete', 4),
('026', 'Menu Sales Invoice', 0),
('027', 'Sales Invoice Create', 1),
('028', 'Sales Invoice Read', 2),
('029', 'Sales Invoice Update', 3),
('030', 'Sales Invoice Delete', 4),
('031', 'Menu Client', 0),
('032', 'Client Create', 1),
('033', 'Client Read', 2),
('034', 'Client Update', 3),
('035', 'Client Delete', 4),
('036', 'Menu Purchase', 0),
('037', 'Purchase Create', 1),
('038', 'Purchase Read', 2),
('039', 'Purchase Update', 3),
('040', 'Purchase Delete', 4),
('041', 'Menu Purchase Details', 0),
('042', 'Purchase Details Create', 1),
('043', 'Purchase Details Read', 2),
('044', 'Purchase Details Update', 3),
('045', 'Purchase Details Delete', 4),
('046', 'Menu Purchase Invoice', 0),
('047', 'Purchase Invoice Create', 1),
('048', 'Purchase Invoice Read', 2),
('049', 'Purchase Invoice Update', 3),
('050', 'Purchase Invoice Delete', 4),
('051', 'Menu Supplier', 0),
('052', 'Supplier Create', 1),
('053', 'Supplier Read', 2),
('054', 'Supplier Update', 3),
('055', 'Supplier Delete', 4),
('056', 'Menu Pharmaceutical Form', 0),
('057', 'Pharmaceutical Form Create', 1),
('058', 'Pharmaceutical Form Read', 2),
('059', 'Pharmaceutical Form Update', 3),
('060', 'Pharmaceutical Form Delete', 4),
('061', 'Menu Brand', 0),
('062', 'Brand Create', 1),
('063', 'Brand Read', 2),
('064', 'Brand Update', 3),
('065', 'Brand Delete', 4),
('066', 'Menu Route of Administration', 0),
('067', 'Route of Administration Create', 1),
('068', 'Route of Administration Read', 2),
('069', 'Route of Administration Update', 3),
('070', 'Route of Administration Delete', 4),
('071', 'Menu Prescription', 0),
('072', 'Prescription Create', 1),
('073', 'Prescription Read', 2),
('074', 'Prescription Update', 3),
('075', 'Prescription Delete', 4),
('076', 'Menu Doctor', 0),
('077', 'Doctor Create', 1),
('078', 'Doctor Read', 2),
('079', 'Doctor Update', 3),
('080', 'Doctor Delete', 4),
('081', 'Menu Item', 0),
('082', 'Item Create', 1),
('083', 'Item Read', 2),
('084', 'Item Update', 3),
('085', 'Item Delete', 4),
('086', 'Menu Category', 0),
('087', 'Category Create', 1),
('088', 'Category Read', 2),
('089', 'Category Update', 3),
('090', 'Category Delete', 4),
('091', 'Menu Subcategory', 0),
('092', 'Subcategory Create', 1),
('093', 'Subcategory Read', 2),
('094', 'Subcategory Update', 3),
('095', 'Subcategory Delete', 4);

-- Asignar accesos a los usuarios

-- Accesos para el usuario 'admin' (acceso a todo)
INSERT INTO ACCESOUSUARIO (IDUSUARIO, IDOPCION) VALUES
('04', '001'), ('04', '002'), ('04', '003'), ('04', '004'), ('04', '005'),
('04', '006'), ('04', '007'), ('04', '008'), ('04', '009'), ('04', '010'),
('04', '011'), ('04', '012'), ('04', '013'), ('04', '014'), ('04', '015'),
('04', '016'), ('04', '017'), ('04', '018'), ('04', '019'), ('04', '020'),
('04', '021'), ('04', '022'), ('04', '023'), ('04', '024'), ('04', '025'),
('04', '026'), ('04', '027'), ('04', '028'), ('04', '029'), ('04', '030'),
('04', '031'), ('04', '032'), ('04', '033'), ('04', '034'), ('04', '035'),
('04', '036'), ('04', '037'), ('04', '038'), ('04', '039'), ('04', '040'),
('04', '041'), ('04', '042'), ('04', '043'), ('04', '044'), ('04', '045'),
('04', '046'), ('04', '047'), ('04', '048'), ('04', '049'), ('04', '050'),
('04', '051'), ('04', '052'), ('04', '053'), ('04', '054'), ('04', '055'),
('04', '056'), ('04', '057'), ('04', '058'), ('04', '059'), ('04', '060'),
('04', '061'), ('04', '062'), ('04', '063'), ('04', '064'), ('04', '065'),
('04', '066'), ('04', '067'), ('04', '068'), ('04', '069'), ('04', '070'),
('04', '071'), ('04', '072'), ('04', '073'), ('04', '074'), ('04', '075'),
('04', '076'), ('04', '077'), ('04', '078'), ('04', '079'), ('04', '080'),
('04', '081'), ('04', '082'), ('04', '083'), ('04', '084'), ('04', '085'),
('04', '086'), ('04', '087'), ('04', '088'), ('04', '089'), ('04', '090'),
('04', '091'), ('04', '092'), ('04', '093'), ('04', '094'), ('04', '095');

-- Accesos para el usuario 'gerente' (manejo de compras y proveedores)
INSERT INTO ACCESOUSUARIO (IDUSUARIO, IDOPCION) VALUES
('01', '036'), -- Menu Purchase
('01', '037'), -- Purchase Create
('01', '038'), -- Purchase Read
('01', '039'), -- Purchase Update
('01', '040'), -- Purchase Delete
('01', '041'), -- Menu Purchase Details
('01', '042'), -- Purchase Details Create
('01', '043'), -- Purchase Details Read
('01', '044'), -- Purchase Details Update
('01', '045'), -- Purchase Details Delete
('01', '046'), -- Menu Purchase Invoice
('01', '047'), -- Purchase Invoice Create
('01', '048'), -- Purchase Invoice Read
('01', '049'), -- Purchase Invoice Update
('01', '050'), -- Purchase Invoice Delete
('01', '051'), -- Menu Supplier
('01', '052'), -- Supplier Create
('01', '053'), -- Supplier Read
('01', '054'), -- Supplier Update
('01', '055'); -- Supplier Delete

-- Accesos para el usuario 'farmaceutico' (manejo de artículos)
INSERT INTO ACCESOUSUARIO (IDUSUARIO, IDOPCION) VALUES
('02', '081'), -- Menu Item
('02', '082'), -- Item Create
('02', '083'), -- Item Read
('02', '084'), -- Item Update
('02', '085'), -- Item Delete
('02', '086'), -- Menu Category
('02', '087'), -- Category Create
('02', '088'), -- Category Read
('02', '089'), -- Category Update
('02', '090'), -- Category Delete
('02', '091'), -- Menu Subcategory
('02', '092'), -- Subcategory Create
('02', '093'), -- Subcategory Read
('02', '094'), -- Subcategory Update
('02', '095'); -- Subcategory Delete

-- Accesos para el usuario 'cajero' (manejo de ventas)
INSERT INTO ACCESOUSUARIO (IDUSUARIO, IDOPCION) VALUES
('03', '016'), -- Menu Sales
('03', '017'), -- Sales Create
('03', '018'), -- Sales Read
('03', '019'), -- Sales Update
('03', '020');
