package model;

/**
 * La clase Policia implementa la interfaz IResponder y representa a un policía que atiende emergencias de seguridad.
 */
public class Policia implements IResponder {

    private Persona persona; // Persona que representa al policía
    private boolean disponible = true; // Estado de disponibilidad del policía

    /**
     * Constructor que inicializa al policía con la persona asociada.
     * @param persona La persona que representa al policía.
     */
    public Policia(Persona persona) {
        this.persona = persona;
    }

    /**
     * Implementación del método atenderEmergencia() para atender una emergencia de seguridad.
     */
    @Override
    public void atenderEmergencia() {
        System.out.println("Policía " + persona.getNombre() + " en camino para atender emergencia de seguridad.");
        disponible = false;
    }

    /**
     * Implementación del método evaluarEstado() para evaluar el estado de la emergencia de seguridad.
     * @return El estado de la emergencia de seguridad.
     */
    @Override
    public String evaluarEstado() {
        return persona.getNombre() + " evaluando estado de la emergencia de seguridad.\n";
    }

    /**
     * Implementación del método liberar() para liberar al policía y marcarlo como disponible.
     */
    @Override
    public void liberar() {
        disponible = true;
    }

    /**
     * Obtiene el estado de disponibilidad del policía.
     * @return true si está disponible, false si está atendiendo una emergencia.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Obtiene la persona que representa al policía.
     * @return La persona que representa al policía.
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Sobrescribe el método toString() para representar al policía.
     * @return Una cadena con los detalles del policía.
     */
    @Override
    public String toString() {
        return "Policia [" + persona.toString() + ", disponible=" + disponible + "]";
    }
}