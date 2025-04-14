package util;

import util.DatabaseConnection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class TestConnection {
    private static final Logger LOGGER = Logger.getLogger(TestConnection.class.getName());

    public static void main(String[] args) {
        try {
            DatabaseConnection dbConnection = DatabaseConnection.getInstance(); // Singleton

            if (dbConnection.getConnection() != null && !dbConnection.getConnection().isClosed()) {
                System.out.println("✅ Conexión exitosa a SQL Server!");
                System.out.println("▸ Base de datos: " + dbConnection.getConnection().getCatalog());
                System.out.println("▸ Usuario: " + dbConnection.getConnection().getMetaData().getUserName());
                dbConnection.getConnection().close();
            }
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión:");
            System.err.println("▸ Código de error: " + e.getErrorCode());
            System.err.println("▸ Mensaje: " + e.getMessage());
            LOGGER.severe("Error de conexión: " + e.getMessage());
        }
    }
}