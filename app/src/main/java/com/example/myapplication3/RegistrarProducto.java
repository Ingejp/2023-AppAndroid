package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrarProducto extends AppCompatActivity {
    EditText codigo, nombre, precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_producto);
        this.codigo=findViewById(R.id.codigoProducto);
        this.nombre=findViewById(R.id.nombreProducto);
        this.precio=findViewById(R.id.precioProducto);
    }

    public void onClick(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.btnRegistrar:
                //intent = new Intent(this, MainActivity.class );
                this.registrarProducto();
                break;
            case R.id.btnCancelar:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                break;
        }
        if (intent!=null){
            startActivity(intent);
        }
    }

    private void registrarProducto(){
        try {
            Conexion conexion = new Conexion(this, Assets.DB_NAME, null, Assets.DB_VERSION);
            SQLiteDatabase db = conexion.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Assets.CAMPO_CODIGO_PRODUCTO, codigo.getText().toString());
            values.put(Assets.CAMPO_NOMBRE_PRODUCTO, nombre.getText().toString());
            values.put(Assets.CAMPO_PRECIO_PRODUCTO, precio.getText().toString());

            Long result = db.insert(Assets.TABLA_PRODUCTO, Assets.CAMPO_CODIGO_PRODUCTO, values);
            Toast.makeText(getApplicationContext(), "Producto Registrado", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }


    }
}