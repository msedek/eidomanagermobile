package com.eidotab.smstab.Model;


import java.util.ArrayList;

public class platoItem {
    private ArrayList<Pladesg> nPlato;
    private int frecuplat = 0;
    private String histID;
    private double price;



    public int getFrecuplat() {
        return frecuplat;
    }

    public void setFrecuplat(int frecuplat) {
        this.frecuplat = frecuplat;
    }

    public String getHistID() {
        return histID;
    }

    public void setHistID(String histID) {
        this.histID = histID;
    }


    public ArrayList<Pladesg> getnPlato() {
        return nPlato;
    }

    public void setnPlato(ArrayList<Pladesg> nPlato) {
        this.nPlato = nPlato;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
