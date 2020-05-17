package com.tel306.lab2.entidades;

public class Departamento {
    private int departmentId;
    private String departmentName;
    private String departmentShortName;

    public Departamento() {
    }

    public Departamento(int departmentId, String departmentName, String departmentShortName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentShortName = departmentShortName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentShortName() {
        return departmentShortName;
    }

    public void setDepartmentShortName(String departmentShortName) {
        this.departmentShortName = departmentShortName;
    }
}