package org.mv.services;

import jakarta.ws.rs.core.Response;
import org.mv.DTO.CreateDistritoDTO;
import org.mv.entidades.Distrito;

public interface DistritoMapper {

    Distrito createDistrito(CreateDistritoDTO dto);

    Response generarReportes(String format, boolean download);
}
