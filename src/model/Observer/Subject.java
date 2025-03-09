package model.Observer;

public interface Subject {

    void addObserver(Observer observer);
    void deleteObserver(Observer observer);
    void notifyObservers();

}
