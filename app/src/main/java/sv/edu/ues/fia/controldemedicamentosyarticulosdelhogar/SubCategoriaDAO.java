package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class SubCategoriaDAO {
    private SQLiteDatabase dbConection;
    private Context context;

    public SubCategoriaDAO(SQLiteDatabase dbConection, Context context) {
        this.dbConection = dbConection;
        this.context = context;
    }



    public boolean insertarSubCategoria(SubCategoria subCategoria){
        long insercion = 0;
        String [] idCategoria = {Integer.toString(subCategoria.getIdCategoria())};
        Cursor categoria = getDbConection().query("CATEGORIA",null,"IDCATEGORIA = ?",idCategoria,null,null,null);
        if(categoria.getCount() == 1){

            ContentValues  subCategory= new ContentValues();
            subCategory.put("IDSUBCATEGORIA", subCategoria.getIdSubCategoria());
            subCategory.put("IDCATEGORIA", subCategoria.getIdCategoria());
            subCategory.put("NOMBRESUBCATEGORIA", subCategoria.getNombreSubCategoria());
            insercion = dbConection.insert("SUBCATEGORIA", null, subCategory);
                if (insercion == -1){
                 Toast.makeText(this.context, "Registro con id duplicado", Toast.LENGTH_SHORT).show();
                return false;
                }
        return true;
        }
        else if (categoria.getCount() > 1){
            Toast.makeText(this.context, "Error: mas de una categoria encontrada", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Toast.makeText(this.context, "No existe la CATEGORIA", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    public ArrayList<SubCategoria> getAllRows(){
        ArrayList<SubCategoria> listado = new ArrayList<SubCategoria>();
        Cursor listadoDB = getDbConection().query("SUBCATEGORIA",null,null,null,null,null,null);
        if (listadoDB.moveToFirst()) {
            listadoDB.moveToFirst();
            for (int i = 0; i < listadoDB.getCount(); i++) {
                SubCategoria subCategoria = new SubCategoria();
                subCategoria.setIdSubCategoria(listadoDB.getInt(0));
                subCategoria.setIdCategoria(listadoDB.getInt(1));
                subCategoria.setNombreSubCategoria(listadoDB.getString(2));
                listado.add(subCategoria);
                listadoDB.moveToNext();
            }
        }

        return listado;
    }


    public SQLiteDatabase getDbConection() {
        return dbConection;
    }

    public void setDbConecction(SQLiteDatabase dbConecction) {
        this.dbConection = dbConecction;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
