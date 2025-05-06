package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

public class Receta {
    private int idDoctor;
    private int idCliente;
    private int idReceta;
    private String fechaExpedida;
    private String descripcion;

    public Receta(int idDoctor, int idCliente, int idReceta, String fechaExpedida, String descripcion) {
        this.idDoctor = idDoctor;
        this.idCliente = idCliente;
        this.idReceta = idReceta;
        this.fechaExpedida = fechaExpedida;
        this.descripcion = descripcion;
    }

    public int getIdDoctor() { return idDoctor; }
    public int getIdCliente() { return idCliente; }
    public int getIdReceta() { return idReceta; }
    public String getFechaExpedida() { return fechaExpedida; }
    public String getDescripcion() { return descripcion; }

    public void setFechaExpedida(String fechaExpedida) {
        this.fechaExpedida = fechaExpedida;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ID Doctor: " + idDoctor +
                "\nID Cliente: " + idCliente +
                "\nID Receta: " + idReceta +
                "\nFecha Expedida: " + fechaExpedida +
                "\nDescripción: " + descripcion;
    }
}
