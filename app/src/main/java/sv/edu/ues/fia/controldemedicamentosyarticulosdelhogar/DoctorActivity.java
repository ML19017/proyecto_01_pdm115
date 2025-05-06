package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class DoctorActivity extends AppCompatActivity {

    private ListView listView;
    private Button btnAgregar;
    private DoctorDAO doctorDAO;
    private ArrayAdapter<Doctor> adapter;
    private List<Doctor> listaDoctores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor); // Asegúrate de crear este layout

        listView = findViewById(R.id.lvDoctor);
        btnAgregar = findViewById(R.id.btnAgregarDoctor);

        SQLiteDatabase db = openOrCreateDatabase("medicamentos", MODE_PRIVATE, null);
        doctorDAO = new DoctorDAO(db, this);
        cargarDoctores();

        btnAgregar.setOnClickListener(view -> mostrarDialogo(null));

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Doctor doctor = listaDoctores.get(position);
            mostrarDialogo(doctor);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Doctor doctor = listaDoctores.get(position);
            new AlertDialog.Builder(this)
                    .setTitle(R.string.delete)
                    .setMessage(R.string.confirm_delete)
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        doctorDAO.deleteDoctor(doctor.getIdDoctor());
                        cargarDoctores();
                    })
                    .setNegativeButton(R.string.no, null)
                    .show();
            return true;
        });
    }

    private void cargarDoctores() {
        listaDoctores = doctorDAO.getAllDoctors();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDoctores);
        listView.setAdapter(adapter);
    }

    private void mostrarDialogo(Doctor doctorExistente) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_doctor, null);
        EditText etId = dialogView.findViewById(R.id.etIdDoctor);
        EditText etNombre = dialogView.findViewById(R.id.etNombreDoctor);
        EditText etEspecialidad = dialogView.findViewById(R.id.etEspecialidadDoctor);
        EditText etJvpm = dialogView.findViewById(R.id.etJvpm);

        if (doctorExistente != null) {
            etId.setText(String.valueOf(doctorExistente.getIdDoctor()));
            etId.setEnabled(false);
            etNombre.setText(doctorExistente.getNombreDoctor());
            etEspecialidad.setText(doctorExistente.getEspecialidadDoctor());
            etJvpm.setText(doctorExistente.getJvpm());
        }

        new AlertDialog.Builder(this)
                //.setTitle(doctorExistente == null ? R.string.add_doctor : R.string.edit_doctor)
                .setView(dialogView)
                .setPositiveButton(R.string.save, (dialog, which) -> {
                    int id = Integer.parseInt(etId.getText().toString());
                    String nombre = etNombre.getText().toString();
                    String especialidad = etEspecialidad.getText().toString();
                    String jvpm = etJvpm.getText().toString();

                    Doctor d = new Doctor(id, nombre, especialidad, jvpm);
                    if (doctorExistente == null) {
                        doctorDAO.addDoctor(d);
                    } else {
                        doctorDAO.updateDoctor(d);
                    }
                    cargarDoctores();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
