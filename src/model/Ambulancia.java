package model;

/**
 * La clase Ambulancia implementa la interfaz IResponder y representa una ambulancia que atiende emergencias médicas.
 */
public class Ambulancia implements IResponder {

    private Persona persona; // Persona que representa al personal de la ambulancia
    private boolean disponible = true; // Estado de disponibilidad de la ambulancia

    /**
     * Constructor que inicializa la ambulancia con la persona asociada.
     * @param persona La persona que representa al personal de la ambulancia.
     */
    public Ambulancia(Persona persona) {
        this.persona = persona;
    }

    /**
     * Implementación del método atenderEmergencia() para atender una emergencia médica.
     */
    @Override
    public void atenderEmergencia() {
        System.out.println("Ambulancia con " + persona.getNombre() + " en camino para atender emergencia médica.");
        disponible = false;
    }

    /**
     * Implementación del método evaluarEstado() para evaluar el estado de la emergencia médica.
     * @return El estado de la emergencia médica.
     */
    @Override
    public String evaluarEstado() {
        return persona.getNombre() + " evaluando estado de la emergencia médica.\n";
    }

    /**
     * Implementación del método liberar() para liberar a la ambulancia y marcarla como disponible.
     */
    @Override
    public void liberar() {
        disponible = true;
    }

    /**
     * Obtiene el estado de disponibilidad de la ambulancia.
     * @return true si está disponible, false si está atendiendo una emergencia.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Obtiene la persona que representa a la ambulancia.
     * @return La persona que representa a la ambulancia.
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Sobrescribe el método toString() para representar la ambulancia.
     * @return Una cadena con los detalles de la ambulancia.
     */
    @Override
    public String toString() {
        return "Ambulancia [ " + persona.toString() + ", disponible=" + disponible + "]";
    }
}