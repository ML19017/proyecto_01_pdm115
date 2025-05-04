package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

public class Proveedor {
    private int idProveedor;
    private String nombreProveedor;
    private String telefonoProveedor;
    private String direccionProveedor;
    private String rubroProveedor;
    private String numRegProveedor;
    private String nitProveedor;
    private String giroProveedor;

    public Proveedor(String nombreProveedor, String telefonoProveedor, String direccionProveedor,
                     String rubroProveedor, String numRegProveedor, String nitProveedor, String giroProveedor) {
        this.nombreProveedor = nombreProveedor;
        this.telefonoProveedor = telefonoProveedor;
        this.direccionProveedor = direccionProveedor;
        this.rubroProveedor = rubroProveedor;
        this.numRegProveedor = numRegProveedor;
        this.nitProveedor = nitProveedor;
        this.giroProveedor = giroProveedor;
    }

    public Proveedor(int idProveedor, String nombreProveedor, String telefonoProveedor, String direccionProveedor,
                     String rubroProveedor, String numRegProveedor, String nitProveedor, String giroProveedor) {
        this.idProveedor = idProveedor;
        this.nombreProveedor = nombreProveedor;
        this.telefonoProveedor = telefonoProveedor;
        this.direccionProveedor = direccionProveedor;
        this.rubroProveedor = rubroProveedor;
        this.numRegProveedor = numRegProveedor;
        this.nitProveedor = nitProveedor;
        this.giroProveedor = giroProveedor;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public String getRubroProveedor() {
        return rubroProveedor;
    }

    public void setRubroProveedor(String rubroProveedor) {
        this.rubroProveedor = rubroProveedor;
    }

    public String getNumRegProveedor() {
        return numRegProveedor;
    }

    public void setNumRegProveedor(String numRegProveedor) {
        this.numRegProveedor = numRegProveedor;
    }

    public String getNitProveedor() {
        return nitProveedor;
    }

    public void setNitProveedor(String nitProveedor) {
        this.nitProveedor = nitProveedor;
    }

    public String getGiroProveedor() {
        return giroProveedor;
    }

    public void setGiroProveedor(String giroProveedor) {
        this.giroProveedor = giroProveedor;
    }

    // esto devuelve algunos atributos de la tabla y los muestra en el listview
    @Override
    public String toString() {
        return "# " + getIdProveedor() + " " + getNombreProveedor() + " - " + getTelefonoProveedor();
    }
}
