package model.Singleton;

// Importación de clases necesarias
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// Importación de clases del modelo
import model.Bombero;
import model.EmergenciaAsignacion;
import model.Emergency;
import model.IResponder;
import model.Persona;
import model.Ubication;
import model.Observer.RespondTeamCoordinator;

public class ManageBomberos {
    // Instancia única de la clase (patrón Singleton)
    private static ManageBomberos instance;
    // Lista de bomberos disponibles
    private static List<Bombero> personal = new ArrayList<>();
    // Mapa que almacena las asignaciones de emergencias
    private static Map<String, EmergenciaAsignacion> casos = new HashMap<>();
    // Ubicación de la estación de bomberos
    private static final Ubication ubication = new Ubication("Calle 5", "Carrera 6", "Estacion Central de Bomberos");

    // Constructor privado para prevenir instanciación externa
    private ManageBomberos() {
    }

    // Método para obtener la instancia única de ManageBomberos
    public static ManageBomberos getInstance() {
        if (instance == null) {
            addPersonal(); // Agregar bomberos al personal si aún no existen
            instance = new ManageBomberos(); // Crear una nueva instancia si no existe
        }
        return instance;
    }

    // Método que calcula el tiempo estimado de llegada de los bomberos a una emergencia
    public int calcularTiempo(Emergency emergency) {
        double distancia;

        // Coordenadas actuales de la estación de bomberos
        int calleActual = ubication.getCalle();
        int carreraActual = ubication.getCarrera();
        // Coordenadas de la emergencia
        int calleEmergencia = emergency.getUbication().getCalle();
        int carreraEmergencia = emergency.getUbication().getCarrera();

        // Cálculo de la distancia dependiendo de la ubicación
        if (calleActual == calleEmergencia && carreraActual == carreraEmergencia) {
            distancia = 0; // Mismo punto
        } else if (carreraActual == carreraEmergencia) {
            distancia = Math.abs(calleActual - calleEmergencia); // Misma carrera, diferente calle
        } else if (calleActual == calleEmergencia) {
            distancia = Math.abs(carreraActual - carreraEmergencia); // Misma calle, diferente carrera
        } else {
            distancia = Math.abs(calleActual - calleEmergencia) + Math.abs(carreraActual - carreraEmergencia); // Distancia Manhattan
        }

        // Velocidad promedio de los bomberos en km/h
        final double VEL_PROMEDIO = 30.0;

        // Conversión de distancia a kilómetros (suponiendo que cada unidad es 50 metros)
        double distanciaEnKm = distancia * 0.05;

        // Cálculo del tiempo en horas (t = d / v)
        double tiempoEnHoras = distanciaEnKm / VEL_PROMEDIO;

        // Convertimos el tiempo a segundos y lo retornamos
        return (int) (tiempoEnHoras * 3600);
    }

    // Método para crear una nueva atención de emergencia
    public EmergenciaAsignacion crearAtencion(Emergency emergency, int numCarros, RespondTeamCoordinator respondTeamCoordinator) {
        List<IResponder> equipoBomberos = new ArrayList<>();
        int bomberosNecesarios = numCarros * 3; // Por cada carro se requieren 3 bomberos
        System.out.println("Bomberos necesarios: " + bomberosNecesarios);

        // Calcular el tiempo estimado de llegada
        int tiempoEstimadoSegundos = calcularTiempo(emergency);

        // Asignar bomberos disponibles
        int asignados = 0;
        for (Bombero bombero : personal) {
            if (bombero.isDisponible() && asignados < bomberosNecesarios) {
                equipoBomberos.add(bombero); // Asignar al bombero al equipo
                bombero.atenderEmergencia(); // Marcar al bombero como atendiendo una emergencia
                asignados++;
            }
        }

        // Verificar si hay suficientes bomberos disponibles
        if (asignados < bomberosNecesarios) {
            System.out.println("ADVERTENCIA: No hay suficientes bomberos disponibles. Se asignaron "
                    + asignados + " de " + bomberosNecesarios + " requeridos.");
        }

        // Generar un ID único para el caso
        String idCaso = UUID.randomUUID().toString();

        // Crear la asignación de emergencia
        EmergenciaAsignacion asignacion = new EmergenciaAsignacion(emergency, equipoBomberos, tiempoEstimadoSegundos, respondTeamCoordinator, "BOMBEROS");
        casos.put(idCaso, asignacion); // Guardar la asignación en el mapa de casos

        // Imprimir el tiempo estimado de llegada
        imprimirTiempoEstimado(idCaso, tiempoEstimadoSegundos);

        return asignacion; // Retornar la asignación
    }

    // Método privado para imprimir el tiempo estimado de llegada
    private void imprimirTiempoEstimado(String idCaso, int tiempoEstimadoSegundos) {
        if (tiempoEstimadoSegundos < 60) {
            // Si el tiempo es menor a un minuto, mostrar solo los segundos
            System.out.printf("Caso creado con ID: %s - Tiempo estimado de llegada: %d segundos.%n%n",
                    idCaso, tiempoEstimadoSegundos);
        } else {
            // Si el tiempo es mayor a un minuto, mostrar minutos y segundos
            int minutos = tiempoEstimadoSegundos / 60;
            int segundos = tiempoEstimadoSegundos % 60;

            if (segundos == 0) {
                // Si no hay segundos restantes, solo mostrar los minutos
                System.out.printf("Caso creado con ID: %s - Tiempo estimado de llegada: %d minutos.%n%n",
                        idCaso, minutos);
            } else {
                // Si hay segundos restantes, mostrar minutos y segundos
                System.out.printf("Caso creado con ID: %s - Tiempo estimado de llegada: %d minutos y %d segundos.%n%n",
                        idCaso, minutos, segundos);
            }
        }
    }

    // Método estático para agregar bomberos al personal
    private static void addPersonal() {
        // Añadir bomberos con diferentes nombres, fechas de nacimiento y números de identificación
        personal.add(new Bombero(new Persona("Jorge", "Benavides", "02/07/1998", "1020321", "Bombero")));
        personal.add(new Bombero(new Persona("Miguel", "Rodríguez", "15/03/1985", "1020322", "Bombero")));
        // Agregar más bomberos...
    }

    // Método para obtener la lista de bomberos
    public static List<Bombero> getPersonal() {
        return personal;
    }

    // Método para obtener el mapa de casos
    public static Map<String, EmergenciaAsignacion> getCasos() {
        return casos;
    }

    // Método para obtener un caso específico por su ID
    public static EmergenciaAsignacion getCaso(String idCaso) {
        return casos.get(idCaso);
    }

    // Método para mostrar el estado de todos los casos de emergencia
    public static void mostrarStatusCasos() {
        System.out.println("=== STATUS DE CASOS DE BOMBEROS ===\n");
        if (casos.isEmpty()) {
            System.out.println("No hay casos.\n");
        } else {
            List<EmergenciaAsignacion> casosActivos = new ArrayList<>();
            List<EmergenciaAsignacion> casosTerminados = new ArrayList<>();

            // Clasificar los casos activos y terminados
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

            System.out.println("\n=== Casos Finalizados ===\n");
            if (casosTerminados.size() == 0) {
                System.out.println("No hay casos Finalizados.\n");
            } else {
                for (EmergenciaAsignacion emergenciaAsignacion : casosTerminados) {
                    System.out.println(emergenciaAsignacion);
                }
            }
        }
        System.out.println("\n===================================");
    }
}