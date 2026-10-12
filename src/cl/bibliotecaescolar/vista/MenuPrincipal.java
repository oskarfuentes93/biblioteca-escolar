package cl.bibliotecaescolar.vista;

import cl.bibliotecaescolar.modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private final Usuario usuario;

    public MenuPrincipal(Usuario usuario) {
        this.usuario = usuario;
        setTitle("Biblioteca Escolar - Menú Principal (" + usuario.getRol() + ")");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblBienvenida = new JLabel("Bienvenido/a: " + usuario.getNombre(), SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridy = 0;
        panel.add(lblBienvenida, gbc);

        if (usuario.getRol().equals("bibliotecario")) {
            JButton btnLibros = new JButton("Gestión de Libros");
            gbc.gridy = 1;
            panel.add(btnLibros, gbc);
            btnLibros.addActionListener(e -> {
                new LibroVista().setVisible(true);
            });

            JButton btnEstudiantes = new JButton("Gestión de Estudiantes");
            gbc.gridy = 2;
            panel.add(btnEstudiantes, gbc);
            btnEstudiantes.addActionListener(e -> {
                new EstudianteVista().setVisible(true);
            });

            JButton btnPrestamos = new JButton("Préstamos y Devoluciones");
            gbc.gridy = 3;
            panel.add(btnPrestamos, gbc);
            btnPrestamos.addActionListener(e -> {
                new PrestamoVista().setVisible(true);
            });

            JButton btnReportes = new JButton("Reportes");
            gbc.gridy = 4;
            panel.add(btnReportes, gbc);
            btnReportes.addActionListener(e -> {
                new ReporteVista().setVisible(true);
            });
        } else {
            JButton btnPrestamos = new JButton("Mis Préstamos y Devoluciones");
            gbc.gridy = 1;
            panel.add(btnPrestamos, gbc);
            btnPrestamos.addActionListener(e -> {
                new PrestamoVista().setVisible(true);
            });

            JButton btnReportes = new JButton("Consultar Reportes");
            gbc.gridy = 2;
            panel.add(btnReportes, gbc);
            btnReportes.addActionListener(e -> {
                new ReporteVista().setVisible(true);
            });
        }

        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        gbc.gridy = 5;
        panel.add(btnCerrarSesion, gbc);
        btnCerrarSesion.addActionListener(e -> {
            new LoginVista().setVisible(true);
            dispose();
        });

        add(panel);
    }
}