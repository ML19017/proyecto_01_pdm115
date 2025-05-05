package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.widget.Toast;
import android.content.Context;

public class FacturaCompraDAO {
    private SQLiteDatabase conexionDB;
    private Context context;

    public FacturaCompraDAO(SQLiteDatabase conexionDB, Context context) {
        this.conexionDB = conexionDB;
        this.context = context;
    }

    // obtener todas las sucursales de farmacias
    public List<SucursalFarmacia> getAllFarmacias() {
        List<SucursalFarmacia> farmacias = new ArrayList<>();
        String sql = "SELECT * FROM SUCURSALFARMACIA";
        Cursor cursor = conexionDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            farmacias.add(new SucursalFarmacia(
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDFARMACIA")),
                    cursor.getString(cursor.getColumnIndexOrThrow("NOMBREFARMACIA"))
            ));
        }
        cursor.close();
        return farmacias;
    }

    // obtener todos los proveedores
    public List<Proveedor> getAllProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM PROVEEDOR";
        Cursor cursor = conexionDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            proveedores.add(new Proveedor(
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDPROVEEDOR")),
                    cursor.getString(cursor.getColumnIndexOrThrow("NOMBREPROVEEDOR"))
            ));
        }
        cursor.close();
        return proveedores;
    }
    public void addFacturaCompra(FacturaCompra facturaCompra) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDCOMPRA", facturaCompra.getIdCompra());
        contentValues.put("FECHACOMPRA", facturaCompra.getFechaCompra());
        contentValues.put("TOTALCOMPRA", facturaCompra.getTotalCompra());
        contentValues.put("IDFARMACIA", facturaCompra.getIdFarmacia());
        contentValues.put("IDPROVEEDOR", facturaCompra.getIdProveedor());
        conexionDB.insert("FACTURACOMPRA", null, contentValues);
    }

    public void updateFacturaCompra(FacturaCompra facturaCompra) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("FECHACOMPRA", facturaCompra.getFechaCompra());
        contentValues.put("TOTALCOMPRA", facturaCompra.getTotalCompra());
        contentValues.put("IDFARMACIA", facturaCompra.getIdFarmacia());
        contentValues.put("IDPROVEEDOR", facturaCompra.getIdProveedor());
        conexionDB.update("FACTURACOMPRA", contentValues, "IDCOMPRA = ?", new String[]{String.valueOf(facturaCompra.getIdCompra())});
    }

    public void deleteFacturaCompra(int idCompra) {
        conexionDB.delete("FACTURACOMPRA", "IDCOMPRA = ?", new String[]{String.valueOf(idCompra)});
    }

    // obtener todas las facturas de compra
    public List<FacturaCompra> getAllFacturaCompra() {
        List<FacturaCompra> facturasCompra = new ArrayList<>();
        String sql = "SELECT * FROM FACTURACOMPRA";
        Cursor cursor = conexionDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            facturasCompra.add(new FacturaCompra(
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDCOMPRA")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDFARMACIA")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDPROVEEDOR")),
                    cursor.getString(cursor.getColumnIndexOrThrow("FECHACOMPRA")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("TOTALCOMPRA"))
            ));
        }
        cursor.close();
        return facturasCompra;
    }

    // obtener unas sucursal por us id
    public SucursalFarmacia getFarmaciaById(int idFarmacia) {
        SucursalFarmacia farmacia = null;
        String sql = "SELECT * FROM SUCURSALFARMACIA WHERE IDFARMACIA = ?";
        Cursor cursor = conexionDB.rawQuery(sql, new String[]{String.valueOf(idFarmacia)});
        if (cursor.moveToFirst()) {
            farmacia = new SucursalFarmacia(
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDFARMACIA")),
                    cursor.getString(cursor.getColumnIndexOrThrow("NOMBREFARMACIA"))
            );
        }
        cursor.close();
        return farmacia;
    }

    // obtener proveedor por su id
    public Proveedor getProveedorById(int idProveedor) {
        Proveedor proveedor = null;
        String sql = "SELECT * FROM PROVEEDOR WHERE IDPROVEEDOR = ?";
        Cursor cursor = conexionDB.rawQuery(sql, new String[]{String.valueOf(idProveedor)});
        if (cursor.moveToFirst()) {
            proveedor = new Proveedor(
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDPROVEEDOR")),
                    cursor.getString(cursor.getColumnIndexOrThrow("NOMBREPROVEEDOR"))
            );
        }
        cursor.close();
        return proveedor;
    }

}
