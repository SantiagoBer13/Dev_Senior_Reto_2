package model;
public class Ubication {

    private String calle;
    private String carrera;
    private String descripcion;

    public Ubication(String calle, String carrera, String descripcion) {
        this.calle = calle;
        this.carrera = carrera;
        this.descripcion = descripcion;
    }

    public int getCalle() {
        return Integer.valueOf(calle.split(" ")[1]);
    }

    public int getCarrera() {
        return Integer.parseInt(carrera.split(" ")[1]);
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Ubicación {" +
                " calle = '" + calle + '\'' +
                ", carrera = '" + carrera + '\'' +
                ", descripcion = '" + descripcion + '\'' +
                '}';
    }

}
