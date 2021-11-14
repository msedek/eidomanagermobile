package com.eidotab.smstab;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eidotab.smstab.Interfaz.IRequestMensaje;
import com.eidotab.smstab.Model.Menua;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuActivity extends AppCompatActivity
    {

    Button salir;
    Button amenu;
    EditText mproximo;
    TextView mactual;
    TextView idactual;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        salir    = findViewById(R.id.salir);
        amenu    = findViewById(R.id.amenu);
        mproximo = findViewById(R.id.mproximo);
        mactual  = findViewById(R.id.mactual);
        idactual = findViewById(R.id.idactual);

        loadretrofitmenu();

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        amenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(mproximo.getText().length() == 0)
                {
                    showmessage("Escribe un menu nuevo");
                }
                else
                {
                    String uno = mproximo.getText().toString();
                    String dos = mactual.getText().toString();

                    if (uno.equals(dos))
                    {
                        showmessage("Menu ya activo elija otro");
                    }
                    else
                    {
                        int compa = Integer.parseInt(mproximo.getText().toString());

                        if(compa > 3)
                        {
                            showmessage("Menu no existe, solo de 1 al 3");
                        }
                        else
                        {
                            Menua menua = new Menua();

                            menua.setId(idactual.getText().toString());

                            String newString = mproximo.getText().toString();

                            String env   =    newString.replaceAll("0", "");

                            menua.setActivo(env);

                            sendmenu(menua.getId(), menua);

                            showmessage("Menú actualizado, reinicia EIDOTAB");

                            finish();
                        }
                    }
                }
            }
        });
    }

    private void loadretrofitmenu()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequestMensaje request = retrofit.create(IRequestMensaje.class);

        Call<ArrayList<Menua>> call = request.getJSONMenues();

        call.enqueue(new Callback<ArrayList<Menua>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Menua>> call, @NonNull Response<ArrayList<Menua>> response)
            {


                ArrayList<Menua> list = new ArrayList<>(Objects.requireNonNull(response.body()));

                for(Menua entra : list)
                {
                    mactual.setText(entra.getActivo());
                    idactual.setText(entra.getId());
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Menua>> call, @NonNull Throwable t)
            {


            }
        });

    }

    private void sendmenu(String id, Menua menua)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequestMensaje request = retrofit.create(IRequestMensaje.class);

        Call<Menua> call = request.updateMenu(id, menua);

        call.enqueue(new Callback<Menua>()
        {
            @Override
            public void onResponse(@NonNull Call<Menua> call, @NonNull Response<Menua> response)
            {
                Log.i("Update Nro Comanda", "Actualizo Correctamente");
            }

            @Override
            public void onFailure(@NonNull Call<Menua> call, @NonNull Throwable t)
            {
                Log.d("Error " , t.getMessage());
            }
        });
    }

    private void showmessage(String mensaje)
    {
        Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

}
