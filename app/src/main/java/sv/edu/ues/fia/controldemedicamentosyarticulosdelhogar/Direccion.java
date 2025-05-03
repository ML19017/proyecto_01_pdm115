package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

public class Direccion {
    private int idDireccion;
    private int idDistrito;
    private String direccionExacta;

    public Direccion(int idDireccion, int idDistrito, String direccionExacta) {
        this.idDireccion = idDireccion;
        this.idDistrito = idDistrito;
        this.direccionExacta = direccionExacta;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public int getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(int idDistrito) {
        this.idDistrito = idDistrito;
    }

    public String getDireccionExacta() {
        return direccionExacta;
    }

    public void setDireccionExacta(String direccionExacta) {
        this.direccionExacta = direccionExacta;
    }
}
