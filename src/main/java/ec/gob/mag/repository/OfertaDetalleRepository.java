package ec.gob.mag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.domain.OfertaDetalle;

@Repository("ofertaDetalleRepository")
public interface OfertaDetalleRepository extends CrudRepository<OfertaDetalle, Long> {

	List<OfertaDetalle> findByOopdEliminadoAndOopdEstadoEquals(boolean oopdEliminado, Integer oopdEstado);

	Optional<OfertaDetalle> findByOopdIdAndOopdEliminadoAndOopdEstadoEquals(Long id, boolean oopdEliminado,
			Integer oopdEstado);
}
