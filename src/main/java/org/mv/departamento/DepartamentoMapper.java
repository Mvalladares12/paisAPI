package org.mv.departamento;

import jakarta.ws.rs.core.Response;

public interface DepartamentoMapper {

    Departamento createDepartamento(CreateDepartamentoDTO dto);

    void updateDepartamento(CreateDepartamentoDTO dto, Departamento depa);

    CreateDepartamentoDTO present(Departamento departamento);

    Response generarReportes(String format, boolean download);
}
