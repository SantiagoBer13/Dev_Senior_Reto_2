package model;
import utils.Gravedad;
import utils.TypeEmergency;
import utils.TypeRespuesta;

public abstract class Emergency {
    
    private Gravedad gravedad;
    private Ubication ubicacion;
    private TypeEmergency typeEmergency;
    private TypeRespuesta typeRespuesta;

    public Emergency(Gravedad gravedad, Ubication ubicacion, TypeEmergency typeEmergency, TypeRespuesta typeRespuesta) {
        this.gravedad = gravedad;
        this.ubicacion = ubicacion;
        this.typeEmergency = typeEmergency;
        this.typeRespuesta = typeRespuesta;
    }

    public Gravedad getGravedad() {
        return gravedad;
    }

    public Ubication getUbication() {
        return ubicacion;
    }

    public TypeEmergency getTypeEmergency() {
        return typeEmergency;
    }

    public TypeRespuesta getTypeRespuesta() {
        return typeRespuesta;
    }

    @Override
    public String toString() {
        return "Emergencia [" + ubicacion + ", gravedad = " + gravedad + ", Tipo de Emergencia = " + typeEmergency
                + "]";
    }

}
