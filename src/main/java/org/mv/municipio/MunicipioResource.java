package org.mv.municipio;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/municipio")
public class MunicipioResource {


    private MunicipioRepository municipioRepository;

    private MunicipioMapper municipioMapper;

    @Inject
    public MunicipioResource(MunicipioMapper municipioMapper, MunicipioRepository municipioRepository) {
        this.municipioRepository = municipioRepository;
        this.municipioMapper = municipioMapper;
    }


    @GET
    @RolesAllowed({"admin","user"})
    public List<MunicipioDTO> getAllMunicipios() {
        return municipioRepository.list("order by id").stream()
                .map(MunicipioDTO::new)
                .collect(Collectors.toList());
    }


    @POST
    @RolesAllowed({"admin", "user"})
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(CreateMunicipioDTO municipio) {
        var entity = municipioMapper.createMunicipio(municipio);
        municipioRepository.persist(entity);
    }


    @DELETE
    @RolesAllowed("admin")
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id){
        municipioRepository.deleteById(id);
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Municipio update(@PathParam("id")Long id, Municipio municipio) {
        var entity = municipioRepository.findById(id);
        if (entity != null) {
            entity.setCodigo(municipio.getCodigo());
            entity.setNombre(municipio.getNombre());
            municipioRepository.persist(entity);
            return entity;
        }else {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/report/{format}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarReporte(
            @PathParam("format") String format,
            @QueryParam("download") @DefaultValue("true") boolean download) {
        return municipioMapper.generarReportes(format, download);
    }
}
