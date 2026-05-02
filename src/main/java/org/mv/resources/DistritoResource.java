package org.mv.resources;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mv.DTO.CreateDistritoDTO;
import org.mv.DTO.DistritoDTO;
import org.mv.repositorio.DistritoRepository;
import org.mv.entidades.Distrito;
import org.mv.services.DistritoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Path("/distrito")
public class DistritoResource {


    DistritoRepository distritoRepository;
    DistritoMapper distritoMapper;

    @Inject
    public DistritoResource(DistritoRepository distritoRepository, DistritoMapper distritoMapper) {
        this.distritoRepository = distritoRepository;
        this.distritoMapper = distritoMapper;
    }

    @GET
    @RolesAllowed({"ver_distrito","admin"})
    public List<DistritoDTO>  getAllDistritos() {
        return distritoRepository.list("order by id").stream()
                .map(DistritoDTO::new)
                .collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"ver_distrito","admin"})
    public Distrito getDep(@PathParam("id") Long id) {
        return distritoRepository.findById(id);
    }

    @POST
    @Transactional
    @RolesAllowed({"crear_distrito","admin"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(CreateDistritoDTO distrito) {
        var entity=distritoMapper.createDistrito(distrito);
        distritoRepository.persist(entity);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"borrar_distrito","admin"})
    @Transactional
    public void delete(@PathParam("id") Long id){
        distritoRepository.deleteById(id);
    }

    @PUT
    @Transactional
    @RolesAllowed({"actualizar_distrito","admin"})
    public Distrito update(Distrito distrito) {
        var entity = distritoRepository.findById(distrito.getId());
        if (entity != null) {
            entity.setIdMunicipio(distrito.getIdMunicipio());
            entity.setCodigo(distrito.getCodigo());
            entity.setNombre(distrito.getNombre());
            distritoRepository.flush();
            return entity;
        }else {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/report/{format}")
    @RolesAllowed({"reporte_distrito","admin"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarReporte(
            @PathParam("format") String format,
            @QueryParam("download") @DefaultValue("true") boolean download) {

        return distritoMapper.generarReportes(format, download);
    }
}
