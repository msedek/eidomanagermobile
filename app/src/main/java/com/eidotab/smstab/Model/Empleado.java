package com.eidotab.smstab.Model;


import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Empleado implements Serializable, Comparable<Empleado>{

    private String _id;
    private String nombre_empleado;
    private String apellido_empleado;
    private String dni;
    private String turno;
    private ArrayList<String> mesas_asignadas;
    private Date fecha_turno;
    private Integer mesas_turno;
    private Integer platos_turno;
    private Integer bebidas_turno;
    private Boolean activo;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNombre_empleado() {
        return nombre_empleado;
    }

    public void setNombre_empleado(String nombre_empleado) {
        this.nombre_empleado = nombre_empleado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public ArrayList<String> getMesas_asignadas() {
        return mesas_asignadas;
    }

    public void setMesas_asignadas(ArrayList<String> mesas_asignadas) {
        this.mesas_asignadas = mesas_asignadas;
    }

    public Date getFecha_turno() {
        return fecha_turno;
    }

    public void setFecha_turno(Date fecha_turno) {
        this.fecha_turno = fecha_turno;
    }

    public Integer getMesas_dia() {
        return mesas_turno;
    }

    public void setMesas_dia(Integer mesas_dia) {
        this.mesas_turno = mesas_dia;
    }

    public Integer getPlatos_turno() {
        return platos_turno;
    }

    public void setPlatos_turno(Integer platos_turno) {
        this.platos_turno = platos_turno;
    }

    public Integer getBebidas_turno() {
        return bebidas_turno;
    }

    public void setBebidas_turno(Integer bebidas_turno) {
        this.bebidas_turno = bebidas_turno;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @Override
    public int compareTo(@NonNull Empleado other) {
        return _id.compareTo(other._id);
    }

    @Override
    public boolean equals(Object anObject) {
        if (!(anObject instanceof Empleado)) {
            return false;
        }
        Empleado otherDataroot = (Empleado) anObject;
        return otherDataroot.get_id().equals(get_id());
    }

    public String getApellido_empleado() {
        return apellido_empleado;
    }

    public void setApellido_empleado(String apellido_empleado) {
        this.apellido_empleado = apellido_empleado;
    }
}
