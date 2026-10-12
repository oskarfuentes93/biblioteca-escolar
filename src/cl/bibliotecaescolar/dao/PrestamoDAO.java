package cl.bibliotecaescolar.dao;

import cl.bibliotecaescolar.modelo.Prestamo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {

    public boolean create(Prestamo p) {
        String sql = "INSERT INTO prestamos (id_estudiante, id_libro, fecha_prestamo, fecha_devolucion, devuelto) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getIdEstudiante());
            ps.setInt(2, p.getIdLibro());
            ps.setString(3, p.getFechaPrestamo());
            ps.setString(4, p.getFechaDevolucion());
            ps.setBoolean(5, p.isDevuelto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear préstamo: " + e.getMessage());
            return false;
        }
    }

    public List<Prestamo> readAll() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.*, e.nombre AS nombre_estudiante, l.titulo AS titulo_libro FROM prestamos p JOIN estudiantes e ON p.id_estudiante = e.id JOIN libros l ON p.id_libro = l.id";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setId(rs.getInt("id"));
                p.setIdEstudiante(rs.getInt("id_estudiante"));
                p.setIdLibro(rs.getInt("id_libro"));
                p.setFechaPrestamo(rs.getString("fecha_prestamo"));
                p.setFechaDevolucion(rs.getString("fecha_devolucion"));
                p.setDevuelto(rs.getBoolean("devuelto"));
                p.setNombreEstudiante(rs.getString("nombre_estudiante"));
                p.setTituloLibro(rs.getString("titulo_libro"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer préstamos: " + e.getMessage());
        }
        return lista;
    }

    public boolean marcarDevuelto(int id) {
        String sql = "UPDATE prestamos SET devuelto = TRUE WHERE id = ?";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al marcar devolución: " + e.getMessage());
            return false;
        }
    }

    public List<Prestamo> historialPorEstudiante(int idEstudiante) {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.*, l.titulo AS titulo_libro FROM prestamos p JOIN libros l ON p.id_libro = l.id WHERE p.id_estudiante = ?";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEstudiante);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setId(rs.getInt("id"));
                p.setIdEstudiante(rs.getInt("id_estudiante"));
                p.setIdLibro(rs.getInt("id_libro"));
                p.setFechaPrestamo(rs.getString("fecha_prestamo"));
                p.setFechaDevolucion(rs.getString("fecha_devolucion"));
                p.setDevuelto(rs.getBoolean("devuelto"));
                p.setTituloLibro(rs.getString("titulo_libro"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar historial: " + e.getMessage());
        }
        return lista;
    }

    public List<Prestamo> librosEnPrestamo() {
        List<Prestamo> lista = new ArrayList<>();
        String sql = "SELECT p.*, e.nombre AS nombre_estudiante, l.titulo AS titulo_libro FROM prestamos p JOIN estudiantes e ON p.id_estudiante = e.id JOIN libros l ON p.id_libro = l.id WHERE p.devuelto = FALSE";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setId(rs.getInt("id"));
                p.setIdEstudiante(rs.getInt("id_estudiante"));
                p.setIdLibro(rs.getInt("id_libro"));
                p.setFechaPrestamo(rs.getString("fecha_prestamo"));
                p.setFechaDevolucion(rs.getString("fecha_devolucion"));
                p.setDevuelto(rs.getBoolean("devuelto"));
                p.setNombreEstudiante(rs.getString("nombre_estudiante"));
                p.setTituloLibro(rs.getString("titulo_libro"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar libros en préstamo: " + e.getMessage());
        }
        return lista;
    }

    public List<Object[]> librosMasPrestados() {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT l.titulo, COUNT(p.id) AS total FROM prestamos p JOIN libros l ON p.id_libro = l.id GROUP BY l.titulo ORDER BY total DESC";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Object[] fila = new Object[2];
                fila[0] = rs.getString("titulo");
                fila[1] = rs.getInt("total");
                lista.add(fila);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar libros más prestados: " + e.getMessage());
        }
        return lista;
    }
}