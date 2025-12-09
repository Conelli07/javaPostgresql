package config;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private Connection connection;
    public DBConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/product_management_db",
                "product_manager_user",
                "123456"
        );
    }
    public Connection getConnection() {
        return connection;
    }
}
