package com.tel306.lab2;

import androidx.appcompat.app.AppCompatActivity;
import static com.tel306.lab2.Util.isInternetAvailable;
import android.os.Bundle;

public class ListaTrabajosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_trabajos);
    }
}
