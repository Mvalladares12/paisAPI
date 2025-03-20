package org.mv.distrito;

public class DistritoDTO {
    public Integer id;
    public String codigo;
    public String nombre;
    public Integer idMunicipio; // ID del municipio al que pertenece

    public DistritoDTO(Distrito distrito) {
        this.id = distrito.getId();
        this.codigo = distrito.getCodigo();
        this.nombre = distrito.getNombre();
        this.idMunicipio = distrito.getIdMunicipio().getId();
    }
}