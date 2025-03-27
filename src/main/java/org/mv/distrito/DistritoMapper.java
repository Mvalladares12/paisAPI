package org.mv.distrito;

import jakarta.ws.rs.core.Response;

public interface DistritoMapper {

    Distrito createDistrito(CreateDistritoDTO dto);

    Response generarReportes(String format, boolean download);
}
