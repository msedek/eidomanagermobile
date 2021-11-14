package com.eidotab.smstab.Model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class platoStats implements Serializable{


    private Date date;
    private int cantidad = 0;
    private platoItem platdat;
    private String nromesa;


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public platoItem getPlatdat() {
        return platdat;
    }

    public void setPlatdat(platoItem platdat) {
        this.platdat = platdat;
    }


    public String getNromesa() {
        return nromesa;
    }

    public void setNromesa(String nromesa) {
        this.nromesa = nromesa;
    }









}




