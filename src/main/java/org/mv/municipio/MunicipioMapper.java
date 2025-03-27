package org.mv.municipio;


import jakarta.ws.rs.core.Response;

public interface MunicipioMapper {

    Municipio createMunicipio(CreateMunicipioDTO dto);

    Response generarReportes(String format, boolean download);
}
