package model;
import java.util.List;

import model.Observer.Observer;
import model.Observer.RespondTeamCoordinator;
import utils.Gravedad;

public class EmergenciaAsignacion implements Observer {
    private Emergency emergencia;
    private List<IResponder> equipoAsignado;
    private int tiempoEstimadoSegundos;
    private boolean atendida;
    private boolean isInTheScene = false;
    private RespondTeamCoordinator respondTeamCoordinator;
    private String entidadRespuesta;

    public EmergenciaAsignacion(Emergency emergencia, List<IResponder> equipoAsignado, int tiempoEstimadoSegundos,
            RespondTeamCoordinator respondTeamCoordinator, String entidadRespuesta) {
        this.emergencia = emergencia;
        this.equipoAsignado = equipoAsignado;
        this.tiempoEstimadoSegundos = tiempoEstimadoSegundos;
        this.atendida = false;
        this.respondTeamCoordinator = respondTeamCoordinator;
        this.entidadRespuesta = entidadRespuesta;
        llegarAEscena();
    }

    public void llegarAEscena() {

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(this.tiempoEstimadoSegundos * 1000);
                String message = String.format("%n [ %s ]  El equipo asignado ha llegado a la escena a atender el %s.%n", this.entidadRespuesta, this.emergencia.getTypeEmergency());

                for (IResponder integrante : this.equipoAsignado) {
                    message += integrante.evaluarEstado();
                }

                System.out.println(message);

                this.isInTheScene = true;
                this.respondTeamCoordinator.respondTeam();

            } catch (InterruptedException e) {
                System.err.println("Interrupción durante llegada al lugar de emergencia: " + e.getMessage());
                e.printStackTrace();
            }
        });

        thread.start();
    }

    @Override
    public void atender() {

        switch (emergencia.getGravedad()) {
            case Gravedad.BAJA:
                System.out.println("\n[ "+ this.entidadRespuesta+" ] La atención del " + this.emergencia.getTypeEmergency() + " durará 10 segundos.");
                new Thread(() -> {
                    try {
                        // Esperar 10 segundos
                        Thread.sleep(10000);

                        // Finalizar emergencia
                        finalizarEmergencia();

                    } catch (InterruptedException e) {
                        System.err.println(
                                "Interrupción durante atención de emergencia de gravedad baja: " + e.getMessage());
                        e.printStackTrace();
                    }
                }).start();
                break;

            case Gravedad.MEDIA:
                System.out.println("\n[ "+ this.entidadRespuesta+" ] La atención del " + this.emergencia.getTypeEmergency() + " durará 15 segundos.");
                new Thread(() -> {
                    try {
                        // Esperar 15 segundos
                        Thread.sleep(15000);

                        // Finalizar emergencia
                        finalizarEmergencia();

                    } catch (InterruptedException e) {
                        System.err.println(
                                "Interrupción durante atención de emergencia de gravedad media: " + e.getMessage());
                        e.printStackTrace();
                    }
                }).start();
                break;

            case Gravedad.ALTA:
            System.out.println("\n[ "+ this.entidadRespuesta+" ] La atención del " + this.emergencia.getTypeEmergency() + " durará 20 segundos.");
                new Thread(() -> {
                    try {
                        // Esperar 20 segundos
                        Thread.sleep(20000);

                        // Finalizar emergencia
                        finalizarEmergencia();

                    } catch (InterruptedException e) {
                        System.err.println(
                                "Interrupción durante atención de emergencia de gravedad alta: " + e.getMessage());
                        e.printStackTrace();
                    }
                }).start();
                break;

            default:
                System.out.println("Gravedad de emergencia no reconocida");
                break;
        }
    }

    private void finalizarEmergencia() {
        System.out.println("\n[ " + this.entidadRespuesta + " ] Emergencia atendida y finalizada: \n" + emergencia + " .");
        liberarEquipo();
        this.atendida = true;
    }

    public void liberarEquipo() {
        for (IResponder integrante : equipoAsignado) {
            integrante.liberar();
        }
    }

    public Emergency getEmergencia() {
        return emergencia;
    }

    public List<IResponder> getEquipoAsignado() {
        return equipoAsignado;
    }

    public double getTiempoEstimadoSegundos() {
        return tiempoEstimadoSegundos;
    }

    public boolean getAtendida() {
        return this.atendida;
    }

    private String imprimirTiempoEstimado() {
        if (tiempoEstimadoSegundos < 60) {
            return String.format("%d segundos.%n", this.tiempoEstimadoSegundos);
        } else {
            // Más de un minuto: mostrar minutos y segundos
            int minutos = tiempoEstimadoSegundos / 60;
            int segundos = tiempoEstimadoSegundos % 60;

            if (segundos == 0) {
                // Sin segundos restantes
                return String.format("%d minutos.%n", minutos);
            } else {
                // Con minutos y segundos
                return String.format(" %d minutos y %d segundos.%n", minutos, segundos);
            }
        }
    }

    @Override
    public String toString() {
        return emergencia + ", Equipo: " + equipoAsignado.size() + " personas, Tiempo estimado: "
                + imprimirTiempoEstimado();
    }

    @Override
    public boolean estaEnLaEscena() {
        return isInTheScene;
    }
}