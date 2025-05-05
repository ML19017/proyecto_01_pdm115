package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class FormaFarmaceuticaActivity extends AppCompatActivity {

    private final ValidarAccesoCRUD vac = new ValidarAccesoCRUD(this);
    private FormaFarmaceuticaDAO formaFarmaceuticaDAO;
    private ArrayAdapter<FormaFarmaceutica> adaptadorFormaFarmaceutica;
    private List<FormaFarmaceutica> listaFormaFarmaceutica;
    private ListView listViewFormaFaramceutica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forma_farmaceutica);

        // Initialize DAO with SQLite connection
        SQLiteDatabase conexionDB = new ControlBD(this).getConnection();
        formaFarmaceuticaDAO = new FormaFarmaceuticaDAO(conexionDB, this);

        // Comprobacion Inicial de Permisos de Consulta
        Button btnAgregarFormaFarmaceutica = findViewById(R.id.btnAgregarFormaFarmaceutica);
        btnAgregarFormaFarmaceutica.setVisibility(vac.validarAcceso(1) ? View.VISIBLE : View.INVISIBLE);
        btnAgregarFormaFarmaceutica.setOnClickListener(v -> {showAddDialog();});

        listViewFormaFaramceutica = findViewById(R.id.lvFormaFarmaceutica);
        listViewFormaFaramceutica.setVisibility(vac.validarAcceso(2) ? View.VISIBLE : View.INVISIBLE);
        //Fill the listView
        fillList();
        // Set item click listener for ListView
        listViewFormaFaramceutica.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FormaFarmaceutica formaFarmaceutica = (FormaFarmaceutica) parent.getItemAtPosition(position);
                showOptionsDialog(formaFarmaceutica);
            }
        });
    }

    private void fillList() {
        listaFormaFarmaceutica = formaFarmaceuticaDAO.getAllFormaFarmaceutica();
        adaptadorFormaFarmaceutica = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaFormaFarmaceutica);
        listViewFormaFaramceutica.setAdapter(adaptadorFormaFarmaceutica);
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_forma_farmaceutica, null);
        builder.setView(dialogView);

        EditText editTextIdFormaFarmaceutica = dialogView.findViewById(R.id.editTextIdFormaFarmaceutica);
        EditText editTextTipoFormaFarmaceutica = dialogView.findViewById(R.id.editTextTipoFormaFarmaceutica);
        Button btnGuardarFormaFarmaceutica = dialogView.findViewById(R.id.btnGuardarFormaFarmaceutica);
        Button btnLimpiarFormaFarmaceutica = dialogView.findViewById(R.id.btnLimpiarFormaFarmaceutica);

        final AlertDialog dialog = builder.create();

        btnGuardarFormaFarmaceutica.setOnClickListener(v -> {
            saveFormaFarmaceutica(editTextIdFormaFarmaceutica, editTextTipoFormaFarmaceutica);
            dialog.dismiss();
        });
        btnLimpiarFormaFarmaceutica.setOnClickListener(v -> clearFieldsFormaFarmaceutica(editTextIdFormaFarmaceutica, editTextTipoFormaFarmaceutica));
        dialog.show();
    }


    private void showOptionsDialog(final FormaFarmaceutica formaFarmaceutica) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.options);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_options, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.buttonView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vac.validarAcceso(2))
                    viewFormaFarmaceutica(formaFarmaceutica);
                else
                    Toast.makeText(getApplicationContext(), R.string.action_block, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.buttonEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vac.validarAcceso(3))
                    editFormaFarmaceutica(formaFarmaceutica);
                else
                    Toast.makeText(getApplicationContext(), R.string.action_block, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialogView.findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vac.validarAcceso(4))
                    deleteFormaFarmaceutica(formaFarmaceutica.getIdFormaFarmaceutica());
                else
                    Toast.makeText(getApplicationContext(), R.string.action_block, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void saveFormaFarmaceutica(EditText editTextIdFormaFarmaceutica, EditText editTextTipoFormaFarmaceutica) {

        int id = Integer.parseInt(editTextIdFormaFarmaceutica.getText().toString());
        String tipo = editTextTipoFormaFarmaceutica.getText().toString().trim();

        FormaFarmaceutica formaFarmaceutica = new FormaFarmaceutica(id, tipo, this);
        formaFarmaceuticaDAO.addFormaFarmaceutica(formaFarmaceutica);
        Toast.makeText(this, R.string.save_message, Toast.LENGTH_SHORT).show();
        fillList(); // Refresh the ListView
        clearFieldsFormaFarmaceutica(editTextIdFormaFarmaceutica, editTextIdFormaFarmaceutica);
    }

    private void clearFieldsFormaFarmaceutica(EditText editTextIdFormaFarmaceutica, EditText editTextTipoFormaFarmaceutica) {
        editTextIdFormaFarmaceutica.setText("");
        editTextTipoFormaFarmaceutica.setText("");
    }

    private void viewFormaFarmaceutica(FormaFarmaceutica formaFarmaceutica) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.view);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_forma_farmaceutica, null);
        builder.setView(dialogView);

        EditText editTextIdFormaFarmaceutica = dialogView.findViewById(R.id.editTextIdFormaFarmaceutica);
        EditText editTextTipoFormaFarmaceutica = dialogView.findViewById(R.id.editTextTipoFormaFarmaceutica);

        editTextIdFormaFarmaceutica.setText(String.valueOf(formaFarmaceutica.getIdFormaFarmaceutica()));
        editTextTipoFormaFarmaceutica.setText(formaFarmaceutica.getTipoFormaFarmaceutica());

        editTextIdFormaFarmaceutica.setEnabled(false);
        editTextTipoFormaFarmaceutica.setEnabled(false);

        // Disable buttons if they exist in the layout
        Button btnGuardarFormaFarmaceutica = dialogView.findViewById(R.id.btnGuardarFormaFarmaceutica);
        Button btnLimpiarFormaFarmaceutica = dialogView.findViewById(R.id.btnLimpiarFormaFarmaceutica);

        if (btnGuardarFormaFarmaceutica != null) {
            btnGuardarFormaFarmaceutica.setVisibility(View.GONE);
        }
        if (btnLimpiarFormaFarmaceutica != null) {
            btnLimpiarFormaFarmaceutica.setVisibility(View.GONE);
        }

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void editFormaFarmaceutica(FormaFarmaceutica formaFarmaceutica) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.edit);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_forma_farmaceutica, null);
        builder.setView(dialogView);

        EditText editTextIdFormaFarmaceutica = dialogView.findViewById(R.id.editTextIdFormaFarmaceutica);
        EditText editTextTipoFormaFarmaceutica = dialogView.findViewById(R.id.editTextTipoFormaFarmaceutica);

        editTextIdFormaFarmaceutica.setText(String.valueOf(formaFarmaceutica.getIdFormaFarmaceutica()));
        editTextTipoFormaFarmaceutica.setText(formaFarmaceutica.getTipoFormaFarmaceutica());

        editTextIdFormaFarmaceutica.setEnabled(false);

        Button btnGuardarFormaFarmaceutica = dialogView.findViewById(R.id.btnGuardarFormaFarmaceutica);
        Button btnLimpiarFormaFarmaceutica = dialogView.findViewById(R.id.btnLimpiarFormaFarmaceutica);

        // Disable the clear button
        btnLimpiarFormaFarmaceutica.setEnabled(false);

        final AlertDialog dialog = builder.create();

        btnGuardarFormaFarmaceutica.setOnClickListener(v -> {
            formaFarmaceutica.setTipoFormaFarmaceutica(editTextTipoFormaFarmaceutica.getText().toString().trim());
            formaFarmaceuticaDAO.updateFormaFarmaceutica(formaFarmaceutica);
            Toast.makeText(this, R.string.update_message, Toast.LENGTH_SHORT).show();
            fillList(); // Refresh the ListView
            dialog.dismiss();
        });

        dialog.show();
    }

    private void deleteFormaFarmaceutica(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm_delete);
        builder.setMessage(getString(R.string.confirm_delete_message) + ": " + id);

        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            formaFarmaceuticaDAO.deleteFormaFarmaceutica(id);
            Toast.makeText(this, R.string.delete_message, Toast.LENGTH_SHORT).show();
            fillList(); // Refresh the ListView
        });

        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
