package org.mv.DTO;

public record CreateMunicipioDTO(
        Long idDepartamento,
        String nombre,
        String codigo
) {
    public Long getIdDepartamento () {
        return idDepartamento;
    }
}
