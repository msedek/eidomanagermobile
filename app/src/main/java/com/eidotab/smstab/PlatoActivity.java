package com.eidotab.smstab;


import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eidotab.smstab.Interfaz.IRequestMensaje;
import com.eidotab.smstab.Model.Menua;
import com.eidotab.smstab.Model.Plato;
import com.owncloud.android.lib.common.OwnCloudClient;
import com.owncloud.android.lib.common.OwnCloudClientFactory;
import com.owncloud.android.lib.common.OwnCloudCredentialsFactory;
import com.owncloud.android.lib.common.network.OnDatatransferProgressListener;
import com.owncloud.android.lib.common.operations.OnRemoteOperationListener;
import com.owncloud.android.lib.common.operations.RemoteOperation;
import com.owncloud.android.lib.common.operations.RemoteOperationResult;
import com.owncloud.android.lib.resources.files.DownloadRemoteFileOperation;
import com.owncloud.android.lib.resources.files.FileUtils;
import com.owncloud.android.lib.resources.files.UploadRemoteFileOperation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.parameter.ScaleType;
import io.fotoapparat.photo.BitmapPhoto;
import io.fotoapparat.result.PendingResult;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.view.CameraView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static io.fotoapparat.log.Loggers.fileLogger;
import static io.fotoapparat.log.Loggers.logcat;
import static io.fotoapparat.log.Loggers.loggers;
import static io.fotoapparat.parameter.selector.FlashSelectors.autoFlash;
import static io.fotoapparat.parameter.selector.FlashSelectors.autoRedEye;
import static io.fotoapparat.parameter.selector.FlashSelectors.torch;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.autoFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.continuousFocus;
import static io.fotoapparat.parameter.selector.FocusModeSelectors.fixed;
import static io.fotoapparat.parameter.selector.LensPositionSelectors.back;
import static io.fotoapparat.parameter.selector.Selectors.firstAvailable;
import static io.fotoapparat.parameter.selector.SizeSelectors.biggestSize;


public class PlatoActivity extends AppCompatActivity implements OnRemoteOperationListener, OnDatatransferProgressListener {

    CameraView cameraView;
    Fotoapparat fotoapparat;

    CheckBox btn_sugecheff;
    Boolean started;
    Boolean ftomada;
    ImageView poto;
    EditText txt_plato;
    EditText txt_cate;
    EditText txt_descri;
    EditText txt_precio;
    Button btn_salir;
    Button btn_guardar;
    File sdcard;
    File d1;

    String username;
    String password;

    TextView mactualp;

    private OwnCloudClient mClient;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plato);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

/*        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);*/


        txt_plato     = findViewById(R.id.txt_plato);
        txt_cate      = findViewById(R.id.txt_cate);
        txt_descri    = findViewById(R.id.txt_descri);
        txt_precio    = findViewById(R.id.txt_precio);
        btn_salir     = findViewById(R.id.btn_salir);
        btn_guardar   = findViewById(R.id.btn_guardar);
        mactualp      = findViewById(R.id.mactualp);
        btn_sugecheff = findViewById(R.id.btn_sugecheff);


        poto = findViewById(R.id.poto);
        cameraView = findViewById(R.id.camera_view);
        started = false;
        ftomada = false;


        username = "eidotab";
        password = "moslMOSL1707";

        Uri serverUri = Uri.parse(getString(R.string.iptab2) + "owncloud");

        mClient = OwnCloudClientFactory.createOwnCloudClient(serverUri, this, true);
        mClient.setCredentials(OwnCloudCredentialsFactory.newBasicCredentials(username, password));


        sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        Fotoapparat
                .with(getApplicationContext())
                .into(cameraView)           // view which will draw the camera preview
                .previewScaleType(ScaleType.CENTER_CROP)  // we want the preview to fill the view
                .photoSize(biggestSize())   // we want to have the biggest photo possible
                .lensPosition(back())       // we want back camera
                .focusMode(firstAvailable(  // (optional) use the first focus mode which is supported by device
                        continuousFocus(),
                        autoFocus(),        // in case if continuous focus is not available on device, auto focus will be used
                        fixed()             // if even auto focus is not available - fixed focus mode will be used
                ))
                .flash(firstAvailable(      // (optional) similar to how it is done for focus mode, this time for flash
                        autoRedEye(),
                        autoFlash(),
                        torch()
                ))
                //.frameProcessor(myFrameProcessor)   // (optional) receives each frame from preview stream
                .logger(loggers(            // (optional) we want to log camera events in 2 places at once
                        logcat(),           // ... in logcat
                        fileLogger(this)    // ... and to file
                ))
                .build();

        loadretrofitmenu();


        fotoapparat = Fotoapparat
                .with(getApplicationContext())
                .into(cameraView)
                .build();


        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ftomada)
                {

                    Plato plato = new Plato();

                    Boolean cplato = txt_plato.getText().length() > 0;
                    Boolean ccat = txt_cate.getText().length() > 0;
                    Boolean cdesc = txt_descri.getText().length() > 0;
                    Boolean cpre = txt_precio.getText().length() > 0;


                    if(cplato)
                    {
                        plato.setNombre_plato(txt_plato.getText().toString().trim());

                        if(ccat)
                        {
                            plato.setCategoria_plato(txt_cate.getText().toString().trim());

                            if(cdesc)
                            {
                                plato.setDescripcion(txt_descri.getText().toString().trim());

                                if(cpre)
                                {
                                    plato.setPrecio_plato(Double.parseDouble(txt_precio.getText().toString().trim()));
                                }
                                else
                                {
                                    mostrarMensaje("Escriba precio del plato");
                                }

                            }
                            else
                            {
                                mostrarMensaje("Escriba descripción del plato");
                            }

                        }
                        else
                        {
                            mostrarMensaje("Escriba categoría del plato");
                        }

                    }
                    else
                    {
                        mostrarMensaje("Escriba nombre del plato");
                    }

                    boolean essuge = btn_sugecheff.isChecked();

                    boolean mm1 = Integer.parseInt(mactualp.getText().toString()) == 1;
                    boolean mm2 = Integer.parseInt(mactualp.getText().toString()) == 2;
                    boolean mm3 = Integer.parseInt(mactualp.getText().toString()) == 3;

                    plato.setM1(mm1);
                    plato.setM2(mm2);
                    plato.setM3(mm3);

                    plato.setSugerencia(essuge);
                    plato.setFoto_plato("C:\\Users\\eidotab\\ownCloud\\" + plato.getNombre_plato() + ".jpg");
                    plato.setFoto_movil(plato.getNombre_plato() + ".jpg");


                    poto.buildDrawingCache();
                    Bitmap bmp = poto.getDrawingCache();

                    int maxHeight = 516;
                    int maxWidth = 616;

                    bmp = Bitmap.createScaledBitmap(bmp, maxWidth, maxHeight, false);

                    d1 = new File(sdcard, plato.getFoto_movil());

                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(d1);
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                        // PNG is a lossless format, the compression factor (100) is ignored
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null) {
                                out.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    if( cplato && ccat && cdesc && cpre)
                    {
                        File file = new File(sdcard, plato.getFoto_movil());
                        Uploadfile(file);
                        sendPlato(plato);
                        finish();
                    }
                    else
                    {
                        mostrarMensaje("Debes completar todos los campos");
                    }

                }
                else
                {
                    mostrarMensaje("Debes Tomar la Fotografía para poder crear el plato");
                }
            }
        });


        cameraView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mostrarMensaje2("Debes Rotar el Teléfono sentido contra Reloj");
                try
                {
                    fotoapparat.start();
                    cameraView.bringToFront();

                }
                catch(Exception e)
                {
                    started = true;
                }

                if(started)
                {
                    started = false;
                    ftomada = true;
                    btn_guardar.setEnabled(true);

                    PhotoResult photoResult = fotoapparat.takePicture();

                    // d1 = new File(sdcard, "platoflash.jpg");
/*
                    mostrarMensaje("aca");

                    boolean isFileExist = d1.exists();

                    if(isFileExist)
                    {
                        d1.delete();
                        d1 = new File(sdcard, "platoflash.jpg");
                       // photoResult.saveToFile(d1);

                    }
                    else
                    {
                       // photoResult.saveToFile(d1);
                    }*/

// Asynchronously converts photo to bitmap and returns result on main thread
                    photoResult
                            .toBitmap()
                            .whenAvailable(new PendingResult.Callback<BitmapPhoto>()
                            {
                                @Override
                                public void onResult(BitmapPhoto result) {

                                    poto.setImageBitmap(result.bitmap);
                                    poto.setRotation(-result.rotationDegrees);



                                }
                            });

                    fotoapparat.stop();
                }
            }
        });
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        try
        {
            fotoapparat.stop();
        }
        catch(Exception e)
        {
            Log.i("Expected", e.toString());
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        poto.setImageBitmap(null);
        ftomada = false;

    }

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

    private void mostrarMensaje2(String mensaje)
    {
        final Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 2000);

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
                    mactualp.setText(entra.getActivo());
                }

            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Menua>> call, @NonNull Throwable t)
            {


            }
        });

    }

    private void Uploadfile(File file)
    {
        String mimeType = "image/jpg";
        Long timeStampLong = file.lastModified() / 1000;
        String timeStamp = timeStampLong.toString();
        String remotePath = FileUtils.PATH_SEPARATOR + file.getName();

        UploadRemoteFileOperation uploadOperation = new UploadRemoteFileOperation(file.getAbsolutePath(), remotePath, mimeType, timeStamp);
        uploadOperation.addDatatransferProgressListener(this);
        uploadOperation.execute(mClient,this,mHandler);
    }

    @Override
    public void onTransferProgress(long l, long l1, long l2, String s)
    {

        mHandler.post( new Runnable()
        {
            @Override
            public void run() {


            }
        });

    }

    @Override
    public void onRemoteOperationFinish(RemoteOperation remoteOperation, RemoteOperationResult remoteOperationResult)
    {
        if (remoteOperation instanceof DownloadRemoteFileOperation)
        {
            remoteOperationResult.isSuccess();
        }
    }

    public void sendPlato(Plato plato)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequestMensaje request = retrofit.create(IRequestMensaje.class);

        Call<Plato> call = request.addPlato(plato);

        call.enqueue(new Callback<Plato>()
        {
            @Override
            public void onResponse(@NonNull Call<Plato> call, @NonNull Response<Plato> response)
            {

                mostrarMensaje2("Tu Plato Flash se creó Exitosamente, Reinicia eidoTab");

            }

            @Override
            public void onFailure(@NonNull Call<Plato> call, @NonNull Throwable t)
            {
                mostrarMensaje2("Error de Conexión, intenta de nuevo");
                Log.d("Error agregar pedido " , t.getMessage());
            }
        });
    }



}
