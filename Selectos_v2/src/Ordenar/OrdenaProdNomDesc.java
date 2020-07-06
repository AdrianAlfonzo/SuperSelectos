package Ordenar;
import Clases.Compra;
import java.util.Comparator;

/**
 *
 * @author AA2020
 */
public class OrdenaProdNomDesc 
        implements Comparator<Compra>{

    @Override
    public int compare(Compra c1, Compra c2) {
       return c2.getProductoNombre().compareTo(c1.getProductoNombre());
    }
}
