package cl.bibliotecaescolar.vista;

import cl.bibliotecaescolar.controlador.LibroControlador;
import cl.bibliotecaescolar.modelo.Categoria;
import cl.bibliotecaescolar.modelo.LibroFisico;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibroVista extends JFrame {

    private JTextField txtTitulo, txtAutor, txtIsbn, txtEditorial, txtStock;
    private JComboBox<Categoria> cmbCategoria;
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnCrear, btnActualizar, btnEliminar, btnLimpiar;
    private final LibroControlador controlador = new LibroControlador();

    public LibroVista() {
        setTitle("Gestión de Libros");
        setSize(800, 500);
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
        panelForm.add(new JLabel("Título:"), gbc);
        txtTitulo = new JTextField(20);
        gbc.gridx = 1;
        panelForm.add(txtTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Autor:"), gbc);
        txtAutor = new JTextField(20);
        gbc.gridx = 1;
        panelForm.add(txtAutor, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("ISBN:"), gbc);
        txtIsbn = new JTextField(20);
        gbc.gridx = 1;
        panelForm.add(txtIsbn, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panelForm.add(new JLabel("Editorial:"), gbc);
        txtEditorial = new JTextField(20);
        gbc.gridx = 1;
        panelForm.add(txtEditorial, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panelForm.add(new JLabel("Stock:"), gbc);
        txtStock = new JTextField(20);
        gbc.gridx = 1;
        panelForm.add(txtStock, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panelForm.add(new JLabel("Categoría:"), gbc);
        cmbCategoria = new JComboBox<>();
        cargarCategorias();
        gbc.gridx = 1;
        panelForm.add(cmbCategoria, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnCrear = new JButton("Crear");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        modeloTabla = new DefaultTableModel(new String[]{"ID", "Título", "Autor", "ISBN", "Editorial", "Stock", "Categoría"}, 0) {
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
                txtTitulo.setText(modeloTabla.getValueAt(fila, 1).toString());
                txtAutor.setText(modeloTabla.getValueAt(fila, 2).toString());
                txtIsbn.setText(modeloTabla.getValueAt(fila, 3).toString());
                txtEditorial.setText(modeloTabla.getValueAt(fila, 4).toString());
                txtStock.setText(modeloTabla.getValueAt(fila, 5).toString());
                String catNombre = modeloTabla.getValueAt(fila, 6).toString();
                for (int i = 0; i < cmbCategoria.getItemCount(); i++) {
                    if (cmbCategoria.getItemAt(i).getNombre().equals(catNombre)) {
                        cmbCategoria.setSelectedIndex(i);
                        break;
                    }
                }
            }
        });

        btnCrear.addActionListener(e -> {
            if (!validarCampos()) return;
            LibroFisico libro = new LibroFisico();
            libro.setTitulo(txtTitulo.getText().trim());
            libro.setAutor(txtAutor.getText().trim());
            libro.setIsbn(txtIsbn.getText().trim());
            libro.setEditorial(txtEditorial.getText().trim());
            libro.setStock(Integer.parseInt(txtStock.getText().trim()));
            libro.setIdCategoria(((Categoria) cmbCategoria.getSelectedItem()).getId());
            if (controlador.crear(libro)) {
                JOptionPane.showMessageDialog(this, "Libro creado.");
                limpiarCampos();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al crear libro.");
            }
        });

        btnActualizar.addActionListener(e -> {
            if (tabla.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla.");
                return;
            }
            if (!validarCampos()) return;
            int id = (int) modeloTabla.getValueAt(tabla.getSelectedRow(), 0);
            LibroFisico libro = new LibroFisico();
            libro.setId(id);
            libro.setTitulo(txtTitulo.getText().trim());
            libro.setAutor(txtAutor.getText().trim());
            libro.setIsbn(txtIsbn.getText().trim());
            libro.setEditorial(txtEditorial.getText().trim());
            libro.setStock(Integer.parseInt(txtStock.getText().trim()));
            libro.setIdCategoria(((Categoria) cmbCategoria.getSelectedItem()).getId());
            if (controlador.actualizar(libro)) {
                JOptionPane.showMessageDialog(this, "Libro actualizado.");
                limpiarCampos();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar libro.");
            }
        });

        btnEliminar.addActionListener(e -> {
            if (tabla.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este libro?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = (int) modeloTabla.getValueAt(tabla.getSelectedRow(), 0);
                if (controlador.eliminar(id)) {
                    JOptionPane.showMessageDialog(this, "Libro eliminado.");
                    limpiarCampos();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar libro.");
                }
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void cargarCategorias() {
        List<Categoria> categorias = controlador.listarCategorias();
        for (Categoria c : categorias) {
            cmbCategoria.addItem(c);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<LibroFisico> libros = controlador.listarTodos();
        for (LibroFisico l : libros) {
            modeloTabla.addRow(new Object[]{l.getId(), l.getTitulo(), l.getAutor(), l.getIsbn(), l.getEditorial(), l.getStock(), l.getNombreCategoria()});
        }
    }

    private boolean validarCampos() {
        if (txtTitulo.getText().trim().isEmpty() || txtAutor.getText().trim().isEmpty() ||
                txtIsbn.getText().trim().isEmpty() || txtEditorial.getText().trim().isEmpty() ||
                txtStock.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar todos los campos.");
            return false;
        }
        try {
            Integer.parseInt(txtStock.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El stock debe ser un número.");
            return false;
        }
        return true;
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtIsbn.setText("");
        txtEditorial.setText("");
        txtStock.setText("");
        cmbCategoria.setSelectedIndex(0);
        tabla.clearSelection();
    }
}