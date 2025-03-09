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
    private static ManagePolicias instance;
    private static List<Policia> personal = new ArrayList<>();
    private static Map<String, EmergenciaAsignacion> casos = new HashMap<>();
    private static final Ubication ubication = new Ubication("Calle 2", "Carrera 3", "Estacion Central de Policias");

    private ManagePolicias() {
    }

    public static ManagePolicias getInstance() {
        if (instance == null) {
            addPersonal();
            instance = new ManagePolicias();
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
        List<IResponder> equipoPolicias = new ArrayList<>();
        int policiasNecesarios = numCarros * 2; // Por cada carro se requieren 3 policías
        System.out.println("Policias necesarios: " + policiasNecesarios);

        int tiempoEstimadoSegundos = calcularTiempo(emergency);

        int asignados = 0;
        for (Policia policia : personal) {
            if (policia.isDisponible() && asignados < policiasNecesarios) {
                equipoPolicias.add(policia);
                policia.atenderEmergencia();
                asignados++;
            }
        }

        if (asignados < policiasNecesarios) {
            System.out.println("ADVERTENCIA: No hay suficientes policías disponibles. Se asignaron "
                    + asignados + " de " + policiasNecesarios + " requeridos.");
        }

        // Generamos un ID único para el caso
        String idCaso = UUID.randomUUID().toString();

        EmergenciaAsignacion asignacion = new EmergenciaAsignacion(emergency, equipoPolicias, tiempoEstimadoSegundos, respondTeamCoordinator, "POLICIAS");

        casos.put(idCaso, asignacion);
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
        personal.add(new Policia(new Persona("Martín", "Pérez", "12/08/1986", "3020321", "Policía")));
        personal.add(new Policia(new Persona("Lucía", "Gómez", "24/02/1990", "3020322", "Policía")));
        personal.add(new Policia(new Persona("Sergio", "Vargas", "09/05/1988", "3020323", "Policía")));
        personal.add(new Policia(new Persona("Marina", "Soto", "17/11/1992", "3020324", "Policía")));
        personal.add(new Policia(new Persona("Fernando", "Rivas", "03/07/1984", "3020325", "Policía")));
        personal.add(new Policia(new Persona("Patricia", "Luna", "29/03/1991", "3020326", "Policía")));
        personal.add(new Policia(new Persona("Ricardo", "Morales", "15/10/1989", "3020327", "Policía")));
        personal.add(new Policia(new Persona("Natalia", "Pinto", "21/04/1987", "3020328", "Policía")));
        personal.add(new Policia(new Persona("Eduardo", "Mendoza", "06/12/1993", "3020329", "Policía")));
        personal.add(new Policia(new Persona("Carolina", "Reyes", "13/09/1985", "3020330", "Policía")));
        personal.add(new Policia(new Persona("Santiago", "Marín", "28/07/1990", "3020331", "Policía")));
        personal.add(new Policia(new Persona("Andrea", "Carvajal", "15/01/1988", "3020332", "Policía")));
        personal.add(new Policia(new Persona("Felipe", "Ospina", "20/05/1992", "3020333", "Policía")));
        personal.add(new Policia(new Persona("Daniela", "Quintero", "11/10/1986", "3020334", "Policía")));
        personal.add(new Policia(new Persona("Sebastián", "Aguilar", "03/03/1989", "3020335", "Policía")));
        personal.add(new Policia(new Persona("Paula", "Arias", "25/08/1993", "3020336", "Policía")));
        personal.add(new Policia(new Persona("Gabriel", "Zúñiga", "07/06/1985", "3020337", "Policía")));
        personal.add(new Policia(new Persona("Mónica", "Escobar", "19/11/1991", "3020338", "Policía")));
        personal.add(new Policia(new Persona("Cristian", "Salazar", "14/04/1987", "3020339", "Policía")));
        personal.add(new Policia(new Persona("Juliana", "Gallego", "30/09/1990", "3020340", "Policía")));
        personal.add(new Policia(new Persona("Jonathan", "Villegas", "22/12/1988", "3020341", "Policía")));
        personal.add(new Policia(new Persona("Diana", "Duque", "10/02/1992", "3020342", "Policía")));
        personal.add(new Policia(new Persona("Oscar", "Jaramillo", "05/07/1984", "3020343", "Policía")));
        personal.add(new Policia(new Persona("Marcela", "Rivera", "28/03/1993", "3020344", "Policía")));
        personal.add(new Policia(new Persona("Andrés", "Muñoz", "16/10/1989", "3020345", "Policía")));
    }

    public static List<Policia> getPersonal() {
        return personal;
    }

    public static Map<String, EmergenciaAsignacion> getCasos() {
        return casos;
    }

    public static EmergenciaAsignacion getCaso(String idCaso) {
        return casos.get(idCaso);
    }

    public static void mostrarStatusCasos() {
        System.out.println("=== STATUS DE CASOS DE POLICIAS ===\n");
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
