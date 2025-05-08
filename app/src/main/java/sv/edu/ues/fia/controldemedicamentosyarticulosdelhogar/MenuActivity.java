package sv.edu.ues.fia.controldemedicamentosyarticulosdelhogar;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends ListActivity {
    private static final String [] ACTIVIDADES = {
            "DireccionActivity",
            "SucursalFarmaciaActivity",
            "DetalleExistenciaActivity",
            "DetalleVentaActivity",
            "FacturaVentaActivity",
            "ClienteActivity",
            "DetalleCompraActivity",
            "FacturaCompraActivity",
            "ProveedorActivity",
            "FormaFarmaceuticaActivity",
            "MarcaActivity",
            "ViaAdministracionActivity",
            "RecetaActivity",
            "DoctorActivity",
            "ArticuloActivity",
            "CategoriaActivity",
            "SubCategoriaActivity",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferencias = getSharedPreferences("PERMISOS_APP", Context.MODE_PRIVATE);
        List<String> opcionesFiltradas = new ArrayList<>();
        List<Integer> iconosFiltrados = new ArrayList<>();
        String[] todasOpciones = getResources().getStringArray(R.array.options_menu);
        String[] idMenu = getResources().getStringArray(R.array.id_menu); // Lista de todas las opciones
        int[] todosIconos = getIconos(getResources().getStringArray(R.array.main_icons));

        for (int i = 0; i < idMenu.length; i++) {
            boolean tienePermiso = preferencias.getBoolean(idMenu[i], false);
            Log.d("MenuActivity", "Opción: " + idMenu[i] + ", Tiene permiso: " + tienePermiso);
            if (tienePermiso) {
                opcionesFiltradas.add(todasOpciones[i]);
                iconosFiltrados.add(todosIconos[i]);
            }
        }
        Log.d("MenuActivity", "Opciones filtradas: " + opcionesFiltradas);
        Log.d("MenuActivity", "Iconos filtrados: " + iconosFiltrados);

        setListAdapter(new MenuAdapter(this, opcionesFiltradas.toArray(new String[0]), iconosFiltrados.stream().mapToInt(i -> i).toArray()));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try {
            String[] idMenu = getResources().getStringArray(R.array.id_menu); // Lista de todas las opciones
            SharedPreferences preferencias = getSharedPreferences("PERMISOS_APP", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("id_opcion", idMenu[position]);
            editor.apply();
            String nombreClase = ACTIVIDADES[position];
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
