package model;
public class Ambulancia implements IResponder {
    private Persona persona;
    private boolean disponible = true;
    
    public Ambulancia(Persona persona) {
        this.persona = persona;
    }
    
    @Override
    public void atenderEmergencia() {
        System.out.println("Ambulancia con " + persona.getNombre() + " en camino para atender emergencia médica.");
        disponible = false;
    }
    
    @Override
    public String evaluarEstado() {
        return persona.getNombre() + " evaluando estado de la emergencia médica.\n";
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
        return "Ambulancia [ " + persona.toString() + ", disponible=" + disponible +"]";
    }

}
