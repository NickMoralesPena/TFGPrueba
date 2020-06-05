package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CRUD {
    Connection conexion = null;

    //Con este metodo realizamos la conexión
    public Connection realizarConexion()throws SQLException {

        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/aseguradora", "administrador", "pass");
            System.out.println("Conexion exitosa guay");
            return conexion;
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            return null;
        }
    }
    public void close() throws SQLException {
        conexion.close();
    }
}
