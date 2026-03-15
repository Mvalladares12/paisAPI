package org.mv.resources;

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
    @RolesAllowed({"ver_distritos","admin"})
    public List<DistritoDTO>  getAllDistritos() {
        return distritoRepository.list("order by id").stream()
                .map(DistritoDTO::new)
                .collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"ver_distritos","admin"})
    public Distrito getDep(@PathParam("id") Long id) {
        return distritoRepository.findById(id);
    }

    @POST
    @Transactional
    @RolesAllowed({"crear_distritos","admin"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(CreateDistritoDTO distrito) {
        var entity=distritoMapper.createDistrito(distrito);
        distritoRepository.persist(entity);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"borrar_distritos","admin"})
    @Transactional
    public void delete(@PathParam("id") Long id){
        distritoRepository.deleteById(id);
    }

    @PUT
    @Path("{id}")
    @Transactional
    @RolesAllowed({"actualizar_distritos","admin"})
    public Distrito update(@PathParam("id")Long id, Distrito distrito) {
        var entity = distritoRepository.findById(id);
        if (entity != null) {
            entity.setCodigo(distrito.getCodigo());
            entity.setNombre(distrito.getNombre());
            distritoRepository.persist(entity);
            return entity;
        }else {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/report/{format}")
    @RolesAllowed({"reporte_distritos","admin"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarReporte(
            @PathParam("format") String format,
            @QueryParam("download") @DefaultValue("true") boolean download) {

        return distritoMapper.generarReportes(format, download);
    }
}
