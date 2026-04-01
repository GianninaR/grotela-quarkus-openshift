package com.example.producto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/productos")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoResource {

    @GET
    public List<Producto> listar() {
        return Producto.listAll();
    }

    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") Long id) {
        Producto producto = Producto.findById(id);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(producto).build();
    }

    @POST
    @Transactional
    public Response crear(Producto producto) {
        producto.persist();
        return Response.status(Response.Status.CREATED).entity(producto).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response actualizar(@PathParam("id") Long id, Producto datos) {
        Producto producto = Producto.findById(id);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        producto.nombre = datos.nombre;
        producto.precio = datos.precio;
        producto.stock = datos.stock;
        return Response.ok(producto).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response eliminar(@PathParam("id") Long id) {
        boolean eliminado = Producto.deleteById(id);
        if (!eliminado) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}
