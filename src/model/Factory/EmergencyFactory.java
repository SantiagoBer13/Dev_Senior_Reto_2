package model.Factory;

import model.AccidenteVehicular;
import model.Emergency;
import model.Incendio;
import model.Robo;
import model.Ubication;
import utils.Gravedad;
import utils.TypeEmergency;
import utils.TypeRespuesta;

/**
 * La clase EmergencyFactory es un patrón de diseño de tipo Factory utilizado para crear objetos de emergencias
 * basados en el tipo de emergencia proporcionado. Este patrón permite abstraer la creación de diferentes tipos
 * de emergencias sin que el cliente tenga que conocer los detalles de la creación.
 */
public class EmergencyFactory {

    /**
     * Método para obtener una emergencia específica basada en el tipo, gravedad y ubicación.
     * 
     * @param typeEmergency El tipo de emergencia (por ejemplo, incendio, accidente vehicular, robo).
     * @param gravedad La gravedad de la emergencia (baja, media, alta).
     * @param ubicacion La ubicación de la emergencia.
     * @return Un objeto de tipo Emergency según el tipo de emergencia.
     */
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