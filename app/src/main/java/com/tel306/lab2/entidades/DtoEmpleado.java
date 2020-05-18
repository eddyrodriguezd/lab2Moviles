package com.tel306.lab2.entidades;

public class DtoEmpleado {
    private String estado;
    private int cuota;
    private Empleado[] empleados;

    public DtoEmpleado(){
    }

    public DtoEmpleado(String estado, int cuota, Empleado[] empleados) {
        this.setEstado(estado);
        this.setCuota(cuota);
        this.setEmpleados(empleados);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCuota() {
        return cuota;
    }

    public void setCuota(int cuota) {
        this.cuota = cuota;
    }

    public Empleado[] getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleado[] empleados) {
        this.empleados = empleados;
    }
}
