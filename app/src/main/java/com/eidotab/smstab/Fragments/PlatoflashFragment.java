package com.eidotab.smstab.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eidotab.smstab.R;


public class PlatoflashFragment extends Fragment {


    public static PlatoflashFragment newInstance()
    {

        return new PlatoflashFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_platoflash, container, false);
    }


}
