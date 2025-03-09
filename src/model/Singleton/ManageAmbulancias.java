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

public class ManageAmbulancias {
    private static ManageAmbulancias instance;
    private static List<Ambulancia> personal = new ArrayList<>();
    private static Map<String, EmergenciaAsignacion> casos = new HashMap<>();
    private static final Ubication ubication = new Ubication("Calle 12", "Carrera 13",
            "Estacion Central de Ambulancias");

    private ManageAmbulancias() {
    }

    public static ManageAmbulancias getInstance() {
        if (instance == null) {
            addPersonal();
            instance = new ManageAmbulancias();
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
        List<IResponder> equipoAmbulancias = new ArrayList<>();
        int paramedicosNecesarios = numCarros * 2; // Por cada ambulancia se requieren 2 paramédicos
        System.out.println("Paramedicos necesarios: " + paramedicosNecesarios);

        int tiempoEstimadoSegundos = calcularTiempo(emergency);

        int asignados = 0;
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

        // Generamos un ID único para el caso
        String idCaso = UUID.randomUUID().toString();

        // Almacenamos la emergencia, el equipo asignado y el tiempo estimado
        EmergenciaAsignacion asignacion = new EmergenciaAsignacion(emergency, equipoAmbulancias,
                tiempoEstimadoSegundos, respondTeamCoordinator, "PARAMEDICOS");
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
        personal.add(new Ambulancia(new Persona("Andrés", "Herrera", "05/04/1988", "2020321", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Laura", "Ramírez", "18/11/1990", "2020322", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Daniel", "Torres", "29/03/1985", "2020323", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Carmen", "Flores", "07/09/1992", "2020324", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Javier", "Rojas", "14/05/1987", "2020325", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Valentina", "Castro", "22/12/1991", "2020326", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Rafael", "Silva", "10/06/1989", "2020327", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Camila", "Ortega", "03/02/1993", "2020328", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Mateo", "Pinzón", "13/08/1986", "2020329", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Isabella", "Valencia", "25/04/1990", "2020330", "Paramédico")));
        personal.add(new Ambulancia(new Persona("David", "Castaño", "02/10/1988", "2020331", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Alejandra", "Giraldo", "19/01/1992", "2020332", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Samuel", "Osorio", "08/07/1984", "2020333", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Catalina", "Mejía", "30/11/1991", "2020334", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Nicolás", "Cardona", "16/05/1989", "2020335", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Valeria", "Cifuentes", "04/03/1993", "2020336", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Juan Pablo", "Álvarez", "21/09/1987", "2020337", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Sara", "Arango", "07/12/1990", "2020338", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Tomás", "Londoño", "11/02/1985", "2020339", "Paramédico")));
        personal.add(new Ambulancia(new Persona("Manuela", "Hoyos", "28/06/1992", "2020340", "Paramédico")));
    }

    public static List<Ambulancia> getPersonal() {
        return personal;
    }

    public static Map<String, EmergenciaAsignacion> getCasos() {
        return casos;
    }

    public static EmergenciaAsignacion getCaso(String idCaso) {
        return casos.get(idCaso);
    }

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
