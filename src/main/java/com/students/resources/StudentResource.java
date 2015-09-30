package com.students.resources;

import com.students.domain.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface StudentResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getAll();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Student getById(@PathParam("id") String id);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Student create(Student student);

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Student update(@PathParam("id") String id, Student student);

    @DELETE
    @Path("/{id}")
    Response delete(@PathParam("id") String id);
}
