package org.maven;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/jwt")
@ApplicationScoped
public class apiJWTResource {

    @Inject
    apiJWTService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getJWT(){
        String jwt=service.generateJWT();
        return Response.ok(jwt).build();
    }
}
