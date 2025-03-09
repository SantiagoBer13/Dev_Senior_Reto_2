package model.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase RespondTeamCoordinator implementa la interfaz Subject y se encarga de coordinar el equipo de respuesta
 * en una emergencia, notificando a los observadores cuando es necesario que actúen.
 */
public class RespondTeamCoordinator implements Subject {

    private List<Observer> observers; // Lista de observadores (equipos de respuesta)

    /**
     * Constructor que inicializa la lista de observadores.
     */
    public RespondTeamCoordinator(){
        this.observers = new ArrayList<>();
    }

    /**
     * Añade un observador a la lista de observadores.
     * @param observer El observador que será añadido.
     */
    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * Elimina un observador de la lista de observadores.
     * @param observer El observador que será eliminado.
     */
    @Override
    public void deleteObserver(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * Notifica a todos los observadores registrados para que actúen.
     */
    @Override
    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.atender();
        }
    }

    /**
     * Método que coordina la respuesta del equipo, verificando si todos los observadores (respondientes) 
     * están en la escena y luego notifica a todos los observadores si es necesario.
     */
    public void respondTeam(){
        boolean teamInTheScene = false;
        for (Observer observer : observers) {
            if(observer.estaEnLaEscena()){
                teamInTheScene = true;
            }else{
                teamInTheScene = false;
                break;
            }
        }
        if(teamInTheScene){
            notifyObservers();
        }
    }
}