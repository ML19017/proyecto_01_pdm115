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
        EditText nameNewSubCategoria = dialogView.findViewById(R.id.editTextsubCategoriaNombre);
        Button btnSaveCategoria = dialogView.findViewById(R.id.btnGuardarSubCategoria);
        Button btnClear = dialogView.findViewById(R.id.btnLimpiarSubCategoria);
        Spinner spinnerIdCategoria = dialogView.findViewById(R.id.spinnerIdCategoria);

        List<Categoria> categorias = subCategoriaDAO.getAllCategoria();
        categorias.add(0, new Categoria(-1, getString(R.string.select_categoria), this)); // opción por defecto
        ArrayAdapter<Categoria> adapterCategoria = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, categorias) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                Categoria categoria = getItem(position);
                view.setText(categoria.getIdCategoria() == -1 ? getString(R.string.select_categoria) : categoria.getNombreCategoria());
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                Categoria categoria = getItem(position);
                view.setText(categoria.getIdCategoria() == -1
                        ? getString(R.string.select_categoria)
                        : categoria.getNombreCategoria() + " (ID: " + categoria.getIdCategoria() + ")");
                view.setTextColor(categoria.getIdCategoria() == -1 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdCategoria.setAdapter(adapterCategoria);

        Cursor cursor = subCategoriaDAO.getDbConection().rawQuery("SELECT COUNT(*) FROM SUBCATEGORIA", null);
        if (cursor.moveToFirst()) {
            numRegistros = cursor.getInt(0);
        }
        idNewSubCategoria.setText(String.valueOf(numRegistros + 1));

        AlertDialog dialog = builder.create();

        btnClear.setOnClickListener(v -> {
            idNewSubCategoria.setText("");
            spinnerIdCategoria.setSelection(0);
            nameNewSubCategoria.setText("");
        });

        btnSaveCategoria.setOnClickListener(v -> {
            Categoria categoriaSeleccionada = (Categoria) spinnerIdCategoria.getSelectedItem();

            boolean valido = true;

            if (categoriaSeleccionada == null || categoriaSeleccionada.getIdCategoria() == -1) {
                Toast.makeText(this, getString(R.string.error_select_categoria), Toast.LENGTH_SHORT).show();
                TextView errorText = (TextView) spinnerIdCategoria.getSelectedView();
                if (errorText != null) {
                    errorText.setError(getString(R.string.field_empty));
                    errorText.setTextColor(Color.RED);
                }
                valido = false;
            }

            String nombre = nameNewSubCategoria.getText().toString().trim();
            String idTexto = idNewSubCategoria.getText().toString().trim();

            if (idTexto.isEmpty()) {
                idNewSubCategoria.setError(getString(R.string.field_empty));
                valido = false;
            }

            if (nombre.isEmpty()) {
                nameNewSubCategoria.setError(getString(R.string.field_empty));
                valido = false;
            }

            if (!valido) return;

            int id = Integer.parseInt(idTexto);
            int idCategoria = categoriaSeleccionada.getIdCategoria();

            SubCategoria subCat = new SubCategoria(id, idCategoria, nombre);
            boolean exito = subCategoriaDAO.insertarSubCategoria(subCat);
            if (exito) {
                dialog.dismiss();
                actualizarListView(selected);
            } else {
                Toast.makeText(this, getString(R.string.error_guardar_subcategoria), Toast.LENGTH_SHORT).show();
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
        EditText nameSubCategoria = dialogView.findViewById(R.id.editTextsubCategoriaNombre);
        Spinner spinnerIdCategoria = dialogView.findViewById(R.id.spinnerIdCategoria);
        Button btnSaveCategoria = dialogView.findViewById(R.id.btnGuardarSubCategoria);
        Button btnClear = dialogView.findViewById(R.id.btnLimpiarSubCategoria);

        spinnerIdCategoria.setEnabled(false);

        List<Categoria> categorias = subCategoriaDAO.getAllCategoria();
        categorias.add(0, new Categoria(-1, getString(R.string.select_categoria), this)); // opción por defecto
        ArrayAdapter<Categoria> adapterCategoria = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, categorias) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                Categoria categoria = getItem(position);
                view.setText(categoria.getIdCategoria() == -1 ? getString(R.string.select_categoria) : categoria.getNombreCategoria());
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                Categoria categoria = getItem(position);
                view.setText(categoria.getIdCategoria() == -1
                        ? getString(R.string.select_categoria)
                        : categoria.getNombreCategoria() + " (ID: " + categoria.getIdCategoria() + ")");
                view.setTextColor(categoria.getIdCategoria() == -1 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdCategoria.setAdapter(adapterCategoria);

        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getIdCategoria() == subCategoria.getIdCategoria()) {
                spinnerIdCategoria.setSelection(i);
                break;
            }
        }


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
        EditText nameSubCategoria = dialogView.findViewById(R.id.editTextsubCategoriaNombre);
        Spinner spinnerIdCategoria = dialogView.findViewById(R.id.spinnerIdCategoria);
        Button btnSaveSubCategoria = dialogView.findViewById(R.id.btnGuardarSubCategoria);
        Button btnClear = dialogView.findViewById(R.id.btnLimpiarSubCategoria);

        List<Categoria> categorias = subCategoriaDAO.getAllCategoria();
        categorias.add(0, new Categoria(-1, getString(R.string.select_categoria), this)); // opción por defecto

        ArrayAdapter<Categoria> adapterCategoria = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_item, categorias) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getView(position, convertView, parent);
                Categoria categoria = getItem(position);
                view.setText(categoria.getIdCategoria() == -1 ? getString(R.string.select_categoria) : categoria.getNombreCategoria());
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView) super.getDropDownView(position, convertView, parent);
                Categoria categoria = getItem(position);
                view.setText(categoria.getIdCategoria() == -1
                        ? getString(R.string.select_categoria)
                        : categoria.getNombreCategoria() + " (ID: " + categoria.getIdCategoria() + ")");
                view.setTextColor(categoria.getIdCategoria() == -1 ? Color.GRAY : Color.BLACK);
                return view;
            }
        };
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIdCategoria.setAdapter(adapterCategoria);

        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getIdCategoria() == subCategoria.getIdCategoria()) {
                spinnerIdCategoria.setSelection(i);
                break;
            }
        }

        idSubCategoria.setText(String.valueOf(subCategoria.getIdSubCategoria()));
        idSubCategoria.setEnabled(false);
        nameSubCategoria.setText(subCategoria.getNombreSubCategoria());

        btnSaveSubCategoria.setOnClickListener(v -> {
            Categoria categoriaSeleccionada = (Categoria) spinnerIdCategoria.getSelectedItem();
            boolean valido = true;

            if (categoriaSeleccionada == null || categoriaSeleccionada.getIdCategoria() == -1) {
                Toast.makeText(this, getString(R.string.error_select_categoria), Toast.LENGTH_SHORT).show();
                TextView errorView = (TextView) spinnerIdCategoria.getSelectedView();
                if (errorView != null) {
                    errorView.setError(getString(R.string.field_empty));
                    errorView.setTextColor(Color.RED);
                }
                valido = false;
            }

            String nombre = nameSubCategoria.getText().toString().trim();
            if (nombre.isEmpty()) {
                nameSubCategoria.setError(getString(R.string.field_empty));
                valido = false;
            }

            if (!valido) return;

            subCategoria.setIdCategoria(categoriaSeleccionada.getIdCategoria());
            subCategoria.setNombreSubCategoria(nombre);
            int respuesta = subCategoriaDAO.updateSubCategoria(subCategoria);

            if (respuesta == 1) {
                actualizarListView(selected);
                dialog.dismiss();
                dialogoPadre.dismiss();
            } else {
                Toast.makeText(this, getString(R.string.error_guardar_subcategoria), Toast.LENGTH_SHORT).show();
            }
        });

        btnClear.setOnClickListener(v -> {
            spinnerIdCategoria.setSelection(0);
            nameSubCategoria.setText("");
        });

        dialog.show();
    }


    public void eliminarSubCategoria(SubCategoria subCategoria, AlertDialog dialogoPadre) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.confirmation));

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_confirmation, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        TextView advertencia = dialogView.findViewById(R.id.confirmationText);
        Button btnConfirmar = dialogView.findViewById(R.id.btnConfirm);
        Button btnCancelar = dialogView.findViewById(R.id.btnDecline);

        String mensaje = getString(R.string.confirmation_message) + " " + getString(R.string.subcategory).toLowerCase() +
                " ID: " + subCategoria.getIdSubCategoria() + "?\n" +
                getString(R.string.confirm_delete);
        advertencia.setText(mensaje);

        btnConfirmar.setText(getString(R.string.yes));
        btnCancelar.setText(getString(R.string.no));

        btnConfirmar.setOnClickListener(v -> {
            int filasAfectadas = subCategoriaDAO.deleteSubCategoria(subCategoria);
            if (filasAfectadas > 0) {
                actualizarListView(selected);
                Toast.makeText(this, getString(R.string.delete_message) + ": " + getString(R.string.subcategory) +
                        " ID: " + subCategoria.getIdSubCategoria(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                dialogoPadre.dismiss();
            } else {
                Log.d("DELETE_ERROR", "No se eliminó");
            }
        });

        btnCancelar.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
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