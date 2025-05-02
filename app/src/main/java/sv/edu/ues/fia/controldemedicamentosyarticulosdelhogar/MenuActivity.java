package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MenuActivity extends ListActivity {
    String [] actividades = {
            "DireccionActivity",
            "SucursalActivity",
            "DetalleExistenciaActivity",
            "VentaActivity",
            "DetalleVentaActivity",
            "FacturaActivity",
            "ClienteActivity",
            "CompraActivity",
            "DetalleCompraActivity",
            "FacturaCompraActivity",
            "FormaFarmaceuticaActivity",
            "MarcaActivity",
            "VíaAdministracionActivity",
            "RecetaActivity",
            "DoctorActivity",
            "ArticuloActivity",
            "CategoriaActivity",
            "SubCategoriaActivity",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String [] menu = getResources().getStringArray(R.array.main_menu); //Obtiene las etiquetas de la lista menu.
        int [] iconos = getIconos(getResources().getStringArray(R.array.main_icons));
        setListAdapter(new MenuAdapter(this, menu, iconos));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try {
            String nombreClase = actividades[position];
            Class<?> clase = Class.forName("sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar." + nombreClase);
            Intent inte = new Intent(this, clase);
            this.startActivity(inte);
        }
        catch (ClassNotFoundException exp) {
            exp.printStackTrace();
        }
    }

    private int[] getIconos(String [] nombreRecurso) {
        int[] iconos = new int[nombreRecurso.length];
        for (int i = 0; i < iconos.length; i++) {
            iconos[i] = getResources().getIdentifier(nombreRecurso[i], "drawable", getPackageName());
        }
        return iconos;
    }
}
