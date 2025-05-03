package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

public class DetalleExistencia {
    private int idArticulo;
    private int idDetalleExistencia;
    private int idFarmacia;
    private int cantidadExistencia;
    private String fechaDeVencimiento;


    public DetalleExistencia(int idArticulo, int idDetalleExistencia, int idFarmacia, int cantidadExistencia, String fechaDeVencimiento) {
        this.idArticulo = idArticulo;
        this.idDetalleExistencia = idDetalleExistencia;
        this.idFarmacia = idFarmacia;
        this.cantidadExistencia = cantidadExistencia;
        this.fechaDeVencimiento = fechaDeVencimiento;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getIdDetalleExistencia() {
        return idDetalleExistencia;
    }

    public void setIdDetalleExistencia(int idDetalleExistencia) {
        this.idDetalleExistencia = idDetalleExistencia;
    }

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public int getCantidadExistencia() {
        return cantidadExistencia;
    }

    public void setCantidadExistencia(int cantidadExistencia) {
        this.cantidadExistencia = cantidadExistencia;
    }

    public String getFechaDeVencimiento() {
        return fechaDeVencimiento;
    }

    public void setFechaDeVencimiento(String fechaDeVencimiento) {
        this.fechaDeVencimiento = fechaDeVencimiento;
    }
}