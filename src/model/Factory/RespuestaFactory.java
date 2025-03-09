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

public class RespuestaFactory {
    private ManageBomberos manageBomberos;
    private ManageAmbulancias manageAmbulancias;
    private ManagePolicias managePolicias;

    public RespuestaFactory() {
        this.manageBomberos = ManageBomberos.getInstance();
        this.manageAmbulancias = ManageAmbulancias.getInstance();
        this.managePolicias = ManagePolicias.getInstance();
    }

    public void getRespuestas( Emergency emergency) {
        Gravedad gravedad = emergency.getGravedad();
        RespondTeamCoordinator respondTeamCoordinator = new RespondTeamCoordinator();
        switch (emergency.getTypeRespuesta()) {
            case BOMBERO:
                if (gravedad == Gravedad.BAJA) {
                    respondTeamCoordinator.addObserver(manageBomberos.crearAtencion(emergency, 1, respondTeamCoordinator));
                } else if (gravedad == Gravedad.MEDIA) {
                    respondTeamCoordinator.addObserver(manageBomberos.crearAtencion(emergency,2,respondTeamCoordinator));
                    respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion(emergency,1,respondTeamCoordinator));
                } else if (gravedad == Gravedad.ALTA) {
                    respondTeamCoordinator.addObserver(manageBomberos.crearAtencion(emergency, 3, respondTeamCoordinator));
                    respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion(emergency, 2, respondTeamCoordinator));
                    respondTeamCoordinator.addObserver(managePolicias.crearAtencion(emergency, 2, respondTeamCoordinator));
                }
                break;
            case MEDICA:
                if (gravedad == Gravedad.BAJA) {
                    respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion(emergency, 1, respondTeamCoordinator));
                } else if (gravedad == Gravedad.MEDIA) {
                    respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion( emergency, 2, respondTeamCoordinator));
                    respondTeamCoordinator.addObserver(manageBomberos.crearAtencion(emergency, 1, respondTeamCoordinator));
                } else if (gravedad == Gravedad.ALTA) {
                    respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion(emergency, 3, respondTeamCoordinator));
                    respondTeamCoordinator.addObserver(manageBomberos.crearAtencion(emergency, 1, respondTeamCoordinator));
                    respondTeamCoordinator.addObserver(managePolicias.crearAtencion(emergency, 2, respondTeamCoordinator));
                }
                break;
            case SEGURIDAD:
                if (gravedad == Gravedad.BAJA) {
                    respondTeamCoordinator.addObserver(managePolicias.crearAtencion(emergency, 1, respondTeamCoordinator));
                } else if (gravedad == Gravedad.MEDIA) {
                    respondTeamCoordinator.addObserver(managePolicias.crearAtencion(emergency, 2, respondTeamCoordinator));
                    respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion(emergency, 1, respondTeamCoordinator));
                } else if (gravedad == Gravedad.ALTA) {
                    respondTeamCoordinator.addObserver(managePolicias.crearAtencion(emergency, 4,respondTeamCoordinator));
                    respondTeamCoordinator.addObserver(manageAmbulancias.crearAtencion(emergency, 2, respondTeamCoordinator));
                    respondTeamCoordinator.addObserver(manageBomberos.crearAtencion(emergency, 2, respondTeamCoordinator));
                }
                break;
        }
    }

    public void getStatusPersona(){
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

    public void showStatus(){
        ManageAmbulancias.mostrarStatusCasos();
        ManageBomberos.mostrarStatusCasos();
        ManagePolicias.mostrarStatusCasos();
    }
}
