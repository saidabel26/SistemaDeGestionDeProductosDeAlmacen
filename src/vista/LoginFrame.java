package vista;

import dao.UsuarioDAO;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginFrame extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        panel.add(txtUsuario);

        panel.add(new JLabel("Contrasena:"));
        txtContrasena = new JPasswordField();
        panel.add(txtContrasena);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.addActionListener(e -> validarLogin());

        JButton btnRegistro = new JButton("Registrarse");
        btnRegistro.addActionListener(e -> new RegistroFrame());

        panel.add(btnLogin);
        panel.add(btnRegistro);

        add(panel);
        setVisible(true);
    }

    private void validarLogin() {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡Debe completar todos los campos!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            UsuarioDAO dao = new UsuarioDAO();
            if (dao.login(usuario, contrasena) != null) {
                new PrincipalFrame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de conexion a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}