package tarea2;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.xdevapi.PreparableStatement;

public class Conexion {
    String bd = "registro";
    String url = "jdbc:mysql://localhost:3306/";
    String user = "root";
    String password = "root";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection con;
//a
    public Connection conectar() {
        System.out.println(bd);
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url + bd, user, password);
            System.out.println("Se conecto a " + bd);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Conexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            System.out.println("No se conecto a " + bd);
        }
        return con;
    }

    public void desconectar() {
        try {
            con.close();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(Conexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public void agregarBD(String pais, int oro, int plata, int bronce, int medallastotales) {
        try {
            if (con != null) {
                String sql = "INSERT INTO medallero (paises, oro, plata, bronce, medallastotales) VALUES (?, ?, ?, ?, ?)";
                java.sql.PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, pais);
                pst.setInt(2, oro);
                pst.setInt(3, plata);
                pst.setInt(4, bronce);
                pst.setInt(5, medallastotales);

                pst.executeUpdate();
            } else {
                System.err.println("La conexión es null.");
            }
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Conexion.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }

    }

    public void mostrarBD() {
        try {
            if (con != null) {
                Statement st = con.createStatement();
                java.sql.ResultSet rs = st.executeQuery("SELECT * FROM medallero");

                while (rs.next()) {

                    System.out.println(rs.getString("paises") + " " + rs.getInt("oro") + " " + rs.getInt("plata") + " "
                            + rs.getInt("bronce") + " " + rs.getInt("medallastotales"));
                }
            } else {
                System.err.println("La conexión es null.");
            }
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Conexion.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
        }
    }

    // FIXME: No funciona
    public void verificarPais(String pais) {
        Boolean existe = false;

        try {
            if (con != null) {
                String paisBuscado = "NombreDelPais";
                String sql = "SELECT * FROM medallero WHERE paises = ?";
                try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                    preparedStatement.setString(1, paisBuscado);

                    try (ResultSet rs = preparedStatement.executeQuery()) {
                        while (rs.next()) {
                            // Obtener los valores de las columnas específicas
                            String paises = rs.getString("paises");
                            int oro = rs.getInt("oro");
                            int plata = rs.getInt("plata");
                            int bronce = rs.getInt("bronce");
                            int medallasTotales = rs.getInt("medallas_totales");

                            // Hacer algo con los valores, por ejemplo, imprimirlos
                            System.out.println("Pais: " + paises + ", Oro: " + oro + ", Plata: " + plata + ", Bronce: "
                                    + bronce + ", Medallas Totales: " + medallasTotales);
                        }
                    }
                }
            } else {
                System.err.println("La conexión es null.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
