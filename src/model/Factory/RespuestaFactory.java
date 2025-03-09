package model.Factory;

import java.util.List;
import model.Ambulancia;
import model.Bombero;
import model.Emergency;
import model.Policia;
import model.Observer.RespondTeamCoordinator;
import model.Singleton.ManageAmbulancias;
import model.Singleton.ManageBomberos;
import model.Singleton.ManagePolicias;
import utils.Gravedad;

/**
 * La clase RespuestaFactory se encarga de coordinar y asignar las respuestas adecuadas a las emergencias
 * según el tipo de respuesta (Bombero, Médico, Seguridad) y la gravedad de la emergencia. 
 * Utiliza el patrón Singleton para gestionar los equipos de bomberos, ambulancias y policías.
 */
public class RespuestaFactory {
    private ManageBomberos manageBomberos;
    private ManageAmbulancias manageAmbulancias;
    private ManagePolicias managePolicias;

    /**
     * Constructor de la clase RespuestaFactory. Inicializa las instancias de los manejadores de bomberos, ambulancias y policías.
     */
    public RespuestaFactory() {
        this.manageBomberos = ManageBomberos.getInstance();
        this.manageAmbulancias = ManageAmbulancias.getInstance();
        this.managePolicias = ManagePolicias.getInstance();
    }

    /**
     * Asigna las respuestas adecuadas a una emergencia según la gravedad de la misma.
     * 
     * @param emergency La emergencia a la que se debe asignar un equipo de respuesta.
     */
    public void getRespuestas(Emergency emergency) {
        Gravedad gravedad = emergency.getGravedad();
        RespondTeamCoordinator respondTeamCoordinator = new RespondTeamCoordinator();

        // Asignación de equipos según el tipo de emergencia y la gravedad
        switch (emergency.getTypeRespuesta()) {
            case BOMBERO:
                asignarBomberos(gravedad, emergency, respondTeamCoordinator);
                break;
            case MEDICA:
                asignarAmbulancias(gravedad, emergency, respondTeamCoordinator);
                break;
            case SEGURIDAD:
                asignarPolicias(gravedad, emergency, respondTeamCoordinator);
                break;
        }
    }

    /**
     * Asigna los bomberos según la gravedad de la emergencia.
     * 
     * @param gravedad La gravedad de la emergencia.
     * @param emergency La emergencia.
     * @param respondTeamCoordinator El coordinador del equipo de respuesta.
     */
    private void asignarBomberos(Gravedad gravedad, Emergency emergency, RespondTeamCoordinator respondTeamCoordinator) {
        switch (gravedad) {
            case BAJA:
                respondTeamCoordinator.addObserver(manageBomberos.crearAtencion(emergency, 1, respondTeamCoordinator));
                break;
            case MEDIA:
                respondTeamCoordinator.addObserver(manageBomberos.crearAtencion(emergency, 2, respondTeamCoordinator));
                respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion(emergency, 1, respondTeamCoordinator));
                break;
            case ALTA:
                respondTeamCoordinator.addObserver(manageBomberos.crearAtencion(emergency, 3, respondTeamCoordinator));
                respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion(emergency, 2, respondTeamCoordinator));
                respondTeamCoordinator.addObserver(managePolicias.crearAtencion(emergency, 2, respondTeamCoordinator));
                break;
        }
    }

    /**
     * Asigna las ambulancias según la gravedad de la emergencia.
     * 
     * @param gravedad La gravedad de la emergencia.
     * @param emergency La emergencia.
     * @param respondTeamCoordinator El coordinador del equipo de respuesta.
     */
    private void asignarAmbulancias(Gravedad gravedad, Emergency emergency, RespondTeamCoordinator respondTeamCoordinator) {
        switch (gravedad) {
            case BAJA:
                respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion(emergency, 1, respondTeamCoordinator));
                break;
            case MEDIA:
                respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion(emergency, 2, respondTeamCoordinator));
                respondTeamCoordinator.addObserver(manageBomberos.crearAtencion(emergency, 1, respondTeamCoordinator));
                break;
            case ALTA:
                respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion(emergency, 3, respondTeamCoordinator));
                respondTeamCoordinator.addObserver(manageBomberos.crearAtencion(emergency, 1, respondTeamCoordinator));
                respondTeamCoordinator.addObserver(managePolicias.crearAtencion(emergency, 2, respondTeamCoordinator));
                break;
        }
    }

    /**
     * Asigna los policías según la gravedad de la emergencia.
     * 
     * @param gravedad La gravedad de la emergencia.
     * @param emergency La emergencia.
     * @param respondTeamCoordinator El coordinador del equipo de respuesta.
     */
    private void asignarPolicias(Gravedad gravedad, Emergency emergency, RespondTeamCoordinator respondTeamCoordinator) {
        switch (gravedad) {
            case BAJA:
                respondTeamCoordinator.addObserver(managePolicias.crearAtencion(emergency, 1, respondTeamCoordinator));
                break;
            case MEDIA:
                respondTeamCoordinator.addObserver(managePolicias.crearAtencion(emergency, 2, respondTeamCoordinator));
                respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion(emergency, 1, respondTeamCoordinator));
                break;
            case ALTA:
                respondTeamCoordinator.addObserver(managePolicias.crearAtencion(emergency, 4, respondTeamCoordinator));
                respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion(emergency, 2, respondTeamCoordinator));
                respondTeamCoordinator.addObserver(manageBomberos.crearAtencion(emergency, 2, respondTeamCoordinator));
                break;
        }
    }

    /**
     * Muestra el estado de la disponibilidad del personal de policía, bomberos y ambulancias.
     */
    public void getStatusPersona() {
        List<Ambulancia> paramedicos = ManageAmbulancias.getPersonal();
        List<Bombero> bomberos = ManageBomberos.getPersonal();
        List<Policia> policias = ManagePolicias.getPersonal();

        System.out.println("\n=== Disponibilidad de personal de Policia ===\n");
        for (Policia policia : policias) {
            System.out.println(policia);
        }

        System.out.println("\n=== Disponibilidad de personal de Bomberos ===\n");
        for (Bombero bombero : bomberos) {
            System.out.println(bombero);
        }

        System.out.println("\n=== Disponibilidad de personal de Paramedicos ===\n");
        for (Ambulancia paramedico : paramedicos) {
            System.out.println(paramedico);
        }
    }

    /**
     * Muestra el estado de los casos en los que está involucrado el personal de ambulancias, bomberos y policías.
     */
    public void showStatus() {
        ManageAmbulancias.mostrarStatusCasos();
        ManageBomberos.mostrarStatusCasos();
        ManagePolicias.mostrarStatusCasos();
    }
}