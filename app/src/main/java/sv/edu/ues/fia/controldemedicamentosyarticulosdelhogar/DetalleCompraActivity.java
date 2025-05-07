package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class DetalleCompraActivity extends AppCompatActivity {

    private DetalleCompraDAO detalleCompraDAO;
    private ArrayAdapter<DetalleCompra> adaptadorDetalleCompra;
    private List<DetalleCompra> listaDetalleCompra;
    private ListView listViewDetalleCompra;
    private final ValidarAccesoCRUD vac = new ValidarAccesoCRUD(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_compra);

        SQLiteDatabase conexionDB = new ControlBD(this).getConnection();
        detalleCompraDAO = new DetalleCompraDAO(conexionDB, this);

        TextView txtBusqueda = findViewById(R.id.busquedaDetalleCompra);

        Button btnBuscarDetalleCompraPorId = findViewById(R.id.btnBuscarDetalleCompra);
        btnBuscarDetalleCompraPorId.setVisibility(vac.validarAcceso(2) ? View.VISIBLE : View.INVISIBLE);
        btnBuscarDetalleCompraPorId.setOnClickListener(v -> {
            try {
                int id = Integer.parseInt(txtBusqueda.getText().toString().trim());
                buscarDetalleCompraPorId(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                Toast.makeText(this, R.string.invalid_search, Toast.LENGTH_LONG).show();
            }
        });

        listViewDetalleCompra = findViewById(R.id.lvDetalleCompra);
        fillList();

        listViewDetalleCompra.setOnItemClickListener((parent, view, position, id) -> {
            DetalleCompra detalleCompra = (DetalleCompra) parent.getItemAtPosition(position);
            showOptionsDialog(detalleCompra);
        });

        Button btnAgregarDetalleCompra = findViewById(R.id.btnAgregarDetalleCompra);
        btnAgregarDetalleCompra.setOnClickListener(v -> showAddDialog());
    }

    private void fillList() {
        listaDetalleCompra = detalleCompraDAO.getAllDetalleCompra();
        adaptadorDetalleCompra = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaDetalleCompra);
        listViewDetalleCompra.setAdapter(adaptadorDetalleCompra);
    }


    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add);

        View dialogView = getLayoutInflater().inflate(R.layout.dialogo_detalle_compra, null);
        builder.setView(dialogView);

        EditText editTextIdDetalleCompra = dialogView.findViewById(R.id.editTextIdDetalleCompra);
        EditText editTextFechaDetalleCompra = dialogView.findViewById(R.id.editTextFechaDetalleCompra);
        EditText editTextUnitarioDetalleCompra = dialogView.findViewById(R.id.editTextUnitarioDetalleCompra);
        EditText editTextCantidadDetalleCompra = dialogView.findViewById(R.id.editTextCantidadDetalleCompra);
        EditText editTextTotalDetalleCompra = dialogView.findViewById(R.id.editTextTotalDetalleCompra);
        EditText editTextArticulos = dialogView.findViewById(R.id.editTextArticulos);
        Spinner spinnerFacturaCompra = dialogView.findViewById(R.id.spinnerFacturaCompra);

        Button btnGuardar = dialogView.findViewById(R.id.btnGuardarDetalleCompra);
        Button btnLimpiar = dialogView.findViewById(R.id.btnLimpiarDetalleCompra);

        List<FacturaCompra> facturas = detalleCompraDAO.getAllFacturaCompra();
        facturas.add(0, new FacturaCompra(-1, this));
        ArrayAdapter<FacturaCompra> adapterFactura = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, facturas);
        adapterFactura.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFacturaCompra.setAdapter(adapterFactura);

        editTextFechaDetalleCompra.setInputType(InputType.TYPE_NULL);
        editTextFechaDetalleCompra.setFocusable(false);
        editTextFechaDetalleCompra.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dp = new DatePickerDialog(this, (view, y, m, d) -> {
                editTextFechaDetalleCompra.setText(String.format(Locale.getDefault(), "%04d-%02d-%02d", y, m + 1, d));
            }, year, month, day);
            dp.show();
        });

        List<View> campos = Arrays.asList(editTextIdDetalleCompra, editTextFechaDetalleCompra, editTextUnitarioDetalleCompra, editTextCantidadDetalleCompra, editTextTotalDetalleCompra, editTextArticulos);
        List<String> regex = Arrays.asList("\\d+", "\\d{4}-\\d{2}-\\d{2}", "\\d+(\\.\\d{1,2})?", "\\d+", "\\d+(\\.\\d{1,2})?", "\\d+");
        List<Integer> errores = Arrays.asList(R.string.only_numbers, R.string.invalid_date, R.string.only_numbers, R.string.only_numbers, R.string.only_numbers, R.string.only_numbers);

        ValidadorDeCampos validador = new ValidadorDeCampos(this, campos, regex, errores);
        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            if (validador.validarCampos()) {
                saveDetalleCompra(editTextIdDetalleCompra, editTextFechaDetalleCompra, editTextUnitarioDetalleCompra,
                        editTextCantidadDetalleCompra, editTextTotalDetalleCompra, editTextArticulos, spinnerFacturaCompra);
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private void saveDetalleCompra(EditText editTextIdDetalleCompra, EditText editTextFechaDetalleCompra, EditText editTextUnitarioDetalleCompra,
                                   EditText editTextCantidadDetalleCompra, EditText editTextTotalDetalleCompra, EditText editTextArticulos, Spinner spinnerFacturaCompra) {

        int idDetalleCompra = Integer.parseInt(editTextIdDetalleCompra.getText().toString());
        String fecha = editTextFechaDetalleCompra.getText().toString().trim();
        double precioUnitario = Double.parseDouble(editTextUnitarioDetalleCompra.getText().toString());
        int cantidadArticulos = Integer.parseInt(editTextCantidadDetalleCompra.getText().toString());
        double totalDetalle = Double.parseDouble(editTextTotalDetalleCompra.getText().toString());
        int idArticulo = Integer.parseInt(editTextArticulos.getText().toString());

        // obtener todas la factura seleccionada y validar que esa factura guarda el id y no un string
        FacturaCompra facturaSeleccionada = (FacturaCompra) spinnerFacturaCompra.getSelectedItem();

        if (facturaSeleccionada != null && facturaSeleccionada.getIdCompra() != -1) {
            int idCompra = facturaSeleccionada.getIdCompra();

            DetalleCompra detalleCompra = new DetalleCompra(idCompra, idArticulo, idDetalleCompra, fecha, precioUnitario, cantidadArticulos, totalDetalle, this
            );

            detalleCompraDAO.addDetalleCompra(detalleCompra);
            Toast.makeText(this, R.string.save_message, Toast.LENGTH_SHORT).show();
            fillList();
        } else {
            Toast.makeText(this, R.string.invalid_date, Toast.LENGTH_SHORT).show();
        }

        clearFieldsDetalleCompra(editTextIdDetalleCompra, editTextFechaDetalleCompra, editTextUnitarioDetalleCompra, editTextCantidadDetalleCompra,
                editTextTotalDetalleCompra, editTextArticulos, spinnerFacturaCompra
        );
    }


    private void showOptionsDialog(final DetalleCompra detalleCompra) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.options);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_options, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.buttonView).setOnClickListener(v -> viewDetalleCompra(detalleCompra));
        dialogView.findViewById(R.id.buttonEdit).setOnClickListener(v -> editDetalleCompra(detalleCompra));

        dialogView.findViewById(R.id.buttonDelete).setOnClickListener(v -> {
            if (vac.validarAcceso(4)) {
                deleteDetalleCompra(
                        detalleCompra.getIdDetalleCompra(),
                        detalleCompra.getIdArticulo(),
                        detalleCompra.getIdCompra()
                );
            } else {
                Toast.makeText(getApplicationContext(), R.string.action_block, Toast.LENGTH_LONG).show();
            }
            dialog.dismiss();
        });

        dialog.show();
    }


    private void viewDetalleCompra(DetalleCompra detalleCompra) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.view);

        View dialogView = getLayoutInflater().inflate(R.layout.dialogo_detalle_compra, null);
        builder.setView(dialogView);

        EditText editTextIdDetalleCompra = dialogView.findViewById(R.id.editTextIdDetalleCompra);
        EditText editTextFechaDetalleCompra = dialogView.findViewById(R.id.editTextFechaDetalleCompra);
        EditText editTextUnitarioDetalleCompra = dialogView.findViewById(R.id.editTextUnitarioDetalleCompra);
        EditText editTextCantidadDetalleCompra = dialogView.findViewById(R.id.editTextCantidadDetalleCompra);
        EditText editTextTotalDetalleCompra = dialogView.findViewById(R.id.editTextTotalDetalleCompra);
        EditText editTextArticulos = dialogView.findViewById(R.id.editTextArticulos);
        Spinner spinnerFacturaCompra = dialogView.findViewById(R.id.spinnerFacturaCompra);

        editTextIdDetalleCompra.setEnabled(false);
        editTextFechaDetalleCompra.setEnabled(false);
        editTextCantidadDetalleCompra.setFocusable(false);
        editTextTotalDetalleCompra.setFocusable(false);
        editTextUnitarioDetalleCompra.setFocusable(false);
        editTextArticulos.setFocusable(false);
        spinnerFacturaCompra.setEnabled(false);

        Button btnGuardar = dialogView.findViewById(R.id.btnGuardarDetalleCompra);
        Button btnLimpiar = dialogView.findViewById(R.id.btnLimpiarDetalleCompra);

        if (btnGuardar != null) btnGuardar.setVisibility(View.GONE);
        if (btnLimpiar != null) btnLimpiar.setVisibility(View.GONE);

        editTextIdDetalleCompra.setText(String.valueOf(detalleCompra.getIdDetalleCompra()));
        editTextFechaDetalleCompra.setText(detalleCompra.getFechaDeCompra());
        editTextUnitarioDetalleCompra.setText(String.valueOf(detalleCompra.getPrecioUnitarioCompra()));
        editTextTotalDetalleCompra.setText(String.valueOf(detalleCompra.getTotalDetalleCompra()));
        editTextCantidadDetalleCompra.setText(String.valueOf(detalleCompra.getCantidadCompra()));
        editTextArticulos.setText(String.valueOf(detalleCompra.getIdArticulo()));

        // obtener lista de facturas compra
        List<FacturaCompra> facturas = detalleCompraDAO.getAllFacturaCompra();

        ArrayAdapter<FacturaCompra> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, facturas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFacturaCompra.setAdapter(adapter);

        // seleccionar factura relacionada
        for (int i = 0; i < facturas.size(); i++) {
            if (facturas.get(i).getIdCompra() == detalleCompra.getIdCompra()) {
                spinnerFacturaCompra.setSelection(i);
                break;
            }
        }

        builder.show();
    }

    private void editDetalleCompra(DetalleCompra detalleCompra) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.edit);

        View dialogView = getLayoutInflater().inflate(R.layout.dialogo_detalle_compra, null);
        builder.setView(dialogView);

        EditText editTextIdDetalleCompra = dialogView.findViewById(R.id.editTextIdDetalleCompra);
        EditText editTextFechaDetalleCompra = dialogView.findViewById(R.id.editTextFechaDetalleCompra);
        EditText editTextUnitarioDetalleCompra = dialogView.findViewById(R.id.editTextUnitarioDetalleCompra);
        EditText editTextCantidadDetalleCompra = dialogView.findViewById(R.id.editTextCantidadDetalleCompra);
        EditText editTextTotalDetalleCompra = dialogView.findViewById(R.id.editTextTotalDetalleCompra);
        EditText editTextArticulos = dialogView.findViewById(R.id.editTextArticulos);
        Spinner spinnerFacturaCompra = dialogView.findViewById(R.id.spinnerFacturaCompra);

        // obtener lista de facturas compra
        List<FacturaCompra> facturas = detalleCompraDAO.getAllFacturaCompra();

        ArrayAdapter<FacturaCompra> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, facturas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFacturaCompra.setAdapter(adapter);

        editTextIdDetalleCompra.setText(String.valueOf(detalleCompra.getIdDetalleCompra()));
        editTextFechaDetalleCompra.setText(detalleCompra.getFechaDeCompra());
        editTextUnitarioDetalleCompra.setText(String.valueOf(detalleCompra.getPrecioUnitarioCompra()));
        editTextTotalDetalleCompra.setText(String.valueOf(detalleCompra.getTotalDetalleCompra()));
        editTextCantidadDetalleCompra.setText(String.valueOf(detalleCompra.getCantidadCompra()));
        editTextArticulos.setText(String.valueOf(detalleCompra.getIdArticulo()));

        // seleccionar factura relacionada
        for (int i = 0; i < facturas.size(); i++) {
            if (facturas.get(i).getIdCompra() == detalleCompra.getIdCompra()) {
                spinnerFacturaCompra.setSelection(i);
                break;
            }
        }

        Button btnGuardar = dialogView.findViewById(R.id.btnGuardarDetalleCompra);
        Button btnLimpiar = dialogView.findViewById(R.id.btnLimpiarDetalleCompra);
        btnLimpiar.setEnabled(false);

        editTextIdDetalleCompra.setInputType(InputType.TYPE_NULL);
        editTextIdDetalleCompra.setFocusable(false);

        spinnerFacturaCompra.setEnabled(false);
        spinnerFacturaCompra.setFocusable(false);

        editTextFechaDetalleCompra.setInputType(InputType.TYPE_NULL);
        editTextFechaDetalleCompra.setFocusable(false);
        editTextFechaDetalleCompra.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dp = new DatePickerDialog(this, (view, y, m, d) -> {
                editTextFechaDetalleCompra.setText(String.format(Locale.getDefault(), "%04d-%02d-%02d", y, m + 1, d));
            }, year, month, day);
            dp.show();
        });

        List<View> campos = Arrays.asList(editTextIdDetalleCompra, editTextFechaDetalleCompra, editTextUnitarioDetalleCompra, editTextCantidadDetalleCompra, editTextTotalDetalleCompra, editTextArticulos);
        List<String> regex = Arrays.asList("\\d+", "\\d{4}-\\d{2}-\\d{2}", "\\d+(\\.\\d{1,2})?", "\\d+", "\\d+(\\.\\d{1,2})?", "\\d+");
        List<Integer> errores = Arrays.asList(R.string.only_numbers, R.string.invalid_date, R.string.only_numbers, R.string.only_numbers, R.string.only_numbers, R.string.only_numbers);

        ValidadorDeCampos validador = new ValidadorDeCampos(this, campos, regex, errores);
        AlertDialog dialog = builder.create();

        btnGuardar.setOnClickListener(v -> {
            if (validador.validarCampos()) {
                int idDetalleCompra = Integer.parseInt(editTextIdDetalleCompra.getText().toString());
                String fecha = editTextFechaDetalleCompra.getText().toString().trim();
                double precioUnitario = Double.parseDouble(editTextUnitarioDetalleCompra.getText().toString());
                int cantidadArticulos = Integer.parseInt(editTextCantidadDetalleCompra.getText().toString());
                double totalDetalle = Double.parseDouble(editTextTotalDetalleCompra.getText().toString());
                int idArticulo = Integer.parseInt(editTextArticulos.getText().toString());

                // obtener todas la factura seleccionada y validar que esa factura guarda el id y no un string
                FacturaCompra facturaSeleccionada = (FacturaCompra) spinnerFacturaCompra.getSelectedItem();

                detalleCompra.setIdCompra(idDetalleCompra);
                detalleCompra.setFechaDeCompra(fecha);
                detalleCompra.setPrecioUnitarioCompra(precioUnitario);
                detalleCompra.setCantidadCompra(cantidadArticulos);
                detalleCompra.setTotalDetalleCompra(totalDetalle);
                detalleCompra.setIdArticulo(idArticulo);
                detalleCompra.setIdCompra(facturaSeleccionada.getIdCompra());

                detalleCompraDAO.updateDetalleCompra(detalleCompra);
                Toast.makeText(this, R.string.update_message, Toast.LENGTH_SHORT).show();
                fillList();

                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void deleteDetalleCompra(int idDetalleCompra, int idArticulo, int idCompra) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm_delete);
        builder.setMessage(getString(R.string.confirm_delete_message) + ": " + idDetalleCompra);

        builder.setPositiveButton(R.string.yes, (dialog, which) -> {
            detalleCompraDAO.deleteDetalleCompra(idCompra, idArticulo, idDetalleCompra);
            fillList();
        });

        builder.setNegativeButton(R.string.no, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void clearFieldsDetalleCompra(EditText editTextIdDetalleCompra, EditText editTextFechaDetalleCompra, EditText editTextUnitarioDetalleCompra, EditText editTextCantidadDetalleCompra,
                                          EditText editTextTotalDetalleCompra, EditText editTextArticulos, Spinner spinnerFacturaCompra) {
        editTextIdDetalleCompra.setText("");
        editTextFechaDetalleCompra.setText("");
        editTextUnitarioDetalleCompra.setText("");
        editTextCantidadDetalleCompra.setText("");
        editTextTotalDetalleCompra.setText("");
        editTextArticulos.setText("");
        spinnerFacturaCompra.setSelection(0);
    }

    private void buscarDetalleCompraPorId(int id) {
        DetalleCompra detalleCompra = detalleCompraDAO.getDetalleCompra(id);
        if(detalleCompra != null) {
            viewDetalleCompra(detalleCompra);
        }
        else {
            Toast.makeText(this, R.string.not_found_message, Toast.LENGTH_SHORT).show();
        }
    }


}
