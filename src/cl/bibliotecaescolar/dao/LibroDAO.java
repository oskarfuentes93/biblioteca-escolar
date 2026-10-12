package cl.bibliotecaescolar.dao;

import cl.bibliotecaescolar.modelo.LibroFisico;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    public boolean create(LibroFisico libro) {
        String sql = "INSERT INTO libros (titulo, autor, isbn, editorial, stock, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getIdCategoria());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al crear libro: " + e.getMessage());
            return false;
        }
    }

    public List<LibroFisico> readAll() {
        List<LibroFisico> lista = new ArrayList<>();
        String sql = "SELECT l.*, c.nombre AS nombre_categoria FROM libros l JOIN categorias c ON l.id_categoria = c.id";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                LibroFisico libro = new LibroFisico();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setEditorial(rs.getString("editorial"));
                libro.setStock(rs.getInt("stock"));
                libro.setIdCategoria(rs.getInt("id_categoria"));
                libro.setNombreCategoria(rs.getString("nombre_categoria"));
                lista.add(libro);
            }
        } catch (SQLException e) {
            System.out.println("Error al leer libros: " + e.getMessage());
        }
        return lista;
    }

    public boolean update(LibroFisico libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, isbn = ?, editorial = ?, stock = ?, id_categoria = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getIsbn());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getStock());
            ps.setInt(6, libro.getIdCategoria());
            ps.setInt(7, libro.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar libro: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar libro: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarStock(int idLibro, int nuevoStock) {
        String sql = "UPDATE libros SET stock = ? WHERE id = ?";
        try (Connection con = DatabaseConnection.getInstance();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nuevoStock);
            ps.setInt(2, idLibro);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }
}