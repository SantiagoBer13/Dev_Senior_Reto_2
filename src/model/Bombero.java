package model;
public class Bombero implements IResponder {
    private Persona persona;
    private boolean disponible = true;
    
    public Bombero(Persona persona) {
        this.persona = persona;
    }
    
    @Override
    public void atenderEmergencia() {
        System.out.println("Bombero " + persona.getNombre() + " preparandose para emergencia de incendio.");
        disponible = false;
    }
    
    @Override
    public String evaluarEstado() {
        return persona.getNombre() + " evaluando estado de la emergencia de incendio.\n";
    }
    
    @Override
    public void liberar(){
        disponible = true;
    }
    
    public boolean isDisponible() {
        return disponible;
    }
    
    public Persona getPersona() {
        return persona;
    }

    @Override
    public String toString() {
        return "Bombero [" + persona.toString() + ", disponible=" + disponible + "]";
    }

}