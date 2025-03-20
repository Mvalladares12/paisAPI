package org.mv.distrito;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DistritoRepository implements PanacheRepository<Distrito> {
}
