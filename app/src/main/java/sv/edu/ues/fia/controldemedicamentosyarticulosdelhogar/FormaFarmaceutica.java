package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

public class FormaFarmaceutica {
    private int idFormaFarmaceutica;
    private String tipoFormaFarmaceutica;

    public FormaFarmaceutica(int idFormaFarmaceutica, String tipoFormaFarmaceutica) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
        this.tipoFormaFarmaceutica = tipoFormaFarmaceutica;
    }

    public int getIdFormaFarmaceutica() {
        return idFormaFarmaceutica;
    }

    public void setIdFormaFarmaceutica(int idFormaFarmaceutica) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
    }

    public String getTipoFormaFarmaceutica() {
        return tipoFormaFarmaceutica;
    }

    public void setTipoFormaFarmaceutica(String tipoFormaFarmaceutica) {
        this.tipoFormaFarmaceutica = tipoFormaFarmaceutica;
    }
}