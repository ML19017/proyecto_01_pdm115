package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String NOMBRE_BASE_DATOS = "control_medicamentos.s3db";
    private static final String [] SCRIPTS = {
            "creation_db_script.sql",
            "user_table_filling_script.sql",
    };
    private static final int VERSION = 1;
    private final Context context;

    public DataBaseHelper(Context context) {
        super(context, NOMBRE_BASE_DATOS, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            for (String script: SCRIPTS) {
                String sqlScript = getSqlScript(script);
                String[] sqlStatements = sqlScript.split(";");

                for (String statement : sqlStatements) {
                    statement = statement.trim();
                    if (!statement.isEmpty()) {
                        db.execSQL(statement + ";");
                    }
                }
            }
        } catch (SQLiteException | IOException e) {
            e.printStackTrace();
            Log.d("Error", "onCreate:"+e.toString());
        }
    }

    private String getSqlScript(String SCRIPT_NAME) throws IOException{
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(SCRIPT_NAME);

        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        return new String(buffer);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
