package ec.gob.mag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.domain.MesCosecha;

@Repository("mesCosechaRepository")
public interface MesCosechaRepository extends CrudRepository<MesCosecha, Long> {

	List<MesCosecha> findByMcoEliminadoAndMcoEstadoEquals(boolean mcoEliminado, Integer mcoEstado);

	Optional<MesCosecha> findByMcoIdAndMcoEliminadoAndMcoEstadoEquals(Long id, boolean mcoEliminado, Integer mcoEstado);
}
