package Clases;
/**
 *
 * @author AA2020
 */
public class Compra {
    private String productoNombre;
    private int productoCantidad;
    private double productoPrecio;
    private double subTotal;    
    
    
    public Compra(){}
    
    
    public Compra(String prodNombre, int prodCantidad, double prodPrecio, double subTotal){
        this.productoNombre = prodNombre;
        this.productoCantidad = prodCantidad;
        this.productoPrecio = prodPrecio;
        this.subTotal = subTotal= (this.productoCantidad * this.productoPrecio);
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public int getProductoCantidad() {
        return productoCantidad;
    }

    public void setProductoCantidad(int productoCantidad) {
        this.productoCantidad = productoCantidad;
    }

    public double getProductoPrecio() {
        return productoPrecio;
    }

    public void setProductoPrecio(double productoPrecio) {
        this.productoPrecio = productoPrecio;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}