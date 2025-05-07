package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SubCategoriaActivity extends AppCompatActivity {

    private SubCategoriaDAO subCategoriaDAO;
    private ArrayAdapter<SubCategoria> adaptador;
    private List<SubCategoria> values = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categoria);
        //Instanciacion de comuniacion con db
        SQLiteDatabase conection = new ControlBD(this).getConnection();
        subCategoriaDAO = new SubCategoriaDAO(conection, this);

        //List view
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
        ListView listV = (ListView)findViewById(R.id.subCategoryListv);
        listV.setAdapter(adaptador);
        actualizarListView();

        //Boton add
        Button btnAdd = (Button)findViewById(R.id.btnAgregarSubCategoria);

        btnAdd.setOnClickListener(v -> {
            showAddDialog();
        });


    }

    public void actualizarListView(){
        if(!values.isEmpty()){
            values.clear();
        }
        values.addAll(subCategoriaDAO.getAllRows());
        adaptador.notifyDataSetChanged();
    }

    public void showAddDialog(){
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

        AlertDialog dialog = builder.create();

        Cursor cursor = subCategoriaDAO.getDbConection().rawQuery("SELECT COUNT(*) FROM SUBCATEGORIA", null);
        if(cursor.moveToFirst()){
            numRegistros = cursor.getInt(0);}
        idNewSubCategoria.setText(Integer.toString(numRegistros+1));



        btnClear.setOnClickListener(v -> {
            idNewSubCategoria.setText("");
            idCategoriaPadre.setText("");
            nameNewSubCategoria.setText("");
        });

        btnSaveCategoria.setOnClickListener(v -> {
            int id = Integer.parseInt(String.valueOf(idNewSubCategoria.getText()));
            int idCategoria = Integer.parseInt(String.valueOf(idCategoriaPadre.getText()));
            String name = String.valueOf(nameNewSubCategoria.getText());
            SubCategoria subCat = new SubCategoria(id,idCategoria,name);
            boolean exito = subCategoriaDAO.insertarSubCategoria(subCat);
            if (exito){
                dialog.dismiss();
            }
            actualizarListView();
        });
        dialog.show();

    }
}