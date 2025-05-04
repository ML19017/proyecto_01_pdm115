package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FormaFarmaceuticaActivity extends AppCompatActivity {

    private final ValidarAccesoCRUD vac = new ValidarAccesoCRUD(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forma_farmaceutica);
        Toast.makeText(this,"Bueno Lo intentamos" + vac.validarAcceso(4), Toast.LENGTH_LONG).show();
    }
}