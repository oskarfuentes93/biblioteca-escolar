package cl.bibliotecaescolar.vista;

import cl.bibliotecaescolar.controlador.PrestamoControlador;
import cl.bibliotecaescolar.dao.ServicioPrestamo;
import cl.bibliotecaescolar.modelo.Estudiante;
import cl.bibliotecaescolar.modelo.LibroFisico;
import cl.bibliotecaescolar.modelo.Prestamo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PrestamoVista extends JFrame {

    private JComboBox<String> cmbEstudiantes, cmbLibros;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnPrestar, btnDevolver;
    private final PrestamoControlador controlador = new PrestamoControlador();
    private List<Estudiante> listaEstudiantes;
    private List<LibroFisico> listaLibros;

    public PrestamoVista() {
        setTitle("Préstamos y Devoluciones");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarComponentes();
        cargarTabla();
    }

    private void inicializarComponentes() {
        JPanel panelForm = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Estudiante:"), gbc);
        cmbEstudiantes = new JComboBox<>();
        cargarEstudiantes();
        gbc.gridx = 1;
        panelForm.add(cmbEstudiantes, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Libro:"), gbc);
        cmbLibros = new JComboBox<>();
        cargarLibros();
        gbc.gridx = 1;
        panelForm.add(cmbLibros, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnPrestar = new JButton("Registrar Préstamo");
        btnDevolver = new JButton("Registrar Devolución");
        panelBotones.add(btnPrestar);
        panelBotones.add(btnDevolver);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Estudiante", "Libro", "Fecha Préstamo", "Fecha Devolución", "Devuelto"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        setLayout(new BorderLayout());
        add(panelForm, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        btnPrestar.addActionListener(e -> {
            if (cmbEstudiantes.getSelectedIndex() == -1 || cmbLibros.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione estudiante y libro.");
                return;
            }

            Estudiante est = listaEstudiantes.get(cmbEstudiantes.getSelectedIndex());
            LibroFisico libro = listaLibros.get(cmbLibros.getSelectedIndex());

            if (libro.getStock() <= 0) {
                JOptionPane.showMessageDialog(this, "No hay stock disponible para este libro.");
                return;
            }

            Prestamo prestamo = new Prestamo();
            prestamo.setIdEstudiante(est.getId());
            prestamo.setIdLibro(libro.getId());
            prestamo.setFechaPrestamo(LocalDate.now().toString());
            prestamo.setFechaDevolucion(LocalDate.now().plusDays(7).toString());
            prestamo.setDevuelto(false);

            new Thread(() -> {
                ServicioPrestamo servicio = new ServicioPrestamo();
                boolean resultado = servicio.registrarPrestamo(prestamo, libro.getStock());
                SwingUtilities.invokeLater(() -> {
                    if (resultado) {
                        JOptionPane.showMessageDialog(this, "Préstamo registrado.");
                        cargarTabla();
                        cargarLibros();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al registrar préstamo.");
                    }
                });
            }).start();
        });

        btnDevolver.addActionListener(e -> {
            if (tabla.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un préstamo de la tabla.");
                return;
            }
            int fila = tabla.getSelectedRow();
            String devueltoStr = modeloTabla.getValueAt(fila, 5).toString();
            if (devueltoStr.equals("Sí")) {
                JOptionPane.showMessageDialog(this, "Este préstamo ya fue devuelto.");
                return;
            }

            int idPrestamo = (int) modeloTabla.getValueAt(fila, 0);
            String tituloLibro = modeloTabla.getValueAt(fila, 2).toString();

            int idLibro = -1;
            int stockActual = 0;
            for (LibroFisico l : listaLibros) {
                if (l.getTitulo().equals(tituloLibro)) {
                    idLibro = l.getId();
                    stockActual = l.getStock();
                    break;
                }
            }

            if (idLibro == -1) {
                JOptionPane.showMessageDialog(this, "No se encontró el libro.");
                return;
            }

            int idLibroFinal = idLibro;
            int stockFinal = stockActual;

            new Thread(() -> {
                ServicioPrestamo servicio = new ServicioPrestamo();
                boolean resultado = servicio.registrarDevolucion(idPrestamo, idLibroFinal, stockFinal);
                SwingUtilities.invokeLater(() -> {
                    if (resultado) {
                        JOptionPane.showMessageDialog(this, "Devolución registrada.");
                        cargarTabla();
                        cargarLibros();
                    } else {
                        JOptionPane.showMessageDialog(this, "Error al registrar devolución.");
                    }
                });
            }).start();
        });
    }

    private void cargarEstudiantes() {
        cmbEstudiantes.removeAllItems();
        listaEstudiantes = controlador.listarEstudiantes();
        for (Estudiante e : listaEstudiantes) {
            cmbEstudiantes.addItem(e.getNombre() + " - " + e.getRut());
        }
    }

    private void cargarLibros() {
        cmbLibros.removeAllItems();
        listaLibros = controlador.listarLibros();
        for (LibroFisico l : listaLibros) {
            cmbLibros.addItem(l.getTitulo() + " (Stock: " + l.getStock() + ")");
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Prestamo> lista = controlador.listarTodos();
        for (Prestamo p : lista) {
            modeloTabla.addRow(new Object[]{
                    p.getId(),
                    p.getNombreEstudiante(),
                    p.getTituloLibro(),
                    p.getFechaPrestamo(),
                    p.getFechaDevolucion(),
                    p.isDevuelto() ? "Sí" : "No"
            });
        }
    }
}