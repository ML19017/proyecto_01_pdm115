package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.os.Bundle;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

public class CategoriaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
    }
}