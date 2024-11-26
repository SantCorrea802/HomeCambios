/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Beans;

/**
 *
 * @author HP
 */
public class Usuario extends Persona {
    private String pass;
    private String correo;
    private int rol;
    private double creditoMxn;
    private double creditoUsd;
    private double creditoCop;
    private double creditoEur;
    private double creditoJpy;

    public Usuario(String pass, String correo, int rol, double creditoMxn, double creditoUsd, double creditoCop, double creditoEur, double creditoJpy, String nombre, int edad, int id) {
        super(nombre, edad, id);
        this.pass = pass;
        this.correo = correo;
        this.rol = rol;
        this.creditoMxn = creditoMxn;
        this.creditoUsd = creditoUsd;
        this.creditoCop = creditoCop;
        this.creditoEur = creditoEur;
        this.creditoJpy = creditoJpy;
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

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public double getCreditoMxn() {
        return creditoMxn;
    }

    public void setCreditoMxn(double creditoMxn) {
        this.creditoMxn = creditoMxn;
    }

    public double getCreditoUsd() {
        return creditoUsd;
    }

    public void setCreditoUsd(double creditoUsd) {
        this.creditoUsd = creditoUsd;
    }

    public double getCreditoCop() {
        return creditoCop;
    }

    public void setCreditoCop(double creditoCop) {
        this.creditoCop = creditoCop;
    }

    public double getCreditoEur() {
        return creditoEur;
    }

    public void setCreditoEur(double creditoEur) {
        this.creditoEur = creditoEur;
    }

    public double getCreditoJpy() {
        return creditoJpy;
    }

    public void setCreditoJpy(double creditoJpy) {
        this.creditoJpy = creditoJpy;
    }

}
