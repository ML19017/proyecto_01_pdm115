package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SucursalFarmaciaDAO {
    private SQLiteDatabase conexionDB;
    private Context context;


    public SucursalFarmaciaDAO(Context contextApp , SQLiteDatabase conexionDB) {
        this.context  = contextApp;
        this.conexionDB = conexionDB;
    }



//    Agregar Farmacia
public void addSucursalFarmacia(SucursalFarmacia sucursalFarmacia) {


        if(getSucursalFarmacia(sucursalFarmacia.getIdFarmacia())== null)
        {
            ContentValues values = new ContentValues();
            values.put("IDFARMACIA", sucursalFarmacia.getIdFarmacia());
            values.put("IDDIRECCION", sucursalFarmacia.getIdDireccion());
            values.put("NOMBREFARMACIA", sucursalFarmacia.getNombreFarmacia());
            conexionDB.insert("SUCURSALFARMACIA", null, values);
        }
        else
        {
            Toast.makeText(context, "Ya existe una Farmacia con ese ID", Toast.LENGTH_SHORT).show();

        }
}

//    Buscar farmacia por id
    public SucursalFarmacia getSucursalFarmacia(int id) {
        String sql = "SELECT * FROM SUCURSALFARMACIA WHERE IDFARMACIA = ?";
        Cursor cursor = conexionDB.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            SucursalFarmacia sucursalFarmacia = new SucursalFarmacia(
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDFARMACIA")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDDIRECCION")),
                    cursor.getString(cursor.getColumnIndexOrThrow("NOMBREFARMACIA"))
            );
            cursor.close();
            return sucursalFarmacia;
        }
        cursor.close();
        return null;
    }

    // Lista de todas las farmacias

    public List<SucursalFarmacia> getAllSucursalFarmacia() {
        List<SucursalFarmacia> list = new ArrayList<>();
        String sql = "SELECT * FROM SUCURSALFARMACIA";
        Cursor cursor = conexionDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            list.add(new SucursalFarmacia(
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDFARMACIA")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDDIRECCION")),
                    cursor.getString(cursor.getColumnIndexOrThrow("NOMBREFARMACIA"))
            ));
        }
        cursor.close();
        return list;
    }

    // Modificar Farmacia

    public void updateSucursalFarmacia(SucursalFarmacia sucursalFarmacia) {
        ContentValues values = new ContentValues();
        values.put("NOMBREFARMACIA", sucursalFarmacia.getNombreFarmacia());
        conexionDB.update("SUCURSALFARMACIA", values, "IDFARMACIA = ?",
                new String[]{String.valueOf(sucursalFarmacia.getIdFarmacia())});
    }


    // eliminar Farmacia

    public int eliminarSucursal(int idFarmacia) {
        return conexionDB.delete("SUCURSALFARMACIA", "IDFARMACIA = ?",
                new String[]{String.valueOf(idFarmacia)});
    }
}
