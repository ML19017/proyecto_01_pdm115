package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class SucursalFarmaciaActivity extends AppCompatActivity {


    private final ValidarAccesoCRUD vac = new ValidarAccesoCRUD(this);
    private SucursalFarmaciaDAO sucursalFarmaciaDAO;
    private ListView lista;

    public ArrayAdapter<SucursalFarmacia> adapter;

    private List<SucursalFarmacia> sucursales;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sucursal_farmacia);

        // Initialize DAO with SQLite connection
        SQLiteDatabase conexionDB = new ControlBD(this).getConnection();
        sucursalFarmaciaDAO = new SucursalFarmaciaDAO(this, conexionDB);

        // Comprobacion Inicial de Permisos de Consulta
        lista = findViewById(R.id.lvBranch);
        cargarLista();

        Button boton = findViewById(R.id.btnAddBranch);



        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SucursalFarmacia sucursalFarmacia = (SucursalFarmacia) parent.getItemAtPosition(position);
                showOptionsDialog(sucursalFarmacia);
            }
        });

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.add);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_branch, null);
                builder.setView(dialogView);

                final EditText idFarmacia = dialogView.findViewById(R.id.editTextIdFarmacia);
                final EditText idDireccion = dialogView.findViewById(R.id.editTextIdDireccion);
                final EditText nombreFarma = dialogView.findViewById(R.id.editTextNombreFarmacia);
                final AlertDialog dialog = builder.create();
                dialog.show();

                Button botonGuardar = dialog.findViewById(R.id.buttonSave);
                botonGuardar.setOnClickListener(view1 ->{
                    try {
                        int idFarma = Integer.parseInt(idFarmacia.getText().toString());
                        int idDir = Integer.parseInt(idDireccion.getText().toString());
                        String nombre = nombreFarma.getText().toString();

                        SucursalFarmacia sucursal = new SucursalFarmacia(idFarma, idDir, nombre);
                        sucursalFarmaciaDAO.addSucursalFarmacia(sucursal); // Asegúrate que este método exista
                        dialog.dismiss();
                        Log.d("DEBUG", "Sucursal guardada: " + sucursal.toString());
                        cargarLista();
                    } catch (Exception e) {
                        Log.e("ERROR", "Error al guardar sucursal: " + e.getMessage());
                    }
                });


                Button botonClear = dialog.findViewById(R.id.buttonClear);
                botonClear.setOnClickListener(view1 ->{
                    idFarmacia.setText("");
                    idDireccion.setText("");
                    nombreFarma.setText("");

                });

            }
        });



    }


    private void cargarLista() {
        sucursales = sucursalFarmaciaDAO.getAllSucursalFarmacia();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sucursales);
        lista.setAdapter(adapter);
    }

    private void showOptionsDialog(final SucursalFarmacia sucursalFarmacia) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.options);

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
                deleteSucursalFarmacia(sucursalFarmacia.getIdFarmacia());
            }
        });

        dialog.show();
    }

    private void deleteSucursalFarmacia(int id) {
        sucursalFarmaciaDAO.eliminarSucursal(id);
        cargarLista();

        // Refresh ListView or handle post-delete actions
    }




}