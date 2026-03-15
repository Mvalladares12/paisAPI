package org.mv.seguridad;

import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Provider
public class NoAutenticadoMapper implements ExceptionMapper<UnauthorizedException> {

    @Override
    public Response toResponse(UnauthorizedException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now().toString());
        errorResponse.put("status", 401);
        errorResponse.put("error", "No Autenticado");
        errorResponse.put("message", "No se detectó un token de acceso válido en la petición o el token ha expirado.");
        errorResponse.put("mitigation", "Debe iniciar sesión en el Proveedor de Identidad para obtener un nuevo Bearer Token.");

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(errorResponse)
                .build();
    }
}