package com.eidotab.smstab;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eidotab.smstab.Adapter.myAdapter;
import com.eidotab.smstab.Interfaz.IRequestMensaje;
import com.eidotab.smstab.Model.Mensaje;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Spinner combobox;
    ImageButton enviar;
    Button salir;
    TextView cuenta;
    EditText mensajet;
    Spinner combobox2;
    Button delete;
    LinearLayout scrolleo;

    TextView habtimertxt;

    Boolean carga;

    RecyclerView mrecyclerView;

    myAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

/*
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);*/

        combobox      = findViewById(R.id.combobox);
        combobox2     = findViewById(R.id.combobox2);
        enviar        = findViewById(R.id.enviar);
        salir         = findViewById(R.id.salir);
        cuenta        = findViewById(R.id.cuenta);
        mensajet      = findViewById(R.id.mensajet);
        delete        = findViewById(R.id.delete);
        scrolleo      = findViewById(R.id.scrolleo);
        habtimertxt   = findViewById(R.id.habtimertxt);
        mrecyclerView = findViewById(R.id.mrecycler);

        carga = true;


        // Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.remis_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner

        combobox.setAdapter(adapter);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.mese_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner

        combobox2.setAdapter(adapter2);



        final Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable()
        {

            @SuppressLint("SetTextI18n")
            @Override
            public void run()
            {

                if(habtimertxt.getText().equals("true"))
                {

                    habtimertxt.setText("false");


                    loadRetrofitMensaje("enviar");
                   // loadRetrofitEmpleado();


                }// LO QUE SE EJECUTA ACA DENTRO SE EJECUTA SIEMPRE Y CUANDO LA ETIQUETA SEA TRUE

                mHandler.postDelayed(this, 2000);
            }
        },0);

        mensajet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                cuenta.setText(30 - charSequence.length() + "");

            }

            @Override
            public void afterTextChanged(Editable editable) {



            }
        });


        enviar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view)
            {

                habtimertxt.setText("false");

                if(mensajet.getText().length() == 0)
                {
                    mostrarMensaje("Coloca un mensaje");
                }
                else
                {

                    Mensaje mensaje = new Mensaje();

                    mensaje.setEstadomensaje("PENDIENTE");
                    mensaje.setRemitente(combobox.getSelectedItem().toString() + "/" + combobox2.getSelectedItem().toString());
                    mensaje.setTexto(mensajet.getText().toString());

                    TextView textView = new TextView(getApplicationContext());
                    textView.setSingleLine(false);

                    String menss = mensaje.getRemitente() + "\n" + mensaje.getTexto();

                    textView.setText(menss);

                    textView.setTextSize(24);


                    sendmensaje(mensaje);

                    mostrarMensaje("Mensaje Enviado...");

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadRetrofitMensaje("enviar");
                        }
                    }, 50);

                    mensajet.setText("");

                    habtimertxt.setText("true");

                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadRetrofitMensaje("eliminar");

            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        combobox.requestFocus();

        setAdapter();
        loadRetrofitMensaje("enviar");


    }

/*    private void loadRetrofitEmpleado()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequestMensaje request = retrofit.create(IRequestMensaje.class);

        Call<ArrayList<Empleado>> call = request.getJSONEmpleados();

        call.enqueue(new Callback<ArrayList<Empleado>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Empleado>> call, @NonNull Response<ArrayList<Empleado>> response)
            {
                ArrayList<Empleado> list = new ArrayList<>();

                for(Empleado entra : response.body())
                {
                    list.add(entra);
                }

                if (adapter.employ.size()> 0)
                {
                    list.removeAll(adapter.employ);

                    for(Empleado filtrado : list)
                    {
                        adapter.employ.add(filtrado);
                        adapter.notifyDataSetChanged();
                    }

                }
                else
                {
                    adapter.employ.add(list.get(0));
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Empleado>> call, @NonNull Throwable t)
            {


            }
        });
    }*/

    private void mostrarMensaje(String mensaje)
    {
        final Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 500);

    }

    public void sendmensaje(Mensaje mensaje)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequestMensaje request = retrofit.create(IRequestMensaje.class);

        Call<Mensaje> call = request.addMensaje(mensaje);

        call.enqueue(new Callback<Mensaje>()
        {
            @Override
            public void onResponse(@NonNull Call<Mensaje> call, @NonNull Response<Mensaje> response)
            {
                Log.i("RETROFIT", "Envió comanda a cocina");

                // setResult(RESULT_OK);

            }

            @Override
            public void onFailure(@NonNull Call<Mensaje> call, @NonNull Throwable t)
            {

                Log.d("Error agregar pedido " , t.getMessage());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadRetrofitMensaje(final String sender)
    {
        habtimertxt.setText("false");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequestMensaje request = retrofit.create(IRequestMensaje.class);

        Call<ArrayList<Mensaje>> call = request.getJSONMensajes();

        call.enqueue(new Callback<ArrayList<Mensaje>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Mensaje>> call, @NonNull Response<ArrayList<Mensaje>> response) {


                ArrayList<Mensaje> very = response.body();

                assert very != null;
                if(very.size() > 0)
                {

                    if(sender.equals("eliminar"))
                    {

                        for (Mensaje llego: very)
                        {

                            deleteMensaje(llego.get_id());

                        }

                        scrolleo.removeAllViews();
                        mostrarMensaje("Todos los mensajes borrados");

                    }

                    if(sender.equals("enviar"))
                    {

                        if (carga)
                        {
                            carga = false;
                            for(Mensaje llego : very )
                            {

                                TextView textView = new TextView(getApplicationContext());
                                textView.setSingleLine(false);

                                String menss = llego.get_id() + "\n" + llego.getRemitente() + "\n" + llego.getTexto();

                                textView.setText(menss);

                                textView.setTextColor(Color.BLACK);

                                textView.setTextSize(18);

                                scrolleo.addView(textView);

                            }
                        }
                        else
                        {
                            for (int j = 0; j < very.size(); j++) {


                                Boolean hay = scrolleo.getChildCount() > 0;

                                if (hay)
                                {

                                    for (int i = 0; i < scrolleo.getChildCount(); i++)
                                    {

                                        TextView text = (TextView) scrolleo.getChildAt(i);

                                        String traigo = (String) text.getText();

                                        String ide = very.get(j).get_id();

                                        if (traigo.contains(ide))
                                        {
                                            very.remove(j);
                                            // i = -1;
                                        }

                                    }

                                    for (int i = 0; i < very.size(); i++) {

                                        TextView textView = new TextView(getApplicationContext());
                                        textView.setSingleLine(false);

                                        String menss = very.get(i).get_id() + "\n" + very.get(i).getRemitente() + "\n" + very.get(i).getTexto();

                                        textView.setText(menss);

                                        textView.setTextColor(Color.BLACK);

                                        textView.setTextSize(18);

                                        scrolleo.addView(textView);

                                    }

                                }
                                else
                                {
                                    TextView textView = new TextView(getApplicationContext());
                                    textView.setSingleLine(false);

                                    String menss = very.get(j).get_id() + "\n" + very.get(j).getRemitente() + "\n" + very.get(j).getTexto();

                                    textView.setText(menss);

                                    textView.setTextColor(Color.BLACK);

                                    textView.setTextSize(18);

                                    scrolleo.addView(textView);
                                }




                            }
                        }

                    }
                }
                else
                {
                    mostrarMensaje("No se registra actividad en el Restaurante");
                }

                habtimertxt.setText("true");

            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Mensaje>> call, @NonNull Throwable t) {

                habtimertxt.setText("true");
                // mostrarMensaje("Error: " + t.getMessage());

            }
        });
    }

    private void deleteMensaje(String id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequestMensaje request = retrofit.create(IRequestMensaje.class);

        Call<Mensaje> call = request.deleteMensaje(id);

        call.enqueue(new Callback<Mensaje>()
        {
            @Override
            public void onResponse(@NonNull Call<Mensaje> call, @NonNull Response<Mensaje> response)
            {
                Log.i("Update Estado Orden", "Se elimino Correctamente");
            }

            @Override
            public void onFailure(@NonNull Call<Mensaje> call, @NonNull Throwable t)
            {
                Log.d("Error " , t.getMessage());
            }
        });
    }

    private void setAdapter()
    {
        //adapter =  new myAdapter(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mrecyclerView.setLayoutManager(linearLayoutManager);
        mrecyclerView.setAdapter(adapter);

    }

}
