package com.eidotab.smstab.Model;


import java.util.Comparator;

public class TimeSorter implements Comparator<Historico>
{

    public int compare(Historico one, Historico another){
    return one.getFechaorden().compareTo(another.getFechaorden());
}


}