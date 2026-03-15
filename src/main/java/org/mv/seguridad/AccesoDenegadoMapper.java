package org.mv.seguridad;

import io.quarkus.security.ForbiddenException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Provider
public class AccesoDenegadoMapper implements ExceptionMapper<ForbiddenException> {

    @Override
    public Response toResponse(ForbiddenException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("status", 403);
        errorResponse.put("error", "Acceso Denegado (Forbidden)");
        errorResponse.put("message", "El usuario autenticado no posee los roles organizacionales necesarios en LDAP para ejecutar esta acción.");
        errorResponse.put("mitigation", "Contacte al administrador del sistema para solicitar elevación de privilegios.");

        // Devolvemos el JSON con el código HTTP 403 correcto
        return Response.status(Response.Status.FORBIDDEN)
                .entity(errorResponse)
                .build();
    }
}