package org.mv.departamento;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.mv.municipio.Municipio;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "departamento")
public class Departamento{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departamento_id_gen")
    @SequenceGenerator(name = "departamento_id_gen", sequenceName = "departamento_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "codigo", nullable = false, length = 2)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @OneToMany(mappedBy = "idDepartam")
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private List<Municipio> municipios;

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

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

}