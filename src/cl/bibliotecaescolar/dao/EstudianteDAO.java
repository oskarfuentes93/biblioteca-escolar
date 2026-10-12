package cl.bibliotecaescolar.dao;

import cl.bibliotecaescolar.modelo.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {

    public boolean create(Estudiante e) {
        String sql = "INSERT INTO estudiantes (nombre, rut, curso, correo) VALUES (?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getRut());
            ps.setString(3, e.getCurso());
            ps.setString(4, e.getCorreo());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al crear estudiante: " + ex.getMessage());
            return false;
        }
    }

    public List<Estudiante> readAll() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Estudiante e = new Estudiante();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                e.setRut(rs.getString("rut"));
                e.setCurso(rs.getString("curso"));
                e.setCorreo(rs.getString("correo"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            System.out.println("Error al leer estudiantes: " + ex.getMessage());
        }
        return lista;
    }

    public boolean update(Estudiante e) {
        String sql = "UPDATE estudiantes SET nombre = ?, rut = ?, curso = ?, correo = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getRut());
            ps.setString(3, e.getCurso());
            ps.setString(4, e.getCorreo());
            ps.setInt(5, e.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al actualizar estudiante: " + ex.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM estudiantes WHERE id = ?";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.out.println("Error al eliminar estudiante: " + ex.getMessage());
            return false;
        }
    }
}