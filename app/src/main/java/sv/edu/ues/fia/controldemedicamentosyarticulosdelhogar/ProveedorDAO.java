package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
    private SQLiteDatabase db;

    public ProveedorDAO(Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<Proveedor> obtenerTodos() {
        List<Proveedor> lista = new ArrayList<>();
        lista.clear();
        Cursor cursor = db.rawQuery("SELECT * FROM PROVEEDOR", null);

        if(cursor !=null && cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                lista.add(new Proveedor(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    public void insertar(Proveedor proveedor) {
        ContentValues values = new ContentValues();
        values.put("IDPROVEEDOR", proveedor.getIdProveedor());
        values.put("NOMBREPROVEEDOR", proveedor.getNombreProveedor());
        values.put("TELEFONOPROVEEDOR", proveedor.getTelefonoProveedor());
        values.put("DIRECCIONPROVEEDOR", proveedor.getDireccionProveedor());
        values.put("RUBROPROVEEDOR", proveedor.getRubroProveedor());
        values.put("NUMREGPROVEEDOR", proveedor.getNumRegProveedor());
        values.put("NIT", proveedor.getNitProveedor());
        values.put("GIROPROVEEDOR", proveedor.getGiroProveedor());
        db.insert("PROVEEDOR", null, values);
    }

    public void actualizar(Proveedor proveedor) {
        ContentValues values = new ContentValues();
        values.put("NOMBREPROVEEDOR", proveedor.getNombreProveedor());
        values.put("TELEFONOPROVEEDOR", proveedor.getTelefonoProveedor());
        values.put("DIRECCIONPROVEEDOR", proveedor.getDireccionProveedor());
        values.put("RUBROPROVEEDOR", proveedor.getRubroProveedor());
        values.put("NUMREGPROVEEDOR", proveedor.getNumRegProveedor());
        values.put("NIT", proveedor.getNitProveedor());
        values.put("GIROPROVEEDOR", proveedor.getGiroProveedor());
        db.update("PROVEEDOR", values, "IDPROVEEDOR = ?", new String[]{String.valueOf(proveedor.getIdProveedor())});
    }

    public void eliminar(int id) {
        db.delete("PROVEEDOR", "IDPROVEEDOR = ?", new String[]{String.valueOf(id)});
    }
}
