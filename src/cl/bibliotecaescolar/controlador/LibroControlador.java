package cl.bibliotecaescolar.controlador;

import cl.bibliotecaescolar.dao.CategoriaDAO;
import cl.bibliotecaescolar.dao.LibroDAO;
import cl.bibliotecaescolar.modelo.Categoria;
import cl.bibliotecaescolar.modelo.LibroFisico;
import java.util.List;

public class LibroControlador {

    private final LibroDAO libroDAO = new LibroDAO();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public boolean crear(LibroFisico libro) {
        return libroDAO.create(libro);
    }

    public List<LibroFisico> listarTodos() {
        return libroDAO.readAll();
    }

    public boolean actualizar(LibroFisico libro) {
        return libroDAO.update(libro);
    }

    public boolean eliminar(int id) {
        return libroDAO.delete(id);
    }

    public List<Categoria> listarCategorias() {
        return categoriaDAO.readAll();
    }
}