package vista;

import dao.UsuarioDAO;
import modelo.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class GestionUsuarios extends JFrame {
    private final UsuarioDAO usuarioDAO;
    private final DefaultTableModel model = new DefaultTableModel();
    private final JTable tablaUsuarios = new JTable(model);

    public GestionUsuarios() {
        setTitle("Gestion de Usuarios");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializar DAO
        UsuarioDAO tempDAO;
        try {
            tempDAO = new UsuarioDAO();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error de conexion: " + e.getMessage());
            tempDAO = null;
        }
        usuarioDAO = tempDAO;

        if (usuarioDAO == null) {
            dispose();
            return;
        }

        // Configurar tabla
        String[] columnas = {"Usuario", "Nombre", "Apellido", "Telefono", "Correo"};
        model.setColumnIdentifiers(columnas);
        actualizarTabla();

        // Botones
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnVolver = new JButton("Volver");

        btnNuevo.addActionListener(e -> nuevoUsuario());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnEliminar.addActionListener(e -> eliminarUsuario());
        btnVolver.addActionListener(e -> dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnNuevo);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        // Layout
        setLayout(new BorderLayout());
        add(new JScrollPane(tablaUsuarios), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void actualizarTabla() {
        model.setRowCount(0);
        try {
            List<Usuario> usuarios = usuarioDAO.obtenerTodosUsuarios();
            for (Usuario u : usuarios) {
                model.addRow(new Object[]{
                        u.getUsuario(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getTelefono(),
                        u.getCorreo()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage());
        }
    }

    private void nuevoUsuario() {
        new RegistroFrame(); // Llama al constructor sin parámetros
    }

    private void actualizarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }

        String usuario = (String) model.getValueAt(filaSeleccionada, 0);
        try {
            Usuario user = usuarioDAO.obtenerUsuarioPorUsername(usuario);
            new RegistroFrame(user); // Llama al constructor con parámetro Usuario
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void eliminarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }

        String usuario = (String) model.getValueAt(filaSeleccionada, 0);
        try {
            if (usuarioDAO.eliminarUsuario(usuario)) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado");
                actualizarTabla();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}