package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

public class Doctor {
    private int idDoctor;
    private String nombreDoctor;
    private String especialidadDoctor;
    private String jvpm;

    public Doctor(int idDoctor, String nombreDoctor, String especialidadDoctor, String jvpm) {
        this.idDoctor = idDoctor;
        this.nombreDoctor = nombreDoctor;
        this.especialidadDoctor = especialidadDoctor;
        this.jvpm = jvpm;
    }

    public int getIdDoctor() { return idDoctor; }
    public String getNombreDoctor() { return nombreDoctor; }
    public String getEspecialidadDoctor() { return especialidadDoctor; }
    public String getJvpm() { return jvpm; }

    public void setIdDoctor(int idDoctor) { this.idDoctor = idDoctor; }
    public void setNombreDoctor(String nombreDoctor) { this.nombreDoctor = nombreDoctor; }
    public void setEspecialidadDoctor(String especialidadDoctor) { this.especialidadDoctor = especialidadDoctor; }
    public void setJvpm(String jvpm) { this.jvpm = jvpm; }

    @Override
    public String toString() {
        return idDoctor + " - " + nombreDoctor + " (" + especialidadDoctor + ")";
    }
}
