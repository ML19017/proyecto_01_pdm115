package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.Context;
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
        btnAgregar = findViewById(R.id.btnAddProveedor);
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

        int idProveedor = proveedorDAO.obtenerIdProveedor();
        edtId.setText(String.valueOf(idProveedor));
        edtId.setEnabled(false);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.add_supplier_dialog))
                .setView(view)
                .setPositiveButton(getString(R.string.save), null)
                .setNegativeButton(getString(R.string.cancel), null)
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            Button botonGuardar = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            botonGuardar.setOnClickListener(view1 -> {
                if (validarCampos(edtNombre, edtTelefono, edtDireccion, edtRubro, edtNumReg, edtNIT, edtGiro) &&
                        validarSoloTexto(edtNombre, getString(R.string.name_supplier)) &&
                        validarSoloNumeros(edtTelefono, getString(R.string.phone_supplier)) &&
                        validarTextoYNumeros(this, edtDireccion, getString(R.string.address_supplier)) &&
                        validarSoloTexto(edtRubro, getString(R.string.industry_supplier)) &&
                        validarTextoYNumeros(this, edtNumReg, getString(R.string.registration_number_supplier)) &&
                        validarNIT(edtNIT) &&
                        validarSoloTexto(edtGiro, getString(R.string.business_type_supplier))
                ) {
                    Proveedor proveedor = new Proveedor(
                            idProveedor,
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
                    dialog.dismiss();
                }
            });
        });

        dialog.show();
    }



    private void mostrarDialogoOpciones(Proveedor proveedor) {
        String[] opciones = {getString(R.string.view),
                getString(R.string.edit),
                getString(R.string.delete)};

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.actions))
                .setItems(opciones, (dialog, which) -> {
                    if (which == 0) {
                        mostrarDialogoVer(proveedor);
                    } else if (which == 1) {
                        mostrarDialogoEditar(proveedor);
                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle(getString(R.string.confirmation))
                                .setMessage(getString(R.string.confirmation_message)+ " " + getString(R.string.provider))
                                .setPositiveButton(getString(R.string.yes), (dialogInterface, i) -> {
                                    proveedorDAO.eliminar(proveedor.getIdProveedor());
                                    cargarLista();
                                })
                                .setNegativeButton(getString(R.string.no), null)
                                .show();
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
                .setTitle(getString(R.string.supplier_details_dialog))
                .setView(dialogView)
                .setPositiveButton(getString(R.string.close), null)
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
        edtId.setEnabled(false);
        edtNombre.setText(proveedor.getNombreProveedor());
        edtTelefono.setText(proveedor.getTelefonoProveedor());
        edtDireccion.setText(proveedor.getDireccionProveedor());
        edtRubro.setText(proveedor.getRubroProveedor());
        edtNumReg.setText(proveedor.getNumRegProveedor());
        edtNIT.setText(proveedor.getNitProveedor());
        edtGiro.setText(proveedor.getGiroProveedor());

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.edit_supplier_dialog))
                .setView(dialogView)
                .setPositiveButton(getString(R.string.update), null)
                .setNegativeButton(getString(R.string.cancel), null)
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(view -> {
                if (validarCampos(edtNombre, edtTelefono, edtDireccion, edtRubro, edtNumReg, edtNIT, edtGiro) &&
                        validarSoloTexto(edtNombre, getString(R.string.name_supplier)) &&
                        validarSoloNumeros(edtTelefono, getString(R.string.phone_supplier)) &&
                        validarTextoYNumeros(this, edtDireccion, getString(R.string.address_supplier)) &&
                        validarSoloTexto(edtRubro, getString(R.string.industry_supplier)) &&
                        validarTextoYNumeros(this, edtNumReg, getString(R.string.registration_number_supplier)) &&
                        validarNIT(edtNIT) &&
                        validarSoloTexto(edtGiro, getString(R.string.business_type_supplier))
                ) {
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
                    dialog.dismiss();
                }
            });
        });

        dialog.show();
    }


    private boolean validarCampos(EditText... campos) {
        for (EditText campo : campos) {
            if (campo.getText().toString().trim().isEmpty()) {
                campo.setError(getString(R.string.required_field));
                campo.requestFocus();
                return false;
            }
        }
        return true;
    }

    private boolean validarSoloTexto(EditText campo, String nombreCampo) {
        if (!campo.getText().toString().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            campo.setError(nombreCampo + getString(R.string.only_letters));
            campo.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validarSoloNumeros(EditText campo, String nombreCampo) {
        if (!campo.getText().toString().matches("\\d+")) {
            campo.setError(nombreCampo + getString(R.string.only_numbers));
            campo.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validarNIT(EditText campo) {
        String nit = campo.getText().toString().trim();
        if (!nit.matches("\\d{4}-\\d{6}-\\d{3}-\\d")) {
            campo.setError(getString(R.string.nit_format));
            campo.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean validarTextoYNumeros(Context context, EditText campo, String nombreCampo) {
        String texto = campo.getText().toString().trim();
        String regex = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$"; // Letras, números y espacios

        if (texto.isEmpty()) {
            campo.setError(context.getString(R.string.field) + " " + nombreCampo + " " + context.getString(R.string.field_empty));
            campo.requestFocus();
            return false;
        } else if (!texto.matches(regex)) {
            campo.setError(context.getString(R.string.field) + " " + nombreCampo + " " + context.getString(R.string.only_letters_and_numbers));
            campo.requestFocus();
            return false;
        }

        return true;
    }


}
