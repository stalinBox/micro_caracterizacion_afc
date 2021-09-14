package ec.gob.mag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.domain.OrganizacionOfertaProductiva;

@Repository("organizacionOfertaProductivaRepository")
public interface OrganizacionOfertaProductivaRepository extends CrudRepository<OrganizacionOfertaProductiva, Long> {

	List<OrganizacionOfertaProductiva> findByOopEliminadoAndOopEstadoEquals(boolean oopEliminado, Integer oopEstado);

	Optional<OrganizacionOfertaProductiva> findByOopIdAndOopEliminadoAndOopEstadoEquals(Long id, boolean oopEliminado,
			Integer oopEstado);
}
