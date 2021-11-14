package com.eidotab.smstab.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eidotab.smstab.Model.Empleado;
import com.eidotab.smstab.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;


public class myAdapter extends RecyclerView.Adapter<myAdapter.myAdapterViewHolder> {


    private Context mContext;
    ArrayList<Empleado> employ;


    public myAdapter(Context context, ArrayList<Empleado> employ) // String numesas, String nullamados, String nullamadosat, String nplatos, String nbebidas, String natencion
    {
        this.mContext = context;
        this.employ = employ;

    }

    @Override
    public myAdapter.myAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new myAdapter.myAdapterViewHolder(LayoutInflater.from(mContext).inflate(R.layout.employrow, parent, false));

    }

    @Override
    public void onBindViewHolder(final myAdapter.myAdapterViewHolder holder, int position) {
        Empleado empleado = employ.get(position);
        holder.setData(empleado);
    }


    @Override
    public int getItemCount() {

        return employ.size();

    }


    private void showmessage(String mensaje) {
        final Toast toast = Toast.makeText(mContext, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 800);

    }


    public class myAdapterViewHolder extends RecyclerView.ViewHolder {


        TimeZone tz;
        DateFormat df;

/*        TextView nmesero;
        TextView nmesas;
        TextView nllamodsmesa;
        TextView nllamodsmesaat;
        TextView nplatos;
        TextView nbebidas;
        TextView natencion;*/

        CardView elpadre;

        CircleImageView foto_empleado;

        TextView nombre;
        TextView apellido;


        public myAdapterViewHolder(View itemView) {
            super(itemView);

            //INICIALIZAR CONTROLES
/*            nmesero = itemView.findViewById(R.id.nmesero);
            nmesas = itemView.findViewById(R.id.nmesas);
            nllamodsmesa = itemView.findViewById(R.id.nllamadosmesa);
            nllamodsmesaat = itemView.findViewById(R.id.nllamadosmesaat);
            nplatos = itemView.findViewById(R.id.nplatos);
            nbebidas = itemView.findViewById(R.id.nbebidas);
            natencion = itemView.findViewById(R.id.natencion);*/
            elpadre = itemView.findViewById(R.id.elpadre);
            foto_empleado = itemView.findViewById(R.id.fotoempleado);
            nombre = itemView.findViewById(R.id.lbl_nombre_empleado);
            apellido = itemView.findViewById(R.id.lbl_apellido_empleado);

            Calendar cal = Calendar.getInstance();
            tz = cal.getTimeZone();
            df = new SimpleDateFormat("h:mm a");
            df.setTimeZone(tz);

            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            //float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            float dpWidth = displayMetrics.widthPixels;

            float margen = (float) (dpWidth * 0.04);

            int ancho = (int) dpWidth + (int)margen;

            int copantalla = 3;

            elpadre.setLayoutParams(new LinearLayout.LayoutParams( (ancho / copantalla), ViewGroup.LayoutParams.MATCH_PARENT));


        }

        void setData(final Empleado data)
        {

            Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.user_empty);

            foto_empleado.setImageBitmap(getCircularBitmapWithWhiteBorder(bitmap,50,mContext));

/*            nombre.post(new Runnable() {
                @Override
                public void run() {
                    nombre.setTextColor(ContextCompat.getColor(mContext, R.color.bgn));
                }
            });

            apellido.setTextColor(Color.GREEN);*/


            //nmesero.setText(data.getNombre_empleado());
            //TODO nmesas.setText();
            // TODO nllamodsmesa.setText();
            // TODO nllamodsmesaat.setText();
            // TODO nplatos.setText();
            // TODO nbebidas.setText();
            //TODO natencion.setText();


                                               /*PREPARACION DE LA DATA


          /*  lyocomanda.removeAllViewsInLayout();

            String date = df.format(Rdata.getFechaorden());

            txt_hora.setText("Hora de la Orden: " + date);


            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            float dpInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 238, dm);//TODO
            elpadre.setLayoutParams(new LinearLayout.LayoutParams(Math.round(dpInPx), LinearLayout.LayoutParams.WRAP_CONTENT));*/
                                               /*PREPARACION DE LA DATA*/


        }






    }

    public static Bitmap getCircularBitmapWithWhiteBorder(Bitmap bitmap,int borderWidth, Context mContext)
    {
        if (bitmap == null || bitmap.isRecycled())
        {
            return null;
        }

        final int width = bitmap.getWidth() + borderWidth;
        final int height = bitmap.getHeight() + borderWidth;

        Bitmap canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        Canvas canvas = new Canvas(canvasBitmap);
        float radius = width > height ? ((float) height) / 2f : ((float) width) / 2f;
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ContextCompat.getColor(mContext, R.color.bgn));
        paint.setStrokeWidth(borderWidth);
        canvas.drawCircle(width / 2, height / 2, radius - borderWidth / 2, paint);
        return canvasBitmap;
    }


}
