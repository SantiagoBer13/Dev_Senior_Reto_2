package model.Factory;

import model.AccidenteVehicular;
import model.Emergency;
import model.Incendio;
import model.Robo;
import model.Ubication;
import utils.Gravedad;
import utils.TypeEmergency;
import utils.TypeRespuesta;

public class EmergencyFactory {

    public Emergency getEmergency(TypeEmergency typeEmergency, Gravedad gravedad, Ubication ubicacion) {
        switch(typeEmergency) {
            case INCENDIO:
                return new Incendio(gravedad, ubicacion, typeEmergency, TypeRespuesta.BOMBERO);
            case ACCIDENTE_VEHICULAR:
                return new AccidenteVehicular(gravedad, ubicacion, typeEmergency, TypeRespuesta.MEDICA);
            case ROBO:
                return new Robo(gravedad, ubicacion, typeEmergency, TypeRespuesta.SEGURIDAD);
        }
        return null;
    }

}
