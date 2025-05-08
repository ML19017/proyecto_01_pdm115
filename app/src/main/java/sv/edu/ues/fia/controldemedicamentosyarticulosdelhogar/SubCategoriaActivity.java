package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import static android.view.View.GONE;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SubCategoriaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private SubCategoriaDAO subCategoriaDAO;
    private CategoriaDAO categoriaDAO;
    private ArrayAdapter<SubCategoria> adaptadorListV;
    private ArrayAdapter<Categoria> adaptadorSpinner;
    private Categoria selected;

    private List<SubCategoria> valuesSubCat = new ArrayList<>();
    private List<Categoria> valuesCat = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categoria);
        //Instanciacion de comuniacion con db
        SQLiteDatabase conection = new ControlBD(this).getConnection();
        subCategoriaDAO = new SubCategoriaDAO(conection, this);
        categoriaDAO = new CategoriaDAO(conection, this);

        //Spinner
        adaptadorSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valuesCat){
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
                    view.setText("ID : " + categoria.getIdCategoria() + ", "  + getString(R.string.category_name) + ": " + categoria.getNombreCategoria());
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

        //List view
        adaptadorListV = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, valuesSubCat);
        ListView listV = (ListView) findViewById(R.id.subCategoryListv);
        listV.setAdapter(adaptadorListV);
        listV.setOnItemClickListener(this);

        //Boton add
        Button btnAdd = (Button) findViewById(R.id.btnAgregarSubCategoria);
        btnAdd.setOnClickListener(v -> {
            showAddDialog();
        });


    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SubCategoria subCategoria = (SubCategoria) parent.getItemAtPosition(position);
        Log.d("Seleccionado", subCategoria.getNombreSubCategoria());
        showOptionsDialog(subCategoria);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected = (Categoria) parent.getItemAtPosition(position);

        actualizarListView(selected);
    }


    public void actualizarListView(Categoria filtro) {
        if (!valuesSubCat.isEmpty()) {
            valuesSubCat.clear();
        }
        int idFiltro = filtro.getIdCategoria();
        if (idFiltro == -1) {
            valuesSubCat.addAll(subCategoriaDAO.getAllRows());
            adaptadorListV.notifyDataSetChanged();
        } else {
            valuesSubCat.addAll(subCategoriaDAO.getRowsFiltredByCategory(idFiltro));
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
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_sub_categoria, null);
        builder.setView(dialogView);

        EditText idNewSubCategoria = dialogView.findViewById(R.id.editTextsubCategoriaId);
        EditText idCategoriaPadre = dialogView.findViewById(R.id.editTextCategoriaPadreId);
        EditText nameNewSubCategoria = dialogView.findViewById(R.id.editTextsubCategoriaNombre);
        Button btnSaveCategoria = dialogView.findViewById(R.id.btnGuardarSubCategoria);
        Button btnClear = dialogView.findViewById(R.id.btnLimpiarSubCategoria);

        EditText[] campos = {idNewSubCategoria, idCategoriaPadre, nameNewSubCategoria};

        AlertDialog dialog = builder.create();

        Cursor cursor = subCategoriaDAO.getDbConection().rawQuery("SELECT COUNT(*) FROM SUBCATEGORIA", null);
        if (cursor.moveToFirst()) {
            numRegistros = cursor.getInt(0);
        }
        idNewSubCategoria.setText(Integer.toString(numRegistros + 1));


        btnClear.setOnClickListener(v -> {
            idNewSubCategoria.setText("");
            idCategoriaPadre.setText("");
            nameNewSubCategoria.setText("");
        });

        btnSaveCategoria.setOnClickListener(v -> {
            if (!areFieldsEmpty(campos)) {
                int id = Integer.parseInt(String.valueOf(idNewSubCategoria.getText()));
                int idCategoria = Integer.parseInt(String.valueOf(idCategoriaPadre.getText()));
                String name = String.valueOf(nameNewSubCategoria.getText());
                SubCategoria subCat = new SubCategoria(id, idCategoria, name);
                boolean exito = subCategoriaDAO.insertarSubCategoria(subCat);
                if (exito) {
                    dialog.dismiss();
                }
                actualizarListView(selected);
            }
        });
        dialog.show();

    }

    public void showOptionsDialog(SubCategoria subCategoria) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.options);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_options, null);
        builder.setView(dialogView);

        Button btnView = dialogView.findViewById(R.id.buttonView);
        Button btnUpdate = dialogView.findViewById(R.id.buttonEdit);
        Button btnDelete = dialogView.findViewById(R.id.buttonDelete);

        AlertDialog dialog = builder.create();

        btnView.setOnClickListener(v -> {
            verSubCategoria(subCategoria);
        });

        btnUpdate.setOnClickListener(v -> {
            editSubCategoria(subCategoria, dialog);
        });

        btnDelete.setOnClickListener(v -> {
            eliminarSubCategoria(subCategoria, dialog);
        });
        dialog.show();

    }

    public void verSubCategoria(SubCategoria subCategoria) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.view);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_sub_categoria, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText idSubCategoria = dialogView.findViewById(R.id.editTextsubCategoriaId);
        EditText idCategoriaPadre = dialogView.findViewById(R.id.editTextCategoriaPadreId);
        EditText nameSubCategoria = dialogView.findViewById(R.id.editTextsubCategoriaNombre);
        Button btnSaveCategoria = dialogView.findViewById(R.id.btnGuardarSubCategoria);
        Button btnClear = dialogView.findViewById(R.id.btnLimpiarSubCategoria);

        idCategoriaPadre.setText(Integer.toString(subCategoria.getIdCategoria()));
        idCategoriaPadre.setEnabled(false);
        idSubCategoria.setText(Integer.toString(subCategoria.getIdSubCategoria()));
        idSubCategoria.setEnabled(false);
        nameSubCategoria.setText(subCategoria.getNombreSubCategoria());
        nameSubCategoria.setEnabled(false);
        btnSaveCategoria.setVisibility(GONE);
        btnClear.setVisibility(GONE);

        dialog.show();
    }

    public void editSubCategoria(SubCategoria subCategoria, AlertDialog dialogoPadre) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.update);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_sub_categoria, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        EditText idSubCategoria = dialogView.findViewById(R.id.editTextsubCategoriaId);
        EditText idCategoriaPadre = dialogView.findViewById(R.id.editTextCategoriaPadreId);
        EditText nameSubCategoria = dialogView.findViewById(R.id.editTextsubCategoriaNombre);
        Button btnSaveSubCategoria = dialogView.findViewById(R.id.btnGuardarSubCategoria);
        Button btnClear = dialogView.findViewById(R.id.btnLimpiarSubCategoria);

        EditText[] campos = {idSubCategoria, idCategoriaPadre, nameSubCategoria};

        idSubCategoria.setText(Integer.toString(subCategoria.getIdSubCategoria()));
        idCategoriaPadre.setText(Integer.toString(subCategoria.getIdCategoria()));
        nameSubCategoria.setText(subCategoria.getNombreSubCategoria());
        idSubCategoria.setEnabled(false);

        btnSaveSubCategoria.setOnClickListener(v -> {
            if (!areFieldsEmpty(campos)) {

                subCategoria.setIdCategoria(Integer.parseInt(String.valueOf(idCategoriaPadre.getText())));
                subCategoria.setNombreSubCategoria(String.valueOf(nameSubCategoria.getText()));
                int respuesta = subCategoriaDAO.updateSubCategoria(subCategoria);
                switch (respuesta) {
                    case 1:
                        actualizarListView(selected);
                        dialog.dismiss();
                        dialogoPadre.dismiss();
                        break;
                    case 2:
                        idCategoriaPadre.setError("Invalido");
                }
            }
        });
        btnClear.setOnClickListener(v -> {
            idCategoriaPadre.setText("");
            nameSubCategoria.setText("");
        });
        dialog.show();
    }

    public void eliminarSubCategoria(SubCategoria subCategoria, AlertDialog dialogoPadre) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WARNING");
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_confirmation, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        TextView advertencia = dialogView.findViewById(R.id.confirmationText);
        Button btnConfirmar = dialogView.findViewById(R.id.btnConfirm);
        Button btnCancelar = dialogView.findViewById(R.id.btnDecline);

        advertencia.setText("Está apunto de eliminar la sub-categoria con id:" + subCategoria.getIdSubCategoria() + "\n Esta acción no se puede revertir, ¿Desea continuar?");

        btnConfirmar.setOnClickListener(v -> {
            int filasAfectadas = subCategoriaDAO.deleteSubCategoria(subCategoria);
            if (filasAfectadas > 0) {
                actualizarListView(selected);
                Toast.makeText(this, "Se ha eliminado: sub - categoria id: " + subCategoria.getIdSubCategoria(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                dialogoPadre.dismiss();
            } else {
                Log.d("DELETE_ERROR", "No se elimino");
            }
        });
        btnCancelar.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public boolean areFieldsEmpty(EditText[] campos) {
        boolean hayVacios = false;
        for (EditText campo : campos) {
            if (campo.getText().toString().trim().isEmpty()) {
                campo.setError("Este campo es obligatorio");
                hayVacios = true;
            }
        }
        return hayVacios;
    }
}