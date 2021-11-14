
package com.eidotab.smstab.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;


public class Historico implements Parcelable, Comparable<Historico>
{


    private String _id;
    private String mesa;
    private String nro_comanda;
    private String estado_orden;
    private String estado_pcuenta;
    private String estado_pago;
    private ArrayList<Itemorder> orden;
    private Date fechaorden;
    private Integer ipb;


    protected Historico(Parcel in) {
        _id = in.readString();
        mesa = in.readString();
        nro_comanda = in.readString();
        estado_orden = in.readString();
        estado_pcuenta = in.readString();
        estado_pago = in.readString();
    }

    public static final Creator<Historico> CREATOR = new Creator<Historico>() {
        @Override
        public Historico createFromParcel(Parcel in) {
            return new Historico(in);
        }

        @Override
        public Historico[] newArray(int size) {
            return new Historico[size];
        }
    };

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getNro_comanda() {
        return nro_comanda;
    }

    public void setNro_comanda(String nro_comanda) {
        this.nro_comanda = nro_comanda;
    }

    public String getEstado_orden() {
        return estado_orden;
    }

    public void setEstado_orden(String estado_orden) {
        this.estado_orden = estado_orden;
    }

    public String getEstado_pcuenta() {
        return estado_pcuenta;
    }

    public void setEstado_pcuenta(String estado_pcuenta) {
        this.estado_pcuenta = estado_pcuenta;
    }

    public String getEstado_pago() {
        return estado_pago;
    }

    public void setEstado_pago(String estado_pago) {
        this.estado_pago = estado_pago;
    }

    public Date getFechaorden() {
        return fechaorden;
    }

    public void setFechaorden(Date fechaorden) {
        this.fechaorden = fechaorden;
    }

    @Override
    public int compareTo(Historico other) {
        return fechaorden.compareTo(other.fechaorden);
    }

    public ArrayList<Itemorder> getOrden() {
        return orden;
    }

    public void setOrden(ArrayList<Itemorder> orden) {
        this.orden = orden;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getIpb() {
        return ipb;
    }

    public void setIpb(Integer ipb) {
        this.ipb = ipb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(mesa);
        parcel.writeString(nro_comanda);
        parcel.writeString(estado_orden);
        parcel.writeString(estado_pcuenta);
        parcel.writeString(estado_pago);
    }


}