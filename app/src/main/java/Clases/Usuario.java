package Clases;

public class Usuario{

    String nombre, correo, tlf;


    public Usuario(String nombre, String correo, String tlf) {
        this.nombre = nombre;
        this.correo = correo;
        this.tlf = tlf;
    }

    public Usuario(){
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }
}
