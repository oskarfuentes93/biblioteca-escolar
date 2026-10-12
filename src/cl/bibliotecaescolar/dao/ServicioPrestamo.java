package cl.bibliotecaescolar.dao;

import cl.bibliotecaescolar.modelo.Prestamo;

public class ServicioPrestamo {

    private final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private final LibroDAO libroDAO = new LibroDAO();

    public synchronized boolean registrarPrestamo(Prestamo prestamo, int stockActual) {
        if (stockActual <= 0) {
            System.out.println("No hay stock disponible para este libro.");
            return false;
        }
        boolean prestamoCreado = prestamoDAO.create(prestamo);
        if (prestamoCreado) {
            libroDAO.actualizarStock(prestamo.getIdLibro(), stockActual - 1);
            System.out.println("Préstamo registrado correctamente.");
        }
        return prestamoCreado;
    }

    public synchronized boolean registrarDevolucion(int idPrestamo, int idLibro, int stockActual) {
        boolean devuelto = prestamoDAO.marcarDevuelto(idPrestamo);
        if (devuelto) {
            libroDAO.actualizarStock(idLibro, stockActual + 1);
            System.out.println("Devolución registrada correctamente.");
        }
        return devuelto;
    }
}