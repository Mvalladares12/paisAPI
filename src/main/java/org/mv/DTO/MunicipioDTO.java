package org.mv.DTO;

import org.mv.entidades.Municipio;

import java.util.List;
import java.util.stream.Collectors;

public class MunicipioDTO {
    public Long id;
    public String codigo;
    public String nombre;
    public Long idDepartamento; // ID del departamento al que pertenece
    public List<DistritoDTO> distritos;

    public MunicipioDTO(Municipio municipio) {
        this.id = municipio.getId();
        this.codigo = municipio.getCodigo();
        this.nombre = municipio.getNombre();
        this.idDepartamento = municipio.getIdDepartam();
        this.distritos = municipio.getDistritos().stream()
                .map(DistritoDTO::new)
                .collect(Collectors.toList());
    }
}
