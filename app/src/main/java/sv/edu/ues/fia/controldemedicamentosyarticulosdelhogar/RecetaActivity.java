package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class RecetaActivity extends AppCompatActivity {

    private RecetaDAO recetaDAO;
    private ListView listViewRecetas;
    private ArrayAdapter<Receta> adaptador;
    private List<Receta> listaRecetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta);

        SQLiteDatabase db = new ControlBD(this).getConnection();
        recetaDAO = new RecetaDAO(db, this);

        listViewRecetas = findViewById(R.id.lvReceta);
        Button btnAgregar = findViewById(R.id.btnAgregarReceta);

        btnAgregar.setOnClickListener(v -> showAddDialog());

        cargarLista();
        listViewRecetas.setOnItemClickListener((parent, view, position, id) -> {
            Receta receta = (Receta) parent.getItemAtPosition(position);
            mostrarOpciones(receta);
        });
    }

    private void cargarLista() {
        listaRecetas = recetaDAO.getAllRecetas();
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaRecetas);
        listViewRecetas.setAdapter(adaptador);
    }

    private void mostrarOpciones(Receta receta) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.options);

        View view = getLayoutInflater().inflate(R.layout.dialog_options, null); // reutiliza dialog_options.xml
        builder.setView(view);

        AlertDialog dialog = builder.create();

        view.findViewById(R.id.buttonView).setOnClickListener(v -> {
            verReceta(receta);
            dialog.dismiss();
        });

        view.findViewById(R.id.buttonEdit).setOnClickListener(v -> {
            editarReceta(receta);
            dialog.dismiss();
        });

        view.findViewById(R.id.buttonDelete).setOnClickListener(v -> {
            recetaDAO.deleteReceta(receta.getIdReceta());
            cargarLista();
            dialog.dismiss();
        });

        dialog.show();
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add);

        View view = getLayoutInflater().inflate(R.layout.dialog_receta, null);
        builder.setView(view);

        EditText etIdDoctor = view.findViewById(R.id.editTextIdDoctor);
        EditText etIdCliente = view.findViewById(R.id.editTextIdCliente);
        EditText etIdReceta = view.findViewById(R.id.editTextIdReceta);
        TextView tvFecha = view.findViewById(R.id.textViewFechaExpedida);
        EditText etDescripcion = view.findViewById(R.id.editTextDescripcion);
        Button btnGuardar = view.findViewById(R.id.btnGuardarReceta);
        Button btnLimpiar = view.findViewById(R.id.btnLimpiarReceta);

        tvFecha.setOnClickListener(v -> mostrarDatePicker(tvFecha));

        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            List<View> vistas = Arrays.asList(etIdDoctor, etIdCliente, etIdReceta, tvFecha, etDescripcion);
            List<String> regex = Arrays.asList("^\\d+$", "^\\d+$", "^\\d+$", "^\\d{4}-\\d{2}-\\d{2}$", "^[\\s\\S]{1,}$");
            List<Integer> mensajesError = Arrays.asList(
                    R.string.only_numbers,
                    R.string.only_numbers,
                    R.string.only_numbers,
                    R.string.invalid_date_format,
                    R.string.required_field
            );

            ValidadorDeCampos validador = new ValidadorDeCampos(this, vistas, regex, mensajesError);

            if (validador.validarCampos()) {
                int idDoctor = Integer.parseInt(etIdDoctor.getText().toString().trim());
                int idCliente = Integer.parseInt(etIdCliente.getText().toString().trim());
                int idReceta = Integer.parseInt(etIdReceta.getText().toString().trim());
                String fecha = tvFecha.getText().toString().trim();
                String descripcion = etDescripcion.getText().toString().trim();

                Receta receta = new Receta(idDoctor, idCliente, idReceta, fecha, descripcion);
                recetaDAO.addReceta(receta);
                cargarLista();
                dialog.dismiss();
            }
        });

        btnLimpiar.setOnClickListener(v -> {
            etIdDoctor.setText("");
            etIdCliente.setText("");
            etIdReceta.setText("");
            tvFecha.setText("");
            etDescripcion.setText("");
        });

        dialog.show();
    }

    private void mostrarDatePicker(TextView target) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR), month = c.get(Calendar.MONTH), day = c.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this, (view, y, m, d) -> {
            String fecha = y + "-" + String.format("%02d", (m + 1)) + "-" + String.format("%02d", d);
            target.setText(fecha);
        }, year, month, day).show();
    }

    private void verReceta(Receta receta) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.prescription);

        View view = getLayoutInflater().inflate(R.layout.dialog_receta, null);
        builder.setView(view);

        EditText etIdDoctor = view.findViewById(R.id.editTextIdDoctor);
        EditText etIdCliente = view.findViewById(R.id.editTextIdCliente);
        EditText etIdReceta = view.findViewById(R.id.editTextIdReceta);
        TextView tvFecha = view.findViewById(R.id.textViewFechaExpedida);
        EditText etDescripcion = view.findViewById(R.id.editTextDescripcion);

        etIdDoctor.setText(String.valueOf(receta.getIdDoctor()));
        etIdCliente.setText(String.valueOf(receta.getIdCliente()));
        etIdReceta.setText(String.valueOf(receta.getIdReceta()));
        tvFecha.setText(receta.getFechaExpedida());
        etDescripcion.setText(receta.getDescripcion());

        etIdDoctor.setEnabled(false);
        etIdCliente.setEnabled(false);
        etIdReceta.setEnabled(false);
        tvFecha.setEnabled(false);
        etDescripcion.setEnabled(false);

        view.findViewById(R.id.btnGuardarReceta).setVisibility(View.GONE);
        view.findViewById(R.id.btnLimpiarReceta).setVisibility(View.GONE);

        builder.create().show();
    }

    private void editarReceta(Receta receta) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.edit);

        View view = getLayoutInflater().inflate(R.layout.dialog_receta, null);
        builder.setView(view);

        EditText etIdDoctor = view.findViewById(R.id.editTextIdDoctor);
        EditText etIdCliente = view.findViewById(R.id.editTextIdCliente);
        EditText etIdReceta = view.findViewById(R.id.editTextIdReceta);
        TextView tvFecha = view.findViewById(R.id.textViewFechaExpedida);
        EditText etDescripcion = view.findViewById(R.id.editTextDescripcion);

        etIdDoctor.setText(String.valueOf(receta.getIdDoctor()));
        etIdCliente.setText(String.valueOf(receta.getIdCliente()));
        etIdReceta.setText(String.valueOf(receta.getIdReceta()));
        tvFecha.setText(receta.getFechaExpedida());
        etDescripcion.setText(receta.getDescripcion());

        etIdDoctor.setEnabled(false);
        etIdCliente.setEnabled(false);
        etIdReceta.setEnabled(false);

        tvFecha.setOnClickListener(v -> mostrarDatePicker(tvFecha));

        Button btnGuardar = view.findViewById(R.id.btnGuardarReceta);
        Button btnLimpiar = view.findViewById(R.id.btnLimpiarReceta);
        btnLimpiar.setVisibility(View.GONE);

        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            List<View> vistas = Arrays.asList(tvFecha, etDescripcion);
            List<String> regex = Arrays.asList("^\\d{4}-\\d{2}-\\d{2}$", "^[\\s\\S]{1,}$");
            List<Integer> errores = Arrays.asList(R.string.invalid_date_format, R.string.required_field);

            ValidadorDeCampos validador = new ValidadorDeCampos(this, vistas, regex, errores);

            if (validador.validarCampos()) {
                receta.setFechaExpedida(tvFecha.getText().toString().trim());
                receta.setDescripcion(etDescripcion.getText().toString().trim());

                recetaDAO.updateReceta(receta);
                cargarLista();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}