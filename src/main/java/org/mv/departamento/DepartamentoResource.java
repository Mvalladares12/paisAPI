package org.mv.departamento;

import io.agroal.api.AgroalDataSource;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mv.services.Report;
import java.util.List;
import java.util.stream.Collectors;

@Path("/departamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartamentoResource {


    DepartamentoRepository departamentoRepository;

    DepartamentoMapper departamentoMapper;

    Report report;


    @Inject
    public DepartamentoResource(DepartamentoRepository departamentoRepository, DepartamentoMapper departamentoMapper, Report report) {
        this.departamentoRepository = departamentoRepository;
        this.departamentoMapper = departamentoMapper;
        this.report = report;
    }

    @GET
    //@PermitAll
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<DepartamentoDTO> getAllDepartamentos() {
        return departamentoRepository.list("order by id").stream()
                .map(DepartamentoDTO::new)
                .collect(Collectors.toList());
    }


    @GET
    @Path("{id}")
    public Departamento getDep(@PathParam("id") Long id) {
        return departamentoRepository.findById(id);
    }

    @POST
    @RolesAllowed({"admin", "writer"})
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(CreateDepartamentoDTO departamento) {
        var entity=departamentoMapper.createDepartamento(departamento);
        departamentoRepository.persist(entity);
    }

    @DELETE
    @RolesAllowed({"admin"})
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id){
        departamentoRepository.deleteById(id);
    }


    @PUT
    @Path("{id}")
    @Transactional
    public CreateDepartamentoDTO update(@PathParam("id")Long id, CreateDepartamentoDTO depa) {
        return departamentoRepository.update(id,depa);
    }


    @GET
    @Path("/report/{format}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarReporte(
            @PathParam("format") String format,
            @QueryParam("download") @DefaultValue("true") boolean download) {

            return departamentoMapper.generarReportes(format, download);
    }
}
