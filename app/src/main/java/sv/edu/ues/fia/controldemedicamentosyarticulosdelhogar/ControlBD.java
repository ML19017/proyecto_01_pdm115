package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.sql.SQLException;

public class ControlBD {

    private final Context context;
    private final DataBaseHelper DBHelper;

    public ControlBD(Context context) {
        this.context = context;
        DBHelper = new DataBaseHelper(context);
    }

    public SQLiteDatabase getConnection() throws SQLException{
        return DBHelper.getWritableDatabase();
    }

    public void closeConnection() throws SQLException {
        DBHelper.close();
    }

    public Context getContext() {
        return context;
    }
}
