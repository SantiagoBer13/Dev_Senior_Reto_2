package model.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import model.Ambulancia;
import model.EmergenciaAsignacion;
import model.Emergency;
import model.IResponder;
import model.Persona;
import model.Ubication;
import model.Observer.RespondTeamCoordinator;

/**
 * La clase ManageAmbulancias es responsable de gestionar las ambulancias disponibles para responder a emergencias,
 * calcular el tiempo de llegada a una emergencia y asignar ambulancias y paramédicos a emergencias específicas.
 * Implementa el patrón Singleton para asegurar que solo haya una instancia de la clase y que se maneje de forma centralizada.
 */
public class ManageAmbulancias {
    private static ManageAmbulancias instance;
    private static List<Ambulancia> personal = new ArrayList<>();
    private static Map<String, EmergenciaAsignacion> casos = new HashMap<>();
    private static final Ubication ubication = new Ubication("Calle 12", "Carrera 13",
            "Estacion Central de Ambulancias");

    /**
     * Constructor privado para evitar la creación directa de instancias.
     */
    private ManageAmbulancias() {
    }

    /**
     * Obtiene la instancia única de ManageAmbulancias. Si aún no existe, la crea.
     *
     * @return La instancia de ManageAmbulancias.
     */
    public static ManageAmbulancias getInstance() {
        if (instance == null) {
            addPersonal();
            instance = new ManageAmbulancias();
        }
        return instance;
    }

    /**
     * Calcula el tiempo estimado en segundos para que las ambulancias lleguen a la ubicación de una emergencia
     * según la distancia en una cuadrícula Manhattan entre la ubicación de la estación de ambulancias y la ubicación
     * de la emergencia.
     *
     * @param emergency La emergencia para la que se calcula el tiempo estimado.
     * @return El tiempo estimado en segundos.
     */
    public int calcularTiempo(Emergency emergency) {
        double distancia;

        int calleActual = ubication.getCalle();
        int carreraActual = ubication.getCarrera();
        int calleEmergencia = emergency.getUbication().getCalle();
        int carreraEmergencia = emergency.getUbication().getCarrera();

        // Cálculo de distancia con la distancia Manhattan
        if (calleActual == calleEmergencia && carreraActual == carreraEmergencia) {
            distancia = 0;
        } else if (carreraActual == carreraEmergencia) {
            distancia = Math.abs(calleActual - calleEmergencia);
        } else if (calleActual == calleEmergencia) {
            distancia = Math.abs(carreraActual - carreraEmergencia);
        } else {
            distancia = Math.abs(calleActual - calleEmergencia) + Math.abs(carreraActual - carreraEmergencia);
        }

        // Velocidad promedio (en km/h) de la ambulancia
        final double VEL_PROMEDIO = 30.0;

        // Conversión de distancia a kilómetros
        double distanciaEnKm = distancia * 0.05; // Asumiendo que cada unidad es 50 metros

        // Tiempo en horas, luego convertido a segundos
        double tiempoEnHoras = distanciaEnKm / VEL_PROMEDIO;
        return (int) (tiempoEnHoras * 3600);
    }

    /**
     * Crea una asignación de atención para una emergencia, seleccionando ambulancias y paramédicos disponibles,
     * y calculando el tiempo estimado de llegada.
     *
     * @param emergency La emergencia que se atenderá.
     * @param numCarros El número de ambulancias requeridas para la atención.
     * @param respondTeamCoordinator El coordinador que gestionará los equipos.
     * @return La asignación de la emergencia con los paramédicos asignados.
     */
    public EmergenciaAsignacion crearAtencion(Emergency emergency, int numCarros, RespondTeamCoordinator respondTeamCoordinator) {
        List<IResponder> equipoAmbulancias = new ArrayList<>();
        int paramedicosNecesarios = numCarros * 2; // Cada ambulancia necesita 2 paramédicos
        System.out.println("Paramedicos necesarios: " + paramedicosNecesarios);

        // Calcular el tiempo estimado para la llegada
        int tiempoEstimadoSegundos = calcularTiempo(emergency);

        int asignados = 0;
        // Asignar ambulancias disponibles
        for (Ambulancia ambulancia : personal) {
            if (ambulancia.isDisponible() && asignados < paramedicosNecesarios) {
                equipoAmbulancias.add(ambulancia);
                ambulancia.atenderEmergencia();
                asignados++;
            }
        }

        if (asignados < paramedicosNecesarios) {
            System.out.println("ADVERTENCIA: No hay suficientes paramédicos disponibles. Se asignaron "
                    + asignados + " de " + paramedicosNecesarios + " requeridos.");
        }

        // Generar un ID único para la asignación
        String idCaso = UUID.randomUUID().toString();

        // Almacenar la asignación en el mapa de casos
        EmergenciaAsignacion asignacion = new EmergenciaAsignacion(emergency, equipoAmbulancias,
                tiempoEstimadoSegundos, respondTeamCoordinator, "PARAMEDICOS");
        casos.put(idCaso, asignacion);

        // Mostrar el tiempo estimado de llegada
        imprimirTiempoEstimado(idCaso, tiempoEstimadoSegundos);

        return asignacion;
    }

    /**
     * Muestra el tiempo estimado de llegada de las ambulancias a la emergencia.
     *
     * @param idCaso El ID del caso para el cual se calcula el tiempo estimado.
     * @param tiempoEstimadoSegundos El tiempo estimado en segundos.
     */
    private void imprimirTiempoEstimado(String idCaso, int tiempoEstimadoSegundos) {
        if (tiempoEstimadoSegundos < 60) {
            System.out.printf("Caso creado con ID: %s - Tiempo estimado de llegada: %d segundos.%n%n",
                    idCaso, tiempoEstimadoSegundos);
        } else {
            int minutos = tiempoEstimadoSegundos / 60;
            int segundos = tiempoEstimadoSegundos % 60;

            if (segundos == 0) {
                System.out.printf("Caso creado con ID: %s - Tiempo estimado de llegada: %d minutos.%n%n",
                        idCaso, minutos);
            } else {
                System.out.printf("Caso creado con ID: %s - Tiempo estimado de llegada: %d minutos y %d segundos.%n%n",
                        idCaso, minutos, segundos);
            }
        }
    }

    /**
     * Agrega un conjunto inicial de ambulancias y paramédicos disponibles.
     */
    private static void addPersonal() {
        personal.add(new Ambulancia(new Persona("Andrés", "Herrera", "05/04/1988", "2020321", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Laura", "Ramírez", "18/11/1990", "2020322", "Paramédico")));
        // Agregar más ambulancias...
    }

    /**
     * Obtiene el personal disponible de ambulancias.
     *
     * @return La lista de ambulancias disponibles.
     */
    public static List<Ambulancia> getPersonal() {
        return personal;
    }

    /**
     * Obtiene todos los casos activos o asignados.
     *
     * @return Un mapa de todos los casos asignados.
     */
    public static Map<String, EmergenciaAsignacion> getCasos() {
        return casos;
    }

    /**
     * Obtiene un caso específico dado su ID.
     *
     * @param idCaso El ID del caso.
     * @return La asignación del caso correspondiente.
     */
    public static EmergenciaAsignacion getCaso(String idCaso) {
        return casos.get(idCaso);
    }

    /**
     * Muestra el estado de todos los casos activos y terminados de ambulancias.
     */
    public static void mostrarStatusCasos() {
        System.out.println("=== STATUS DE CASOS DE AMBULANCIAS ===\n");
        if (casos.isEmpty()) {
            System.out.println("No hay casos.\n");
        } else {
            List<EmergenciaAsignacion> casosActivos = new ArrayList<>();
            List<EmergenciaAsignacion> casosTerminados = new ArrayList<>();

            for (EmergenciaAsignacion emergenciaAsignacion : casos.values()) {
                if (emergenciaAsignacion.getAtendida()) {
                    casosTerminados.add(emergenciaAsignacion);
                } else {
                    casosActivos.add(emergenciaAsignacion);
                }
            }

            System.out.println("=== Casos Activos ===\n");
            if (casosActivos.size() == 0) {
                System.out.println("No hay casos activos.\n");
            } else {
                for (EmergenciaAsignacion emergenciaAsignacion : casosActivos) {
                    System.out.println(emergenciaAsignacion);
                }
            }

            System.out.println("=== Casos Finalizados ===\n");
            if (casosTerminados.size() == 0) {
                System.out.println("No hay casos Finalizados.\n");
            } else {
                for (EmergenciaAsignacion emergenciaAsignacion : casosTerminados) {
                    System.out.println(emergenciaAsignacion);
                }
            }
        }
        System.out.println("===================================");
    }
}