package Clases;

import android.net.Uri;

public class Producto {


    public String descripcion, nombre, cantidad, precio, urlimagen;


    public Producto(String cantidad, String descripcion, String nombre, String precio) {
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    public Producto(String cantidad, String descripcion, String nombre, String precio, String urlimagen) {
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.urlimagen = urlimagen;
    }


    public Producto(){}


    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlimagen() {
        return urlimagen;
    }

    public void setUrlimagen(String urlimagen) {
        this.urlimagen = urlimagen;
    }
}
