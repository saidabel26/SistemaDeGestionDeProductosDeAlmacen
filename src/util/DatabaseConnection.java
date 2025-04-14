package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private final Connection connection;
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=AlmacenDB;encrypt=true;trustServerCertificate=true";
    private static final String USUARIO = "saiddeoleo";
    private static final String CONTRASENA = "contrasena2606";

    private DatabaseConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            this.connection = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver no encontrado", ex);
        }
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}