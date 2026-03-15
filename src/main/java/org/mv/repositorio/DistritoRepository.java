package org.mv.repositorio;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mv.entidades.Distrito;

@ApplicationScoped
public class DistritoRepository implements PanacheRepository<Distrito> {
}
