package org.mv.repositorio;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.mv.DTO.CreateDepartamentoDTO;
import org.mv.entidades.Departamento;
import org.mv.services.DepartamentoMapper;

@ApplicationScoped
public class DepartamentoRepository implements PanacheRepository<Departamento> {
    @Inject
    DepartamentoMapper departamentoMapper;

    public Departamento update(Departamento depa) {
        Departamento updateDepa=findById(depa.getId());
        if(updateDepa!=null){
            updateDepa.setNombre(depa.getNombre());
            updateDepa.setCodigo(depa.getCodigo());
            flush();
            return updateDepa;
        }else {
            throw  new RuntimeException("Departamento nao encontrado");
        }
    }
}
