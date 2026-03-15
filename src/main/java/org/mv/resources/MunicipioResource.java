package org.mv.resources;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mv.DTO.CreateMunicipioDTO;
import org.mv.DTO.MunicipioDTO;
import org.mv.entidades.Municipio;
import org.mv.repositorio.MunicipioRepository;
import org.mv.services.MunicipioMapper;

import java.util.List;
import java.util.stream.Collectors;

@Path("/municipio")
public class MunicipioResource {


    private MunicipioRepository municipioRepository;

    private MunicipioMapper municipioMapper;

    @Inject
    public MunicipioResource(MunicipioMapper municipioMapper,
                             MunicipioRepository municipioRepository) {
        this.municipioRepository = municipioRepository;
        this.municipioMapper = municipioMapper;
    }


    @GET
    @RolesAllowed({"ver_municipio","admin"})
    public List<MunicipioDTO> getAllMunicipios() {
        return municipioRepository.list("order by id").stream()
                .map(MunicipioDTO::new)
                .collect(Collectors.toList());
    }


    @POST
    @RolesAllowed({"admin", "crear_municipio"})
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(CreateMunicipioDTO municipio) {
        var entity = municipioMapper.createMunicipio(municipio);
        municipioRepository.persist(entity);
    }


    @DELETE
    @RolesAllowed({"admin","borrar_municipio"})
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id){
        municipioRepository.deleteById(id);
    }

    @PUT
    @Path("{id}")
    @RolesAllowed({"admin","actualizar_municipio"})
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
    @RolesAllowed({"admin","reporte_municipio"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generarReporte(
            @PathParam("format") String format,
            @QueryParam("download") @DefaultValue("true") boolean download) {
        return municipioMapper.generarReportes(format, download);
    }
}
