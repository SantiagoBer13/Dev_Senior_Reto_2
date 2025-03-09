package model;

/**
 * La clase Ubication representa una ubicación en una dirección, que se compone de una calle, una carrera y una descripción.
 */
public class Ubication {

    // Atributos privados de la clase
    private String calle;      // Almacena el nombre de la calle junto con un número
    private String carrera;    // Almacena el nombre de la carrera junto con un número
    private String descripcion; // Almacena una descripción adicional de la ubicación

    /**
     * Constructor de la clase Ubication.
     * @param calle La calle de la ubicación, en formato "nombre número".
     * @param carrera La carrera de la ubicación, en formato "nombre número".
     * @param descripcion Una descripción adicional sobre la ubicación.
     */
    public Ubication(String calle, String carrera, String descripcion) {
        this.calle = calle;
        this.carrera = carrera;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el número asociado a la calle. Se extrae del atributo 'calle', asumiendo que la cadena está en el formato "nombre número".
     * @return El número de la calle como un valor entero.
     */
    public int getCalle() {
        return Integer.valueOf(calle.split(" ")[1]); // Extrae el número de la calle después del primer espacio
    }

    /**
     * Obtiene el número asociado a la carrera. Se extrae del atributo 'carrera', asumiendo que la cadena está en el formato "nombre número".
     * @return El número de la carrera como un valor entero.
     */
    public int getCarrera() {
        return Integer.parseInt(carrera.split(" ")[1]); // Extrae el número de la carrera después del primer espacio
    }

    /**
     * Obtiene la descripción adicional de la ubicación.
     * @return La descripción como una cadena de texto.
     */
    public String getDescripcion() {
        return descripcion; // Retorna la descripción almacenada
    }

    /**
     * Sobrescribe el método toString para proporcionar una representación de la ubicación en formato legible.
     * @return Una cadena que representa la ubicación, mostrando calle, carrera y descripción.
     */
    @Override
    public String toString() {
        return "Ubicación {" +
                " calle = '" + calle + '\'' +
                ", carrera = '" + carrera + '\'' +
                ", descripcion = '" + descripcion + '\'' +
                '}';
    }
}
