package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.SQLException;

public class DireccionActivity extends AppCompatActivity {

    ControlBD controlBD = new ControlBD(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void delete(int id) {
        try {
            SQLiteDatabase conexion = controlBD.getConnection();
            conexion.execSQL("select * from a");
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }
}
