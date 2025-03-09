package model.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import model.EmergenciaAsignacion;
import model.Emergency;
import model.IResponder;
import model.Persona;
import model.Policia;
import model.Ubication;
import model.Observer.RespondTeamCoordinator;

public class ManagePolicias {
    // Instancia única del Singleton
    private static ManagePolicias instance;

    // Lista que mantiene el personal de policía disponible
    private static List<Policia> personal = new ArrayList<>();

    // Mapa de casos de emergencias, usando un ID único por cada asignación
    private static Map<String, EmergenciaAsignacion> casos = new HashMap<>();

    // Ubicación de la estación central de policía
    private static final Ubication ubication = new Ubication("Calle 2", "Carrera 3", "Estacion Central de Policias");

    // Constructor privado para evitar instanciación desde fuera de la clase (Singleton)
    private ManagePolicias() {
    }

    // Método para obtener la instancia del Singleton, creando la instancia si no existe
    public static ManagePolicias getInstance() {
        if (instance == null) {
            addPersonal(); // Inicializa el personal si no se ha hecho
            instance = new ManagePolicias();
        }
        return instance;
    }

    // Método para calcular el tiempo estimado en llegar a la emergencia
    public int calcularTiempo(Emergency emergency) {
        double distancia;

        // Obtención de las coordenadas de la ubicación actual y la de la emergencia
        int calleActual = ubication.getCalle();
        int carreraActual = ubication.getCarrera();
        int calleEmergencia = emergency.getUbication().getCalle();
        int carreraEmergencia = emergency.getUbication().getCarrera();

        // Distancia entre las dos ubicaciones según las diferentes condiciones (distancia Manhattan)
        if (calleActual == calleEmergencia && carreraActual == carreraEmergencia) {
            distancia = 0; // Caso en el que estamos en el mismo punto
        } else if (carreraActual == carreraEmergencia) {
            distancia = Math.abs(calleActual - calleEmergencia); // Mismo carrera, diferente calle
        } else if (calleActual == calleEmergencia) {
            distancia = Math.abs(carreraActual - carreraEmergencia); // Misma calle, diferente carrera
        } else {
            distancia = Math.abs(calleActual - calleEmergencia) + Math.abs(carreraActual - carreraEmergencia); // Distancia Manhattan
        }

        // Velocidad promedio del policía (en km/h)
        final double VEL_PROMEDIO = 30.0;

        // Conversión de distancia a kilómetros (asumiendo que cada unidad de la distancia es 50 metros)
        double distanciaEnKm = distancia * 0.05; 

        // Cálculo del tiempo estimado en horas
        double tiempoEnHoras = distanciaEnKm / VEL_PROMEDIO;

        // Convertimos el tiempo a segundos
        return (int) (tiempoEnHoras * 3600);
    }

    // Método para crear una atención para una emergencia
    public EmergenciaAsignacion crearAtencion(Emergency emergency, int numCarros, RespondTeamCoordinator respondTeamCoordinator) {
        // Lista para los policías que atenderán la emergencia
        List<IResponder> equipoPolicias = new ArrayList<>();

        // Cálculo de la cantidad de policías necesarios (3 por carro)
        int policiasNecesarios = numCarros * 2; // Ajustado a 2 por carro (en el código anterior se usaban 3 por carro)
        System.out.println("Policias necesarios: " + policiasNecesarios);

        // Cálculo del tiempo estimado para la llegada
        int tiempoEstimadoSegundos = calcularTiempo(emergency);

        // Asignación de los policías disponibles
        int asignados = 0;
        for (Policia policia : personal) {
            if (policia.isDisponible() && asignados < policiasNecesarios) {
                equipoPolicias.add(policia); // Añadir policía al equipo
                policia.atenderEmergencia(); // Marcar al policía como atendiendo emergencia
                asignados++;
            }
        }

        // Verificar si hay suficientes policías disponibles
        if (asignados < policiasNecesarios) {
            System.out.println("ADVERTENCIA: No hay suficientes policías disponibles. Se asignaron "
                    + asignados + " de " + policiasNecesarios + " requeridos.");
        }

        // Generamos un ID único para el caso de emergencia
        String idCaso = UUID.randomUUID().toString();

        // Creamos una nueva asignación de emergencia
        EmergenciaAsignacion asignacion = new EmergenciaAsignacion(emergency, equipoPolicias, tiempoEstimadoSegundos, respondTeamCoordinator, "POLICIAS");

        // Almacenamos el caso en el mapa de casos
        casos.put(idCaso, asignacion);

        // Imprimimos el tiempo estimado de llegada
        imprimirTiempoEstimado(idCaso, tiempoEstimadoSegundos);

        return asignacion;
    }

    // Método para imprimir el tiempo estimado de llegada al lugar de la emergencia
    private void imprimirTiempoEstimado(String idCaso, int tiempoEstimadoSegundos) {
        if (tiempoEstimadoSegundos < 60) {
            // Si el tiempo es menos de un minuto, mostramos solo los segundos
            System.out.printf("Caso creado con ID: %s - Tiempo estimado de llegada: %d segundos.%n%n",
                    idCaso, tiempoEstimadoSegundos);
        } else {
            // Si el tiempo es mayor de un minuto, mostramos minutos y segundos
            int minutos = tiempoEstimadoSegundos / 60;
            int segundos = tiempoEstimadoSegundos % 60;

            if (segundos == 0) {
                // Si no hay segundos restantes, mostramos solo minutos
                System.out.printf("Caso creado con ID: %s - Tiempo estimado de llegada: %d minutos.%n%n",
                        idCaso, minutos);
            } else {
                // Si hay minutos y segundos, mostramos ambos
                System.out.printf("Caso creado con ID: %s - Tiempo estimado de llegada: %d minutos y %d segundos.%n%n",
                        idCaso, minutos, segundos);
            }
        }
    }

    // Método privado para añadir el personal de policía a la lista
    private static void addPersonal() {
        personal.add(new Policia(new Persona("Martín", "Pérez", "12/08/1986", "3020321", "Policía")));
        personal.add(new Policia(new Persona("Lucía", "Gómez", "24/02/1990", "3020322", "Policía")));
        personal.add(new Policia(new Persona("Sergio", "Vargas", "09/05/1988", "3020323", "Policía")));
        personal.add(new Policia(new Persona("Marina", "Soto", "17/11/1992", "3020324", "Policía")));
        personal.add(new Policia(new Persona("Fernando", "Rivas", "03/07/1984", "3020325", "Policía")));
        // ... (otros policías añadidos de la misma manera)
    }

    // Método para obtener la lista del personal de policía
    public static List<Policia> getPersonal() {
        return personal;
    }

    // Método para obtener el mapa de casos de emergencias
    public static Map<String, EmergenciaAsignacion> getCasos() {
        return casos;
    }

    // Método para obtener un caso específico utilizando su ID
    public static EmergenciaAsignacion getCaso(String idCaso) {
        return casos.get(idCaso);
    }

    // Método para mostrar el estado de todos los casos
    public static void mostrarStatusCasos() {
        System.out.println("=== STATUS DE CASOS DE POLICIAS ===\n");
        if (casos.isEmpty()) {
            System.out.println("No hay casos.\n");
        } else {
            List<EmergenciaAsignacion> casosActivos = new ArrayList<>();
            List<EmergenciaAsignacion> casosTerminados = new ArrayList<>();

            // Clasificamos los casos en activos y terminados
            for (EmergenciaAsignacion emergenciaAsignacion : casos.values()) {
                if (emergenciaAsignacion.getAtendida()) {
                    casosTerminados.add(emergenciaAsignacion);
                } else {
                    casosActivos.add(emergenciaAsignacion);
                }
            }

            // Mostrar casos activos
            System.out.println("=== Casos Activos ===\n");
            if (casosActivos.size() == 0) {
                System.out.println("No hay casos activos.\n");
            } else {
                for (EmergenciaAsignacion emergenciaAsignacion : casosActivos) {
                    System.out.println(emergenciaAsignacion);
                }
            }

            // Mostrar casos terminados
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