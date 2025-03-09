package model;

import utils.Gravedad;
import utils.TypeEmergency;
import utils.TypeRespuesta;

/**
 * Representa un tipo específico de emergencia: un incendio.
 * Esta clase extiende de la clase abstracta Emergency y tiene un identificador único.
 */
public class Incendio extends Emergency {

    // Atributo estático para generar IDs únicos para cada incendio
    private static int idGeneral = 0;
    
    // Atributo para el ID específico de cada incendio
    private int id = 0;

    /**
     * Constructor de la clase Incendio.
     * @param gravedad La gravedad del incendio (enum Gravedad).
     * @param ubicacion La ubicación donde ocurrió el incendio (de tipo Ubication).
     * @param typeEmergency El tipo de emergencia (enum TypeEmergency).
     * @param typeRespuesta El tipo de respuesta (enum TypeRespuesta).
     */
    public Incendio(Gravedad gravedad, Ubication ubicacion, TypeEmergency typeEmergency, TypeRespuesta typeRespuesta) {
        // Llamada al constructor de la clase padre (Emergency)
        super(gravedad, ubicacion, typeEmergency, typeRespuesta);
        
        // Incremento del contador general de IDs y asignación del ID único al incendio
        ++idGeneral;
        this.id = idGeneral;
    }

    /**
     * Obtiene el ID único del incendio.
     * @return El ID del incendio.
     */
    public int getId() {        
        return id;
    }

}