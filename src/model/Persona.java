package model;
public class Persona {
    private String nombre;
    private String apellido;
    private String fechaNacimiento;
    private String identificacion;
    private String cargo;
 
    public Persona(String nombre, String apellido, String fechaNacimiento, String identificacion, String cargo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.identificacion = identificacion;
        this.cargo = cargo;
    }
    
    // Añadidos getters
    public String getNombre() {
        return nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public String getCargo() {
        return cargo;
    }
    
    public String getIdentificacion() {
        return identificacion;
    }

    @Override
    public String toString() {
        return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", fechaNacimiento=" + fechaNacimiento
                + ", identificacion=" + identificacion + ", cargo=" + cargo + "]";
    }

}