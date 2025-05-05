package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class FacturaCompraActivity extends AppCompatActivity {

    private FacturaCompraDAO facturaCompraDAO;
    private ArrayAdapter<FacturaCompra> adaptadorFacturaCompra;
    private List<FacturaCompra> listaFacturaCompra;
    private ListView listViewFacturaCompra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_factura_compra);

        SQLiteDatabase conexionDB = new ControlBD(this).getConnection();
        facturaCompraDAO = new FacturaCompraDAO(conexionDB, this);

        listViewFacturaCompra = findViewById(R.id.lvFacturaCompra);
        fillList();

        listViewFacturaCompra.setOnItemClickListener((parent, view, position, id) -> {
            FacturaCompra facturaCompra = (FacturaCompra) parent.getItemAtPosition(position);
            showOptionsDialog(facturaCompra);
        });

        Button btnAgregarFacturaCompra = findViewById(R.id.btnAgregarFacturaCompra);
        btnAgregarFacturaCompra.setOnClickListener(v -> showAddDialog());
    }

    private void fillList() {
        listaFacturaCompra = facturaCompraDAO.getAllFacturaCompra();
        adaptadorFacturaCompra = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaFacturaCompra);
        listViewFacturaCompra.setAdapter(adaptadorFacturaCompra);
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_factura_compra, null);
        builder.setView(dialogView);

        EditText editTextIdFactura = dialogView.findViewById(R.id.editTextIdFactura);
        EditText editTextFechaCompra = dialogView.findViewById(R.id.editTextFechaCompra);
        EditText editTextTotalCompra = dialogView.findViewById(R.id.editTextTotalCompra);

        Spinner spinnerFarmacia = dialogView.findViewById(R.id.spinnerFarmacia);
        Spinner spinnerProveedor = dialogView.findViewById(R.id.spinnerProveedor);

        // llenar los tipo combobox
        List<SucursalFarmacia> farmacias = facturaCompraDAO.getAllFarmacias();
        ArrayAdapter<SucursalFarmacia> adapterFarmacia = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, farmacias);
        adapterFarmacia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFarmacia.setAdapter(adapterFarmacia);

        List<Proveedor> proveedores = facturaCompraDAO.getAllProveedores();
        ArrayAdapter<Proveedor> adapterProveedor = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, proveedores);
        adapterProveedor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProveedor.setAdapter(adapterProveedor);

        Button btnGuardarFacturaCompra = dialogView.findViewById(R.id.btnGuardarFacturaCompra);

        final AlertDialog dialog = builder.create();

        btnGuardarFacturaCompra.setOnClickListener(v -> {
            saveFacturaCompra(editTextIdFactura, editTextFechaCompra, editTextTotalCompra, spinnerFarmacia, spinnerProveedor);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void saveFacturaCompra(EditText editTextIdFactura, EditText editTextFechaCompra, EditText editTextTotalCompra, Spinner spinnerFarmacia, Spinner spinnerProveedor) {
        int id = Integer.parseInt(editTextIdFactura.getText().toString());
        String fecha = editTextFechaCompra.getText().toString().trim();
        double total = Double.parseDouble(editTextTotalCompra.getText().toString());

        // obtener los proveedores o farmacias que estan seleccionados
        SucursalFarmacia farmaciaSeleccionada = (SucursalFarmacia) spinnerFarmacia.getSelectedItem();
        Proveedor proveedorSeleccionado = (Proveedor) spinnerProveedor.getSelectedItem();

        FacturaCompra facturaCompra = new FacturaCompra(id, farmaciaSeleccionada.getIdFarmacia(), proveedorSeleccionado.getIdProveedor(), fecha, total);
        facturaCompraDAO.addFacturaCompra(facturaCompra);
        fillList();
    }

    private void showOptionsDialog(final FacturaCompra facturaCompra) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.options);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_options, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.buttonView).setOnClickListener(v -> viewFacturaCompra(facturaCompra));
        dialogView.findViewById(R.id.buttonEdit).setOnClickListener(v -> editFacturaCompra(facturaCompra));
        dialogView.findViewById(R.id.buttonDelete).setOnClickListener(v -> deleteFacturaCompra(facturaCompra.getIdCompra()));

        dialog.show();
    }

    private void viewFacturaCompra(FacturaCompra facturaCompra) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.view);

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_factura_compra, null);
        builder.setView(dialogView);

        TextView tvIdFactura = dialogView.findViewById(R.id.editTextIdFactura);
        TextView tvFechaCompra = dialogView.findViewById(R.id.editTextFechaCompra);
        TextView tvTotalCompra = dialogView.findViewById(R.id.editTextTotalCompra);
        TextView tvFarmacia = dialogView.findViewById(R.id.spinnerFarmacia);
        TextView tvProveedor = dialogView.findViewById(R.id.spinnerProveedor);

        tvIdFactura.setText(String.valueOf(facturaCompra.getIdCompra()));
        tvFechaCompra.setText(facturaCompra.getFechaCompra());
        tvTotalCompra.setText(String.valueOf(facturaCompra.getTotalCompra()));

        //  obtener las farmacias y los proveedoes de la db
        SucursalFarmacia farmacia = facturaCompraDAO.getFarmaciaById(facturaCompra.getIdFarmacia());
        Proveedor proveedor = facturaCompraDAO.getProveedorById(facturaCompra.getIdProveedor());


        tvFarmacia.setText(farmacia.getNombreFarmacia());
        tvProveedor.setText(proveedor.getNombreProveedor());

        builder.show();
    }

    private void editFacturaCompra(FacturaCompra facturaCompra) {

    }

    private void deleteFacturaCompra(int idFacturaCompra) {
        facturaCompraDAO.deleteFacturaCompra(idFacturaCompra);
        fillList();

    }
}