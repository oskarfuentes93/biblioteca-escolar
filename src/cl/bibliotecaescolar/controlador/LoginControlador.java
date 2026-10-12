package cl.bibliotecaescolar.controlador;

import cl.bibliotecaescolar.dao.UsuarioDAO;
import cl.bibliotecaescolar.modelo.Usuario;

public class LoginControlador {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario iniciarSesion(String rut, String contrasena) {
        return usuarioDAO.validarLogin(rut, contrasena);
    }
}