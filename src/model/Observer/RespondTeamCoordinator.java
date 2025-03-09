package model.Observer;

import java.util.ArrayList;
import java.util.List;

public class RespondTeamCoordinator implements Subject {

    private List<Observer> observers;

    public RespondTeamCoordinator(){
        this.observers = new ArrayList<>();
    }

    @Override
    public void addObserver(Observer observer) {
       this.observers.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
       this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.atender();
        }
    }

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
