package com.eidotab.smstab.Interfaz;




import com.eidotab.smstab.Model.Historico;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface IRequestHistorico {



    @GET("api/historico/{id}")
    Call<Historico> getJSONHistoricosid(@Path("id") String id);


    @GET("api/historicos")
    Call<ArrayList<Historico>> getJSONHistoricos();

    @POST("api/historicos")
    Call<Historico> addHistorico(@Body Historico historico);

    @PUT("api/historicos/{id}")
    Call<Historico> updateHistorico(@Path("id") String historicoId, @Body Historico historico);

    @DELETE("api/historicos/{id}")
    Call<Historico> deleteHistorico(@Path("id") String historicoId);
}


