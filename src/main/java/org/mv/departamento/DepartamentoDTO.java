package org.mv.departamento;

import org.mv.municipio.MunicipioDTO;

import java.util.List;
import java.util.stream.Collectors;

public class DepartamentoDTO {
    public Integer id;
    public String codigo;
    public String nombre;
    public List<MunicipioDTO> municipios;

    public DepartamentoDTO(Departamento departamento) {
        this.id = departamento.getId();
        this.codigo = departamento.getCodigo();
        this.nombre = departamento.getNombre();
        this.municipios = departamento.getMunicipios().stream()
                .map(MunicipioDTO::new)
                .collect(Collectors.toList());
    }
}