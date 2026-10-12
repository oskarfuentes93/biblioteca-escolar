package cl.bibliotecaescolar.controlador;

import cl.bibliotecaescolar.dao.EstudianteDAO;
import cl.bibliotecaescolar.dao.LibroDAO;
import cl.bibliotecaescolar.dao.PrestamoDAO;
import cl.bibliotecaescolar.dao.ServicioPrestamo;
import cl.bibliotecaescolar.modelo.Estudiante;
import cl.bibliotecaescolar.modelo.LibroFisico;
import cl.bibliotecaescolar.modelo.Prestamo;
import java.util.List;

public class PrestamoControlador {

    private final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private final ServicioPrestamo servicioPrestamo = new ServicioPrestamo();
    private final EstudianteDAO estudianteDAO = new EstudianteDAO();
    private final LibroDAO libroDAO = new LibroDAO();

    public boolean registrarPrestamo(Prestamo prestamo, int stockActual) {
        return servicioPrestamo.registrarPrestamo(prestamo, stockActual);
    }

    public boolean registrarDevolucion(int idPrestamo, int idLibro, int stockActual) {
        return servicioPrestamo.registrarDevolucion(idPrestamo, idLibro, stockActual);
    }

    public List<Prestamo> listarTodos() {
        return prestamoDAO.readAll();
    }

    public List<Estudiante> listarEstudiantes() {
        return estudianteDAO.readAll();
    }

    public List<LibroFisico> listarLibros() {
        return libroDAO.readAll();
    }

    public List<Prestamo> historialPorEstudiante(int idEstudiante) {
        return prestamoDAO.historialPorEstudiante(idEstudiante);
    }

    public List<Prestamo> librosEnPrestamo() {
        return prestamoDAO.librosEnPrestamo();
    }

    public List<Object[]> librosMasPrestados() {
        return prestamoDAO.librosMasPrestados();
    }
}