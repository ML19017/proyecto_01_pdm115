package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

public class FacturaVenta {
    private int idCliente;
    private int idVenta;
    private int idFarmacia;
    private String fechaVenta;
    private double totalVenta;

    public FacturaVenta(int idCliente, int idVenta, int idFarmacia, String fechaVenta, double totalVenta) {
        this.idCliente = idCliente;
        this.idVenta = idVenta;
        this.idFarmacia = idFarmacia;
        this.fechaVenta = fechaVenta;
        this.totalVenta = totalVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }
}

