package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
public class CategoriaDAO {

    private SQLiteDatabase dbConection;

    public CategoriaDAO(SQLiteDatabase dbConection) {
        this.dbConection = dbConection;
    }

    //Create

    public void createCategoria(Categoria categoria){
        long contador = 0;
        Cursor cursor = dbConection.rawQuery("SELECT COUNT(*) FROM CATEGORIA", null);
        if (cursor.moveToFirst()){
            contador = cursor.getInt(0);
        }
        ContentValues category = new ContentValues();
        category.put("NOMBRECATEGORIA", categoria.getNombreCategoria());
    }
}
