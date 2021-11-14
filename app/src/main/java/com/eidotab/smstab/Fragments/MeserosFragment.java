package com.eidotab.smstab.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eidotab.smstab.Adapter.myAdapter;
import com.eidotab.smstab.Interfaz.IRequestMensaje;
import com.eidotab.smstab.Model.Empleado;
import com.eidotab.smstab.R;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MeserosFragment extends Fragment {




    RecyclerView mRecyclerView;

    myAdapter adp;
    Context context;

    ArrayList<Empleado> employ;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_meseros, container, false);

        context = getContext();
        mRecyclerView = v.findViewById(R.id.mRecyclerViewemp);
        employ = new ArrayList<>();


        setAdapter();
        loadRetrofitEmpleado();
        loadRetrofitEmpleado();



        return v;

    }

    private void loadRetrofitEmpleado()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequestMensaje request = retrofit.create(IRequestMensaje.class);

        Call<ArrayList<Empleado>> call = request.getJSONEmpleados();

        call.enqueue(new Callback<ArrayList<Empleado>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Empleado>> call, @NonNull Response<ArrayList<Empleado>> response)
            {


                ArrayList<Empleado> list = new ArrayList<>(Objects.requireNonNull(response.body()));

                if (employ.size()> 0)
                {
                    list.removeAll(employ);

                    for(Empleado filtrado : list)
                    {
                        employ.add(filtrado);
                        adp.notifyDataSetChanged();
                    }

                }
                else
                {
                    employ.add(list.get(0));
                    adp.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Empleado>> call, @NonNull Throwable t)
            {


            }
        });
    }

    private void setAdapter()
    {
        adp =  new myAdapter(context, employ);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(adp);

    }

    public static MeserosFragment newInstance()
    {

        return new MeserosFragment();
    }



}
