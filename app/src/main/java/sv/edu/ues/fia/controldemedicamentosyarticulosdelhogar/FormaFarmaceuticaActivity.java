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
        formaFarmaceuticaDAO = new FormaFarmaceuticaDAO(conexionDB);

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
                // Handle view action
                viewFormaFarmaceutica(formaFarmaceutica);
                dialog.dismiss();
                // Implement view logic here
            }
        });

        dialogView.findViewById(R.id.buttonEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle edit action
                dialog.dismiss();
                // Implement edit logic here
            }
        });

        dialogView.findViewById(R.id.buttonDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete action
                dialog.dismiss();
                deleteFormaFarmaceutica(formaFarmaceutica.getIdFormaFarmaceutica());
            }
        });
        dialog.show();
    }

    private void saveFormaFarmaceutica(EditText editTextIdFormaFarmaceutica, EditText editTextTipoFormaFarmaceutica) {

        int id = Integer.parseInt(editTextIdFormaFarmaceutica.getText().toString());
        String tipo = editTextTipoFormaFarmaceutica.getText().toString().trim();

        FormaFarmaceutica formaFarmaceutica = new FormaFarmaceutica(id, tipo);
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

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void editFormaFarmaceutica(FormaFarmaceutica formaFarmaceutica) {

    }

    private void deleteFormaFarmaceutica(int id) {
        formaFarmaceuticaDAO.deleteFormaFarmaceutica(id);
        // Refresh ListView or handle post-delete actions
    }
}
