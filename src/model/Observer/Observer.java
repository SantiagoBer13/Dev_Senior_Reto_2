package model.Observer;

/**
 * La interfaz Observer define los métodos que debe implementar cualquier clase
 * que observe y responda a una emergencia.
 */
public interface Observer {

    /**
     * Método para atender la emergencia.
     */
    void atender();

    /**
     * Método que indica si el observador está presente en la escena de la emergencia.
     * @return true si está en la escena, false si no está en la escena.
     */
    boolean estaEnLaEscena();
    
}
