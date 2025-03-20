package org.mv.municipio;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.mv.departamento.Departamento;
import org.mv.departamento.DepartamentoRepository;

@RequestScoped
public class MunicipioMapperImpl implements MunicipioMapper {

    @Inject
    DepartamentoRepository depa;

    @Override
    public Municipio createMunicipio(CreateMunicipioDTO dto) {
        Municipio municipio=new Municipio();
        Departamento departamento=depa.findById(dto.idDepartamento());
        municipio.setIdDepartam(departamento);
        municipio.setNombre(dto.nombre());
        municipio.setCodigo(dto.codigo());
        return municipio;
    }
}
