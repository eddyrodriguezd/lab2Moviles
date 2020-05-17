package com.tel306.lab2.entidades;

public class Empleado {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Trabajo jobId;
    private double salary;
    private double commissionPct;
    private Gerente managerId;
    private Departamento departmentId;
    private String createdBy;

    public Empleado() {
    }

    public Empleado(String employeeId, String firstName, String lastName, String email,
                    String phoneNumber, Trabajo jobId, double salary, double commissionPct,
                    Gerente managerId, Departamento departmentId, String createdBy) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.jobId = jobId;
        this.salary = salary;
        this.commissionPct = commissionPct;
        this.managerId = managerId;
        this.departmentId = departmentId;
        this.createdBy = createdBy;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Trabajo getJobId() {
        return jobId;
    }

    public void setJobId(Trabajo jobId) {
        this.jobId = jobId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getCommissionPct() {
        return commissionPct;
    }

    public void setCommissionPct(double commissionPct) {
        this.commissionPct = commissionPct;
    }

    public Gerente getManagerId() {
        return managerId;
    }

    public void setManagerId(Gerente managerId) {
        this.managerId = managerId;
    }

    public Departamento getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Departamento departmentId) {
        this.departmentId = departmentId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
