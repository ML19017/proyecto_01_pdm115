package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Redirigir al LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finaliza MainActivity para que no se pueda volver atrás
    }
}