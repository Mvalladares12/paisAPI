package org.mv.municipio;

import org.mv.distrito.DistritoDTO;

import java.util.List;
import java.util.stream.Collectors;

public class MunicipioDTO {
    public Integer id;
    public String codigo;
    public String nombre;
    public Integer idDepartamento; // ID del departamento al que pertenece
    public List<DistritoDTO> distritos;

    public MunicipioDTO(Municipio municipio) {
        this.id = municipio.getId();
        this.codigo = municipio.getCodigo();
        this.nombre = municipio.getNombre();
        this.idDepartamento = municipio.getIdDepartam().getId();
        this.distritos = municipio.getDistritos().stream()
                .map(DistritoDTO::new)
                .collect(Collectors.toList());
    }
}
