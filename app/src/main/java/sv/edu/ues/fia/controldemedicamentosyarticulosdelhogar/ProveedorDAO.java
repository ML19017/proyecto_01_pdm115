package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
    private SQLiteDatabase db;
    private Context context;

    public ProveedorDAO(Context context) {
        this.context = context;
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
        // verifica si hay un proveedor con el mismo nit
        if (proveedorDuplicado(proveedor.getIdProveedor(), proveedor.getNitProveedor())) {
            Toast.makeText(context, "Ya existe un proveedor con este ID o NIT", Toast.LENGTH_SHORT).show();
            return;
        }
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
        // validar si hay duplicados, es decir si ponen el nit de otra persona

        if (proveedorDuplicadoEditar(proveedor.getIdProveedor(), proveedor.getNitProveedor())) {
            Toast.makeText(context, "Ya existe otro proveedor con este NIT", Toast.LENGTH_SHORT).show();
            return;
        }
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

    // Id autoincrementable pq no deja el PRIMARY KEY AUTOICREMENT XD
    public int obtenerIdProveedor() {
        Cursor cursor = db.rawQuery("SELECT MAX(IDPROVEEDOR) FROM PROVEEDOR", null);
        if (cursor.moveToFirst()) {
            int ultimoId = cursor.getInt(0);
            cursor.close();
            return ultimoId + 1;
        } else {
            cursor.close();
            return 1; // si no hay uno creado le mete por defecto 1
        }
    }

    public boolean proveedorDuplicado(int idProveedor, String nit) {
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM PROVEEDOR WHERE IDPROVEEDOR = ? OR NIT = ?",
                new String[]{String.valueOf(idProveedor), nit}
        );

        boolean duplicado  = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            duplicado  = count > 0;
        }

        cursor.close();
        return duplicado;
    }

    public boolean proveedorDuplicadoEditar(int idProveedor, String nit) {
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM PROVEEDOR WHERE NIT = ? AND IDPROVEEDOR != ?",
                new String[]{nit, String.valueOf(idProveedor)}
        );

        boolean duplicado = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            duplicado = count > 0;
        }

        cursor.close();
        return duplicado;
    }
}
