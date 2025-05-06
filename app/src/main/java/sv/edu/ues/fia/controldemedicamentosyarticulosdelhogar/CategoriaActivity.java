package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private CategoriaDAO categoriaDAO;
    private ArrayAdapter<Categoria> adaptador;
    private List<Categoria> values = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        //Conexion a la db
        SQLiteDatabase conection = new ControlBD(this).getConnection();
        categoriaDAO = new CategoriaDAO(conection,this);

        adaptador = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, values);
        ListView listV = (ListView)findViewById(R.id.categoryListv);
        listV.setAdapter(adaptador);
        actualizarListView();
        listV.setOnItemClickListener(this);
        Button btnInsertarCategoria = (Button)findViewById(R.id.btnAgregarCategoria);
        btnInsertarCategoria.setOnClickListener(v -> {showAddDialog();});

    }

    public void showAddDialog(){

        int numRegistros = 0;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_categoria, null);
        builder.setView(dialogView);

        EditText idNewCategoria = dialogView.findViewById(R.id.editTextNewCategoriaId);
        EditText nameNewCategoria = dialogView.findViewById(R.id.editTextNewCategoriaNombre);
        Button btnSaveCategoria = dialogView.findViewById(R.id.btnGuardarCategoria);
        Button btnClear = dialogView.findViewById(R.id.btnLimpiarNewCategoria);

        AlertDialog dialog = builder.create();

        Cursor cursor = categoriaDAO.getDbConection().rawQuery("SELECT COUNT(*) FROM CATEGORIA", null);
        if(cursor.moveToFirst()){
        numRegistros = cursor.getInt(0);}
        idNewCategoria.setText(Integer.toString(numRegistros+1));



        btnClear.setOnClickListener(v -> {
            idNewCategoria.setText("");
            nameNewCategoria.setText("");
        });

        btnSaveCategoria.setOnClickListener(v -> {
        int id = Integer.parseInt(String.valueOf(idNewCategoria.getText()));
        String name = String.valueOf(nameNewCategoria.getText());
        Categoria cat = new Categoria(id,name);
        boolean exito = categoriaDAO.insertarCategoria(cat);
        if (exito){
            dialog.dismiss();
        }
        actualizarListView();
        });
        dialog.show();


    }

    public void actualizarListView(){
        if(!values.isEmpty()){
        values.clear();
        }
        values.addAll(categoriaDAO.getAllRows());
        adaptador.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            Categoria categoria = (Categoria)parent.getItemAtPosition(position);
        Log.d("Seleccionado", categoria.getNombreCategoria());
            //showOptionsDialog(formaFarmaceutica);
        }

}