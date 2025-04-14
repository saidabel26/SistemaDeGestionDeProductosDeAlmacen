package dao;

import modelo.Producto;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private final Connection conexion;

    public ProductoDAO() throws SQLException {
        conexion = DatabaseConnection.getInstance().getConnection();
    }

    // CRUD Completo
    public boolean agregarProducto(Producto producto) throws SQLException {
        String sql = "INSERT INTO productos (nombre, marca, categoria, precio, cantidad) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getMarca());
            pstmt.setString(3, producto.getCategoria());
            pstmt.setDouble(4, producto.getPrecio());
            pstmt.setInt(5, producto.getCantidad());
            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Producto> obtenerTodosProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("marca"),
                        rs.getString("categoria"),
                        rs.getDouble("precio"),
                        rs.getInt("cantidad")
                ));
            }
        }
        return productos;
    }

    public boolean actualizarProducto(Producto producto) throws SQLException {
        String sql = "UPDATE productos SET nombre=?, marca=?, categoria=?, precio=?, cantidad=? WHERE id=?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, producto.getNombre());
            pstmt.setString(2, producto.getMarca());
            pstmt.setString(3, producto.getCategoria());
            pstmt.setDouble(4, producto.getPrecio());
            pstmt.setInt(5, producto.getCantidad());
            pstmt.setInt(6, producto.getId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean eliminarProducto(int id) throws SQLException {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }

    public Producto obtenerProductoPorId(int id) throws SQLException {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next() ? new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("marca"),
                    rs.getString("categoria"),
                    rs.getDouble("precio"),
                    rs.getInt("cantidad")
            ) : null;
        }
    }
}