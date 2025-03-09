package controller;
import java.util.Scanner;

import model.Emergency;
import model.Ubication;
import model.Factory.EmergencyFactory;
import model.Factory.RespuestaFactory;
import utils.Gravedad;
import utils.TypeEmergency;

public class SistemaEmergencias {
    private Scanner scanner;
    private RespuestaFactory factoryRespuesta;
    private EmergencyFactory factoryEmergency;


    public SistemaEmergencias(Scanner scanner, RespuestaFactory factoryRespuesta, EmergencyFactory factoryEmergency) {
        this.scanner = scanner;
        this.factoryRespuesta = factoryRespuesta;
        this.factoryEmergency = factoryEmergency;
    }

    public void mostrarMenu() {
        int option = 0;
        do {
            imprimirOpcionesMenu();
            option = leerOpcion();
            procesarOpcion(option);
        } while (option != 4);
    }

    private void imprimirOpcionesMenu() {
        System.out.println("\n=== Sistema de Emergencias ===");
        System.out.println("===          Menu          ===");
        System.out.println("1) Registrar nueva emergencia.");
        System.out.println("2) Estado de los recursos disponibles.");
        System.out.println("3) Mostrar estadisticas.");
        System.out.println("4) Salir.");
        System.out.print("Ingresa una opción: ");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void procesarOpcion(int option) {
        switch (option) {
            case 1:
                registrarNuevaEmergencia();
                break;
            case 2:
                consultarRecursosDisponibles();
                break;
            case 3:
                mostrarEstadisticas();
                break;
            case 4:
                System.out.println("Saliendo..");
                break;
            default:
                System.out.println("Opción incorrecta.");
                break;
        }
    }

    private void registrarNuevaEmergencia() {
        // Selección de tipo de emergencia
        TypeEmergency typeEmergency = seleccionarTipoEmergencia();
        
        // Selección de gravedad
        Gravedad gravedad = seleccionarGravedad();
        
        // Crear ubicación
        Ubication ubicacion = crearUbicacion();
        
        // Crear emergencia
        Emergency emergencia = factoryEmergency.getEmergency(typeEmergency, gravedad, ubicacion);
        
        // Procesar recursos para la emergencia
        procesarRecursosEmergencia(emergencia);
    }

    private TypeEmergency seleccionarTipoEmergencia() {
        System.out.println("Escoja el tipo de emergencia:");
        System.out.println("(1) Incendio, (2) Accidente Vehicular, (3) Robo");
        int option = Integer.parseInt(scanner.nextLine());
        
        return switch(option) {
            case 1 -> TypeEmergency.INCENDIO;
            case 2 -> TypeEmergency.ACCIDENTE_VEHICULAR;
            case 3 -> TypeEmergency.ROBO;
            default -> throw new IllegalArgumentException("Tipo de emergencia inválido");
        };
    }

    private Gravedad seleccionarGravedad() {
        System.out.println("Escoja la gravedad de la emergencia:");
        System.out.println("(1) Baja, (2) Media, (3) Alta");
        int option = Integer.parseInt(scanner.nextLine());
        
        return switch(option) {
            case 1 -> Gravedad.BAJA;
            case 2 -> Gravedad.MEDIA;
            case 3 -> Gravedad.ALTA;
            default -> throw new IllegalArgumentException("Gravedad inválida");
        };
    }

    private Ubication crearUbicacion() {
        System.out.println("Ingresar la ubicacion de la emergencia.");
        
        System.out.print("Ingresa número de calle 1 - 100: ");
        String calle = "Calle " + scanner.nextLine();
        
        System.out.print("Ingresa número de carrera 1 - 100: ");
        String carrera = "Carrera " + scanner.nextLine();
        
        System.out.print("Ingresa la descripción de la ubicación: ");
        String descripcion = scanner.nextLine();
        
        return new Ubication(calle, carrera, descripcion);
    }

    private void procesarRecursosEmergencia(Emergency emergencia) {
        System.out.println("\n=================================");
        System.out.println("Recursos necesarios para este caso.\n");
        
        factoryRespuesta.getRespuestas(emergencia);
        
        System.out.println("=======================");
    }

    private void consultarRecursosDisponibles() {
        factoryRespuesta.getStatusPersona();
    }

    private void mostrarEstadisticas() {
        factoryRespuesta.showStatus();
    }
}