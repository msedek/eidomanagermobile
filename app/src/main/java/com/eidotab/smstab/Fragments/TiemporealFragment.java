package com.eidotab.smstab.Fragments;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eidotab.smstab.Adapter.RecyclerViewAdapter;
import com.eidotab.smstab.Model.Datamesa;
import com.eidotab.smstab.R;

import java.util.ArrayList;


public class TiemporealFragment extends Fragment {



    RecyclerView mRecyclerView;
    ArrayList<Datamesa> datames;
    RecyclerViewAdapter adp;
    Context context;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tiemporeal, container, false);

        context = getContext();
        mRecyclerView = v.findViewById(R.id.mRecyclerView);
        datames = new ArrayList<>();


        for (int i = 1; i < 91; i++) {

            Datamesa datamesa = new Datamesa();

            if (i % 2 == 0) {
                datamesa.setEstado("activo"); // even
            } else {
                datamesa.setEstado("inactivo");// odd
            }

            datamesa.setNromesa(i);

            datames.add(datamesa);

        }

        setAdapter();

        return v;

    }

    public static TiemporealFragment newInstance()
    {

        return new TiemporealFragment();

    }

    private void setAdapter()
    {
        adp = new RecyclerViewAdapter(context, datames);
        recyclerViewLayoutManager = new GridLayoutManager(context, 8);
        mRecyclerView.setLayoutManager(recyclerViewLayoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(25));
        mRecyclerView.setAdapter(adp);
    }

    private class SpacesItemDecoration extends RecyclerView.ItemDecoration
    {

        private int halfSpace;

        SpacesItemDecoration(int space) {
            this.halfSpace = space / 2;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if (parent.getPaddingLeft() != halfSpace) {
                parent.setPadding(halfSpace, halfSpace, halfSpace, halfSpace);
                parent.setClipToPadding(false);
            }

            outRect.top = halfSpace;
            outRect.bottom = halfSpace;
            outRect.left = halfSpace;
            outRect.right = halfSpace;
        }
    }

}
