package org.mv.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.mv.DTO.CreateDepartamentoDTO;
import org.mv.DTO.DepartamentoDTO;
import org.mv.repositorio.DepartamentoRepository;
import org.mv.entidades.Departamento;
import org.mv.services.DepartamentoMapper;
import org.mv.services.Roles;

import java.util.List;
import java.util.stream.Collectors;

@Path("/departamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartamentoResource {


    DepartamentoRepository departamentoRepository;

    DepartamentoMapper departamentoMapper;

    //Report report;


    @Inject
    public DepartamentoResource(DepartamentoRepository departamentoRepository, DepartamentoMapper departamentoMapper) {
        this.departamentoRepository = departamentoRepository;
        this.departamentoMapper = departamentoMapper;
        //this.report = report;
    }




    @GET
    @RolesAllowed({Roles.VER_DEPARTAMENTO, Roles.ADMIN})
    @Produces(MediaType.APPLICATION_JSON)
    public List<DepartamentoDTO> getAllDepartamentos() {
        return departamentoRepository.list("order by id").stream()
                .map(DepartamentoDTO::new)
                .collect(Collectors.toList());
    }


    @GET
    @Path("{id}")
    @RolesAllowed({"ver_departamento", "admin"})
    public Departamento getDep(@PathParam("id") Long id) {
        return departamentoRepository.findById(id);
    }

    @POST
    @RolesAllowed({"admin", "crear_departamento"})
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(CreateDepartamentoDTO departamento) {
        var entity=departamentoMapper.createDepartamento(departamento);
        departamentoRepository.persist(entity);
    }

    @DELETE
    @RolesAllowed({"admin","borrar_departamento"})
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id){
        departamentoRepository.deleteById(id);
    }


    @PUT
    @RolesAllowed({"admin","actualizar_departamento"})
    public Departamento update(Departamento depa) {
        var entity = departamentoRepository.findById(depa.getId());
        if (entity != null) {
            entity.setCodigo(depa.getCodigo());
            entity.setNombre(depa.getNombre());
            departamentoRepository.flush();
            return entity;
        }else {
            throw new NotFoundException();
        }
    }


    @GET
    @Path("/report/{format}")
    @RolesAllowed({"admin","reporte_departamento"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarReporte(
            @PathParam("format") String format,
            @QueryParam("download") @DefaultValue("true") boolean download) {

            return departamentoMapper.generarReportes(format, download);
    }
}
