package org.mv.departamento;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.stream.Collectors;

@Path("/departamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartamentoResource {


    DepartamentoRepository departamentoRepository;

    DepartamentoMapper departamentoMapper;

    @Inject
    public DepartamentoResource(DepartamentoRepository departamentoRepository, DepartamentoMapper departamentoMapper) {
        this.departamentoRepository = departamentoRepository;
        this.departamentoMapper = departamentoMapper;
    }

    /*@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Departamento> findAll() {
        return departamentoRepository.listAll();
    }*/

    @GET
    public List<DepartamentoDTO> getAllDepartamentos() {
        return departamentoRepository.list("order by id").stream()
                .map(departamento -> new DepartamentoDTO((Departamento) departamento))
                .collect(Collectors.toList());
    }

    @GET
    @Path("{id}")
    public Departamento getDep(@PathParam("id") Long id) {
        return departamentoRepository.findById(id);
    }

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(CreateDepartamentoDTO departamento) {
        var entity=departamentoMapper.createDepartamento(departamento);
        departamentoRepository.persist(entity);
    }

    @DELETE
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
}
