package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ArticuloActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private ArticuloDAO articuloDAO;
    private CategoriaDAO categoriaDAO;
    private ArrayAdapter<Articulo> adaptadorListV;
    private ArrayAdapter<Categoria> adaptadorSpinner;

    private Categoria selected = new Categoria();
    private List<Articulo> valuesArt = new ArrayList<>();

    private List<Categoria> valuesCat = new ArrayList<>();

    private Articulo busqueda = new Articulo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);

        //Database conection
        SQLiteDatabase conection = new ControlBD(this).getConnection();
        articuloDAO = new ArticuloDAO(this, conection);
        categoriaDAO = new CategoriaDAO(conection, this);

        //Spinner
        adaptadorSpinner = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, valuesCat) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                Categoria categoria = getItem(position);
                if (categoria.getIdCategoria() == -1) {
                    view.setText(getString(R.string.category_prompt));
                } else {
                    view.setText(categoria.getNombreCategoria()); // Esto se muestra cuando está cerrado
                }
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                Categoria categoria = getItem(position);
                if (categoria.getIdCategoria() == -1) {
                    view.setText(getString(R.string.category_prompt));
                    view.setTextColor(Color.GRAY);
                } else {
                    view.setText("ID : " + categoria.getIdCategoria() + ", " + getString(R.string.category_name) + ": " + categoria.getNombreCategoria());
                    view.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.itemCategorySpinner);
        spinner.setAdapter(adaptadorSpinner);
        llenadoSpinner();
        spinner.setOnItemSelectedListener(this);

        //textView
        TextView categoriaFiltro = (TextView) findViewById(R.id.itemCategoryText);

        //ListV
        adaptadorListV = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, valuesArt);
        ListView listV = (ListView) findViewById(R.id.itemListv);
        listV.setAdapter(adaptadorListV);
        listV.setOnItemClickListener(this);

        //EditText
        EditText buscar = (EditText) findViewById(R.id.editTextSearchItem);
        buscar.setVisibility(GONE);

        //Botones
        Button btnAdd = (Button) findViewById(R.id.btnAddItem);
        Button btnSearch = (Button) findViewById(R.id.btnSearchItem);
        btnAdd.setOnClickListener(v -> {
            showAddDialog();
        });

        btnSearch.setOnClickListener(v -> {
            if (buscar.getVisibility() == GONE) {
                Log.d("inicial", "se activo");
                categoriaFiltro.setVisibility(GONE);
                spinner.setVisibility(GONE);
                buscar.setVisibility(VISIBLE);
                buscar.requestFocus();
            }else if (buscar.getVisibility() == VISIBLE) {
                if (buscar.getText().length() > 0) {
                    Log.d("primero", "mas de 0");
                    int id = Integer.parseInt(String.valueOf(buscar.getText()));
                    buscarArticulo(id);
                }else if(buscar.getText().length() == 0){
                    Log.d("segundo", "cabal 0 ");
                    buscar.setVisibility(GONE);
                    actualizarListView(selected);
                    categoriaFiltro.setVisibility(VISIBLE);
                    spinner.setVisibility(VISIBLE);
                }
            }
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
        if (filtro != null) {
            int idFiltro = filtro.getIdCategoria();
            if (idFiltro == -1) {
                valuesArt.addAll(articuloDAO.getAllRows());
                adaptadorListV.notifyDataSetChanged();
            } else {
                valuesArt.addAll(articuloDAO.getRowsFiltredByCategory(idFiltro));
                adaptadorListV.notifyDataSetChanged();
            }
        } else if (filtro == null) {
            valuesArt.clear();
            if(busqueda!=null){
            valuesArt.add(busqueda);
            }
            adaptadorListV.notifyDataSetChanged();
        }
    }

    public void llenadoSpinner() {
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

        EditText[] campos = {idMarca, idROA, idSubCat, idPF, idArticulo, name, description, price};

        Button btnSaveArticulo = dialogView.findViewById(R.id.btnGuardarArticulo);
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

        btnSaveArticulo.setOnClickListener(v -> {
            if (!areFieldsEmpty(campos)) {

                int id = Integer.parseInt(String.valueOf(idArticulo.getText()));
                int brand = Integer.parseInt(String.valueOf(idMarca.getText()));
                int ROA = Integer.parseInt(String.valueOf(idROA.getText()));
                int subCat = Integer.parseInt(String.valueOf(idSubCat.getText()));
                int PF = Integer.parseInt(String.valueOf(idPF.getText()));
                String nombre = String.valueOf(name.getText());
                String descipcion = String.valueOf(description.getText());
                Boolean restringido = isRestricted.isChecked();
                Double precio = Double.parseDouble(String.valueOf(price.getText()));
                Articulo art = new Articulo(id, brand, ROA, subCat, PF, nombre, descipcion, restringido, precio);
                boolean exito = articuloDAO.insertarArticulo(art);
                if (exito) {
                    dialog.dismiss();
                    actualizarListView(selected);
                }
            }
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
            verArticulo(articulo);
        });

        btnUpdate.setOnClickListener(v -> {
            editArticulo(articulo, dialog);
        });

        btnDelete.setOnClickListener(v -> {
            eliminarArticulo(articulo, dialog);
        });
        dialog.show();

    }

    public void verArticulo(Articulo articulo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.view);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_articulo, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();


        EditText idMarca = dialogView.findViewById(R.id.editTextItemIdBrand);
        EditText idROA = dialogView.findViewById(R.id.editTextItemIdROA);
        EditText idSubCat = dialogView.findViewById(R.id.editTextItemIdSubCategory);
        EditText idPF = dialogView.findViewById(R.id.editTextItemPharmaceuticalForm);
        EditText idArticulo = dialogView.findViewById(R.id.editTextItemId);
        EditText name = dialogView.findViewById(R.id.editTextItemName);
        EditText description = dialogView.findViewById(R.id.editTextItemDescription);
        CheckBox isRestricted = dialogView.findViewById(R.id.checkBoxItemRestricted);
        EditText price = dialogView.findViewById(R.id.editTextItemPrice);

        Button btnSaveArticulo = dialogView.findViewById(R.id.btnGuardarArticulo);
        Button btnClear = dialogView.findViewById(R.id.btnLimpiarArticulo);


        idMarca.setText(Integer.toString(articulo.getIdMarca()));
        idROA.setText(Integer.toString(articulo.getIdViaAdministracion()));
        idSubCat.setText(Integer.toString(articulo.getIdSubCategoria()));
        idPF.setText(Integer.toString(articulo.getIdFormaFarmaceutica()));
        idArticulo.setText(Integer.toString(articulo.getIdArticulo()));
        name.setText(articulo.getNombreArticulo());
        description.setText(articulo.getDescripcionArticulo());
        isRestricted.setChecked(articulo.getRestringidoArticulo());
        price.setText(Double.toString(articulo.getPrecioArticulo()));

        idMarca.setEnabled(false);
        idROA.setEnabled(false);
        idSubCat.setEnabled(false);
        idPF.setEnabled(false);
        idArticulo.setEnabled(false);
        name.setEnabled(false);
        description.setEnabled(false);
        isRestricted.setEnabled(false);
        price.setEnabled(false);

        btnSaveArticulo.setVisibility(GONE);
        btnClear.setVisibility(GONE);

        dialog.show();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = (Categoria) parent.getItemAtPosition(position);
        actualizarListView(selected);
    }

    public void editArticulo(Articulo articulo, AlertDialog dialogoPadre) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.update);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_articulo, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText idMarca = dialogView.findViewById(R.id.editTextItemIdBrand);
        EditText idROA = dialogView.findViewById(R.id.editTextItemIdROA);
        EditText idSubCat = dialogView.findViewById(R.id.editTextItemIdSubCategory);
        EditText idPF = dialogView.findViewById(R.id.editTextItemPharmaceuticalForm);
        EditText idArticulo = dialogView.findViewById(R.id.editTextItemId);
        EditText name = dialogView.findViewById(R.id.editTextItemName);
        EditText description = dialogView.findViewById(R.id.editTextItemDescription);
        CheckBox isRestricted = dialogView.findViewById(R.id.checkBoxItemRestricted);
        EditText price = dialogView.findViewById(R.id.editTextItemPrice);

        EditText[] campos = {idMarca, idSubCat, idPF, idArticulo, name, description, price};


        //idROA, idPF
        EditText[] camposNulos = {idROA, idPF};

        Button btnSaveArticulo = dialogView.findViewById(R.id.btnGuardarArticulo);
        Button btnClear = dialogView.findViewById(R.id.btnLimpiarArticulo);

        idMarca.setText(Integer.toString(articulo.getIdMarca()));
        idROA.setText(Integer.toString(articulo.getIdViaAdministracion()));
        idSubCat.setText(Integer.toString(articulo.getIdSubCategoria()));
        idPF.setText(Integer.toString(articulo.getIdFormaFarmaceutica()));
        idArticulo.setText(Integer.toString(articulo.getIdArticulo()));
        name.setText(articulo.getNombreArticulo());
        description.setText(articulo.getDescripcionArticulo());
        isRestricted.setChecked(articulo.getRestringidoArticulo());
        price.setText(Double.toString(articulo.getPrecioArticulo()));

        idArticulo.setEnabled(false);


        btnSaveArticulo.setOnClickListener(v -> {
            if(!areFieldsEmpty(camposNulos)) {
                if (!areFieldsEmpty(campos)) {
                    articulo.setIdArticulo(Integer.parseInt(String.valueOf(idArticulo.getText())));
                    articulo.setIdMarca(Integer.parseInt(String.valueOf(idMarca.getText())));
                    articulo.setIdViaAdministracion(Integer.parseInt(String.valueOf(idROA.getText())));
                    articulo.setIdSubCategoria(Integer.parseInt(String.valueOf(idSubCat.getText())));
                    articulo.setIdFormaFarmaceutica(Integer.parseInt(String.valueOf(idPF.getText())));
                    articulo.setNombreArticulo(String.valueOf(name.getText()));
                    articulo.setDescripcionArticulo(String.valueOf(description.getText()));
                    articulo.setRestringidoArticulo(isRestricted.isChecked());
                    articulo.setPrecioArticulo(Double.parseDouble(String.valueOf(price.getText())));

                    boolean exito = articuloDAO.updateArticulo(articulo);
                    if (exito) {
                        dialog.dismiss();
                        dialogoPadre.dismiss();
                    }
                }
            }
            else {
                if (areFieldsEmpty(camposNulos) && !areFieldsEmpty(campos)) {
                    articulo.setIdArticulo(Integer.parseInt(String.valueOf(idArticulo.getText())));
                    articulo.setIdMarca(Integer.parseInt(String.valueOf(idMarca.getText())));
                    articulo.setIdViaAdministracion(Integer.parseInt(String.valueOf(idROA.getText())));
                    articulo.setIdSubCategoria(Integer.parseInt(String.valueOf(idSubCat.getText())));
                    articulo.setIdFormaFarmaceutica(Integer.parseInt(String.valueOf(idPF.getText())));
                    articulo.setNombreArticulo(String.valueOf(name.getText()));
                    articulo.setDescripcionArticulo(String.valueOf(description.getText()));
                    articulo.setRestringidoArticulo(isRestricted.isChecked());
                    articulo.setPrecioArticulo(Double.parseDouble(String.valueOf(price.getText())));

                    boolean exito = articuloDAO.updateArticulo(articulo);
                    if (exito) {
                        dialog.dismiss();
                        dialogoPadre.dismiss();
                    }
                }
            }
        });

        btnClear.setOnClickListener(v -> {
            idMarca.setText("");
            idROA.setText("");
            idSubCat.setText("");
            idPF.setText("");
            name.setText("");
            description.setText("");
            isRestricted.setChecked(false);
            price.setText("");
        });
        dialog.show();
    }

    public void eliminarArticulo(Articulo articulo, AlertDialog dialogoPadre) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_confirmation, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        TextView advertencia = dialogView.findViewById(R.id.confirmationText);
        Button btnConfirmar = dialogView.findViewById(R.id.btnConfirm);
        Button btnCancelar = dialogView.findViewById(R.id.btnDecline);

        advertencia.setText(getText(R.string.confirm_delete_message) + ": " + articulo.getIdArticulo());

        btnConfirmar.setOnClickListener(v -> {
            int filasAfectadas = articuloDAO.deleteArticulo(articulo);
            if (filasAfectadas > 0) {
                actualizarListView(selected);
                Toast.makeText(this, getString(R.string.delete_message)+ ": " + articulo.getIdArticulo(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                dialogoPadre.dismiss();
            } else {
                Log.d("DELETE_ERROR", "No se eliminó el artículo");
            }
        });

        btnCancelar.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    public void buscarArticulo(int id) {
        Articulo articulo = articuloDAO.getArticulo(id);
        if (articulo != null) {
            busqueda = articulo;
            actualizarListView(null);
        }
        else{
            busqueda = null;
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public boolean areFieldsEmpty(EditText[] campos) {
        boolean hayVacios = false;
        for (EditText campo : campos) {
            if (campo.getText().toString().trim().isEmpty()) {
                campo.setError(getString(R.string.emptyWarning));
                hayVacios = true;
            }
        }
        return hayVacios;
    }
}