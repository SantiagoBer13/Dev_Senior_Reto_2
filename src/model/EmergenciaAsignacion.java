package model;

import java.util.List;
import model.Observer.Observer;
import model.Observer.RespondTeamCoordinator;
import utils.Gravedad;

/**
 * La clase EmergenciaAsignacion implementa la interfaz Observer y gestiona la asignación
 * y atención de un equipo a una emergencia específica.
 */
public class EmergenciaAsignacion implements Observer {

    private Emergency emergencia; // Emergencia asociada a la asignación
    private List<IResponder> equipoAsignado; // Equipo asignado a la emergencia
    private int tiempoEstimadoSegundos; // Tiempo estimado para llegar a la escena
    private boolean atendida; // Indicador de si la emergencia ha sido atendida
    private boolean isInTheScene = false; // Indicador de si el equipo ha llegado a la escena
    private RespondTeamCoordinator respondTeamCoordinator; // Coordinador de equipo de respuesta
    private String entidadRespuesta; // Entidad responsable de la respuesta

    /**
     * Constructor que inicializa la asignación de emergencia con los detalles proporcionados.
     * @param emergencia La emergencia que necesita atención.
     * @param equipoAsignado Lista de respondedores asignados a la emergencia.
     * @param tiempoEstimadoSegundos El tiempo estimado en segundos para llegar a la escena.
     * @param respondTeamCoordinator El coordinador del equipo de respuesta.
     * @param entidadRespuesta Nombre de la entidad responsable de la respuesta.
     */
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

    /**
     * Simula el tiempo necesario para que el equipo llegue a la escena de la emergencia.
     * Una vez que el equipo llega, se evalúa el estado de la emergencia.
     */
    public void llegarAEscena() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(this.tiempoEstimadoSegundos * 1000);
                String message = String.format("%n [ %s ] El equipo asignado ha llegado a la escena a atender el %s.%n", 
                                                this.entidadRespuesta, this.emergencia.getTypeEmergency());

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

    /**
     * Método para atender la emergencia dependiendo de su gravedad.
     */
    @Override
    public void atender() {
        switch (emergencia.getGravedad()) {
            case Gravedad.BAJA:
                System.out.println("\n[ " + this.entidadRespuesta + " ] La atención del " + this.emergencia.getTypeEmergency() + " durará 10 segundos.");
                new Thread(() -> {
                    try {
                        Thread.sleep(10000); // Esperar 10 segundos
                        finalizarEmergencia();
                    } catch (InterruptedException e) {
                        System.err.println("Interrupción durante atención de emergencia de gravedad baja: " + e.getMessage());
                        e.printStackTrace();
                    }
                }).start();
                break;

            case Gravedad.MEDIA:
                System.out.println("\n[ " + this.entidadRespuesta + " ] La atención del " + this.emergencia.getTypeEmergency() + " durará 15 segundos.");
                new Thread(() -> {
                    try {
                        Thread.sleep(15000); // Esperar 15 segundos
                        finalizarEmergencia();
                    } catch (InterruptedException e) {
                        System.err.println("Interrupción durante atención de emergencia de gravedad media: " + e.getMessage());
                        e.printStackTrace();
                    }
                }).start();
                break;

            case Gravedad.ALTA:
                System.out.println("\n[ " + this.entidadRespuesta + " ] La atención del " + this.emergencia.getTypeEmergency() + " durará 20 segundos.");
                new Thread(() -> {
                    try {
                        Thread.sleep(20000); // Esperar 20 segundos
                        finalizarEmergencia();
                    } catch (InterruptedException e) {
                        System.err.println("Interrupción durante atención de emergencia de gravedad alta: " + e.getMessage());
                        e.printStackTrace();
                    }
                }).start();
                break;

            default:
                System.out.println("Gravedad de emergencia no reconocida");
                break;
        }
    }

    /**
     * Finaliza la emergencia, liberando al equipo y marcando la emergencia como atendida.
     */
    private void finalizarEmergencia() {
        System.out.println("\n[ " + this.entidadRespuesta + " ] Emergencia atendida y finalizada: \n" + emergencia + " .");
        liberarEquipo();
        this.atendida = true;
    }

    /**
     * Libera el equipo de respondientes para que puedan atender otras emergencias.
     */
    public void liberarEquipo() {
        for (IResponder integrante : equipoAsignado) {
            integrante.liberar();
        }
    }

    /**
     * Obtiene la emergencia asociada a esta asignación.
     * @return La emergencia.
     */
    public Emergency getEmergencia() {
        return emergencia;
    }

    /**
     * Obtiene el equipo asignado a la emergencia.
     * @return El equipo de respondedores.
     */
    public List<IResponder> getEquipoAsignado() {
        return equipoAsignado;
    }

    /**
     * Obtiene el tiempo estimado en segundos para llegar a la escena.
     * @return El tiempo estimado en segundos.
     */
    public double getTiempoEstimadoSegundos() {
        return tiempoEstimadoSegundos;
    }

    /**
     * Obtiene el estado de si la emergencia ha sido atendida.
     * @return true si ha sido atendida, false si no ha sido atendida.
     */
    public boolean getAtendida() {
        return this.atendida;
    }

    /**
     * Imprime el tiempo estimado de llegada a la escena en un formato legible.
     * @return El tiempo estimado formateado.
     */
    private String imprimirTiempoEstimado() {
        if (tiempoEstimadoSegundos < 60) {
            return String.format("%d segundos.%n", this.tiempoEstimadoSegundos);
        } else {
            int minutos = tiempoEstimadoSegundos / 60;
            int segundos = tiempoEstimadoSegundos % 60;
            return segundos == 0 ? String.format("%d minutos.%n", minutos)
                                 : String.format(" %d minutos y %d segundos.%n", minutos, segundos);
        }
    }

    /**
     * Representación en cadena de la asignación de emergencia.
     * @return Una cadena que describe la asignación de emergencia.
     */
    @Override
    public String toString() {
        return emergencia + ", Equipo: " + equipoAsignado.size() + " personas, Tiempo estimado: "
                + imprimirTiempoEstimado();
    }

    /**
     * Método que indica si el equipo está presente en la escena de la emergencia.
     * @return true si el equipo está en la escena, false si no lo está.
     */
    @Override
    public boolean estaEnLaEscena() {
        return isInTheScene;
    }
}