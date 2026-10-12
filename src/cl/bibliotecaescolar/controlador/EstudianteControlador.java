package cl.bibliotecaescolar.controlador;

import cl.bibliotecaescolar.dao.EstudianteDAO;
import cl.bibliotecaescolar.modelo.Estudiante;
import java.util.List;

public class EstudianteControlador {

    private final EstudianteDAO estudianteDAO = new EstudianteDAO();

    public boolean crear(Estudiante e) {
        return estudianteDAO.create(e);
    }

    public List<Estudiante> listarTodos() {
        return estudianteDAO.readAll();
    }

    public boolean actualizar(Estudiante e) {
        return estudianteDAO.update(e);
    }

    public boolean eliminar(int id) {
        return estudianteDAO.delete(id);
    }
}