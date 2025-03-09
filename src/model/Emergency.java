package model;

import utils.Gravedad;
import utils.TypeEmergency;
import utils.TypeRespuesta;

/**
 * La clase abstracta Emergency representa una emergencia con atributos relacionados con la gravedad,
 * la ubicación, el tipo de emergencia y el tipo de respuesta a la emergencia.
 */
public abstract class Emergency {

    // Atributos privados de la clase
    private Gravedad gravedad;        // Representa la gravedad de la emergencia (enum Gravedad)
    private Ubication ubicacion;      // Representa la ubicación donde ocurre la emergencia (tipo Ubication)
    private TypeEmergency typeEmergency; // Representa el tipo de emergencia (enum TypeEmergency)
    private TypeRespuesta typeRespuesta; // Representa el tipo de respuesta a la emergencia (enum TypeRespuesta)

    /**
     * Constructor de la clase Emergency.
     * @param gravedad La gravedad de la emergencia, representada por un valor de la clase Gravedad.
     * @param ubicacion La ubicación de la emergencia, representada por un objeto de tipo Ubication.
     * @param typeEmergency El tipo de emergencia, representado por un valor de la clase TypeEmergency.
     * @param typeRespuesta El tipo de respuesta esperada a la emergencia, representado por un valor de la clase TypeRespuesta.
     */
    public Emergency(Gravedad gravedad, Ubication ubicacion, TypeEmergency typeEmergency, TypeRespuesta typeRespuesta) {
        this.gravedad = gravedad;
        this.ubicacion = ubicacion;
        this.typeEmergency = typeEmergency;
        this.typeRespuesta = typeRespuesta;
    }

    /**
     * Obtiene la gravedad de la emergencia.
     * @return La gravedad de la emergencia.
     */
    public Gravedad getGravedad() {
        return gravedad;
    }

    /**
     * Obtiene la ubicación de la emergencia.
     * @return La ubicación donde ocurre la emergencia.
     */
    public Ubication getUbication() {
        return ubicacion;
    }

    /**
     * Obtiene el tipo de emergencia.
     * @return El tipo de emergencia.
     */
    public TypeEmergency getTypeEmergency() {
        return typeEmergency;
    }

    /**
     * Obtiene el tipo de respuesta que se espera para la emergencia.
     * @return El tipo de respuesta a la emergencia.
     */
    public TypeRespuesta getTypeRespuesta() {
        return typeRespuesta;
    }

    /**
     * Sobrescribe el método toString para proporcionar una representación en cadena legible de la emergencia.
     * @return Una cadena que representa la emergencia con su ubicación, gravedad y tipo de emergencia.
     */
    @Override
    public String toString() {
        return "Emergencia [" + ubicacion + ", gravedad = " + gravedad + ", Tipo de Emergencia = " + typeEmergency
                + "]";
    }

}