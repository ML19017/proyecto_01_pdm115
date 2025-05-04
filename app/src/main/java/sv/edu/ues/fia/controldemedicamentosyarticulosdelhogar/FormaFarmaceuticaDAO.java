package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
public class FormaFarmaceuticaDAO {
    private SQLiteDatabase conexionDB;

    public FormaFarmaceuticaDAO(SQLiteDatabase conexionDB) {
        this.conexionDB = conexionDB;
    }

    public void addFormaFarmaceutica(FormaFarmaceutica formaFarmaceutica) {
        ContentValues values = new ContentValues();
        values.put("IDFORMAFARMACEUTICA", formaFarmaceutica.getIdFormaFarmaceutica());
        values.put("TIPOFORMAFARMACEUTICA", formaFarmaceutica.getTipoFormaFarmaceutica());
        conexionDB.insert("FORMAFARMACEUTICA", null, values);
    }

    public FormaFarmaceutica getFormaFarmaceutica(int id) {
        String sql = "SELECT * FROM FORMAFARMACEUTICA WHERE IDFORMAFARMACEUTICA = ?";
        Cursor cursor = conexionDB.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            FormaFarmaceutica formaFarmaceutica = new FormaFarmaceutica(
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDFORMAFARMACEUTICA")),
                    cursor.getString(cursor.getColumnIndexOrThrow("TIPOFORMAFARMACEUTICA"))
            );
            cursor.close();
            return formaFarmaceutica;
        }
        cursor.close();
        return null;
    }

    public List<FormaFarmaceutica> getAllFormaFarmaceutica() {
        List<FormaFarmaceutica> list = new ArrayList<>();
        String sql = "SELECT * FROM FORMAFARMACEUTICA";
        Cursor cursor = conexionDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            list.add(new FormaFarmaceutica(
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDFORMAFARMACEUTICA")),
                    cursor.getString(cursor.getColumnIndexOrThrow("TIPOFORMAFARMACEUTICA"))
            ));
        }
        cursor.close();
        return list;
    }

    public void updateFormaFarmaceutica(FormaFarmaceutica formaFarmaceutica) {
        ContentValues values = new ContentValues();
        values.put("TIPOFORMAFARMACEUTICA", formaFarmaceutica.getTipoFormaFarmaceutica());
        conexionDB.update("FORMAFARMACEUTICA", values, "IDFORMAFARMACEUTICA = ?", new String[]{String.valueOf(formaFarmaceutica.getIdFormaFarmaceutica())});
    }

    public void deleteFormaFarmaceutica(int id) {
        conexionDB.delete("FORMAFARMACEUTICA", "IDFORMAFARMACEUTICA = ?", new String[]{String.valueOf(id)});
    }
}