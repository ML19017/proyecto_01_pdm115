package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.IOException;
import java.io.InputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DATOS = "control_medicamentos.s3db";
    private static final String SCRIPT_CREACION = "db_script.sql";
    private static final int VERSION = 1;
    private final Context context;

    public DataBaseHelper(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(SCRIPT_CREACION);

            StringBuilder stringBuilder = new StringBuilder();
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String sqlScript = new String(buffer);
            String[] sqlStatements = sqlScript.split(";");

            for (String statement : sqlStatements) {
                statement = statement.trim();
                if (!statement.isEmpty()) {
                    db.execSQL(statement + ";");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
