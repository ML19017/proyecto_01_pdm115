package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

public class FacturaCompra {
    private int idCompra;
    private int idFarmacia;
    private int idProveedor;
    private String fechaCompra;
    private double totalCompra;

    public FacturaCompra(int idCompra, int idFarmacia, int idProveedor, String fechaCompra, double totalCompra) {
        this.idCompra = idCompra;
        this.idFarmacia = idFarmacia;
        this.idProveedor = idProveedor;
        this.fechaCompra = fechaCompra;
        this.totalCompra = totalCompra;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }
}
