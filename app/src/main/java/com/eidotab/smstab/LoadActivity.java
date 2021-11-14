package com.eidotab.smstab;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.eidotab.smstab.Interfaz.IRequestHistorico;
import com.eidotab.smstab.Model.Historico;
import com.eidotab.smstab.Model.Itemorder;
import com.eidotab.smstab.Model.Pladesg;
import com.eidotab.smstab.Model.TimeSorter;
import com.eidotab.smstab.Model.platoItem;
import com.eidotab.smstab.Model.platoStats;
import com.eidotab.smstab.SQlite.DBHelper;

import org.joda.time.DateTimeComparator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadActivity extends AppCompatActivity {


    DBHelper myDB;
    ArrayList<Historico> datadb;
    ArrayList<ArrayList<platoStats>> rdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);


        myDB = DBHelper.GetDBHelper(this);
        datadb = myDB.listHistoricos();
        rdata = myDB.listplatostats();


        // ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);

        // loadRetrofitHistorico();
        if(datadb == null  || datadb.size() == 0) //TODO VERIFICAR RECOLECTAR DATA NUEVA
        {
            // progressBar.setVisibility(View.VISIBLE);

            loadRetrofitHistorico();

        }

        if(rdata == null  || rdata.size() == 0)
        {

            assert rdata != null;
            rdata.clear();

            datadb = myDB.listHistoricos();

            Collections.sort(datadb, new TimeSorter());


            ArrayList<Historico> copia = new ArrayList<>(datadb);

            //copia.addAll(datadb);

            for (int i = 0; i < datadb.size(); i++)
            {

                ArrayList<platoStats> pstats = new ArrayList<>();

                for(int j = 0; j < copia.size(); j++)
                {
                    boolean eshora = hora(datadb.get(i).getFechaorden(),copia.get(j).getFechaorden() );

                    int futuro = j + 1 ;

                    if (futuro < copia.size())
                    {
                        if(eshora)
                        {
                            platoStats platostats = platotics(datadb.get(i));

                            pstats.add(platostats);

                            copia.remove(j);
                            j = -1;

                        }
                        else
                        {
                            if(pstats.size() > 0)
                            {
                                rdata.add(pstats);
                            }
                            datadb.remove(i);
                            i = -1;
                            break;
                        }
                    }
                    else
                    {
                        platoStats platostats = platotics(copia.get(i));

                        pstats.add(platostats);

                        if(pstats.size() > 0)
                        {
                            rdata.add(pstats);
                        }

                        copia.remove(j);

                        j = -1;
                    }
                }



            }


            myDB.addPlatostats(rdata);
        }

        for (int i = 0; i < rdata.size(); i++) {

            for (int j = 0; j <rdata.get(i).size() ; j++) {

                Log.i("fecha ",rdata.get(i).get(j).getDate() + "");

            }

        }

/*        Intent intent = new Intent(this,LauncherActivity.class);
        LoadActivity.this.startActivity(intent);*/

    }
    private void loadRetrofitHistorico()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final IRequestHistorico request = retrofit.create(IRequestHistorico.class);

        Call<ArrayList<Historico>> call = request.getJSONHistoricos();

        call.enqueue(new Callback<ArrayList<Historico>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Historico>> call, @NonNull Response<ArrayList<Historico>> response)
            {

                ArrayList<Historico> recibido = new ArrayList<>();


                recibido.addAll(response.body());


                for(Historico data : recibido)
                {
                    myDB.addHistorico(data);
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Historico>> call, @NonNull Throwable t)
            {

                Log.d("Error", t.getMessage());


            }
        });
    }

    private platoStats platotics(Historico historico)
    {
        platoStats platostats = new platoStats();

        platostats.setCantidad(platostats.getCantidad() + historico.getOrden().size());
        platostats.setNromesa(historico.getMesa());
        platostats.setDate(historico.getFechaorden());
        platoItem platoitem = new platoItem();
        Pladesg pladesg = new Pladesg();
        ArrayList<Pladesg> pladesga = new ArrayList<>();

        for (Itemorder order : historico.getOrden()) {
            pladesg.setnPlato(order.getNombre_plato());
            pladesg.setCplato(order.getCategoria());
            platoitem.setPrice(order.getPrecio_plato());
            pladesga.add(pladesg);
        }

        platoitem.setHistID(historico.get_id());
        platoitem.setnPlato(pladesga);


        platostats.setPlatdat(platoitem);

        return platostats;

    }

    private boolean hora(Date fechaorden, Date fechaorden1)
    {


        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = simpleDateFormatter.format(fechaorden);
        String leftString = simpleDateFormatter.format(fechaorden1);


        return DateTimeComparator.getDateOnlyInstance().compare(dateString, leftString) >= 0;


    }

    private View setSystemUiVisibilityMode()
    {
        View decorView = getWindow().getDecorView();
        int options;
        options =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        decorView.setSystemUiVisibility(options);
        return decorView;
    }

    private void setupMainWindowDisplayMode()
    {
        View decorView = setSystemUiVisibilityMode();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                setSystemUiVisibilityMode(); // Needed to avoid exiting immersive_sticky when keyboard is displayed
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        setupMainWindowDisplayMode();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus)
        {
            setupMainWindowDisplayMode();
        }

    }
}
