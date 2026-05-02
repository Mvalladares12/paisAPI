package org.mv.DTO;

public record CreateMunicipioDTO(
        Long idDepartam,
        String nombre,
        String codigo
) {
    public Long getIdDepartamento () {
        return idDepartam;
    }
}
