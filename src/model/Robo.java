package model;

import utils.Gravedad;
import utils.TypeEmergency;
import utils.TypeRespuesta;

public class Robo extends Emergency {

    private static int idGeneral = 0;
    private int id = 0;

    public Robo(Gravedad gravedad, Ubication ubicacion, TypeEmergency typeEmergency, TypeRespuesta typeRespuesta) {
        super(gravedad, ubicacion, typeEmergency, typeRespuesta);
        ++idGeneral;
        this.id = idGeneral;
    }

    public int getId() {        
        return id;
    }
}
