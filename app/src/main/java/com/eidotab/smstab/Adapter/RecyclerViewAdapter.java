package com.eidotab.smstab.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eidotab.smstab.Model.Datamesa;
import com.eidotab.smstab.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.mHolderViewHolder>{

    private ArrayList<Datamesa> mesas;
    private Context context;

    public RecyclerViewAdapter(Context context,ArrayList<Datamesa> mesas){

        this.mesas = mesas;

        this.context = context;
    }


    @Override
    public RecyclerViewAdapter.mHolderViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        return new RecyclerViewAdapter.mHolderViewHolder(LayoutInflater.from(context).inflate(R.layout.grow,parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.mHolderViewHolder holder, int position){

        Datamesa mesa = mesas.get(position);


        holder.mesaestado.setImageDrawable(null);


        if(mesa.getEstado().equals("activo"))
        {

            holder.mesaestado.setImageDrawable(context.getDrawable(R.drawable.mesap));

        }
        else
        {
            holder.mesaestado.setImageDrawable(context.getDrawable(R.drawable.mesaa));

        }

        if (mesa.getNromesa()<10)
        {
            holder.mesanro.setText("0" + mesa.getNromesa() + "");
        }
        else
        {
            holder.mesanro.setText(mesa.getNromesa() + "");
        }

        holder.mesanro.bringToFront();

    }

    @Override
    public int getItemCount(){

        return mesas.size();
    }


    public class mHolderViewHolder extends RecyclerView.ViewHolder{

        CircleImageView mesaestado;
        TextView mesanro;

        public mHolderViewHolder(View itemView){

            super(itemView);

            mesaestado = itemView.findViewById(R.id.mesaestado);
            mesanro = itemView.findViewById(R.id.mesanro);

        }
    }


}