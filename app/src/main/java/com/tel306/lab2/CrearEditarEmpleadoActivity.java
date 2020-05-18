package com.tel306.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.tel306.lab2.entidades.DtoTrabajo;
import com.tel306.lab2.entidades.Gerente;
import com.tel306.lab2.entidades.Trabajo;
import com.tel306.lab2.entidades.Empleado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tel306.lab2.Util.isInternetAvailable;

public class CrearEditarEmpleadoActivity extends AppCompatActivity {

    private EditText editTextEmpleadoNombre;
    private EditText editTextEmpleadoApellido;
    private EditText editTextEmpleadoEmail;
    private EditText editTextEmpleadoNumero;
    private EditText editTextEmpleadoSalario;
    private EditText editTextEmpleadoComision;
    private TextView txtNuevoEmpleadoTrabajo;
    private TextView txtNuevoEmpleadoJefe;
    private TextView txtNuevoEmpleadoDepartamento;

    private Spinner sItemsT, sItemsG, sItemsD;
    private Button buttonAceptar;
    private Button buttonCancelar;

    private String action;
    private String apiKey;
    private Empleado empleado;

    private Empleado[] listaEmpleados;
    private Departamento[] listaDepartamentos;
    private Trabajo[] listaTrabajos;
    private Gerente[] listaJefes;
    private List<String> spinnerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_empleado);

        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        apiKey = intent.getStringExtra("apikey");

        spinnerArray = new ArrayList<String>();

        editTextEmpleadoNombre = findViewById(R.id.editTextEmpleadoNombre);
        editTextEmpleadoApellido = findViewById(R.id.editTextEmpleadoApellido);
        editTextEmpleadoEmail = findViewById(R.id.editTextEmpleadoEmail);
        editTextEmpleadoNumero = findViewById(R.id.editTextEmpleadoNumero);
        editTextEmpleadoSalario = findViewById(R.id.editTextEmpleadoSalario);
        editTextEmpleadoComision = findViewById(R.id.editTextEmpleadoComision);
        sItemsT = findViewById(R.id.spinnerTrabajo);
        sItemsG = findViewById(R.id.spinnerJefe);
        sItemsD = findViewById(R.id.spinnerDepartamento);

        if(action.equals("edit")){
            setTitle("Editar Empleado");

            empleado = (Empleado) intent.getSerializableExtra("empleado");
            editTextEmpleadoNombre.setText(empleado.getFirstName());
            editTextEmpleadoApellido.setText(empleado.getLastName());
            editTextEmpleadoEmail.setText(empleado.getEmail());
            editTextEmpleadoNumero.setText(empleado.getPhoneNumber());
            sItemsT.setVisibility(View.INVISIBLE);
            editTextEmpleadoSalario.setText(String.valueOf(empleado.getSalary()));
            editTextEmpleadoComision.setText(String.valueOf(empleado.getCommissionPct()));
            sItemsG.setVisibility(View.INVISIBLE);
            sItemsD.setVisibility(View.INVISIBLE);
            txtNuevoEmpleadoTrabajo.setVisibility(View.INVISIBLE);
            txtNuevoEmpleadoJefe.setVisibility(View.INVISIBLE);
            txtNuevoEmpleadoDepartamento.setVisibility(View.INVISIBLE);
        }
        else if (action.equals("new")){
            setTitle("Nuevo Empleado");
            getListaDepartamentos(new VolleyCallBack() {
                @Override
                public void onSuccess() {
                    Log.d("Crear", "Terminó la búsqueda de departamentos");
                    for(int i=0; i<listaDepartamentos.length; i++){
                        Log.d("Crear", "Uno más al spinner: " + listaDepartamentos[i].getDepartmentName());
                        spinnerArray.add(listaDepartamentos[i].getDepartmentName());
                    }
                    Log.d("Crear", "Acabó el for para poblar el spinner");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CrearEditarEmpleadoActivity.this,
                            android.R.layout.simple_spinner_item, spinnerArray);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sItemsD.setAdapter(adapter);
                }
            });
        }

        buttonAceptar = findViewById(R.id.buttonAceptar);
        buttonCancelar = findViewById(R.id.buttonCancelar);

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void guardarActualizarEmpleado(){
        if(isInternetAvailable(this)){
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            String url = "http://ec2-54-165-73-192.compute-1.amazonaws.com:9000/empleado";
            StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
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
                protected Map<String, String> getParams() throws AuthFailureError{
                    Map<String, String> params = new HashMap<>();
                    params.put("firstName",editTextEmpleadoNombre.getText().toString());
                    params.put("lastName",editTextEmpleadoApellido.getText().toString());
                    params.put("email",editTextEmpleadoEmail.getText().toString());
                    params.put("phoneNumber",editTextEmpleadoNumero.getText().toString());
                    params.put("salary",editTextEmpleadoSalario.getText().toString());
                    params.put("commissionPct",editTextEmpleadoComision.getText().toString());
                    if (action.equals("edit")){
                        params.put("update", "true");
                    }
                    else if (action.equals("new")){
                        params.put("employeeId", String.valueOf(listaDepartamentos[sItemsD.getSelectedItemPosition()]));
                    }
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }

    public void getListaDepartamentos(final VolleyCallBack callBack){
        if (isInternetAvailable(this)){
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

    public void getListaTrabajos(final VolleyCallBack callBack){
        if (isInternetAvailable(this)){
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            Log.d("Crear", "Apikey: " + apiKey);

            String url = "http://ec2-54-165-73-192.compute-1.amazonaws.com:9000/listar/trabajos";
            StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Crear", response);
                    Gson gson = new Gson();
                    DtoTrabajo dtoTrabajo = gson.fromJson(response, DtoTrabajo.class);
                    listaTrabajos = dtoTrabajo.getTrabajos();
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
