package org.mv.municipio;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.mv.entidades.Municipio;

@ApplicationScoped
public class MunicipioRepository implements PanacheRepository<Municipio> {
}
