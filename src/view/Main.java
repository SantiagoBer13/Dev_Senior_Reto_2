package view;

import java.util.Scanner;

import controller.SistemaEmergencias;
import model.Factory.EmergencyFactory;
import model.Factory.RespuestaFactory;

public class Main {

    public static void main(String[] args) {
        SistemaEmergencias se = new SistemaEmergencias(new Scanner(System.in), new RespuestaFactory(), new EmergencyFactory());
        se.mostrarMenu();
    }

}
