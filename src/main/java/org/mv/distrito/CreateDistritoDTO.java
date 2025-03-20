package org.mv.distrito;

public record CreateDistritoDTO(
        Long idMunicipio,
        String nombre,
        String codigo
) {
}
