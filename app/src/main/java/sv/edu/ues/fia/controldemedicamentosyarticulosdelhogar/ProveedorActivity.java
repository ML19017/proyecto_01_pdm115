package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;


public class ProveedorActivity extends AppCompatActivity {
    private ListView listView;
    public Button btnAgregar;
    private ProveedorDAO proveedorDAO;
    public ArrayAdapter<Proveedor> adapter;
    private List<Proveedor> listaProveedores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proveedor);

        listView = findViewById(R.id.listViewProveedores);
        btnAgregar = findViewById(R.id.btnAgregar);
        proveedorDAO = new ProveedorDAO(this);

        cargarLista();

        btnAgregar.setOnClickListener(v -> mostrarDialogoAgregar());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Proveedor seleccionado = listaProveedores.get(position);
            mostrarDialogoOpciones(seleccionado);
        });
    }

    private void cargarLista() {
        listaProveedores = proveedorDAO.obtenerTodos();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaProveedores);
        listView.setAdapter(adapter);
    }

    private void mostrarDialogoAgregar() {
        View view = getLayoutInflater().inflate(R.layout.dialogo_proveedor, null);
        EditText edtId = view.findViewById(R.id.edtId);
        EditText edtNombre = view.findViewById(R.id.edtNombre);
        EditText edtTelefono = view.findViewById(R.id.edtTelefono);
        EditText edtDireccion = view.findViewById(R.id.edtDireccion);
        EditText edtRubro = view.findViewById(R.id.edtRubro);
        EditText edtNumReg = view.findViewById(R.id.edtNumReg);
        EditText edtNIT = view.findViewById(R.id.edtNIT);
        EditText edtGiro = view.findViewById(R.id.edtGiro);

        new AlertDialog.Builder(this)
                .setTitle("Agregar Proveedor")
                .setView(view)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    Proveedor proveedor = new Proveedor(
                            Integer.parseInt(edtId.getText().toString()),
                            edtNombre.getText().toString(),
                            edtTelefono.getText().toString(),
                            edtDireccion.getText().toString(),
                            edtRubro.getText().toString(),
                            edtNumReg.getText().toString(),
                            edtNIT.getText().toString(),
                            edtGiro.getText().toString()
                    );
                    proveedorDAO.insertar(proveedor);
                    cargarLista();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }


    private void mostrarDialogoOpciones(Proveedor proveedor) {
        String[] opciones = {"Ver", "Editar", "Eliminar"};

        new AlertDialog.Builder(this)
                .setTitle("Acciones")
                .setItems(opciones, (dialog, which) -> {
                    if (which == 0) {
                        mostrarDialogoVer(proveedor);
                    } else if (which == 1) {
                        mostrarDialogoEditar(proveedor);
                    } else {
                        proveedorDAO.eliminar(proveedor.getIdProveedor());
                        cargarLista();
                    }
                })
                .show();
    }

    private void mostrarDialogoVer(Proveedor proveedor) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogo_proveedor, null);

        EditText edtId = dialogView.findViewById(R.id.edtId);
        EditText edtNombre = dialogView.findViewById(R.id.edtNombre);
        EditText edtTelefono = dialogView.findViewById(R.id.edtTelefono);
        EditText edtDireccion = dialogView.findViewById(R.id.edtDireccion);
        EditText edtRubro = dialogView.findViewById(R.id.edtRubro);
        EditText edtNumReg = dialogView.findViewById(R.id.edtNumReg);
        EditText edtNIT = dialogView.findViewById(R.id.edtNIT);
        EditText edtGiro = dialogView.findViewById(R.id.edtGiro);

        edtId.setText(String.valueOf(proveedor.getIdProveedor()));
        edtNombre.setText(proveedor.getNombreProveedor());
        edtTelefono.setText(proveedor.getTelefonoProveedor());
        edtDireccion.setText(proveedor.getDireccionProveedor());
        edtRubro.setText(proveedor.getRubroProveedor());
        edtNumReg.setText(proveedor.getNumRegProveedor());
        edtNIT.setText(proveedor.getNitProveedor());
        edtGiro.setText(proveedor.getGiroProveedor());


        edtId.setEnabled(false);
        edtNombre.setEnabled(false);
        edtTelefono.setEnabled(false);
        edtDireccion.setEnabled(false);
        edtRubro.setEnabled(false);
        edtNumReg.setEnabled(false);
        edtNIT.setEnabled(false);
        edtGiro.setEnabled(false);

        new AlertDialog.Builder(this)
                .setTitle("Detalles del Proveedor")
                .setView(dialogView)
                .setPositiveButton("Cerrar", null)
                .show();
    }

    private void mostrarDialogoEditar(Proveedor proveedor) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogo_proveedor, null);

        EditText edtId = dialogView.findViewById(R.id.edtId);
        EditText edtNombre = dialogView.findViewById(R.id.edtNombre);
        EditText edtTelefono = dialogView.findViewById(R.id.edtTelefono);
        EditText edtDireccion = dialogView.findViewById(R.id.edtDireccion);
        EditText edtRubro = dialogView.findViewById(R.id.edtRubro);
        EditText edtNumReg = dialogView.findViewById(R.id.edtNumReg);
        EditText edtNIT = dialogView.findViewById(R.id.edtNIT);
        EditText edtGiro = dialogView.findViewById(R.id.edtGiro);

        edtId.setText(String.valueOf(proveedor.getIdProveedor()));
        edtNombre.setText(proveedor.getNombreProveedor());
        edtTelefono.setText(proveedor.getTelefonoProveedor());
        edtDireccion.setText(proveedor.getDireccionProveedor());
        edtRubro.setText(proveedor.getRubroProveedor());
        edtNumReg.setText(proveedor.getNumRegProveedor());
        edtNIT.setText(proveedor.getNitProveedor());
        edtGiro.setText(proveedor.getGiroProveedor());

        new AlertDialog.Builder(this)
                .setTitle("Editar Proveedor")
                .setView(dialogView)
                .setPositiveButton("Actualizar", (dialog, which) -> {
                    proveedorDAO.actualizar(new Proveedor(
                            proveedor.getIdProveedor(),
                            edtNombre.getText().toString(),
                            edtTelefono.getText().toString(),
                            edtDireccion.getText().toString(),
                            edtRubro.getText().toString(),
                            edtNumReg.getText().toString(),
                            edtNIT.getText().toString(),
                            edtGiro.getText().toString()

                    ));
                    cargarLista();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

}
