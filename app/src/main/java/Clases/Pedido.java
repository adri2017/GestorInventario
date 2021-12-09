package Clases;

public class Pedido {

    String estado, cantidad, direccion, nombreper, nombreprod, id;

    //constructor sin estado
    public Pedido(String cantidad, String direccion, String nombreper, String nombreprod) {
        this.cantidad = cantidad;
        this.direccion = direccion;
        this.nombreper = nombreper;
        this.nombreprod = nombreprod;
    }

    //constructor con estado
    public Pedido(String estado, String cantidad, String direccion, String nombreper, String nombreprod, String id) {
        this.estado = estado;
        this.cantidad = cantidad;
        this.direccion = direccion;
        this.nombreper = nombreper;
        this.nombreprod = nombreprod;
        this.id = id;
    }

    public Pedido(){}

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombreper() {
        return nombreper;
    }

    public void setNombreper(String nombreper) {
        this.nombreper = nombreper;
    }

    public String getNombreprod() {
        return nombreprod;
    }

    public void setNombreprod(String nombreprod) {
        this.nombreprod = nombreprod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
