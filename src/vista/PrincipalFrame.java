package vista;

import javax.swing.*;
import java.awt.*;

public class PrincipalFrame extends JFrame {
    public PrincipalFrame() {
        setTitle("Menú Principal");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnUsuarios = new JButton("Gestión de Usuarios");
        JButton btnProductos = new JButton("Gestión de Productos");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        btnUsuarios.addActionListener(e -> new GestionUsuarios());
        btnProductos.addActionListener(e -> new GestionProductos());
        btnCerrarSesion.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(btnUsuarios);
        panel.add(btnProductos);
        panel.add(btnCerrarSesion);

        add(panel);
        setVisible(true);
    }
}