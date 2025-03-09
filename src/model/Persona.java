package model;

/**
 * La clase Persona representa una persona con atributos básicos como nombre, apellido, fecha de nacimiento,
 * identificacion y cargo.
 */
public class Persona {

    // Atributos privados de la clase
    private String nombre;          // Almacena el nombre de la persona
    private String apellido;        // Almacena el apellido de la persona
    private String fechaNacimiento; // Almacena la fecha de nacimiento de la persona
    private String identificacion;  // Almacena el número de identificación de la persona
    private String cargo;           // Almacena el cargo o rol de la persona

    /**
     * Constructor de la clase Persona.
     * @param nombre El nombre de la persona.
     * @param apellido El apellido de la persona.
     * @param fechaNacimiento La fecha de nacimiento de la persona.
     * @param identificacion El número de identificación de la persona.
     * @param cargo El cargo o rol de la persona.
     */
    public Persona(String nombre, String apellido, String fechaNacimiento, String identificacion, String cargo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.identificacion = identificacion;
        this.cargo = cargo;
    }

    // Métodos getter para obtener los valores de los atributos

    /**
     * Obtiene el nombre de la persona.
     * @return El nombre de la persona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el apellido de la persona.
     * @return El apellido de la persona.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Obtiene el cargo o rol de la persona.
     * @return El cargo de la persona.
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Obtiene el número de identificación de la persona.
     * @return El número de identificación.
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * Sobrescribe el método toString para proporcionar una representación de la persona en formato legible.
     * @return Una cadena que representa la persona con su nombre, apellido, fecha de nacimiento,
     *         identificacion y cargo.
     */
    @Override
    public String toString() {
        return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", fechaNacimiento=" + fechaNacimiento
                + ", identificacion=" + identificacion + ", cargo=" + cargo + "]";
    }

}