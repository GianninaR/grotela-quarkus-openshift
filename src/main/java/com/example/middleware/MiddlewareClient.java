package com.example.middleware;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "middleware-api")
@ClientHeaderParam(name = "X-INTERFISA-BE-VERSION", value = "V1.0")
public interface MiddlewareClient {

    @GET
    @Path("/api/common/centros-servicios")
    @Produces(MediaType.APPLICATION_JSON)
    Object getCentrosServicios(@QueryParam("nombreODireccion") String nombreODireccion);

    @GET
    @Path("/api/secure/common/parametros")
    @Produces(MediaType.APPLICATION_JSON)
    Object getParametrosSipap(
            @QueryParam("dominio") String dominio,
            @HeaderParam("Authorization") String authorization);
}
