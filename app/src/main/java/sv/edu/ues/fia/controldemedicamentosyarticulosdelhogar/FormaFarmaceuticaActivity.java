package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class FormaFarmaceuticaActivity extends AppCompatActivity {

    private final ValidarAccesoCRUD vac = new ValidarAccesoCRUD(this);
    private FormaFarmaceuticaDAO formaFarmaceuticaDAO;
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forma_farmaceutica);

        // Initialize DAO with SQLite connection
        SQLiteDatabase conexionDB = new ControlBD(this).getConnection();
        formaFarmaceuticaDAO = new FormaFarmaceuticaDAO(conexionDB);

        // Comprobacion Inicial de Permisos de Consulta
        lista = findViewById(R.id.lvFormaFarmaceutica);
        Button boton = findViewById(R.id.btnAgregarFormaFarmaceutica);

        boton.setVisibility(vac.validarAcceso(1) ? View.VISIBLE : View.INVISIBLE);
        lista.setVisibility(vac.validarAcceso(2) ? View.VISIBLE : View.INVISIBLE);

        // Set item click listener for ListView
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FormaFarmaceutica formaFarmaceutica = (FormaFarmaceutica) parent.getItemAtPosition(position);
                showOptionsDialog(formaFarmaceutica);
            }
        });
    }

    private void showOptionsDialog(final FormaFarmaceutica formaFarmaceutica) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_options, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.buttonView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle view action
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

    private void deleteFormaFarmaceutica(int id) {
        formaFarmaceuticaDAO.deleteFormaFarmaceutica(id);
        // Refresh ListView or handle post-delete actions
    }
}
