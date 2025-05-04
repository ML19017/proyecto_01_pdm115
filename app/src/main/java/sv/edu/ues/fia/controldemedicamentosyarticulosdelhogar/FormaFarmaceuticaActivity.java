package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class FormaFarmaceuticaActivity extends AppCompatActivity {

    private ValidarAccesoCRUD validarAccesoCRUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forma_farmaceutica);
        validarAccesoCRUD.validarAcceso(1, this);
    }
}