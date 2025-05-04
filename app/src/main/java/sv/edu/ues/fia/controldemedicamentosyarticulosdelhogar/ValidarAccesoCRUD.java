package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class ValidarAccesoCRUD {

    private ControlBD controlBD;
    private Context context;
    public ValidarAccesoCRUD(Context context) {
        this.controlBD = new ControlBD(context);
        this.context = context;
    }

    public boolean validarAcceso(int opcionCRUD) {
        SharedPreferences permisosApp = context.getSharedPreferences("PERMISOS_APP", Context.MODE_PRIVATE);
        String seleccion = permisosApp.getString("seleccion", "-1");

        if(seleccion.equals("-1"))
            return false;

        try {
            SQLiteDatabase conexionDB = controlBD.getConnection();
        }
        catch (SQLiteException e) {
            e.printStackTrace();
        }
        return false;
    }
}
