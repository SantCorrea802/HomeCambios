/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Beans;

/**
 *
 * @author HP
 */
public class Empleado extends Persona {
    private int rol;
    private String pass;
    private String correo;
    private String fechaInicio;
    private String cargo;

    public Empleado(int rol, String pass, String correo, String fechaInicio, String cargo, String nombre, int edad, int id) {
        super(nombre, edad, id);
        this.rol = rol;
        this.pass = pass;
        this.correo = correo;
        this.fechaInicio = fechaInicio;
        this.cargo = cargo;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}
