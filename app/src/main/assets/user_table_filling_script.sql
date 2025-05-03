-- Insert sample users into USUARIO table
INSERT INTO USUARIO (IDUSUARIO, NOMBREUSUARIO, CLAVE) VALUES
('user1', 'User One', 'password1'),
('user2', 'User Two', 'password2'),
('admin', 'admin', '1234');

-- Insert sample options into OPCIONCRUD table
INSERT INTO OPCIONCRUD (IDOPCION, DESOPCION, NUMCRUD) VALUES
('option1', 'Option 1', 1),
('option2', 'Option 2', 2),
('option3', 'Option 3', 3);

-- Insert sample access records into ACCESOUSUARIO table
INSERT INTO ACCESOUSUARIO (IDUSUARIO, IDOPCION) VALUES
('user1', 'option1'),
('user1', 'option2'),
('user2', 'option2'),
('user2', 'option3'),
('admin', 'option1'),
('admin', 'option2'),
('admin', 'option3');
