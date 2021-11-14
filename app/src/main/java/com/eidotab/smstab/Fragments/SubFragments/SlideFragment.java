package com.eidotab.smstab.Fragments.SubFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.eidotab.smstab.Model.BarChart;
import com.eidotab.smstab.Model.Historico;
import com.eidotab.smstab.Model.MyValueFormatter;
import com.eidotab.smstab.Model.MyYAxisValueFormatter;
import com.eidotab.smstab.Model.TimeSorter2;
import com.eidotab.smstab.Model.platoItem;
import com.eidotab.smstab.Model.platoStats;
import com.eidotab.smstab.R;
import com.eidotab.smstab.SQlite.DBHelper;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.joda.time.DateTimeComparator;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;


public class SlideFragment extends Fragment {


    public static SlideFragment newInstance(String ttit, String ttot)
    {
        SlideFragment slide = new SlideFragment();
        Bundle bundle = new Bundle();
        bundle.putString("TituloContent", ttit);
        bundle.putString("TotContent", ttot);

        slide.setArguments(bundle);

        return slide;

    }

    Button vmas;

    TextView ttitulo;
    TextView ttotal;

    RadioButton rdia;
    RadioButton rsem;
    RadioButton rmes;
    RadioButton rano;
    TextView txrango;

    LinearLayout btras;
    LinearLayout blante;

    int primeritem;

    Double tsemana;

    double totales;

    ArrayList<ArrayList<platoStats>> rdata;

    ArrayList<ArrayList<platoStats>> rmdata;

    ArrayList<ArrayList<platoStats>> rmdatabmes;

    boolean primera;
    boolean segunda;
    int deleteador;
    int deleteador2;
    int savedeleteador;
    int savedeleteador2;

    DBHelper myDB;

    BarChart chart;

    int subsi;

    Locale spanish;

    int factordemo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.fragment_slide, container, false);

        vmas    = v.findViewById(R.id.btn_vermas);
        ttitulo = v.findViewById(R.id.txttitu);
        ttotal  = v.findViewById(R.id.txttotales);
        rdia    = v.findViewById(R.id.radiodia);
        rsem    = v.findViewById(R.id.radiosemana);
        rmes    = v.findViewById(R.id.radiomes);
        rano    = v.findViewById(R.id.radioano);
        txrango = v.findViewById(R.id.txrango);
        btras   = v.findViewById(R.id.btn_datetras);
        blante  = v.findViewById(R.id.btn_datelante);
        chart   = v.findViewById(R.id.chart);


        spanish = new Locale("es", "PE");


        myDB = DBHelper.GetDBHelper(getContext());
        rdata = new ArrayList<>();

        rdata.addAll(myDB.listplatostats());

        primera = true;
        segunda = true;


        rmdata = new ArrayList<>();
        rmdatabmes = new ArrayList<>();

        factordemo = 6;

        primeritem = -1;
        subsi = 0;
        tsemana = 0.0;

        chart.setNoDataText("");

        totales = 0.0;

        ttitulo.setText(getArguments().getString("TituloContent"));
        ttotal.setText(getArguments().getString("TotContent"));




        switch (ttitulo.getText().toString())
        {
            case "Total ventas: S/. " :

                vmas.setBackground(getContext().getDrawable(R.drawable.mesebtnbb));

                rdia.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsub));
                rsem.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsub));
                rmes.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsub));
                rano.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsub));


                break;

            case "Total mesas ocupadas: " :

                vmas.setBackground(getContext().getDrawable(R.drawable.mesebtnbbama));

                rdia.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsubama));
                rsem.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsubama));
                rmes.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsubama));
                rano.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsubama));

                break;

            case "Total platos vendidos: " :

                vmas.setBackground(getContext().getDrawable(R.drawable.mesebtnbbrojo));

                rdia.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsubrj));
                rsem.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsubrj));
                rmes.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsubrj));
                rano.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsubrj));

                break;

            case "QUE VA ACA: " :

                vmas.setBackground(getContext().getDrawable(R.drawable.mesebtnbbverde));

                rdia.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsubvd));
                rsem.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsubvd));
                rmes.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsubvd));
                rano.setBackground(getContext().getDrawable(R.drawable.mesebtnbapagsubvd));

                break;
        }

        vmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch(ttitulo.getText().toString())
                {
                    case "Total ventas: S/. " :

                        //TODO

                        break;

                    case "Total mesas ocupadas: " :

//TODO

                        break;

                    case "Total platos vendidos: " :

//TODO

                        break;

                    case "QUE VA ACA: " :

//TODO

                        break;

                }
            }
        });

        rdia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    txrango.setVisibility(View.VISIBLE);
                    btras.setVisibility(View.VISIBLE);
                    blante.setVisibility(View.VISIBLE);

                    switch(ttitulo.getText().toString())
                    {
                        case "Total ventas: S/. " :

                            primeritem = 0;

                            setupchartdia(primeritem);

                            break;

                        case "Total mesas ocupadas: " :


//TODO
                            break;

                        case "Total platos vendidos: " :

//TODO

                            break;

                        case "QUE VA ACA: " :

//TODO

                            break;

                    }

                }
                else
                {
/*                    txrango.setVisibility(View.INVISIBLE);
                    btras.setVisibility(View.INVISIBLE);
                    blante.setVisibility(View.INVISIBLE);*/
                }

            }
        });

        rsem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    txrango.setVisibility(View.VISIBLE);
                    btras.setVisibility(View.VISIBLE);
                    blante.setVisibility(View.VISIBLE);

                    switch(ttitulo.getText().toString())
                    {
                        case "Total ventas: S/. " :

                            primeritem = 0;


                            if(!segunda)
                            {
                                for (int i = 0; i < deleteador2; i++) {

                                    rdata.remove(i);

                                    i = -1;

                                    deleteador2--;

                                }
                                segunda = true;
                            }

                            if(primera)
                            {
                                acomodata();
                                primera = false;
                            }

                            setupchartsem();

                            break;

                        case "Total mesas ocupadas: " :

//TODO

                            break;

                        case "Total platos vendidos: " :

//TODO

                            break;

                        case "QUE VA ACA: " :

//TODO

                            break;

                    }

                }
                else
                {
/*                    txrango.setVisibility(View.INVISIBLE);
                    btras.setVisibility(View.INVISIBLE);
                    blante.setVisibility(View.INVISIBLE);*/
                }

            }
        });

        rmes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    txrango.setVisibility(View.VISIBLE);
                    btras.setVisibility(View.VISIBLE);
                    blante.setVisibility(View.VISIBLE);

                    switch(ttitulo.getText().toString())
                    {
                        case "Total ventas: S/. " :

                            primeritem = 0;

                            if(!primera)
                            {
                                for (int i = 0; i < deleteador; i++) {

                                    rdata.remove(i);

                                    i = -1;

                                    deleteador--;

                                }
                                primera = true;
                            }


                            if(segunda)
                            {
                                acomodatames();
                                segunda = false;
                            }

                            setupchartmon();

                            break;

                        case "Total mesas ocupadas: " :

//TODO

                            break;

                        case "Total platos vendidos: " :

//TODO

                            break;

                        case "QUE VA ACA: " :


//TODO
                            break;

                    }

                }
                else
                {
/*                    txrango.setVisibility(View.INVISIBLE);
                    btras.setVisibility(View.INVISIBLE);
                    blante.setVisibility(View.INVISIBLE);*/
                }

            }
        });

        rano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    txrango.setVisibility(View.VISIBLE);
                    btras.setVisibility(View.VISIBLE);
                    blante.setVisibility(View.VISIBLE);

                    switch(ttitulo.getText().toString())
                    {
                        case "Total ventas: S/. " :

                            primeritem = 0;

                            setupchartyear();



                            break;

                        case "Total mesas ocupadas: " :



                            break;

                        case "Total platos vendidos: " :



                            break;

                        case "QUE VA ACA: " :



                            break;

                    }

                }
                else
                {
/*                    txrango.setVisibility(View.INVISIBLE);
                    btras.setVisibility(View.INVISIBLE);
                    blante.setVisibility(View.INVISIBLE);*/
                }

            }
        });

        btras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO


            }
        });

        blante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch(ttitulo.getText().toString())
                {
                    case "Total ventas: S/. " :

                        if(rdia.isChecked()) {
                            primeritem = primeritem + 1;

                            setupchartdia(primeritem);
                        }

                        if(rsem.isChecked())
                        {
                            primeritem = primeritem + 1;
                            setupchartsem();
                        }

                        if(rmes.isChecked())
                        {

/*                            if(!segunda)
                            {
                                for (int i = 0; i < deleteador2; i++) {

                                    rdata.remove(i);

                                    i = -1;

                                    deleteador2--;

                                }
                                segunda = true;
                            }*/

                            primeritem = primeritem + 1;
                            setupchartmon();
                        }

                        if(rano.isChecked())
                        {
                            primeritem = primeritem + 1;
                            setupchartyear();
                        }
                        break;

                    case "Total mesas ocupadas: " :

//TODO

                        break;

                    case "Total platos vendidos: " :

//TODO

                        break;

                    case "QUE VA ACA: " :

//TODO

                        break;

                }
            }
        });
        return v;
    }

    private void acomodatames()
    {
        ArrayList<platoStats> compled = new ArrayList<>();
        rmdatabmes.addAll(rdata);
        GregorianCalendar gcal = new  GregorianCalendar();
        gcal.setTime(rmdatabmes.get(0).get(0).getDate());



            int month = gcal.get(Calendar.MONTH);

            switch (month)
            {
                //MESES DE 31
                case  0 :
                case  2 :
                case  6 :
                case  4 :
                case  7 :
                case  9 :
                case 11 :


                        gcal.add(Calendar.DAY_OF_MONTH, 0);
                        SimpleDateFormat sdf2 = new SimpleDateFormat("dd", spanish);
                        String datestr2 = sdf2.format(gcal.getTime());

                       deleteador2 = gcal.get(Calendar.DAY_OF_MONTH);
                       savedeleteador2 = gcal.get(Calendar.DAY_OF_MONTH);

                        for (int i = 0; i < deleteador2; i++) {


                                platoStats plato = new platoStats();
                                plato.setCantidad(0);
                                try {
                                    Date date = sdf2.parse(datestr2);
                                    plato.setDate(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                plato.setNromesa("00");
                                platoItem platda = new platoItem();
                                platda.setPrice(0);
                                platda.setnPlato(null);
                                plato.setPlatdat(platda);
                                compled.add(i, plato);

                                rdata.add(i, compled);
                            }

                    for (int i = 0; i < 32; i++) {

                            if(rmdatabmes.get(i).get(0) == null)
                            {
                                platoStats plato = new platoStats();
                                plato.setCantidad(0);
                                try {
                                    Date date = sdf2.parse(datestr2);
                                    plato.setDate(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                plato.setNromesa("00");
                                platoItem platda = new platoItem();
                                platda.setPrice(0);
                                platda.setnPlato(null);
                                plato.setPlatdat(platda);
                                compled.add(i, plato);

                                rdata.add(i, compled);
                            }

                    }



                    break;

                //MESES DE 30
                case  3 :
                case  5 :
                case  8 :
                case 10 :

                    //TODO
                    break;

                // MESES DE 28
                case 1 :

                    boolean bisi = gcal.isLeapYear(gcal.get(Calendar.YEAR));

                    //TODO DETECTAR ACA EL BISIESTO

                    break;


            }

    }

    private void acomodata()
    {
        ArrayList<platoStats>compled = new ArrayList<>();

        Date birthDate = (rdata.get(0).get(0).getDate());
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthDate);
        cal.add(Calendar.HOUR, -  factordemo);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", spanish);
        String datestr = sdf.format(cal.getTime()).toLowerCase();

        switch (datestr)
        {
            case "martes":

                //TODO

                break;

            case "miercoles":

                for (int j = 0; j < 2 ; j++)
                {

                    //TODO

                }
                break;

            case "jueves":

                for (int j = 0; j < 3 ; j++)
                {

                    //TODO

                }
                break;

            case "viernes":

                for (int j = 0; j < 4 ; j++)
                {

                    //TODO

                }
                break;

            case "sabado":

                for (int j = 0; j < 5 ; j++)
                {

                    //TODO

                }
                break;

            case "domingo":

                for (int j = 0; j < 6 ; j++)
                {

                    deleteador = 6;
                    savedeleteador = 6;

                    Date birthDate2 = (rdata.get(0).get(0).getDate());
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(birthDate2);
                    cal2.add(Calendar.DAY_OF_WEEK, - j - 1);
                    SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy", spanish);
                    String datestr2 = sdf2.format(cal2.getTime());

                    platoStats plato = new platoStats();
                    plato.setCantidad(0);
                    try {
                        Date date = sdf2.parse(datestr2) ;
                        plato.setDate(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    plato.setNromesa("00");
                    platoItem platda = new platoItem();
                    platda.setPrice(0);
                    platda.setnPlato(null);
                    plato.setPlatdat(platda);
                    compled.add(j,plato);

                    rdata.add(j,compled);
                }
                break;

            default:
                break;
        }
    }

    private void setupchartdia(int primeritem)
    {
        if(rdia.isChecked()) {


            totales = 0.0;

            if(!primera)
            {
                for (int i = 0; i < deleteador; i++) {

                    rdata.remove(i);

                    i = -1;

                    deleteador--;

                }
                primera = true;
            }

            if(!segunda)
            {
                for (int i = 0; i < deleteador2; i++) {

                    rdata.remove(i);

                    i = -1;

                    deleteador2--;

                }
                segunda = true;
            }

            ArrayList<platoStats> copia = rdata.get(primeritem);

            factordemo = 6;

            Collections.sort(copia, new TimeSorter2());

            String play = json(copia);

            List<BarEntry> entries = valores(copia);

            ArrayList<platoStats> playstats = dra(play);

            SimpleDateFormat dnFormat = new SimpleDateFormat("dd-MM-yyyy");

            SimpleDateFormat inFormat = new SimpleDateFormat("EEEE", spanish);

            String dateeda = dnFormat.format(playstats.get(0).getDate());
            String dateetk = inFormat.format(playstats.get(0).getDate());

            ttotal.setText(String.format(Locale.GERMANY, "%,.2f", totales));

            StringBuilder rackingSystemSb = new StringBuilder(dateetk.toLowerCase());
            rackingSystemSb.setCharAt(0, Character.toUpperCase(rackingSystemSb.charAt(0)));
            dateetk = rackingSystemSb.toString();

            txrango.setText("Ventas del " + dateetk + " " + dateeda);

            BarDataSet set = new BarDataSet(entries, "Ventas por Hora");

            set.setValueFormatter(new MyValueFormatter());
            set.setDrawValues(false);
            set.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            set.setAxisDependency(YAxis.AxisDependency.LEFT);

            BarData bdata = new BarData(set);
            bdata.setBarWidth(0.9f);

            chart.getXAxis().setValueFormatter(new MyYAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    if(value > 10 && value < 24)
                    {
                        if(value >= 12 && value < 13)
                        {
                            return "12p";
                        }

                        if(value >= 15 && value < 16)
                        {
                            return "3p";
                        }

                        if(value >= 18 && value < 19)
                        {
                            return "6p";
                        }

                        if(value >= 21 && value < 22)
                        {
                            return "9p";
                        }
                    }
                    return "";
                }
            });

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(10f);
            xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.over));
            xAxis.setDrawAxisLine(true);
            xAxis.setDrawGridLines(false);
            xAxis.setAxisMinimum(10);
            xAxis.setAxisMaximum(24);
            xAxis.setLabelCount(6, true);

            YAxis left = chart.getAxisLeft();
            left.setDrawLabels(true);
            left.setDrawAxisLine(true);
            left.setDrawGridLines(true);
            left.setDrawZeroLine(false);
            left.setAxisMinimum(0f);
            left.setTextSize(12f);
            left.setTextColor(ContextCompat.getColor(getContext(), R.color.over));
            left.setValueFormatter(new MyYAxisValueFormatter());
            left.setGranularity(1f);
            left.setLabelCount(1, true);

            chart.setDrawBorders(false);
            chart.setDrawGridBackground(false);
            chart.setDescription(null);
            chart.setTouchEnabled(false);
            chart.getAxisRight().setEnabled(false);
            chart.setData(bdata);
            chart.fitScreen();
            Legend leg = chart.getLegend();
            leg.setEnabled(false);
            chart.invalidate();
        }
    }

    private void setupchartsem()
    {

        if (rsem.isChecked())
        {
            double semtot = 0.0;

            ArrayList<ArrayList<BarEntry>> barsem = new ArrayList<>();

            ArrayList<ArrayList<ArrayList<platoStats>>>  parts =  chopped(rdata,7);

            rmdata = parts.get(primeritem);

            for (int i = 0; i < rmdata.size() ; i++)
            {

                totales = 0.0;

                ArrayList<platoStats> dia = rmdata.get(i);

                Collections.sort(dia, new TimeSorter2());

                ArrayList<BarEntry> bsem = valores(dia);
                barsem.add(bsem);

                semtot = semtot + totales;

            }

            ArrayList<BarEntry> entsem = new ArrayList<>();

            for (int i = 0; i < barsem.size(); i++)
            {
                ArrayList<BarEntry> entries = barsem.get(i);

                float ye = 0;

                for (int j = 0; j <entries.size(); j++)
                {
                    ye = ye + entries.get(j).getY();
                }

                BarEntry entry = new BarEntry(i,ye);

                entsem.add(entry);

            }

            final String[] weeksName = {"L", "M", "M", "J", "V", "S","D"};

            chart.getXAxis().setValueFormatter(new MyYAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    if(value > -1 && value < 7)
                    {
                        return weeksName[(int) value];
                    }

                    return "";
                }

            });

            BarDataSet set = new BarDataSet(entsem, "Venta Semanal");

            //  SimpleDateFormat dnFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat dnFormat = new SimpleDateFormat("dd");

            SimpleDateFormat inFormat = new SimpleDateFormat("MMM yyyy", spanish);

            SimpleDateFormat inFormat2 = new SimpleDateFormat("MMM", spanish);

            String dateeda =  dnFormat.format(rmdata.get(0).get(1).getDate());
            String dateetk =  inFormat2.format(rmdata.get(0).get(1).getDate());


            String dateeda2 =  dnFormat.format(rmdata.get(savedeleteador).get(0).getDate());
            String dateetk2 =  inFormat.format(rmdata.get(savedeleteador).get(0).getDate());

            ttotal.setText(String.format(Locale.GERMANY, "%,.2f", semtot));

            StringBuilder rackingSystemSb = new StringBuilder(dateetk.toLowerCase());
            rackingSystemSb.setCharAt(0, Character.toUpperCase(rackingSystemSb.charAt(0)));
            dateetk = rackingSystemSb.toString();

            rackingSystemSb = new StringBuilder(dateetk2.toLowerCase());
            rackingSystemSb.setCharAt(0, Character.toUpperCase(rackingSystemSb.charAt(0)));
            dateetk2 = rackingSystemSb.toString();

            txrango.setText("Ventas Sem. " + dateeda + " " + dateetk + " al " + dateeda2 + " " + dateetk2);

            set.setValueFormatter(new MyValueFormatter());
            set.setDrawValues(false);
            set.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            set.setAxisDependency(YAxis.AxisDependency.LEFT);

            BarData bdata = new BarData(set);
            bdata.setBarWidth(0.95f);

            XAxis xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(10f);
            xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.over));
            xAxis.setDrawAxisLine(true);
            xAxis.setDrawGridLines(false);
            xAxis.setAxisMinimum(-1);
            xAxis.setAxisMaximum(7);
            xAxis.setLabelCount(9, true);

            YAxis left = chart.getAxisLeft();
            left.setDrawLabels(true);
            left.setDrawAxisLine(true);
            left.setDrawGridLines(true);
            left.setDrawZeroLine(false);
            left.setAxisMinimum(0f);
            left.setTextSize(12f);
            left.setTextColor(ContextCompat.getColor(getContext(), R.color.over));
            left.setValueFormatter(new MyYAxisValueFormatter());
            left.setGranularity(1f);
            left.setLabelCount(1, true);

            chart.setDrawBorders(false);
            chart.setDrawGridBackground(false);
            chart.setDescription(null);
            chart.setTouchEnabled(false);
            chart.getAxisRight().setEnabled(false);
            chart.setData(bdata);
            chart.fitScreen();
            Legend leg = chart.getLegend();
            leg.setEnabled(false);
            chart.invalidate();


        }
    }

    @SuppressLint("SetTextI18n")
    private void setupchartmon()
    {

        if (rmes.isChecked())
        {
            double semtot = 0.0;

            ArrayList<ArrayList<BarEntry>> barsem = new ArrayList<>();

            int picada = 32;

            GregorianCalendar gcal = new GregorianCalendar();

            ArrayList<ArrayList<ArrayList<platoStats>>>  parts =  chopped(rdata,31);

            rmdata = parts.get(primeritem);

            gcal.setTime(rmdata.get(savedeleteador2 +1 ).get(0).getDate());

            int month = gcal.get(Calendar.MONTH);

            switch (month)
            {
                //MESES DE 31
                case  0 :
                case  2 :
                case  6 :
                case  4 :
                case  7 :
                case  9 :
                case 11 :

                    break;

                //MESES DE 30
                case  3 :
                case  5 :
                case  8 :
                case 10 :

                    parts.clear();

                    picada = 31;

                    parts =  chopped(rdata,30);

                    rmdata = parts.get(primeritem);

                    break;

                // MESES DE 28
                case 1 :


                    boolean bisi = gcal.isLeapYear(gcal.get(Calendar.YEAR));

                    if(bisi && gcal.get(Calendar.YEAR) > 2016)
                    {

                        picada = 30;

                        parts.clear();

                        parts =  chopped(rdata,29);

                        rmdata = parts.get(primeritem);

                    }
                    else
                    {
                        picada = 29;

                        parts.clear();

                        parts =  chopped(rdata,28);

                        rmdata = parts.get(primeritem);
                    }

                    break;


            }





            for (int i = 0; i < rmdata.size() ; i++)
            {

                totales = 0.0;

                ArrayList<platoStats> dia = rmdata.get(i);

                Collections.sort(dia, new TimeSorter2());

                ArrayList<BarEntry> bsem = valores(dia);
                barsem.add(bsem);

                semtot = semtot + totales;

            }

            ArrayList<BarEntry> entsem = new ArrayList<>();

            for (int i = 0; i < barsem.size(); i++)
            {
                ArrayList<BarEntry> entries = barsem.get(i);

                float ye = 0;

                for (int j = 0; j <entries.size(); j++)
                {
                    ye = ye + entries.get(j).getY();
                }

                BarEntry entry = new BarEntry(i+1,ye);

                entsem.add(entry);

            }

            BarDataSet set = new BarDataSet(entsem, "Venta Mensual");
            BarData bdata = new BarData(set);
            bdata.setBarWidth(0.9f);
            chart.setData(bdata);


            XAxis xAxis = chart.getXAxis();
            xAxis.setLabelCount(18, true);
            xAxis.setAxisMinimum(-1);
            xAxis.setAxisMaximum(33);
            xAxis.setGranularityEnabled(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(10f);
            xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.over));
            xAxis.setDrawAxisLine(true);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(1);


            final String[] daysNumber = { "0","1", "2", "3", "4", "5", "6","7","8", "9", "10", "11", "12", "13","14",
                    "15", "16", "17", "18", "19", "20","21","22", "23", "24", "25", "26", "27","28",
                    "29","30","31"};

            final int finalPicada = picada;
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {

                    if(value >0 && value < finalPicada)
                    {

                        int posn = (int)value ;
                        String pos = daysNumber[posn];
                        return pos;
                    }

                    return "";
                }
            });


            SimpleDateFormat inFormat = new SimpleDateFormat("MMMM yyyy", spanish);

            String dateetk2 =  inFormat.format(rmdata.get(savedeleteador2 + 1).get(0).getDate());

            ttotal.setText(String.format(Locale.GERMANY, "%,.2f", semtot));

            txrango.setText("Ventas de "  + dateetk2);

            set.setValueFormatter(new MyValueFormatter());
            set.setDrawValues(false);
            set.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            set.setAxisDependency(YAxis.AxisDependency.LEFT);

            YAxis left = chart.getAxisLeft();
            left.setDrawLabels(true);
            left.setDrawAxisLine(true);
            left.setDrawGridLines(true);
            left.setDrawZeroLine(false);
            left.setAxisMinimum(0f);
            left.setTextSize(12f);
            left.setTextColor(ContextCompat.getColor(getContext(), R.color.over));
            left.setValueFormatter(new MyYAxisValueFormatter());
            left.setGranularity(1f);
            left.setLabelCount(1, true);

            chart.setDrawBorders(false);
            chart.setDrawGridBackground(false);
            chart.setDescription(null);
            chart.setTouchEnabled(false);
            chart.getAxisRight().setEnabled(false);

            Legend leg = chart.getLegend();
            leg.setEnabled(false);
            chart.invalidate();


        }

    }

    @SuppressLint("SetTextI18n")
    private void setupchartyear()
    {


        double semtot = 0.0;

        ArrayList<ArrayList<BarEntry>> barsem = new ArrayList<>();

        ArrayList<ArrayList<ArrayList<platoStats>>> parts = chopped(rdata,30);

        ArrayList<ArrayList<platoStats>> rmdata = parts.get(primeritem);

        for (int i = 0; i < rmdata.size() ; i++)
        {

            totales = 0.0;

            ArrayList<platoStats> dia = rmdata.get(i);

            Collections.sort(dia, new TimeSorter2());

            ArrayList<BarEntry> bsem = valores(dia);
            barsem.add(bsem);

            semtot = semtot + totales;

        }

        ArrayList<BarEntry> entsem = new ArrayList<>();

        for (int i = 0; i < barsem.size(); i++)
        {
            ArrayList<BarEntry> entries = barsem.get(i);

            float ye = 0;

            for (int j = 0; j <entries.size(); j++)
            {
                ye = ye + entries.get(j).getY();

            }

            BarEntry entry = new BarEntry(i,ye);

            entsem.add(entry);

        }

        BarDataSet set = new BarDataSet(entsem, "Venta Semanal");

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dnFormat = new SimpleDateFormat("dd-MM-yyyy");

        SimpleDateFormat inFormat = new SimpleDateFormat("EEEE", spanish);

        String dateeda =  dnFormat.format(rmdata.get(0).get(0).getDate());
        String dateetk =  inFormat.format(rmdata.get(0).get(0).getDate());

        ttotal.setText(String.format( spanish, "%,.2f", semtot));

        StringBuilder rackingSystemSb = new StringBuilder(dateetk.toLowerCase());
        rackingSystemSb.setCharAt(0, Character.toUpperCase(rackingSystemSb.charAt(0)));
        dateetk= rackingSystemSb.toString();

        txrango.setText("Ventas del " + dateetk + " " + dateeda);




        set.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        chart.setDrawBorders(false);
        chart.setDrawGridBackground(false);
        Description desc = null;
        chart.setDescription(desc);
        chart.setTouchEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.over));
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);

        xAxis.setLabelCount(12, true);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        // data has AxisDependency.LEFT
        YAxis left = chart.getAxisLeft();
        left.setDrawLabels(false); // no axis labels
        left.setDrawAxisLine(true); // no axis line
        left.setDrawGridLines(false); // no grid lines
        left.setDrawZeroLine(true); // draw a zero line


        left.setAxisMinimum(1f); // start at zero
        //  left.setAxisMaximum(max * 2); // the axis maximum is 100
        left.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        left.setGranularity(1f); // interval 1
        //yAxis.setLabelCount(6, true); // force 6 labels

        //chart.setExtraOffsets(0, 0, 0, 1);//TODO COLOCAR ACA EL OFFSET REAL

        chart.getAxisRight().setEnabled(false); // no right axis


        BarData bdata = new BarData(set);
        bdata.setBarWidth(0.9f); // set custom bar width
        chart.setData(bdata);
        //  chart.setFitBars(true); // make the x-axis fit exactly all bars
        chart.invalidate();

        chart.notifyDataSetChanged(); // refresh




/*
        List<BarEntry>entries = valores(copia);

        ArrayList<platoStats>playstats = dra(play);*/




        // Log.i("tag", "setupchartsem: ");


    }

    private ArrayList<BarEntry> valores(ArrayList<platoStats>copia)
    {

        ArrayList<BarEntry>val = new ArrayList<>();

        double subt = 0.0;
        Date past = null;


        for (int i = 0; i < copia.size() ; i++)
        {

            int next = i + 1;

            totales = totales + copia.get(i).getPlatdat().getPrice();

            if(next >= copia.size())
            {
                if(past == null)
                {
                    float ye = (float) totales;

                    Date birthDate = (copia.get(i).getDate());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(birthDate);
                    cal.add(Calendar.HOUR, -  factordemo);

                    SimpleDateFormat sdf = new SimpleDateFormat("HH", spanish);
                    String datestr = sdf.format(cal.getTime());

                    float ex = Float.parseFloat(datestr);

                    val.add(new BarEntry(ex, ye));
                }
                else
                {
                    float ye = (float) subt + (float) copia.get(i).getPlatdat().getPrice();

                    Date birthDate = (copia.get(i).getDate());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(birthDate);
                    cal.add(Calendar.HOUR, -  factordemo);

                    SimpleDateFormat sdf = new SimpleDateFormat("HH", spanish);
                    String datestr = sdf.format(cal.getTime());


                    float ex = Float.parseFloat(datestr);

                    val.add(new BarEntry(ex, ye));
                }
            }
            else
            {
                boolean eshora = hora(copia.get(i).getDate(),copia.get(next).getDate());

                if(!eshora)
                {
                    float ye = (float) subt;
                    Date birthDate = (copia.get(i).getDate());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(birthDate);
                    cal.add(Calendar.HOUR, -  factordemo);
                    SimpleDateFormat sdf = new SimpleDateFormat("HH", spanish);
                    String datestr = sdf.format(cal.getTime());
                    float ex = Float.parseFloat(datestr);

                    val.add(new BarEntry(ex, ye));
                }
                else
                {
                    past = copia.get(i).getDate();
                    subt = subt + copia.get(i).getPlatdat().getPrice();
                }
            }
        }
        return val;
    }

    private String json(ArrayList<platoStats>rda)
    {
        Gson gson = new Gson();
        return gson.toJson(rda);
    }

    private ArrayList<platoStats> dra(String json)
    {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<platoStats>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    private boolean hora(Date fechaorden, Date fechaorden1)
    {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormatter = new SimpleDateFormat("HH");
        String dateString = simpleDateFormatter.format(fechaorden);
        String leftString = simpleDateFormatter.format(fechaorden1);
        return DateTimeComparator.getTimeOnlyInstance().compare(dateString, leftString) >= 0;
    }

    static ArrayList<ArrayList<ArrayList<platoStats>>> chopped(ArrayList<ArrayList<platoStats>> list, final int L)
    {
        ArrayList<ArrayList<ArrayList<platoStats>>> parts = new ArrayList<>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

}

