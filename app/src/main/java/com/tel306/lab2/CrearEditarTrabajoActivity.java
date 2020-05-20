package com.tel306.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tel306.lab2.entidades.Departamento;
import com.tel306.lab2.entidades.DtoDepartamento;
import com.tel306.lab2.entidades.Trabajo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tel306.lab2.Util.isInternetAvailable;

public class CrearEditarTrabajoActivity extends AppCompatActivity {

    private EditText editTextTrabajoNombre;
    private EditText editTextTrabajoSalarioMin;
    private EditText editTextTrabajoSalarioMax;
    private EditText editTextTrabajoAbreviacion;

    private Spinner sItems;
    private Button buttonAceptar;
    private Button buttonCancelar;

    private String action;
    private String apiKey;
    private Trabajo trabajo;

    private Departamento[] listaDepartamentos;
    private List<String> spinnerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_trabajo);

        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        apiKey = intent.getStringExtra("apikey");

        spinnerArray = new ArrayList<String>();

        editTextTrabajoNombre = findViewById(R.id.editTextTrabajoNombre);
        editTextTrabajoSalarioMin = findViewById(R.id.editTextTrabajoSalarioMin);
        editTextTrabajoSalarioMax = findViewById(R.id.editTextTrabajoSalarioMax);
        editTextTrabajoAbreviacion = findViewById(R.id.editTextTrabajoAbreviacion);
        sItems = findViewById(R.id.spinnerDepartamento);

        if (action.equals("edit")) {
            setTitle("Editar Trabajo");

            trabajo = (Trabajo) intent.getSerializableExtra("trabajo");
            editTextTrabajoNombre.setText(trabajo.getJobTitle());
            editTextTrabajoSalarioMin.setText(String.valueOf(trabajo.getMinSalary()));
            editTextTrabajoSalarioMax.setText(String.valueOf(trabajo.getMaxSalary()));
            editTextTrabajoAbreviacion.setText(String.valueOf(trabajo.getJobId()));

            findViewById(R.id.txtNuevoTrabajoDepartamento).setVisibility(View.INVISIBLE);
            findViewById(R.id.txtNuevoTrabajoAbreviacion).setVisibility(View.INVISIBLE);
            sItems.setVisibility(View.INVISIBLE);
            editTextTrabajoAbreviacion.setVisibility(View.INVISIBLE);

           getListaDepartamentos(new VolleyCallBack() {
                @Override
                public void onSuccess() {
                    for (int i = 0; i < listaDepartamentos.length; i++) {
                        spinnerArray.add(listaDepartamentos[i].getDepartmentName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CrearEditarTrabajoActivity.this,
                            android.R.layout.simple_spinner_item, spinnerArray);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sItems.setAdapter(adapter);
                }
            });

        } else if (action.equals("new")) {
            setTitle("Nuevo Trabajo");


            getListaDepartamentos(new VolleyCallBack() {
                @Override
                public void onSuccess() {
                    for (int i = 0; i < listaDepartamentos.length; i++) {
                        spinnerArray.add(listaDepartamentos[i].getDepartmentName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CrearEditarTrabajoActivity.this,
                            android.R.layout.simple_spinner_item, spinnerArray);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sItems.setAdapter(adapter);
                }
            });
        }

        buttonAceptar = findViewById(R.id.buttonAceptar);
        buttonCancelar = findViewById(R.id.buttonCancelar);

        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regex = "^\\d+$";
                if (!editTextTrabajoNombre.getText().toString().isEmpty()
                        && !editTextTrabajoSalarioMin.getText().toString().isEmpty()
                        && !editTextTrabajoSalarioMax.getText().toString().isEmpty()
                        && editTextTrabajoSalarioMin.getText().toString().matches(regex)
                        && editTextTrabajoSalarioMax.getText().toString().matches(regex)
                    )
                {

                    if (action.equals("new")) {
                        if (editTextTrabajoAbreviacion.getText().toString().isEmpty()) {
                            return;
                        }
                    }

                    guardarActualizarTrabajo();
                }
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void guardarActualizarTrabajo() {
        if (isInternetAvailable(this)) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String url = "http://ec2-54-165-73-192.compute-1.amazonaws.com:9000/trabajo";
            StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Intent returnIntent  = new Intent();
                    setResult(CrearEditarTrabajoActivity.RESULT_OK, returnIntent);
                    Log.d("Crear", response);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Crear", error.getLocalizedMessage());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("api-key", apiKey);
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("jobTitle", editTextTrabajoNombre.getText().toString());
                    params.put("minSalary", editTextTrabajoSalarioMin.getText().toString());
                    params.put("maxSalary", editTextTrabajoSalarioMax.getText().toString());

                    if (action.equals("edit")) {
                        params.put("update", "true");
                        params.put("jobId", trabajo.getJobId());
                    } else if (action.equals("new")) {
                        params.put("jobId", listaDepartamentos[sItems.getSelectedItemPosition()].getDepartmentShortName() + "_" + editTextTrabajoAbreviacion.getText().toString());
                    }
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    public void getListaDepartamentos(final VolleyCallBack callBack) {
        if (isInternetAvailable(this)) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            Log.d("Crear", "Apikey: " + apiKey);

            String url = "http://ec2-54-165-73-192.compute-1.amazonaws.com:9000/listar/departamentos";
            StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Crear", response);
                    Gson gson = new Gson();
                    DtoDepartamento dtoDepartamento = gson.fromJson(response, DtoDepartamento.class);
                    listaDepartamentos = dtoDepartamento.getDepartamentos();
                    callBack.onSuccess();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Crear", error.getLocalizedMessage());
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

