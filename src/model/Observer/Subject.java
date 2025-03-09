package model.Observer;

/**
 * La interfaz Subject define los métodos que debe implementar cualquier clase que gestione
 * una lista de observadores, permitiendo agregar, eliminar y notificar a los observadores.
 */
public interface Subject {

    /**
     * Añade un observador a la lista de observadores.
     * @param observer El observador que será añadido.
     */
    void addObserver(Observer observer);

    /**
     * Elimina un observador de la lista de observadores.
     * @param observer El observador que será eliminado.
     */
    void deleteObserver(Observer observer);

    /**
     * Notifica a todos los observadores registrados para que actúen.
     */
    void notifyObservers();
}