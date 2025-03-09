package model;

/**
 * La interfaz IResponder define los métodos básicos que deben implementar las clases
 * encargadas de responder a emergencias.
 */
public interface IResponder {

    /**
     * Método que simula la acción de atender una emergencia.
     */
    void atenderEmergencia();

    /**
     * Método que evalúa el estado de la emergencia.
     * @return Una cadena con el estado actual de la emergencia.
     */
    String evaluarEstado();

    /**
     * Método que libera al responder para que pueda atender otra emergencia.
     */
    void liberar();
}