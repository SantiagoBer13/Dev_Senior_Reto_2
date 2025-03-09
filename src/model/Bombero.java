package model;

/**
 * La clase Bombero implementa la interfaz IResponder y representa a un bombero que atiende emergencias de incendios.
 */
public class Bombero implements IResponder {

    private Persona persona; // Persona que representa al bombero
    private boolean disponible = true; // Estado de disponibilidad del bombero

    /**
     * Constructor que inicializa al bombero con la persona asociada.
     * @param persona La persona que representa al bombero.
     */
    public Bombero(Persona persona) {
        this.persona = persona;
    }

    /**
     * Implementación del método atenderEmergencia() para atender una emergencia de incendio.
     */
    @Override
    public void atenderEmergencia() {
        System.out.println("Bombero " + persona.getNombre() + " preparandose para emergencia de incendio.");
        disponible = false;
    }

    /**
     * Implementación del método evaluarEstado() para evaluar el estado de la emergencia de incendio.
     * @return El estado de la emergencia de incendio.
     */
    @Override
    public String evaluarEstado() {
        return persona.getNombre() + " evaluando estado de la emergencia de incendio.\n";
    }

    /**
     * Implementación del método liberar() para liberar al bombero y marcarlo como disponible.
     */
    @Override
    public void liberar() {
        disponible = true;
    }

    /**
     * Obtiene el estado de disponibilidad del bombero.
     * @return true si está disponible, false si está atendiendo una emergencia.
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Obtiene la persona que representa al bombero.
     * @return La persona que representa al bombero.
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * Sobrescribe el método toString() para representar al bombero.
     * @return Una cadena con los detalles del bombero.
     */
    @Override
    public String toString() {
        return "Bombero [" + persona.toString() + ", disponible=" + disponible + "]";
    }
}