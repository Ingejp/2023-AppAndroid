package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaProductos extends AppCompatActivity {
    Conexion conexion;
    ListView listViewProductos;
    ArrayList<String> listado;
    ArrayList<Producto> listaProductos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        conexion = new Conexion(getApplicationContext(), "bd_inventario", null, 1);
        listViewProductos = (ListView) findViewById(R.id.listProductos);
        consultarInventario();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listado);
        listViewProductos.setAdapter(adapter);

        listViewProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String datos = "id: "+ listaProductos.get(position).getId()+"\n";
                datos+= "Nombre: "+ listaProductos.get(position).getNombre()+"\n";
                datos+= "Precio: "+ listaProductos.get(position).getPrecio()+"\n";

                Toast.makeText(ListaProductos.this, datos, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void consultarInventario(){
        try {
            SQLiteDatabase db =conexion.getReadableDatabase();
            Producto producto=null;
            listaProductos = new ArrayList<Producto>();
            Cursor cursor= db.rawQuery("SELECT * FROM " + Assets.TABLA_PRODUCTO, null);

            while(cursor.moveToNext()){
                producto = new Producto();
                producto.setId(cursor.getString(0));
                producto.setNombre(cursor.getString(1));
                producto.setPrecio(cursor.getString(2));
                listaProductos.add(producto);
        }
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listado = new ArrayList<String>();
        for (int i=0; i<listaProductos.size(); i++){
            listado.add(listaProductos.get(i).getId()+"-"+listaProductos.get(i).getNombre()+"-"+listaProductos.get(i).getPrecio());
        }
    }
}