package com.eidotab.smstab.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eidotab.smstab.R;


public class EstadisticasFragment extends Fragment {



    public static EstadisticasFragment newInstance()
    {
        EstadisticasFragment fragment = new EstadisticasFragment();

        return fragment;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_estadisticas, container, false);
    }




}
