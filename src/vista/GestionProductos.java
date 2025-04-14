package vista;

import dao.ProductoDAO;
import modelo.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class GestionProductos extends JFrame {
    private final ProductoDAO productoDAO;
    private final DefaultTableModel model = new DefaultTableModel();
    private final JTable tablaProductos = new JTable(model);

    public GestionProductos() {
        setTitle("Gestión de Productos");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicialización de DAO
        ProductoDAO tempDAO;
        try {
            tempDAO = new ProductoDAO();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error de conexión: " + e.getMessage());
            tempDAO = null;
        }
        productoDAO = tempDAO;

        if (productoDAO == null) {
            dispose();
            return;
        }

        // Configurar modelo de tabla
        String[] columnas = {"ID", "Nombre", "Marca", "Categoría", "Precio", "Cantidad"};
        model.setColumnIdentifiers(columnas);
        actualizarTabla();

        // Botones
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Volver");

        // Listeners
        btnNuevo.addActionListener(e -> nuevoProducto());
        btnActualizar.addActionListener(e -> actualizarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnVolver.addActionListener(e -> dispose());

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnNuevo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        // Layout
        setLayout(new BorderLayout());
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void actualizarTabla() {
        model.setRowCount(0);
        try {
            List<Producto> productos = productoDAO.obtenerTodosProductos();
            for (Producto p : productos) {
                model.addRow(new Object[]{
                        p.getId(),
                        p.getNombre(),
                        p.getMarca(),
                        p.getCategoria(),
                        p.getPrecio(),
                        p.getCantidad()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage());
        }
    }

    private void nuevoProducto() {
        new FormularioProducto(this, null);
    }

    private void actualizarProducto() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return;
        }
        int id = (int) model.getValueAt(filaSeleccionada, 0);
        try {
            Producto producto = productoDAO.obtenerProductoPorId(id);
            new FormularioProducto(this, producto);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener producto: " + e.getMessage());
        }
    }

    private void eliminarProducto() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto");
            return;
        }
        int id = (int) model.getValueAt(filaSeleccionada, 0);
        try {
            if (productoDAO.eliminarProducto(id)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado");
                actualizarTabla();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + e.getMessage());
        }
    }

    private class FormularioProducto extends JFrame {
        public FormularioProducto(JFrame parent, Producto producto) {
            setTitle(producto == null ? "Nuevo Producto" : "Editar Producto");
            setSize(300, 250);
            setLocationRelativeTo(parent);

            JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JTextField txtNombre = new JTextField();
            JTextField txtMarca = new JTextField();
            JTextField txtCategoria = new JTextField();
            JTextField txtPrecio = new JTextField();
            JTextField txtCantidad = new JTextField();

            if (producto != null) {
                txtNombre.setText(producto.getNombre());
                txtMarca.setText(producto.getMarca());
                txtCategoria.setText(producto.getCategoria());
                txtPrecio.setText(String.valueOf(producto.getPrecio()));
                txtCantidad.setText(String.valueOf(producto.getCantidad()));
            }

            panel.add(new JLabel("Nombre*:"));
            panel.add(txtNombre);
            panel.add(new JLabel("Marca*:"));
            panel.add(txtMarca);
            panel.add(new JLabel("Categoría*:"));
            panel.add(txtCategoria);
            panel.add(new JLabel("Precio*:"));
            panel.add(txtPrecio);
            panel.add(new JLabel("Cantidad*:"));
            panel.add(txtCantidad);

            JButton btnGuardar = new JButton("Guardar");
            btnGuardar.addActionListener(e -> guardarProducto(
                    producto != null ? producto.getId() : -1,
                    txtNombre.getText(),
                    txtMarca.getText(),
                    txtCategoria.getText(),
                    txtPrecio.getText(),
                    txtCantidad.getText()
            ));

            panel.add(btnGuardar);
            add(panel);
            setVisible(true);
        }

        private void guardarProducto(int id, String nombre, String marca, String categoria,
                                     String precioStr, String cantidadStr) {
            if (nombre.isEmpty() || marca.isEmpty() || categoria.isEmpty() ||
                    precioStr.isEmpty() || cantidadStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
                return;
            }

            try {
                double precio = Double.parseDouble(precioStr);
                int cantidad = Integer.parseInt(cantidadStr);
                Producto producto = new Producto(id, nombre, marca, categoria, precio, cantidad);

                boolean exito;
                if (id == -1) {
                    exito = productoDAO.agregarProducto(producto);
                } else {
                    exito = productoDAO.actualizarProducto(producto);
                }

                if (exito) {
                    JOptionPane.showMessageDialog(this, "Operación exitosa");
                    GestionProductos.this.actualizarTabla();
                    dispose();
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Formato numérico inválido");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error de base de datos: " + e.getMessage());
            }
        }
    }
}