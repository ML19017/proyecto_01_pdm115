package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DetalleCompraDAO {
    private SQLiteDatabase conexionDB;
    private Context context;

    public DetalleCompraDAO(SQLiteDatabase conexionDB, Context context) {
        this.conexionDB = conexionDB;
        this.context = context;
    }

    public void addDetalleCompra(DetalleCompra detalleCompra) {
        int duplicado = checkDuplicate(detalleCompra.getIdCompra(), detalleCompra.getIdArticulo(), detalleCompra.getIdDetalleCompra());

        if (duplicado == 1) {
            Toast.makeText(context, "Ya existe un registro con esta factura, artículo y detalle.", Toast.LENGTH_SHORT).show();
            return;
        } else if (duplicado == 2) {
            Toast.makeText(context, "Ya existe un registro con esta factura y este ID de detalle.", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("IDCOMPRA", detalleCompra.getIdCompra());
        values.put("IDARTICULO", detalleCompra.getIdArticulo());
        values.put("IDDETALLECOMPRA", detalleCompra.getIdDetalleCompra());
        values.put("FECHADECOMPRA", detalleCompra.getFechaDeCompra());
        values.put("PRECIOUNITARIOCOMPRA", detalleCompra.getPrecioUnitarioCompra());
        values.put("CANTIDADCOMPRA", detalleCompra.getCantidadCompra());
        values.put("TOTALDETALLECOMPRA", detalleCompra.getTotalDetalleCompra());

        conexionDB.insert("DETALLECOMPRA", null, values);
        Toast.makeText(context, R.string.save_message, Toast.LENGTH_SHORT).show();
    }


    public void updateDetalleCompra(DetalleCompra detalleCompra) {
        if (isDuplicateOnUpdate(detalleCompra.getIdCompra(), detalleCompra.getIdDetalleCompra(), detalleCompra.getIdArticulo())) {
            Toast.makeText(context, "Ya existe un artículo con esta factura y ID de detalle.", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("IDARTICULO", detalleCompra.getIdArticulo());
        values.put("FECHADECOMPRA", detalleCompra.getFechaDeCompra());
        values.put("PRECIOUNITARIOCOMPRA", detalleCompra.getPrecioUnitarioCompra());
        values.put("CANTIDADCOMPRA", detalleCompra.getCantidadCompra());
        values.put("TOTALDETALLECOMPRA", detalleCompra.getTotalDetalleCompra());

        int rowsAffected = conexionDB.update(
                "DETALLECOMPRA",
                values,
                "IDCOMPRA = ? AND IDDETALLECOMPRA = ?",
                new String[]{
                        String.valueOf(detalleCompra.getIdCompra()),
                        String.valueOf(detalleCompra.getIdDetalleCompra())
                }
        );

        if (rowsAffected == 0) {
            Toast.makeText(context, R.string.not_found_message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.update_message, Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteDetalleCompra(int idCompra, int idArticulo, int idDetalleCompra) {
        int rowsAffected = conexionDB.delete(
                "DETALLECOMPRA",
                "IDCOMPRA = ? AND IDARTICULO = ? AND IDDETALLECOMPRA = ?",
                new String[]{
                        String.valueOf(idCompra),
                        String.valueOf(idArticulo),
                        String.valueOf(idDetalleCompra)
                }
        );

        if (rowsAffected == 0) {
            Toast.makeText(context, R.string.not_found_message, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.delete_message, Toast.LENGTH_SHORT).show();
        }
    }

    public List<DetalleCompra> getAllDetalleCompra() {
        List<DetalleCompra> detalleCompraList = new ArrayList<>();
        String sql = "SELECT * FROM DETALLECOMPRA";
        Cursor cursor = conexionDB.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            DetalleCompra detalle = new DetalleCompra(
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDCOMPRA")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDARTICULO")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDDETALLECOMPRA")),
                    cursor.getString(cursor.getColumnIndexOrThrow("FECHADECOMPRA")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("PRECIOUNITARIOCOMPRA")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("CANTIDADCOMPRA")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("TOTALDETALLECOMPRA")),
                    context
            );
            detalleCompraList.add(detalle);
        }
        cursor.close();
        return detalleCompraList;
    }

    public DetalleCompra getDetalleCompra(int id) {
        String sql = "SELECT * FROM DETALLECOMPRA WHERE IDCOMPRA = ?";
        Cursor cursor = conexionDB.rawQuery(sql, new String[]{String.valueOf(id)});

        if(cursor.moveToFirst()){
            DetalleCompra detalleCompra = new DetalleCompra(
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDCOMPRA")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDARTICULO")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("IDDETALLECOMPRA")),
                    cursor.getString(cursor.getColumnIndexOrThrow("FECHADECOMPRA")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("PRECIOUNITARIOCOMPRA")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("CANTIDADCOMPRA")),
                    cursor.getDouble(cursor.getColumnIndexOrThrow("TOTALDETALLECOMPRA")),
                    context
            );
            cursor.close();
            return detalleCompra;
        }
        cursor.close();
        Toast.makeText(context, R.string.not_found_message, Toast.LENGTH_SHORT).show();
        return null;
    }

    public List<FacturaCompra> getAllFacturaCompra() {
        List<FacturaCompra> lista = new ArrayList<>();
        String sql = "SELECT * FROM FACTURACOMPRA";
        Cursor cursor = conexionDB.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                int idCompra = cursor.getInt(cursor.getColumnIndexOrThrow("IDCOMPRA"));
                lista.add(new FacturaCompra(idCompra, context));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    private int checkDuplicate(int idCompra, int idArticulo, int idDetalleCompra) {
        String sqlTriple = "SELECT 1 FROM DETALLECOMPRA WHERE IDCOMPRA = ? AND IDARTICULO = ? AND IDDETALLECOMPRA = ?";
        Cursor cursorTriple = conexionDB.rawQuery(sqlTriple, new String[]{
                String.valueOf(idCompra),
                String.valueOf(idArticulo),
                String.valueOf(idDetalleCompra)
        });
        if (cursorTriple.moveToFirst()) {
            cursorTriple.close();
            return 1;
        }
        cursorTriple.close();

        String sqlParcial = "SELECT 1 FROM DETALLECOMPRA WHERE IDCOMPRA = ? AND IDDETALLECOMPRA = ?";
        Cursor cursorParcial = conexionDB.rawQuery(sqlParcial, new String[]{
                String.valueOf(idCompra),
                String.valueOf(idDetalleCompra)
        });
        if (cursorParcial.moveToFirst()) {
            cursorParcial.close();
            return 2;
        }
        cursorParcial.close();

        return 0;
    }

    private boolean isDuplicateOnUpdate(int idCompra, int idDetalleCompra, int newIdArticulo) {
        String sql = "SELECT 1 FROM DETALLECOMPRA WHERE IDCOMPRA = ? AND IDDETALLECOMPRA = ? AND IDARTICULO = ?";
        Cursor cursor = conexionDB.rawQuery(sql, new String[]{
                String.valueOf(idCompra),
                String.valueOf(idDetalleCompra),
                String.valueOf(newIdArticulo)
        });

        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }


}

