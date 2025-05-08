package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class ArticuloActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{

    private ArticuloDAO articuloDAO;
    private CategoriaDAO categoriaDAO;
    private ArrayAdapter<Articulo> adaptadorListV;
    private ArrayAdapter<Categoria> adaptadorSpinner;

    private Categoria selected = new Categoria();
    private List<Articulo> valuesArt = new ArrayList<>();

    private List<Categoria> valuesCat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);

        //Database conection
        SQLiteDatabase conection = new ControlBD(this).getConnection();
        articuloDAO = new ArticuloDAO(this, conection);
        categoriaDAO = new CategoriaDAO( conection, this);

        //Spinner
        adaptadorSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valuesCat);
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.itemCategorySpinner);
        spinner.setAdapter(adaptadorSpinner);
        llenadoSpinner();
        spinner.setOnItemSelectedListener(this);


        //ListV
        adaptadorListV = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, valuesArt);
        ListView listV = (ListView) findViewById(R.id.itemListv);
        listV.setAdapter(adaptadorListV);
        listV.setOnItemClickListener(this);

        //Botones
        Button btnAdd = (Button) findViewById(R.id.btnAddItem);
        btnAdd.setOnClickListener(v -> {
            showAddDialog();
        });

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Articulo articulo = (Articulo) parent.getItemAtPosition(position);
        Log.d("Seleccionado", articulo.getNombreArticulo());
        showOptionsDialog(articulo);
    }
    public void actualizarListView(Categoria filtro) {
        if (!valuesArt.isEmpty()) {
            valuesArt.clear();
        }
        int idFiltro =filtro.getIdCategoria();
        if ( idFiltro== -1) {
            valuesArt.addAll(articuloDAO.getAllRows());
            adaptadorListV.notifyDataSetChanged();
        }else{
            valuesArt.addAll(articuloDAO.getRowsFiltredByCategory(idFiltro));
            adaptadorListV.notifyDataSetChanged();
        }
    }

    public void llenadoSpinner(){
        if (!valuesCat.isEmpty()) {
            valuesCat.clear();
        }
        Categoria opcionTodas = new Categoria(-1, "All");
        valuesCat.add(0, opcionTodas);
        valuesCat.addAll(categoriaDAO.getAllRows());
        adaptadorSpinner.notifyDataSetChanged();
    }

    public void showAddDialog() {
        int numRegistros = 0;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_articulo, null);
        builder.setView(dialogView);

        EditText idMarca = dialogView.findViewById(R.id.editTextItemIdBrand);
        EditText idROA = dialogView.findViewById(R.id.editTextItemIdROA);
        EditText idSubCat = dialogView.findViewById(R.id.editTextItemIdSubCategory);
        EditText idPF = dialogView.findViewById(R.id.editTextItemPharmaceuticalForm);
        EditText idArticulo = dialogView.findViewById(R.id.editTextItemId);
        EditText name = dialogView.findViewById(R.id.editTextItemName);
        EditText description = dialogView.findViewById(R.id.editTextItemDescription);
        CheckBox isRestricted = dialogView.findViewById(R.id.checkBoxItemRestricted);
        EditText price = dialogView.findViewById(R.id.editTextItemPrice);

        Button btnSaveCategoria = dialogView.findViewById(R.id.btnGuardarArticulo);
        Button btnClear = dialogView.findViewById(R.id.btnLimpiarArticulo);


        AlertDialog dialog = builder.create();

        Cursor cursor = articuloDAO.getDbConection().rawQuery("SELECT COUNT(*) FROM ARTICULO", null);
        if (cursor.moveToFirst()) {
            numRegistros = cursor.getInt(0);
        }

        idArticulo.setText(Integer.toString(numRegistros + 1));

        btnClear.setOnClickListener(v -> {
            idMarca.setText("");
            idROA.setText("");
            idSubCat.setText("");
            idPF.setText("");
            idArticulo.setText("");
            name.setText("");
            description.setText("");
            isRestricted.setChecked(false);
            price.setText("");
        });

        btnSaveCategoria.setOnClickListener(v -> {
            int id = Integer.parseInt(String.valueOf(idArticulo.getText()));
            int brand = Integer.parseInt(String.valueOf(idMarca.getText()));
            int ROA = Integer.parseInt(String.valueOf(idROA.getText()));
            int subCat = Integer.parseInt(String.valueOf(idSubCat.getText()));
            int PF = Integer.parseInt(String.valueOf(idPF.getText()));
            String nombre = String.valueOf(name.getText());
            String descipcion = String.valueOf(description.getText());
            Boolean restringido = isRestricted.isChecked();
            Double precio = Double.parseDouble(String.valueOf(price.getText()));
            Articulo art = new Articulo(id,brand,ROA,subCat,PF, nombre,descipcion,restringido,precio);
            boolean exito = articuloDAO.insertarArticulo(art);
            if (exito) {
                dialog.dismiss();
            }
            actualizarListView(selected);
        });
        dialog.show();

    }

    public void showOptionsDialog(Articulo articulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.options);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_options, null);
        builder.setView(dialogView);

        Button btnView = dialogView.findViewById(R.id.buttonView);
        Button btnUpdate = dialogView.findViewById(R.id.buttonEdit);
        Button btnDelete = dialogView.findViewById(R.id.buttonDelete);

        AlertDialog dialog = builder.create();

        btnView.setOnClickListener(v -> {
            //verSubCategoria(subCategoria);
        });

        btnUpdate.setOnClickListener(v -> {
            //editSubCategoria(subCategoria, dialog);
        });

        btnDelete.setOnClickListener(v -> {
           // eliminarSubCategoria(subCategoria, dialog);
        });
        dialog.show();

    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = (Categoria)parent.getItemAtPosition(position);
        actualizarListView(selected);
    }

    public void onNothingSelected(AdapterView<?> arg0){

    }
}