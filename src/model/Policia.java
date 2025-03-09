package model;
public class Policia implements IResponder{
    private Persona persona;
    private boolean disponible = true;
    
    public Policia(Persona persona) {
        this.persona = persona;
    }
    
    @Override
    public void atenderEmergencia() {
        System.out.println("Policía " + persona.getNombre() + " en camino para atender emergencia de seguridad.");
        disponible = false;
    }
    
    @Override
    public String evaluarEstado() {
        return persona.getNombre() + " evaluando estado de la emergencia de seguridad.\n";
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
        return "Policia [" + persona.toString() + ", disponible=" + disponible +"]";
    }

    
}
