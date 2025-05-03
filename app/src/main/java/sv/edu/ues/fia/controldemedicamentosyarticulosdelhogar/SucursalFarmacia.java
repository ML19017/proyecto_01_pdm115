package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

public class SucursalFarmacia {
    private int idFarmacia;
    private int idDireccion;
    private String nombreFarmacia;

    public SucursalFarmacia(int idFarmacia, int idDireccion, String nombreFarmacia) {
        this.idFarmacia = idFarmacia;
        this.idDireccion = idDireccion;
        this.nombreFarmacia = nombreFarmacia;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public int getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(int idDireccion) {
        this.idDireccion = idDireccion;
    }

    public String getNombreFarmacia() {
        return nombreFarmacia;
    }

    public void setNombreFarmacia(String nombreFarmacia) {
        this.nombreFarmacia = nombreFarmacia;
    }
}
