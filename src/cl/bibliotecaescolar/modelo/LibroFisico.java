package cl.bibliotecaescolar.modelo;

import java.util.ArrayList;
import java.util.List;

public class LibroFisico extends Libro implements Reportable {

    public LibroFisico() {
    }

    public LibroFisico(int id, String titulo, String autor, String isbn, String editorial, int stock, int idCategoria) {
        super(id, titulo, autor, isbn, editorial, stock, idCategoria);
    }

    @Override
    public String getTipoLibro() {
        return "Físico";
    }

    @Override
    public List<String> generarReporte() {
        List<String> reporte = new ArrayList<>();
        reporte.add("Título: " + getTitulo());
        reporte.add("Autor: " + getAutor());
        reporte.add("ISBN: " + getIsbn());
        reporte.add("Editorial: " + getEditorial());
        reporte.add("Stock: " + getStock());
        reporte.add("Disponible: " + (estaDisponible() ? "Sí" : "No"));
        return reporte;
    }
}