-- CREACIÓN DE BASE DE DATOS
CREATE DATABASE egg;

-- USO DE BASE DE DATOS
USE egg;

-- CREACIÓN DE TABLA
CREATE TABLE usuario (
    correo VARCHAR(45) NOT NULL PRIMARY KEY,
    nombre VARCHAR(45) NOT NULL,
    apellido VARCHAR(45) NOT NULL
);

-- INSERCIÓN DE DATOS
INSERT INTO usuario VALUES('fernando@egg.com', 'Fernando', 'Cocco');
INSERT INTO usuario VALUES('gonzalo@egg.com', 'Gonzalo', 'Baldiviezo');
INSERT INTO usuario VALUES('julieta@egg.com', 'Julieta', 'Villarruel');

-- CONSULTA GENERAL
SELECT * FROM usuario;

-- ELIMINACIÓN DE BASE DE DATOS
-- DROP DATABASE egg;

-- SENTENCIA PARA SABER EN QUÉ PUERTO ESTÁ CORRIENDO MYSQL
-- SHOW GLOBAL VARIABLES LIKE 'PORT';