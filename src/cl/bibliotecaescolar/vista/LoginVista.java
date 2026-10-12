package cl.bibliotecaescolar.vista;

import cl.bibliotecaescolar.controlador.LoginControlador;
import cl.bibliotecaescolar.modelo.Usuario;
import javax.swing.*;
import java.awt.*;

public class LoginVista extends JFrame {

    private JTextField txtRut;
    private JPasswordField txtContrasena;
    private JButton btnIngresar;
    private final LoginControlador controlador = new LoginControlador();

    public LoginVista() {
        setTitle("Biblioteca Escolar - Login");
        setSize(400, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Sistema de Biblioteca Escolar", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("RUT:"), gbc);

        txtRut = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtRut, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Contraseña:"), gbc);

        txtContrasena = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(txtContrasena, gbc);

        btnIngresar = new JButton("Ingresar");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        panel.add(btnIngresar, gbc);

        add(panel);

        btnIngresar.addActionListener(e -> {
            String rut = txtRut.getText().trim();
            String clave = new String(txtContrasena.getPassword()).trim();

            if (rut.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe completar todos los campos.");
                return;
            }

            Usuario usuario = controlador.iniciarSesion(rut, clave);
            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Bienvenido/a " + usuario.getNombre());
                new MenuPrincipal(usuario).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "RUT o contraseña incorrectos.");
            }
        });
    }
}