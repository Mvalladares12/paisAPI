package org.mv.departamento;

public interface DepartamentoMapper {

    Departamento createDepartamento(CreateDepartamentoDTO dto);

    void updateDepartamento(CreateDepartamentoDTO dto, Departamento depa);

    CreateDepartamentoDTO present(Departamento departamento);
}
