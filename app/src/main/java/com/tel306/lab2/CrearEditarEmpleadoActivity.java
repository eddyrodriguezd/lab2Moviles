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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tel306.lab2.entidades.Departamento;
import com.tel306.lab2.entidades.DtoDepartamento;
import com.tel306.lab2.entidades.DtoEmpleado;
import com.tel306.lab2.entidades.DtoTrabajo;
import com.tel306.lab2.entidades.Empleado;
import com.tel306.lab2.entidades.Trabajo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tel306.lab2.Util.isInternetAvailable;

public class CrearEditarEmpleadoActivity extends AppCompatActivity {

    private String action;
    private String apiKey;

    private Departamento[] listaDepartamentos;
    private Empleado[] listaEmpleados;
    private Trabajo[] listaTrabajos;


    private List<String> spinnerArray1;
    private List<String> spinnerArray2;
    private List<String> spinnerArray3;

    private Button EmpleadoButtonAceptar;
    private Button EmpleadoButtonCancelar;

    private EditText editTextEmpleadoId;
    private EditText editTextEmpleadoNombre;
    private EditText editTextEmpleadoApellido;
    private EditText editTextEmpleadoCorreo;
    private EditText editTextEmpleadoNumero;
    private EditText editTextEmpleadoSalario;
    private EditText editTextEmpleadoComision;
    private Spinner spinnerEmpleadoDepartamento;
    private Spinner spinnerEmpleadoTrabajo;
    private Spinner spinnerEmpleadoJefe;


    private Empleado empleado;

    private int aux1;
    private int aux2;
    private int aux3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_empleado);

        Intent intent = getIntent();
        action = intent.getStringExtra("action");
        apiKey = intent.getStringExtra("apikey");

        spinnerArray1 = new ArrayList<String>();
        spinnerArray2 = new ArrayList<String>();
        spinnerArray3 = new ArrayList<String>();


        editTextEmpleadoId = findViewById(R.id.editTextEmpleadoId);
        editTextEmpleadoNombre = findViewById(R.id.editTextEmpleadoNombre);
        editTextEmpleadoApellido = findViewById(R.id.editTextEmpleadoApellido);
        editTextEmpleadoCorreo = findViewById(R.id.editTextEmpleadoCorreo);
        editTextEmpleadoNumero = findViewById(R.id.editTextEmpleadoNumero);
        editTextEmpleadoSalario = findViewById(R.id.editTextEmpleadoSalario);
        editTextEmpleadoComision = findViewById(R.id.editTextEmpleadoComision);
        spinnerEmpleadoDepartamento = findViewById(R.id.spinnerEmpleadoDepartamento);
        spinnerEmpleadoJefe = findViewById(R.id.spinnerEmpleadoJefe);
        spinnerEmpleadoTrabajo = findViewById(R.id.spinnerEmpleadoTrabajo);


        if (action.equals("edit")) {
            setTitle("Editar Empleado");
            empleado = (Empleado) intent.getSerializableExtra("empleado");
            editTextEmpleadoId.setText(empleado.getEmployeeId());
            editTextEmpleadoNombre.setText(empleado.getFirstName());
            editTextEmpleadoApellido.setText(empleado.getLastName());
            editTextEmpleadoCorreo.setText(empleado.getEmail());
            editTextEmpleadoNumero.setText(empleado.getPhoneNumber());
            editTextEmpleadoSalario.setText(String.valueOf(empleado.getSalary()));
            editTextEmpleadoComision.setText(String.valueOf(empleado.getCommissionPct()));


            // EMPIEZA Listar Spinners

            getListaDepartamentos(new VolleyCallBack() {
                @Override
                public void onSuccess() {
                    for (int i = 0; i < listaDepartamentos.length; i++) {
                        spinnerArray1.add(listaDepartamentos[i].getDepartmentName());
                        if (listaDepartamentos[i].getDepartmentId() == empleado.getDepartmentId().getDepartmentId()) {
                            aux1 = i;
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CrearEditarEmpleadoActivity.this,
                            android.R.layout.simple_spinner_item, spinnerArray1);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEmpleadoDepartamento.setAdapter(adapter);
                    spinnerEmpleadoDepartamento.setSelection(aux1);
                }
            });

            //Listar Spinner 2

            getListaEmpleados(new VolleyCallBack() {
                @Override
                public void onSuccess() {
                    for (int i = 0; i < listaEmpleados.length; i++) {
                        spinnerArray2.add(listaEmpleados[i].getFirstName() + ' ' + listaEmpleados[i].getLastName());
                        if (listaEmpleados[i].getEmployeeId().equalsIgnoreCase(empleado.getEmployeeId())) {
                            aux2 = i;
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CrearEditarEmpleadoActivity.this,
                            android.R.layout.simple_spinner_item, spinnerArray2);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEmpleadoJefe.setAdapter(adapter);
                    spinnerEmpleadoJefe.setSelection(aux2);
                }
            });

            //Listar Spinner 3
            getListaTrabajos(new VolleyCallBack() {
                @Override
                public void onSuccess() {
                    for (int i = 0; i < listaTrabajos.length; i++) {
                        spinnerArray3.add(listaTrabajos[i].getJobTitle());
                        if (listaTrabajos[i].getJobId().equalsIgnoreCase(empleado.getJobId().getJobId())) {
                            aux3 = i;
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CrearEditarEmpleadoActivity.this,
                            android.R.layout.simple_spinner_item, spinnerArray3);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEmpleadoTrabajo.setAdapter(adapter);
                    spinnerEmpleadoTrabajo.setSelection(aux1);
                }
            });
            //termina de lista spinners


        } else if (action.equals("new")) {

            setTitle("Nuevo Empleado");


            // EMPIEZA Listar Spinners

            getListaDepartamentos(new VolleyCallBack() {
                @Override
                public void onSuccess() {
                    for (int i = 0; i < listaDepartamentos.length; i++) {
                        spinnerArray1.add(listaDepartamentos[i].getDepartmentName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CrearEditarEmpleadoActivity.this,
                            android.R.layout.simple_spinner_item, spinnerArray1);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEmpleadoDepartamento.setAdapter(adapter);
                }
            });

            //Listar Spinner 2

            getListaEmpleados(new VolleyCallBack() {
                @Override
                public void onSuccess() {
                    for (int i = 0; i < listaEmpleados.length; i++) {
                        spinnerArray2.add(listaEmpleados[i].getFirstName() + ' ' + listaEmpleados[i].getLastName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CrearEditarEmpleadoActivity.this,
                            android.R.layout.simple_spinner_item, spinnerArray2);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEmpleadoJefe.setAdapter(adapter);
                }
            });

            //Listar Spinner 3
            getListaTrabajos(new VolleyCallBack() {
                @Override
                public void onSuccess() {
                    for (int i = 0; i < listaTrabajos.length; i++) {
                        spinnerArray3.add(listaTrabajos[i].getJobTitle());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(CrearEditarEmpleadoActivity.this,
                            android.R.layout.simple_spinner_item, spinnerArray3);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerEmpleadoTrabajo.setAdapter(adapter);
                }
            });
            //termina de lista spinners


        }
        //termina de listar empleados

        // TODO botones inicia
        EmpleadoButtonAceptar = findViewById(R.id.EmpleadoButtonAceptar);
        EmpleadoButtonCancelar = findViewById(R.id.EmpleadoButtonCancelar);

        EmpleadoButtonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Crear", "clicked");
                if (!editTextEmpleadoApellido.getText().toString().isEmpty() && !editTextEmpleadoCorreo.getText().toString().isEmpty()
                        && !listaDepartamentos[spinnerEmpleadoDepartamento.getSelectedItemPosition()].getDepartmentName().isEmpty()
                        && !listaEmpleados[spinnerEmpleadoJefe.getSelectedItemPosition()].getLastName().isEmpty()
                        && !listaTrabajos[spinnerEmpleadoTrabajo.getSelectedItemPosition()].getJobTitle().isEmpty()

                ) {

                    if (action.equals("new")) {
                        if (editTextEmpleadoId.getText().toString().isEmpty()) {
                            return;
                        }
                    }

                    guardarActualizarEmpleado();
                }
            }
        });

        EmpleadoButtonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//Terminan los botones

    }

        // TODO empieza logica guardar
        public void guardarActualizarEmpleado(){
            if (isInternetAvailable(this)) {
                RequestQueue requestQueue = Volley.newRequestQueue(this);

                String url = "http://ec2-54-165-73-192.compute-1.amazonaws.com:9000/empleado";
                StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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


                        params.put("firstName", editTextEmpleadoNombre.getText().toString());
                        params.put("lastName", editTextEmpleadoApellido.getText().toString());
                        params.put("email", editTextEmpleadoCorreo.getText().toString());
                        params.put("phoneNumber", editTextEmpleadoNumero.getText().toString());
                        params.put("salary", editTextEmpleadoSalario.getText().toString());
                        params.put("commissionPct", editTextEmpleadoComision.getText().toString());

                        if (action.equals("edit")) {
                            params.put("update", "true");
                            params.put("employeeId", empleado.getEmployeeId());
                        } else if (action.equals("new")) {
                            //TODO verificacion de no cambiar el ID de empleado. oops.
                            params.put("employeeId", editTextEmpleadoId.toString());
                        }
                        params.put("jobId", listaEmpleados[spinnerEmpleadoTrabajo.getSelectedItemPosition()].getJobId().getJobId());

                        params.put("managerId", listaEmpleados[spinnerEmpleadoJefe.getSelectedItemPosition()].getEmployeeId());

                        params.put("departmentId",String.valueOf(listaDepartamentos[spinnerEmpleadoDepartamento.getSelectedItemPosition()].getDepartmentId()));




                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        }

        //termina logica guardar


































    public void getListaEmpleados(final VolleyCallBack callBack) {
        if (isInternetAvailable(this)) {
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            Log.d("Crear", "Apikey: " + apiKey);

            String url = "http://ec2-54-165-73-192.compute-1.amazonaws.com:9000/listar/empleados";
            StringRequest stringRequest = new StringRequest(StringRequest.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("Crear", response);
                    Gson gson = new Gson();
                    DtoEmpleado dtoEmpleado = gson.fromJson(response, DtoEmpleado.class);
                    listaEmpleados = dtoEmpleado.getEmpleados();
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


    public void getListaTrabajos(final VolleyCallBack callBack) {
        if (isInternetAvailable(this)) {
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
