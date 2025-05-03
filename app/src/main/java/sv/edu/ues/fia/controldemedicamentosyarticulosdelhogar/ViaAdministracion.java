package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

public class ViaAdministracion {
    private int idViaAdministracion;
    private String tipoAdministracion;

    public ViaAdministracion(int idViaAdministracion, String tipoAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
        this.tipoAdministracion = tipoAdministracion;
    }

    public int getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(int idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public String getTipoAdministracion() {
        return tipoAdministracion;
    }

    public void setTipoAdministracion(String tipoAdministracion) {
        this.tipoAdministracion = tipoAdministracion;
    }
}

