package model;

import utils.Gravedad;
import utils.TypeEmergency;
import utils.TypeRespuesta;

/**
 * Representa un tipo específico de emergencia: un robo.
 * Esta clase extiende de la clase abstracta Emergency y tiene un identificador único.
 */
public class Robo extends Emergency {

    // Atributo estático para generar IDs únicos para cada robo
    private static int idGeneral = 0;
    
    // Atributo para el ID específico de cada robo
    private int id = 0;

    /**
     * Constructor de la clase Robo.
     * @param gravedad La gravedad del robo (de tipo Gravedad).
     * @param ubicacion La ubicación donde ocurrió el robo (de tipo Ubication).
     * @param typeEmergency El tipo de emergencia (de tipo TypeEmergency).
     * @param typeRespuesta El tipo de respuesta (de tipo TypeRespuesta).
     */
    public Robo(Gravedad gravedad, Ubication ubicacion, TypeEmergency typeEmergency, TypeRespuesta typeRespuesta) {
        // Llamada al constructor de la clase padre (Emergency)
        super(gravedad, ubicacion, typeEmergency, typeRespuesta);
        
        // Incremento del contador general de IDs y asignación del ID único al robo
        ++idGeneral;
        this.id = idGeneral;
    }

    /**
     * Obtiene el ID único del robo.
     * @return El ID del robo.
     */
    public int getId() {        
        return id;
    }
}