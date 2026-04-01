package com.example.middleware;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/api")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
public class MiddlewareResource {

    @RestClient
    MiddlewareClient middlewareClient;

    @ConfigProperty(name = "interfisa.jwt.token")
    String jwtToken;

    @GET
    @Path("/sucursales")
    public Object getSucursales() {
        return middlewareClient.getCentrosServicios("Microcentro");
    }

    @GET
    @Path("/sipap")
    public Object getSipap() {
        return middlewareClient.getParametrosSipap("motivos-sipap", "Bearer " + jwtToken);
    }
}
