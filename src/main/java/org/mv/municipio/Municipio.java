package org.mv.municipio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.mv.departamento.Departamento;
import org.mv.distrito.Distrito;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "municipio")
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "municipio_id_gen")
    @SequenceGenerator(name = "municipio_id_gen", sequenceName = "municipio_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_departam", nullable = false)
    private Departamento idDepartam;

    @Column(name = "codigo", nullable = false, length = 3)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "idMunicipio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Distrito> distritos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Departamento getIdDepartam() {
        return idDepartam;
    }

    public void setIdDepartam(Departamento idDepartam) {
        this.idDepartam = idDepartam;
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

    public List<Distrito> getDistritos() {
        return distritos;
    }

    public void setDistritos(List<Distrito> distritos) {
        this.distritos = distritos;
    }


    public String getDepartamento(){
        return this.idDepartam.getNombre();
    }
}