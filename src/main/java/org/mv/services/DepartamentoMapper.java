package org.mv.services;

import jakarta.ws.rs.core.Response;
import org.mv.DTO.CreateDepartamentoDTO;
import org.mv.entidades.Departamento;

public interface DepartamentoMapper {

    Departamento createDepartamento(CreateDepartamentoDTO dto);

    void updateDepartamento(CreateDepartamentoDTO dto, Departamento depa);

    CreateDepartamentoDTO present(Departamento departamento);

    Response generarReportes(String format, boolean download);
}
