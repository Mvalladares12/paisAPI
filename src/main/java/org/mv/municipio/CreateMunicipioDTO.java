package org.mv.municipio;

public record CreateMunicipioDTO(
        Long idDepartamento,
        String nombre,
        String codigo
) {
    public Long getIdDepartamento () {
        return idDepartamento;
    }
}
