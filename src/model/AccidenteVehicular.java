package model;

import utils.Gravedad;
import utils.TypeEmergency;
import utils.TypeRespuesta;

/**
 * Representa un tipo específico de emergencia: un accidente vehicular.
 * Esta clase extiende de la clase abstracta Emergency y tiene un identificador único.
 */
public class AccidenteVehicular extends Emergency {

    // Atributo estático para generar IDs únicos para cada accidente
    private static int idGeneral = 0;
    
    // Atributo para el ID específico de cada accidente vehicular
    private int id = 0;

    /**
     * Constructor de la clase AccidenteVehicular.
     * @param gravedad La gravedad del accidente (enum Gravedad).
     * @param ubicacion La ubicación donde ocurrió el accidente (de tipo Ubication).
     * @param typeEmergency El tipo de emergencia (enum TypeEmergency).
     * @param typeRespuesta El tipo de respuesta (enum TypeRespuesta).
     */
    public AccidenteVehicular(Gravedad gravedad, Ubication ubicacion, TypeEmergency typeEmergency, TypeRespuesta typeRespuesta) {
        // Llamada al constructor de la clase padre (Emergency)
        super(gravedad, ubicacion, typeEmergency, typeRespuesta);
        
        // Incremento del contador general de IDs y asignación del ID único al accidente
        ++idGeneral;
        this.id = idGeneral;
    }

    /**
     * Obtiene el ID único del accidente vehicular.
     * @return El ID del accidente.
     */
    public int getId() {        
        return id;
    }

}