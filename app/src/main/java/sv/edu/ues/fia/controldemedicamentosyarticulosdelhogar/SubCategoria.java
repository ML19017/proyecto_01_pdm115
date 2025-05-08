package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

public class SubCategoria {
    private int idSubCategoria;
    private int idCategoria;
    private String nombreSubCategoria;

    public SubCategoria(int idSubCategoria, int idCategoria, String nombreSubCategoria) {
        this.idSubCategoria = idSubCategoria;
        this.idCategoria = idCategoria;
        this.nombreSubCategoria = nombreSubCategoria;
    }

    public SubCategoria() {
    }

    public int getIdSubCategoria() {
        return idSubCategoria;
    }
    public void setIdSubCategoria(int idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreSubCategoria() {
        return nombreSubCategoria;
    }

    public void setNombreSubCategoria(String nombreSubCategoria) {
        this.nombreSubCategoria = nombreSubCategoria;
    }

    @Override
    public String toString() {
        return "Sub-categoria:" +
                "\nID= " + idSubCategoria +
                "\nNombre='" + nombreSubCategoria;
    }
}


