package model.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import model.Bombero;
import model.EmergenciaAsignacion;
import model.Emergency;
import model.IResponder;
import model.Persona;
import model.Ubication;
import model.Observer.RespondTeamCoordinator;

public class ManageBomberos {
    private static ManageBomberos instance;
    private static List<Bombero> personal = new ArrayList<>();
    private static Map<String, EmergenciaAsignacion> casos = new HashMap<>();
    private static final Ubication ubication = new Ubication("Calle 5", "Carrera 6", "Estacion Central de Bomberos");

    private ManageBomberos() {
    }

    public static ManageBomberos getInstance() {
        if (instance == null) {
            addPersonal();
            instance = new ManageBomberos();
        }
        return instance;
    }

    public int calcularTiempo(Emergency emergency) {
        double distancia;

        int calleActual = ubication.getCalle();
        int carreraActual = ubication.getCarrera();
        int calleEmergencia = emergency.getUbication().getCalle();
        int carreraEmergencia = emergency.getUbication().getCarrera();

        // Caso 1: Mismo punto
        if (calleActual == calleEmergencia && carreraActual == carreraEmergencia) {
            distancia = 0;
        }
        // Caso 2: Misma carrera, diferente calle
        else if (carreraActual == carreraEmergencia) {
            distancia = Math.abs(calleActual - calleEmergencia);
        }
        // Caso 3: Misma calle, diferente carrera
        else if (calleActual == calleEmergencia) {
            distancia = Math.abs(carreraActual - carreraEmergencia);
        }
        // Caso 4: Diferente calle y carrera (distancia Manhattan)
        else {
            distancia = Math.abs(calleActual - calleEmergencia) + Math.abs(carreraActual - carreraEmergencia);
        }

        // Velocidad promedio en km/h
        final double VEL_PROMEDIO = 30.0;

        // Conversión de distancia a kilómetros (si la unidad base es cuadras o metros,
        // ajusta aquí)
        double distanciaEnKm = distancia * 0.05; // Suponiendo que cada unidad es 50 metros.

        // Tiempo en horas (t = d / v)
        double tiempoEnHoras = distanciaEnKm / VEL_PROMEDIO;

        // Convertimos el tiempo a segundos
        return (int) (tiempoEnHoras * 3600);
    }

    public EmergenciaAsignacion crearAtencion(Emergency emergency, int numCarros, RespondTeamCoordinator respondTeamCoordinator) {
        List<IResponder> equipoBomberos = new ArrayList<>();
        int bomberosNecesarios = numCarros * 3; // Por cada carro se requieren 4 bomberos
        System.out.println("Bomberos necesarios: " + bomberosNecesarios);

        int tiempoEstimadoSegundos = calcularTiempo(emergency);

        // Asignar bomberos disponibles
        int asignados = 0;
        for (Bombero bombero : personal) {
            if (bombero.isDisponible() && asignados < bomberosNecesarios) {
                equipoBomberos.add(bombero);
                bombero.atenderEmergencia();
                asignados++;
            }
        }

        // Verificar si hay suficientes bomberos
        if (asignados < bomberosNecesarios) {
            System.out.println("ADVERTENCIA: No hay suficientes bomberos disponibles. Se asignaron "
                    + asignados + " de " + bomberosNecesarios + " requeridos.");
        }

        // Generar ID único para el caso
        String idCaso = UUID.randomUUID().toString();

        // Almacenar información del caso
        EmergenciaAsignacion asignacion = new EmergenciaAsignacion(emergency, equipoBomberos, tiempoEstimadoSegundos, respondTeamCoordinator, "BOMBEROS");
        casos.put(idCaso, asignacion);

        // Formatear y mostrar tiempo estimado de llegada
        imprimirTiempoEstimado(idCaso, tiempoEstimadoSegundos);

        return asignacion;
    }

    private void imprimirTiempoEstimado(String idCaso, int tiempoEstimadoSegundos) {
        if (tiempoEstimadoSegundos < 60) {
            // Menos de un minuto: mostrar solo segundos
            System.out.printf("Caso creado con ID: %s - Tiempo estimado de llegada: %d segundos.%n%n",
                    idCaso, tiempoEstimadoSegundos);
        } else {
            // Más de un minuto: mostrar minutos y segundos
            int minutos = tiempoEstimadoSegundos / 60;
            int segundos = tiempoEstimadoSegundos % 60;

            if (segundos == 0) {
                // Sin segundos restantes
                System.out.printf("Caso creado con ID: %s - Tiempo estimado de llegada: %d minutos.%n%n",
                        idCaso, minutos);
            } else {
                // Con minutos y segundos
                System.out.printf("Caso creado con ID: %s - Tiempo estimado de llegada: %d minutos y %d segundos.%n%n",
                        idCaso, minutos, segundos);
            }
        }
    }

    private static void addPersonal() {
        personal.add(new Bombero(new Persona("Jorge", "Benavides", "02/07/1998", "1020321", "Bombero")));
        personal.add(new Bombero(new Persona("Miguel", "Rodríguez", "15/03/1985", "1020322", "Bombero")));
        personal.add(new Bombero(new Persona("Carlos", "Gutiérrez", "27/11/1990", "1020323", "Bombero")));
        personal.add(new Bombero(new Persona("Luis", "Martínez", "08/05/1992", "1020324", "Bombero")));
        personal.add(new Bombero(new Persona("Ana", "López", "19/09/1988", "1020325", "Bombero")));
        personal.add(new Bombero(new Persona("María", "González", "03/12/1991", "1020326", "Bombero")));
        personal.add(new Bombero(new Persona("Pedro", "Sánchez", "11/04/1986", "1020327", "Bombero")));
        personal.add(new Bombero(new Persona("Juan", "Fernández", "22/10/1989", "1020328", "Bombero")));
        personal.add(new Bombero(new Persona("Isabel", "Moreno", "14/06/1993", "1020329", "Bombero")));
        personal.add(new Bombero(new Persona("Roberto", "Díaz", "30/08/1987", "1020330", "Bombero")));
        personal.add(new Bombero(new Persona("Sofía", "Vega", "25/01/1994", "1020331", "Bombero")));
        personal.add(new Bombero(new Persona("Diego", "Castro", "17/07/1990", "1020332", "Bombero")));
        personal.add(new Bombero(new Persona("Alejandro", "Vargas", "12/03/1995", "1020333", "Bombero")));
        personal.add(new Bombero(new Persona("Gabriela", "Restrepo", "05/09/1991", "1020334", "Bombero")));
        personal.add(new Bombero(new Persona("Pablo", "Jiménez", "27/02/1989", "1020335", "Bombero")));
        personal.add(new Bombero(new Persona("Valentina", "Medina", "18/11/1993", "1020336", "Bombero")));
        personal.add(new Bombero(new Persona("Héctor", "Ruiz", "30/04/1987", "1020337", "Bombero")));
        personal.add(new Bombero(new Persona("Camila", "Ochoa", "14/08/1992", "1020338", "Bombero")));
        personal.add(new Bombero(new Persona("Daniel", "Durán", "21/05/1988", "1020339", "Bombero")));
        personal.add(new Bombero(new Persona("Carolina", "Torres", "03/12/1994", "1020340", "Bombero")));
        personal.add(new Bombero(new Persona("Javier", "Contreras", "09/06/1986", "1020341", "Bombero")));
        personal.add(new Bombero(new Persona("Mariana", "Ortiz", "22/10/1990", "1020342", "Bombero")));
        personal.add(new Bombero(new Persona("Ricardo", "Herrera", "01/07/1992", "1020343", "Bombero")));
        personal.add(new Bombero(new Persona("Natalia", "Parra", "16/04/1989", "1020344", "Bombero")));
    }

    public static List<Bombero> getPersonal() {
        return personal;
    }

    public static Map<String, EmergenciaAsignacion> getCasos() {
        return casos;
    }

    public static EmergenciaAsignacion getCaso(String idCaso) {
        return casos.get(idCaso);
    }

    public static void mostrarStatusCasos() {
        System.out.println("=== STATUS DE CASOS DE BOMBEROS ===\n");
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