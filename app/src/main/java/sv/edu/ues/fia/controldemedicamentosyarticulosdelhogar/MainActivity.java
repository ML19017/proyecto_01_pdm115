package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferencias = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        boolean primeraVez =  preferencias.getBoolean("primera_vez", true);

        if(primeraVez) {
            SQLiteDatabase db = new DataBaseHelper(this).getWritableDatabase();
            DataBaseHelper DBHelper = new DataBaseHelper(this);
            DBHelper.onCreate(db);
            DBHelper.close();
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putBoolean("primera_vez", false);
            editor.apply();
        }
        // Redirigir al LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Finaliza MainActivity para que no se pueda volver atrás
    }
}