package com.example.favorito;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@Path("/api/favoritos")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FavoritoResource {

    @Inject
    EntityManager em;

    @GET
    public List<Favorito> listar() {
        return em.createQuery("SELECT f FROM Favorito f", Favorito.class).getResultList();
    }

    @POST
    @Transactional
    public Response crear(Favorito favorito) {
        favorito.fechaRegistro = LocalDateTime.now();
        try {
            em.persist(favorito);
            em.flush();
            return Response.status(Response.Status.CREATED).entity(favorito).build();
        } catch (PersistenceException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response eliminar(@PathParam("id") Long id) {
        Favorito favorito = em.find(Favorito.class, id);
        if (favorito == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        em.remove(favorito);
        return Response.noContent().build();
    }
}
