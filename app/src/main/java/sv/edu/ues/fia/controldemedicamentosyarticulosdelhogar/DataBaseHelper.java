package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DATOS = "control_medicamentos.s3db";
    private static final int VERSION = 1;

    private static final String [] TABLAS = {
            "",
    };
    private static final String [] RESTRICCIONES = {
            "",
    };
    private static final String [] DISPARADORES = {
            "",
    };

    private static final String [] DATOS_PRUEBA = {
            "",
    };

    public DataBaseHelper(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            for (String tabla: TABLAS) {
                db.execSQL(tabla);
            }
            for (String dato: DATOS_PRUEBA) {
                db.execSQL(dato);
            }
            for (String restriccion: RESTRICCIONES) {
                db.execSQL(restriccion);
            }
            for (String disparador: DISPARADORES) {
                db.execSQL(disparador);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
