package com.tel306.lab2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.tel306.lab2.Util.isInternetAvailable;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
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
import com.tel306.lab2.adapters.ListaEmpleadosAdapter;
import com.tel306.lab2.entidades.DtoEmpleado;
import com.tel306.lab2.entidades.Empleado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class ListaEmpleadosActivity extends AppCompatActivity {

    private String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_empleados);
        setTitle("Empleados");


        getApiKey(new VolleyCallBack() {
            @Override
            public void onSuccess() {
                getListaEmpleados();
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
                startActivity(new Intent(this, ListaTrabajosActivity.class));
                finish();
                break;
            case R.id.itemAdd:
                Intent intent = new Intent(this, CrearEditarEmpleadoActivity.class);
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

    public void getListaEmpleados() {
        if (isInternetAvailable(this)) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String url = "http://ec2-54-165-73-192.compute-1.amazonaws.com:9000/listar/empleados";
            StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Revisar esta parte si falta algo
                    Log.d("ListaEmpleados", response);
                    Gson gson = new Gson();
                    DtoEmpleado dtoEmpleado = gson.fromJson(response, DtoEmpleado.class);
                    final Empleado[] listaEmpleados = dtoEmpleado.getEmpleados();

                    ListaEmpleadosAdapter listaEmpleadosAdapter = new ListaEmpleadosAdapter(listaEmpleados, ListaEmpleadosActivity.this, new ClickListener() {

                        @Override
                        public void onPositionClicked(boolean action, int position) {

                            if (listaEmpleados[position].getCreatedBy() != null) { //Fue creado por nosotros
                                if (action) { // TODO  DIALOG ELIMINAR

                                    borrarEmpleado();


                                } else { //EDITAR
                                    Intent intent = new Intent(ListaEmpleadosActivity.this, CrearEditarEmpleadoActivity.class);
                                    intent.putExtra("action", "edit");
                                    intent.putExtra("apikey", apiKey);
                                    intent.putExtra("empleado", listaEmpleados[position]);
                                    startActivity(intent);
                                }
                            } else {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ListaEmpleadosActivity.this);
                                builder1.setMessage("No se puede modificar ni eliminar empleados no creados por el usuario");
                                builder1.setCancelable(true);
                                builder1.setNeutralButton(android.R.string.ok,
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }


                        }

                        @Override
                        public void onLongClicked(int position) {

                        }


                    }
                            //finish click listener


                    );
                    RecyclerView recyclerView = findViewById(R.id.recyclerViewListaEmpleados);
                    recyclerView.setAdapter(listaEmpleadosAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ListaEmpleadosActivity.this));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ListaEmpleados", error.getLocalizedMessage());
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


    public void borrarEmpleado() {
        if (isInternetAvailable(this)) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String url = "http://ec2-54-165-73-192.compute-1.amazonaws.com:9000/borrar/empleado";
            StringRequest stringRequest = new StringRequest(StringRequest.Method.DELETE, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response);
                        String estado = jsonObject.getString("estado");
                        Toast.makeText(ListaEmpleadosActivity.this, estado, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Api-key", error.getLocalizedMessage());
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
