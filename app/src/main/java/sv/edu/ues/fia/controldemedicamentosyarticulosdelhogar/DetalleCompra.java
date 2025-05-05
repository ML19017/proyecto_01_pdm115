package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

public class DetalleCompra {
    private int idCompra;
    private int idArticulo;
    private int idDetalleCompra;
    private String fechaDeCompra;
    private double precioUnitarioCompra;
    private int cantidadCompra;
    private double totalDetalleCompra;

    public DetalleCompra(int idArticulo, int idCompra, String fechaDeCompra, int cantidadCompra, double precioUnitarioCompra, double totalDetalleCompra) {
        this.idArticulo = idArticulo;
        this.idCompra = idCompra;
        this.fechaDeCompra = fechaDeCompra;
        this.cantidadCompra = cantidadCompra;
        this.precioUnitarioCompra = precioUnitarioCompra;
        this.totalDetalleCompra = totalDetalleCompra;
    }

    public DetalleCompra(int idCompra, int idArticulo, int idDetalleCompra, String fechaDeCompra, double precioUnitarioCompra, int cantidadCompra, double totalDetalleCompra) {
        this.idCompra = idCompra;
        this.idArticulo = idArticulo;
        this.idDetalleCompra = idDetalleCompra;
        this.fechaDeCompra = fechaDeCompra;
        this.precioUnitarioCompra = precioUnitarioCompra;
        this.cantidadCompra = cantidadCompra;
        this.totalDetalleCompra = totalDetalleCompra;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getIdDetalleCompra() {
        return idDetalleCompra;
    }

    public void setIdDetalleCompra(int idDetalleCompra) {
        this.idDetalleCompra = idDetalleCompra;
    }

    public String getFechaDeCompra() {
        return fechaDeCompra;
    }

    public void setFechaDeCompra(String fechaDeCompra) {
        this.fechaDeCompra = fechaDeCompra;
    }

    public double getPrecioUnitarioCompra() {
        return precioUnitarioCompra;
    }

    public void setPrecioUnitarioCompra(double precioUnitarioCompra) {
        this.precioUnitarioCompra = precioUnitarioCompra;
    }

    public int getCantidadCompra() {
        return cantidadCompra;
    }

    public void setCantidadCompra(int cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }

    public double getTotalDetalleCompra() {
        return totalDetalleCompra;
    }

    public void setTotalDetalleCompra(double totalDetalleCompra) {
        this.totalDetalleCompra = totalDetalleCompra;
    }
}

