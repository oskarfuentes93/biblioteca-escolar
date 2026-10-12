package cl.bibliotecaescolar.vista;

import cl.bibliotecaescolar.controlador.PrestamoControlador;
import cl.bibliotecaescolar.modelo.Estudiante;
import cl.bibliotecaescolar.modelo.Prestamo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReporteVista extends JFrame {

    private JButton btnMasPrestados, btnHistorial, btnEnPrestamo;
    private JComboBox<String> cmbEstudiantes;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private final PrestamoControlador controlador = new PrestamoControlador();
    private List<Estudiante> listaEstudiantes;

    public ReporteVista() {
        setTitle("Reportes");
        setSize(750, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panelSuperior = new JPanel(new FlowLayout());

        btnMasPrestados = new JButton("Libros más prestados");
        btnEnPrestamo = new JButton("Libros en préstamo");
        btnHistorial = new JButton("Historial por estudiante");

        cmbEstudiantes = new JComboBox<>();
        cargarEstudiantes();

        panelSuperior.add(btnMasPrestados);
        panelSuperior.add(btnEnPrestamo);
        panelSuperior.add(btnHistorial);
        panelSuperior.add(new JLabel("Estudiante:"));
        panelSuperior.add(cmbEstudiantes);

        modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        setLayout(new BorderLayout());
        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnMasPrestados.addActionListener(e -> {
            modeloTabla.setColumnCount(0);
            modeloTabla.setRowCount(0);
            modeloTabla.addColumn("Título");
            modeloTabla.addColumn("Total Préstamos");
            List<Object[]> lista = controlador.librosMasPrestados();
            for (Object[] fila : lista) {
                modeloTabla.addRow(fila);
            }
        });

        btnEnPrestamo.addActionListener(e -> {
            modeloTabla.setColumnCount(0);
            modeloTabla.setRowCount(0);
            modeloTabla.addColumn("ID");
            modeloTabla.addColumn("Estudiante");
            modeloTabla.addColumn("Libro");
            modeloTabla.addColumn("Fecha Préstamo");
            modeloTabla.addColumn("Fecha Devolución");
            List<Prestamo> lista = controlador.librosEnPrestamo();
            for (Prestamo p : lista) {
                modeloTabla.addRow(new Object[]{
                        p.getId(),
                        p.getNombreEstudiante(),
                        p.getTituloLibro(),
                        p.getFechaPrestamo(),
                        p.getFechaDevolucion()
                });
            }
        });

        btnHistorial.addActionListener(e -> {
            if (cmbEstudiantes.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un estudiante.");
                return;
            }
            modeloTabla.setColumnCount(0);
            modeloTabla.setRowCount(0);
            modeloTabla.addColumn("ID");
            modeloTabla.addColumn("Libro");
            modeloTabla.addColumn("Fecha Préstamo");
            modeloTabla.addColumn("Fecha Devolución");
            modeloTabla.addColumn("Devuelto");
            Estudiante est = listaEstudiantes.get(cmbEstudiantes.getSelectedIndex());
            List<Prestamo> lista = controlador.historialPorEstudiante(est.getId());
            for (Prestamo p : lista) {
                modeloTabla.addRow(new Object[]{
                        p.getId(),
                        p.getTituloLibro(),
                        p.getFechaPrestamo(),
                        p.getFechaDevolucion(),
                        p.isDevuelto() ? "Sí" : "No"
                });
            }
        });
    }

    private void cargarEstudiantes() {
        cmbEstudiantes.removeAllItems();
        listaEstudiantes = controlador.listarEstudiantes();
        for (Estudiante e : listaEstudiantes) {
            cmbEstudiantes.addItem(e.getNombre() + " - " + e.getRut());
        }
    }
}