package cl.bibliotecaescolar.vista;

import cl.bibliotecaescolar.controlador.EstudianteControlador;
import cl.bibliotecaescolar.modelo.Estudiante;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EstudianteVista extends JFrame {

    private JTextField txtNombre, txtRut, txtCurso, txtCorreo;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnCrear, btnActualizar, btnEliminar, btnLimpiar;
    private final EstudianteControlador controlador = new EstudianteControlador();

    public EstudianteVista() {
        setTitle("Gestión de Estudiantes");
        setSize(700, 450);
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
        panelForm.add(new JLabel("Nombre:"), gbc);
        txtNombre = new JTextField(20);
        gbc.gridx = 1;
        panelForm.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("RUT:"), gbc);
        txtRut = new JTextField(20);
        gbc.gridx = 1;
        panelForm.add(txtRut, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Curso:"), gbc);
        txtCurso = new JTextField(20);
        gbc.gridx = 1;
        panelForm.add(txtCurso, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Correo:"), gbc);
        txtCorreo = new JTextField(20);
        gbc.gridx = 1;
        panelForm.add(txtCorreo, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnCrear = new JButton("Crear");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "RUT", "Curso", "Correo"}, 0) {
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

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() != -1) {
                int fila = tabla.getSelectedRow();
                txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtRut.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtCurso.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtCorreo.setText(modeloTabla.getValueAt(fila, 4).toString());
            }
        });

        btnCrear.addActionListener(e -> {
            if (!validarCampos()) return;
            Estudiante est = new Estudiante();
            est.setNombre(txtNombre.getText().trim());
            est.setRut(txtRut.getText().trim());
            est.setCurso(txtCurso.getText().trim());
            est.setCorreo(txtCorreo.getText().trim());
            if (controlador.crear(est)) {
                JOptionPane.showMessageDialog(this, "Estudiante creado.");
                limpiarCampos();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear estudiante.");
            }
        });

        btnActualizar.addActionListener(e -> {
            if (tabla.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un estudiante de la tabla.");
                return;
            }
            if (!validarCampos()) return;
            int id = (int) modeloTabla.getValueAt(tabla.getSelectedRow(), 0);
            Estudiante est = new Estudiante();
            est.setId(id);
            est.setNombre(txtNombre.getText().trim());
            est.setRut(txtRut.getText().trim());
            est.setCurso(txtCurso.getText().trim());
            est.setCorreo(txtCorreo.getText().trim());
            if (controlador.actualizar(est)) {
                JOptionPane.showMessageDialog(this, "Estudiante actualizado.");
                limpiarCampos();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar estudiante.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (tabla.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un estudiante de la tabla.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este estudiante?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = (int) modeloTabla.getValueAt(tabla.getSelectedRow(), 0);
                if (controlador.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Estudiante eliminado.");
                    limpiarCampos();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar estudiante.");
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<Estudiante> lista = controlador.listarTodos();
        for (Estudiante e : lista) {
            modeloTabla.addRow(new Object[]{e.getId(), e.getNombre(), e.getRut(), e.getCurso(), e.getCorreo()});
        }
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() || txtRut.getText().trim().isEmpty() ||
                txtCurso.getText().trim().isEmpty() || txtCorreo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos.");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtRut.setText("");
        txtCurso.setText("");
        txtCorreo.setText("");
        tabla.clearSelection();
    }
}