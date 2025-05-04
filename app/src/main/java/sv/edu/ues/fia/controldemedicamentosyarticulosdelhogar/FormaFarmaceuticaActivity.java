package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FormaFarmaceuticaActivity extends AppCompatActivity {

    /*  Validar los accesos al CRUD
        CREATE -> 1
        READ -> 2
        UPDATE -> 3
        DELETE -> 4
    */
    private final ValidarAccesoCRUD vac = new ValidarAccesoCRUD(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forma_farmaceutica);

        //Comprobacion Inicial de Permisos de Consulta
        ListView lista = (ListView) findViewById(R.id.lvFormaFarmaceutica);
        Button boton  = (Button) findViewById(R.id.btnAgregarFormaFarmaceutica);

        boton.setVisibility(vac.validarAcceso(1) ? View.VISIBLE : View.INVISIBLE);
        lista.setVisibility(vac.validarAcceso(2) ? View.VISIBLE : View.INVISIBLE);
    }



}