package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

public class ArticuloDAO {
    private SQLiteDatabase dbConection;
    private Context context;

    public ArticuloDAO(Context context, SQLiteDatabase dbConection) {
        this.context = context;
        this.dbConection = dbConection;
    }

    public ArticuloDAO() {
    }

    public boolean insertarArticulo(Articulo articulo) {
        long insercion = 0;
        //Comprobar que existan los registros de las llaves foraneas
        boolean marcaValida = validarForaneas(articulo.getIdMarca(),1);
        boolean viaAdminValida = validarForaneas(articulo.getIdViaAdministracion(),2);
        boolean subCatValida = validarForaneas(articulo.getIdSubCategoria(),3);
        boolean formaFarmValida = validarForaneas(articulo.getIdFormaFarmaceutica(),4);
        if (marcaValida && viaAdminValida && subCatValida && formaFarmValida) {
            ContentValues item = new ContentValues();
            item.put("IDARTICULO",articulo.getIdArticulo());
            item.put("IDMARCA",articulo.getIdMarca());
            item.put("IDVIAADMINISTRACION",articulo.getIdViaAdministracion());
            item.put("IDSUBCATEGORIA",articulo.getIdSubCategoria());
            item.put("IDFORMAFARMACEUTICA",articulo.getIdFormaFarmaceutica());
            item.put("NOMBREARTICULO",articulo.getNombreArticulo());
            item.put("DESCRIPCIONARTICULO",articulo.getDescripcionArticulo());
            item.put("RESTRINGIDOARTICULO",articulo.getRestringidoArticulo());
            item.put("PRECIOARTICULO",articulo.getPrecioArticulo());

            insercion = dbConection.insert("ARTICULO", null, item);
            if (insercion == -1) {
                Toast.makeText(this.context, "Registro con id duplicado", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } else{
            return false;
        }
    }

    public ArrayList<Articulo> getAllRows(){
        ArrayList<Articulo> listado = new ArrayList<Articulo>();
        Cursor listadoDB = getDbConection().query("ARTICULO",null,null,null,null,null,null);
        if (listadoDB.moveToFirst()) {
            listadoDB.moveToFirst();
            for (int i = 0; i < listadoDB.getCount(); i++) {
                Articulo articulo = new Articulo();
                articulo.setIdArticulo(listadoDB.getInt(0));
                articulo.setIdMarca(listadoDB.getInt(1));
                articulo.setIdViaAdministracion(listadoDB.getInt(2));
                articulo.setIdSubCategoria(listadoDB.getInt(3));
                articulo.setIdFormaFarmaceutica(listadoDB.getInt(4));
                articulo.setNombreArticulo(listadoDB.getString(5));
                articulo.setDescripcionArticulo(listadoDB.getString(6));
                articulo.setRestringidoArticulo(Boolean.getBoolean(listadoDB.getString(7)));
                articulo.setPrecioArticulo(listadoDB.getDouble(8));
                listado.add(articulo);
                listadoDB.moveToNext();
            }
        }

        return listado;
    }

    public ArrayList<Articulo> getRowsFiltredByCategory(int idCategoria){
        String [] id = {Integer.toString(idCategoria)};
        ArrayList<Articulo> listado = new ArrayList<Articulo>();
        Cursor listadoSubCats = getDbConection().query("SUBCATEGORIA", null, "IDCATEGORIA = ?", id, null, null, null);
        if (listadoSubCats.moveToFirst()) {
            listadoSubCats.moveToFirst();
            for (int i = 0; i < listadoSubCats.getCount(); i++) {
                String [] idSubCat =  {listadoSubCats.getString(0)};
                Cursor listadoArticulos = getDbConection().query("ARTICULO", null, "IDSUBCATEGORIA = ?", idSubCat,null,null,null);
                Articulo articulo = new Articulo();
                articulo.setIdArticulo(listadoArticulos.getInt(0));
                articulo.setIdMarca(listadoArticulos.getInt(1));
                articulo.setIdViaAdministracion(listadoArticulos.getInt(2));
                articulo.setIdSubCategoria(listadoArticulos.getInt(3));
                articulo.setIdFormaFarmaceutica(listadoArticulos.getInt(4));
                articulo.setNombreArticulo(listadoArticulos.getString(5));
                articulo.setDescripcionArticulo(listadoArticulos.getString(6));
                articulo.setRestringidoArticulo(Boolean.getBoolean(listadoArticulos.getString(7)));
                articulo.setPrecioArticulo(listadoArticulos.getDouble(8));
                listado.add(articulo);
                listadoArticulos.moveToNext();
            }
        }
        return listado;
    }


    public SQLiteDatabase getDbConection() {
        return dbConection;
    }

    public void setDbConection(SQLiteDatabase dbConection) {
        this.dbConection = dbConection;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public boolean validarForaneas(Integer idForanea, Integer opcion) {
        /*Opciones:
        1 - Marca
        2 - Via Administracion
        3 - SubCategoria
        4- Forma farmaceutica
        */
        switch (opcion) {
            case 1:
                String[] marca = {Integer.toString(idForanea)};
                Cursor findMarca = getDbConection().query("MARCA", null, "IDMARCA = ?", marca, null, null, null);
                if (findMarca.getCount() == 1) {
                    return true;
                } else if (findMarca.getCount() > 1) {
                    Toast.makeText(this.context, "Error: mas de una MARCA encontrada", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    Toast.makeText(this.context, "No existe la MARCA", Toast.LENGTH_SHORT).show();
                    return false;
                }
            case 2:
                String[] viaAdmin = {Integer.toString(idForanea)};
                Cursor findViaAdmin = getDbConection().query("VIAADMINISTRACION", null, "IDVIAADMINISTRACION = ?", viaAdmin, null, null, null);
                if (findViaAdmin.getCount() == 1) {
                    return true;
                } else if (findViaAdmin.getCount() > 1) {
                    Toast.makeText(this.context, "Error: mas de una VIA ADMINISTRACION encontrada", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    Toast.makeText(this.context, "No existe la VIA ADMINISTRACION", Toast.LENGTH_SHORT).show();
                    return false;
                }
            case 3:
                String[] subCat = {Integer.toString(idForanea)};
                Cursor findSubCat = getDbConection().query("SUBCATEGORIA", null, "IDSUBCATEGORIA = ?", subCat, null, null, null);
                if (findSubCat.getCount() == 1) {
                    return true;
                } else if (findSubCat.getCount() > 1) {
                    Toast.makeText(this.context, "Error: mas de una SUB-CATEGORIA encontrada", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    Toast.makeText(this.context, "No existe la SUB-CATEGORIA", Toast.LENGTH_SHORT).show();
                    return false;
                }

            case 4:
                String[] formaFarm = {Integer.toString(idForanea)};
                Cursor findFormaFarm = getDbConection().query("FORMAFARMACEUTICA", null, "IDFORMAFARMACEUTICA = ?", formaFarm, null, null, null);
                if (findFormaFarm.getCount() == 1) {
                    return true;
                } else if (findFormaFarm.getCount() > 1) {
                    Toast.makeText(this.context, "Error: mas de una FORMA FARMACEUTICA encontrada", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    Toast.makeText(this.context, "No existe la FORMA FARMACEUTICA", Toast.LENGTH_SHORT).show();
                    return false;
                }
            default:
                return false;
        }
    }
}





