package ec.gob.mag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.domain.CialcoOfertaProductiva;

@Repository("cialcoOfertaproductivaRepository")

public interface CialcoOfertaProductivaRepository extends CrudRepository<CialcoOfertaProductiva, Long> {

	List<CialcoOfertaProductiva> findByCiopEliminadoAndCiopEstadoEquals(boolean ociEliminado, Integer ociEstado);

	Optional<CialcoOfertaProductiva> findByCiopIdAndCiopEliminadoAndCiopEstadoEquals(Long id, boolean ociEliminado,
			Integer ociEstado);
}
