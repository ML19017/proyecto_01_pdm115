package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.Context;

public class Articulo {
    private int idArticulo;
    private int idMarca;
    private int idViaAdministracion;
    private int idSubCategoria;
    private int idDetalleExistencia; //No debe ir aca, sujeto a revision
    private int idFormaFarmaceutica;
    private String nombreArticulo;
    private String descripcionArticulo;
    private boolean restringidoArticulo;
    private double precioArticulo;
    private Context context;

    public Articulo(int idArticulo, int idMarca, int idViaAdministracion, int idSubCategoria, int idDetalleExistencia, int idFormaFarmaceutica, String nombreArticulo, String descripcionArticulo, boolean restringidoArticulo, double precioArticulo) {
        this.idArticulo = idArticulo;
        this.idMarca = idMarca;
        this.idViaAdministracion = idViaAdministracion;
        this.idSubCategoria = idSubCategoria;
        this.idDetalleExistencia = idDetalleExistencia;
        this.idFormaFarmaceutica = idFormaFarmaceutica;
        this.nombreArticulo = nombreArticulo;
        this.descripcionArticulo = descripcionArticulo;
        this.restringidoArticulo = restringidoArticulo;
        this.precioArticulo = precioArticulo;
    }

    public Articulo(int idArticulo, String nombreArticulo, Context context) {
        this.idArticulo = idArticulo;
        this.nombreArticulo = nombreArticulo;
        this.context = context;
    }

    public int getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(int idArticulo) {
        this.idArticulo = idArticulo;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public int getIdViaAdministracion() {
        return idViaAdministracion;
    }

    public void setIdViaAdministracion(int idViaAdministracion) {
        this.idViaAdministracion = idViaAdministracion;
    }

    public int getIdSubCategoria() {
        return idSubCategoria;
    }

    public void setIdSubCategoria(int idSubCategoria) {
        this.idSubCategoria = idSubCategoria;
    }

    public int getIdDetalleExistencia() {
        return idDetalleExistencia;
    }

    public void setIdDetalleExistencia(int idDetalleExistencia) {
        this.idDetalleExistencia = idDetalleExistencia;
    }

    public int getIdFormaFarmaceutica() {
        return idFormaFarmaceutica;
    }

    public void setIdFormaFarmaceutica(int idFormaFarmaceutica) {
        this.idFormaFarmaceutica = idFormaFarmaceutica;
    }

    public String getNombreArticulo() {
        return nombreArticulo;
    }

    public void setNombreArticulo(String nombreArticulo) {
        this.nombreArticulo = nombreArticulo;
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
        this.descripcionArticulo = descripcionArticulo;
    }

    public boolean getRestringidoArticulo() {
        return restringidoArticulo;
    }

    public void setRestringidoArticulo(boolean restringidoArticulo) {
        this.restringidoArticulo = restringidoArticulo;
    }

    public double getPrecioArticulo() {
        return precioArticulo;
    }

    public void setPrecioArticulo(double precioArticulo) {
        this.precioArticulo = precioArticulo;
    }

    public String toString() {
        if (getIdArticulo() == -1) {
            return context.getString(R.string.select_articulo);
        }
        return "ID Articulo : " + getIdArticulo() + "\n" +"Nombre: " + getNombreArticulo();
    }
}

