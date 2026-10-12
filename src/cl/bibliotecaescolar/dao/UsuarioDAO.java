package cl.bibliotecaescolar.dao;

import cl.bibliotecaescolar.modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario validarLogin(String rut, String contrasena) {
        String sql = "SELECT * FROM usuarios WHERE rut = ? AND contraseña = ?";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rut);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setRut(rs.getString("rut"));
                u.setCorreo(rs.getString("correo"));
                u.setContrasena(rs.getString("contraseña"));
                u.setRol(rs.getString("rol"));
                return u;
            }
        } catch (SQLException e) {
            System.out.println("Error en login: " + e.getMessage());
        }
        return null;
    }
}