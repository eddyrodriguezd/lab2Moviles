package com.tel306.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.tel306.lab2.Util.isInternetAvailable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tel306.lab2.adapters.ListaTrabajosAdapter;
import com.tel306.lab2.entidades.Departamento;
import com.tel306.lab2.entidades.DtoDepartamento;
import com.tel306.lab2.entidades.DtoTrabajo;
import com.tel306.lab2.entidades.Trabajo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ListaTrabajosActivity extends AppCompatActivity {

    private String apiKey;
    private Departamento[] listaDepartamentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_trabajos);
        setTitle("Trabajos");

        getApiKey(new VolleyCallBack() {
            @Override
            public void onSuccess() {

                getListaDepartamentos(new VolleyCallBack() {
                    @Override
                    public void onSuccess() {
                        getListaTrabajos();
                    }
                });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.itemChangeList:
                startActivity(new Intent(this, ListaEmpleadosActivity.class));
                finish();
                break;
            case R.id.itemAdd:
                Intent intent = new Intent(this, CrearEditarTrabajoActivity.class);
                intent.putExtra("action", "new");
                intent.putExtra("apikey", apiKey);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void getApiKey(final VolleyCallBack callBack) {
        if (isInternetAvailable(this)) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String url = "http://ec2-54-165-73-192.compute-1.amazonaws.com:9000/getApiKey?groupKey=HTUxbtfKpEb2GJ3Y2d9e";
            StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        apiKey = jsonObject.getString("api-key");
                        callBack.onSuccess();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Api-key", error.getLocalizedMessage());
                }
            });
            requestQueue.add(stringRequest);
        }

    }

    public void getListaTrabajos() {
        if (isInternetAvailable(this)) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String url = "http://ec2-54-165-73-192.compute-1.amazonaws.com:9000/listar/trabajos";
            StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("ListaTrabajos", response);
                    Gson gson = new Gson();
                    DtoTrabajo dtoTrabajo = gson.fromJson(response, DtoTrabajo.class);
                    final Trabajo[] listaTrabajos = dtoTrabajo.getTrabajos();

                    ListaTrabajosAdapter listaTrabajosAdapter = new ListaTrabajosAdapter(listaTrabajos, listaDepartamentos, ListaTrabajosActivity.this, new ClickListener() {
                        @Override
                        public void onPositionClicked(boolean action, int position) {

                            if (listaTrabajos[position].getCreatedBy() != null) { //Fue creado por nosotros
                                if (action) { //ELIMINAR

                                } else { //EDITAR
                                    Intent intent = new Intent(ListaTrabajosActivity.this, CrearEditarTrabajoActivity.class);
                                    intent.putExtra("action", "edit");
                                    intent.putExtra("apikey", apiKey);
                                    intent.putExtra("trabajo", listaTrabajos[position]);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(ListaTrabajosActivity.this, "No se pueden hacer modificaciones en un trabajo por defecto", Toast.LENGTH_SHORT).show();
                                //Mostrar DIALOG que indique que no se pueden hacer modificaciones porque no lo creamos nosotros
                            }


                        }

                        @Override
                        public void onLongClicked(int position) {

                        }
                    });
                    RecyclerView recyclerView = findViewById(R.id.recyclerViewListaTrabajos);
                    recyclerView.setAdapter(listaTrabajosAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ListaTrabajosActivity.this));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ListaTrabajos", error.getLocalizedMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("api-key", apiKey);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    public void getListaDepartamentos(final VolleyCallBack callBack) {
        if (isInternetAvailable(this)) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String url = "http://ec2-54-165-73-192.compute-1.amazonaws.com:9000/listar/departamentos";
            StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("ListaDepartamentos", response);
                    Gson gson = new Gson();
                    DtoDepartamento dtoDepartamento = gson.fromJson(response, DtoDepartamento.class);
                    listaDepartamentos = dtoDepartamento.getDepartamentos();
                    callBack.onSuccess();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ListaTrabajos", error.getLocalizedMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("api-key", apiKey);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}
