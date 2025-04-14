package vista;

import dao.UsuarioDAO;
import modelo.Usuario;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class RegistroFrame extends JFrame {
    private final JTextField txtUsuario;
    private final JTextField txtNombre;
    private final JTextField txtApellido;
    private final JTextField txtTelefono;
    private final JTextField txtCorreo;
    private final JPasswordField txtContrasena;
    private final JPasswordField txtConfirmarContrasena;

    // Constructor para nuevo usuario
    public RegistroFrame() {
        this(null);
    }

    // Constructor para editar usuario
    public RegistroFrame(Usuario usuario) {
        setTitle(usuario == null ? "Nuevo Usuario" : "Editar Usuario");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtUsuario = new JTextField();
        txtNombre = new JTextField();
        txtApellido = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();
        txtContrasena = new JPasswordField();
        txtConfirmarContrasena = new JPasswordField();

        if (usuario != null) {
            txtUsuario.setText(usuario.getUsuario());
            txtNombre.setText(usuario.getNombre());
            txtApellido.setText(usuario.getApellido());
            txtTelefono.setText(usuario.getTelefono());
            txtCorreo.setText(usuario.getCorreo());
        }

        panel.add(new JLabel("Usuario*:"));
        panel.add(txtUsuario);
        panel.add(new JLabel("Nombre*:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Apellido*:"));
        panel.add(txtApellido);
        panel.add(new JLabel("Telefono*:"));
        panel.add(txtTelefono);
        panel.add(new JLabel("Correo*:"));
        panel.add(txtCorreo);
        panel.add(new JLabel("Contrasena*:"));
        panel.add(txtContrasena);
        panel.add(new JLabel("Confirmar Contrasena*:"));
        panel.add(txtConfirmarContrasena);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarUsuario(usuario));

        panel.add(btnGuardar);
        add(panel);
        setVisible(true);
    }

    private void guardarUsuario(Usuario usuarioExistente) {
        String usuario = txtUsuario.getText().trim();
        String nombre = txtNombre.getText().trim();
        String apellido = txtApellido.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        String confirmarContrasena = new String(txtConfirmarContrasena.getPassword());

        // Validaciones
        if (usuario.isEmpty() || nombre.isEmpty() || apellido.isEmpty() ||
                telefono.isEmpty() || correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
            return;
        }

        if (!contrasena.equals(confirmarContrasena)) {
            JOptionPane.showMessageDialog(this, "Las contrasenas no coinciden");
            return;
        }

        try {
            Usuario nuevoUsuario = new Usuario(
                    usuario, nombre, apellido, telefono, correo, contrasena
            );

            UsuarioDAO dao = new UsuarioDAO();
            boolean exito;

            if (usuarioExistente == null) {
                exito = dao.registrarUsuario(nuevoUsuario);
            } else {
                exito = dao.actualizarUsuario(usuarioExistente.getUsuario(), nuevoUsuario);
            }

            if (exito) {
                JOptionPane.showMessageDialog(this, "Operacion exitosa");
                dispose();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de base de datos: " + ex.getMessage());
        }
    }
}