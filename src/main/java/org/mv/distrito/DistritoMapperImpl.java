package org.mv.distrito;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import org.mv.municipio.Municipio;
import org.mv.municipio.MunicipioRepository;

@RequestScoped
public class DistritoMapperImpl implements DistritoMapper {

    @Inject
    MunicipioRepository muni;

    @Override
    public Distrito createDistrito(CreateDistritoDTO dto) {
        Distrito distrito=new Distrito();
        Municipio municipio= muni.findById(dto.idMunicipio());
        distrito.setIdMunicipio(municipio);
        distrito.setNombre(dto.nombre());
        distrito.setCodigo(dto.codigo());
        return distrito;
    }
}
