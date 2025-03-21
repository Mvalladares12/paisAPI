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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<MunicipioDTO> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<MunicipioDTO> municipios) {
        this.municipios = municipios;
    }
}