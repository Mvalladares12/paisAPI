package org.mv.services;


import jakarta.ws.rs.core.Response;
import org.mv.DTO.CreateMunicipioDTO;
import org.mv.entidades.Municipio;

public interface MunicipioMapper {

    Municipio createMunicipio(CreateMunicipioDTO dto);

    Response generarReportes(String format, boolean download);
}
