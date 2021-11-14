package com.eidotab.smstab.Model;


import java.util.Comparator;

public class TimeSorter2 implements Comparator<platoStats>
{

    public int compare(platoStats one, platoStats another){
    return one.getDate().compareTo(another.getDate());
}


}