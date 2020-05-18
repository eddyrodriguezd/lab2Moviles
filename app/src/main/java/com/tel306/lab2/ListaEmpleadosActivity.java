package com.tel306.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import static com.tel306.lab2.Util.isInternetAvailable;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ListaEmpleadosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_empleados);
        setTitle("Empleados");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.itemChangeList:
                startActivity(new Intent(this, ListaTrabajosActivity.class));
                finish();
            case R.id.itemAdd:
                Toast.makeText(this, "Agregar", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
