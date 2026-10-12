# Biblioteca Escolar

Sistema de gestion de biblioteca escolar desarrollado en Java con IntelliJ IDEA para la Evaluacion Final Transversal del ramo Desarrollo Orientado a Objetos II (PRY2203) de DuocUC.

## Descripcion

Aplicacion de escritorio que permite gestionar el prestamo y devolucion de libros en una biblioteca escolar. Incluye autenticacion por roles (bibliotecario y estudiante), gestion de libros, estudiantes, prestamos/devoluciones y reportes.

## Tecnologias

* Java (JDK 21)
* IntelliJ IDEA
* Java Swing (interfaz grafica)
* MySQL 8.0 (base de datos relacional)
* JDBC (conexion Java - MySQL)

## Estructura del proyecto

* `cl.bibliotecaescolar.modelo` : clases del dominio (Usuario, Estudiante, Categoria, Libro, LibroFisico, Prestamo, interfaces Prestable y Reportable)
* `cl.bibliotecaescolar.dao` : acceso a datos (DatabaseConnection con patron Singleton, DAOs con CRUD, ServicioPrestamo con sincronizacion)
* `cl.bibliotecaescolar.controlador` : intermediarios MVC entre vistas y DAOs
* `cl.bibliotecaescolar.vista` : interfaces graficas JFrame (Login, Menu Principal, Gestion de Libros, Estudiantes, Prestamos, Reportes)

## Patrones de diseno

* MVC (Modelo - Vista - Controlador)
* DAO (Data Access Object)
* Singleton (conexion a base de datos)

## POO aplicada

* Clase abstracta: Libro (con metodo abstracto getTipoLibro)
* Herencia: LibroFisico extiende Libro
* Interfaces: Prestable (estaDisponible, prestar, devolver) y Reportable (generarReporte)
* Polimorfismo: uso de tipos abstractos en DAOs y controladores

## Concurrencia

* Hilos (Thread) en operaciones de prestamo y devolucion para no bloquear la interfaz grafica
* Metodos synchronized en ServicioPrestamo para garantizar consistencia del stock

## Reportes

* Libros mas prestados
* Historial de prestamos por estudiante
* Listado de libros actualmente en prestamo

## Base de datos

Ejecutar los scripts en la carpeta `sql/` en MySQL Workbench:
1. Script_crea_tablas_biblioteca.sql
2. Script_poblado_tablas_biblioteca.sql

## Ejecucion

Ejecutar la clase Main ubicada en src/cl/bibliotecaescolar/Main.java

## Autor

Oscar Fuentes - DuocUC
