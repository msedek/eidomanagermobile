package com.eidotab.smstab.Interfaz;


import com.eidotab.smstab.Model.Empleado;
import com.eidotab.smstab.Model.Mensaje;
import com.eidotab.smstab.Model.Menua;
import com.eidotab.smstab.Model.Plato;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRequestMensaje
{
    @GET("api/mensajes")
    Call<ArrayList<Mensaje>> getJSONMensajes();

    @GET("api/empleados")
    Call<ArrayList<Empleado>> getJSONEmpleados();

    @POST("api/mensajes")
    Call<Mensaje> addMensaje(@Body Mensaje mensaje);

    @POST("api/platos")
    Call<Plato> addPlato(@Body Plato plato);

    @PUT("api/mensajes/{id}")
    Call<Mensaje> updateMensaje(@Path("id") String mensajeId, @Body Mensaje mensaje);

    @PUT("api/empleados/{id}")
    Call<Empleado> updateEmpleado(@Path("id") String empleadoId, @Body Empleado empleado);

    @DELETE("api/mensajes/{id}")
    Call<Mensaje> deleteMensaje(@Path("id") String mensajeId);

    @PUT("api/menues/{id}")
    Call<Menua> updateMenu(@Path("id") String menuId, @Body Menua menua);

    @GET("api/menues")
    Call<ArrayList<Menua>> getJSONMenues();

}
