package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

public class DetalleVenta {
    private int idCliente;
    private int idVenta;
    private int idArticulo;
    private int idVentaDetalle;
    private int cantidadVenta;
    private double precioUnitarioVenta;
    private String fechaDeVenta;
    private double totalDetalleVenta;

    public DetalleVenta(int idCliente, int idVenta, int idArticulo, int idVentaDetalle, int cantidadVenta, double precioUnitarioVenta, String fechaDeVenta, double totalDetalleVenta) {
        this.idCliente = idCliente;
        this.idVenta = idVenta;
        this.idArticulo = idArticulo;
        this.idVentaDetalle = idVentaDetalle;
        this.cantidadVenta = cantidadVenta;
        this.precioUnitarioVenta = precioUnitarioVenta;
        this.fechaDeVenta = fechaDeVenta;
        this.totalDetalleVenta = totalDetalleVenta;
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

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getIdVentaDetalle() {
        return idVentaDetalle;
    }

    public void setIdVentaDetalle(int idVentaDetalle) {
        this.idVentaDetalle = idVentaDetalle;
    }

    public int getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(int cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public double getPrecioUnitarioVenta() {
        return precioUnitarioVenta;
    }

    public void setPrecioUnitarioVenta(double precioUnitarioVenta) {
        this.precioUnitarioVenta = precioUnitarioVenta;
    }

    public String getFechaDeVenta() {
        return fechaDeVenta;
    }

    public void setFechaDeVenta(String fechaDeVenta) {
        this.fechaDeVenta = fechaDeVenta;
    }

    public double getTotalDetalleVenta() {
        return totalDetalleVenta;
    }

    public void setTotalDetalleVenta(double totalDetalleVenta) {
        this.totalDetalleVenta = totalDetalleVenta;
    }
}