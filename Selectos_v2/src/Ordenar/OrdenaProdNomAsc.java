package Ordenar;
import Clases.Compra;
import java.util.Comparator;

/**
 *
 * @author AA2020
 */
public class OrdenaProdNomAsc 
        implements Comparator<Compra>{

    @Override
    public int compare(Compra p1, Compra p2) {
        return p1.getProductoNombre().compareTo(p2.getProductoNombre());
    }
 }
