package dao;

import modelo.Usuario;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final Connection conexion;

    public UsuarioDAO() throws SQLException {
        conexion = DatabaseConnection.getInstance().getConnection();
    }

    // Método para eliminar usuario
    public boolean eliminarUsuario(String usuario) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE usuario = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            return pstmt.executeUpdate() > 0;
        }
    }

    // Método para login
    public Usuario login(String usuario, String contrasena) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            pstmt.setString(2, contrasena);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new Usuario(
                    rs.getString("usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("contrasena")
            ) : null;
        }
    }

    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, usuario.getUsuario());
            pstmt.setString(2, usuario.getNombre());
            pstmt.setString(3, usuario.getApellido());
            pstmt.setString(4, usuario.getTelefono());
            pstmt.setString(5, usuario.getCorreo());
            pstmt.setString(6, usuario.getContrasena());
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Usuario> obtenerTodosUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getString("usuario"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("telefono"),
                        rs.getString("correo"),
                        rs.getString("contrasena")
                ));
            }
        }
        return usuarios;
    }

    public boolean actualizarUsuario(String usuarioOriginal, Usuario usuarioActualizado) throws SQLException {
        String sql = "UPDATE usuarios SET usuario=?, nombre=?, apellido=?, telefono=?, correo=?, contrasena=? WHERE usuario=?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, usuarioActualizado.getUsuario());
            pstmt.setString(2, usuarioActualizado.getNombre());
            pstmt.setString(3, usuarioActualizado.getApellido());
            pstmt.setString(4, usuarioActualizado.getTelefono());
            pstmt.setString(5, usuarioActualizado.getCorreo());
            pstmt.setString(6, usuarioActualizado.getContrasena());
            pstmt.setString(7, usuarioOriginal);
            return pstmt.executeUpdate() > 0;
        }
    }

    public Usuario obtenerUsuarioPorUsername(String usuario) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new Usuario(
                    rs.getString("usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("telefono"),
                    rs.getString("correo"),
                    rs.getString("contrasena")
            ) : null;
        }
    }
}