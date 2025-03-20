package org.mv.departamento;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DepartamentoRepository implements PanacheRepository<Departamento> {
    @Inject
    DepartamentoMapper departamentoMapper;

    public CreateDepartamentoDTO update(Long id, CreateDepartamentoDTO depa) {
        var updateDepa=findById(id);
        if(updateDepa!=null){
            departamentoMapper.updateDepartamento(depa,updateDepa);
            persist(updateDepa);
            return departamentoMapper.present(updateDepa);
        }else {
            throw  new RuntimeException("Departamento nao encontrado");
        }
    }
}
