package com.tel306.lab2.entidades;

public class DtoTrabajo {
    private String estado;
    private int cuota;
    private Trabajo[] trabajos;

    public DtoTrabajo() {
    }

    public DtoTrabajo(String estado, int cuota, Trabajo[] trabajos) {
        this.estado = estado;
        this.cuota = cuota;
        this.trabajos = trabajos;
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

    public Trabajo[] getTrabajos() {
        return trabajos;
    }

    public void setTrabajos(Trabajo[] trabajos) {
        this.trabajos = trabajos;
    }
}
