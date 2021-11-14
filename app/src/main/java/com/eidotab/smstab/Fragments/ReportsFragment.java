package com.eidotab.smstab.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.eidotab.smstab.Fragments.SubFragments.SlideFragment;
import com.eidotab.smstab.Model.ScrollableViewPager;
import com.eidotab.smstab.R;

import java.util.ArrayList;


public class ReportsFragment extends Fragment  {


    public static ReportsFragment newInstance()
    {


        return new ReportsFragment();
    }

    RadioButton rventas;
    RadioButton rmesas;
    RadioButton rplatos;
    RadioButton rmeseros;

    ScrollableViewPager pager;

    String ttit;
    String ttot;


    ArrayList<Fragment> list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_reports, container, false);

        pager    = v.findViewById(R.id.subfra);
        rventas  = v.findViewById(R.id.radioventas);
        rmesas   = v.findViewById(R.id.radiomesas);
        rplatos  = v.findViewById(R.id.radioplatos);
        rmeseros = v.findViewById(R.id.radiomeseros);

        list = new ArrayList<>();

        for (int i = 0; i < 4; i++)
        {
            switch (i)
            {
                case 0:

                    ttit = "Total ventas: S/. ";
                    ttot = "";

                    list.add(SlideFragment.newInstance(ttit, ttot));

                    break;

                case 1:

                    ttit = "Total mesas ocupadas: ";
                    ttot = "";



                    list.add(SlideFragment.newInstance(ttit, ttot));

                    break;

                case 2:


                    ttit = "Total platos vendidos: ";
                    ttot = "";

                    list.add(SlideFragment.newInstance(ttit, ttot));

                    break;

                case 3:

                    ttit = "QUE VA ACA: ";
                    ttot = "";


                    list.add(SlideFragment.newInstance(ttit, ttot));

                    break;
            }



        }

        pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager())
        {

            @Override
            public Fragment getItem(int i) {

                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        pager.setCanScroll(false);
        pager.setOffscreenPageLimit(4);


        rventas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    pager.setCurrentItem(0,false);
                    pager.setVisibility(View.VISIBLE);

                }

            }
        });

        rmesas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    pager.setCurrentItem(1,false);
                    pager.setVisibility(View.VISIBLE);
                }

            }
        });

        rplatos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    pager.setCurrentItem(2,false);
                    pager.setVisibility(View.VISIBLE);
                }

            }
        });

        rmeseros.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    pager.setCurrentItem(3,false);
                    pager.setVisibility(View.VISIBLE);
                }

            }
        });

        return v;
    }
}
