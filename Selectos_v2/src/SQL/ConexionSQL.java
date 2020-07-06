package SQL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
/**
 *
 * @author AA2020
 */
public class ConexionSQL {
    Connection conexion;
    
    public void ConnectDataBase(){
        try {
            Class.forName("org.postgresql.Driver") ;
              conexion = DriverManager.getConnection(
                      "jdbc:postgresql://127.0.0.1:5432/super_selectos", "postgres", "laam");
        } catch (Exception e) 
        {
            JOptionPane.showMessageDialog(null, "Recurso No Encontrado 404: No funciona la CONEXION con la BASE DE DATOS");
        }
    }
    
    
    public void InsertarProducto(String nombreProducto, int cantidadProducto, double precioProducto, double subTotal){
        try {
            String query = "INSERT INTO compra (nombre_producto, cantidad, precio_producto, subtotal)" + " VALUES (?, ?, ?, ?)"; 
            
            PreparedStatement pst = conexion.prepareStatement(query);
            
            pst.setString(1, nombreProducto);
            pst.setInt(2, cantidadProducto);
            pst.setDouble(3, precioProducto);
            pst.setDouble(4, subTotal);
            
            pst.execute();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error: "+e.toString() + JOptionPane.ERROR_MESSAGE);  
        }
    }
}
