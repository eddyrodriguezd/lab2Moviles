package com.tel306.lab2.entidades;

public class DtoDepartamento {
    private String estado;
    private int cuota;
    private Departamento[] departamentos;

    public DtoDepartamento() {
    }

    public DtoDepartamento(String estado, int cuota, Departamento[] departamentos) {
        this.estado = estado;
        this.cuota = cuota;
        this.departamentos = departamentos;
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

    public Departamento[] getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(Departamento[] departamentos) {
        this.departamentos = departamentos;
    }
}
