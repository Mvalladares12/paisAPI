package org.mv.services;


import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@Path("/")
public class UserResource {
    @GET
    @RolesAllowed("user")
    @Path("/user")
    public String me() {
        return "user";
    }

    @GET
    @RolesAllowed("admin")
    @Path("/admin")
    @Produces(MediaType.TEXT_PLAIN)
    public String adminResource() {
        return "admin";
    }

    @GET
    @PermitAll

    @Produces(MediaType.TEXT_PLAIN)
    public String publicResource() {
        return "public";
    }
}
