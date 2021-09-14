package ec.gob.mag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.domain.Cialco;

@Repository("cialcoRepository")
public interface CialcoRepository extends CrudRepository<Cialco, Long> {

	List<Cialco> findByCiaEliminadoAndCiaEstadoEquals(boolean ciaEliminado, Integer ciaEstado);

	Optional<Cialco> findByCiaIdAndCiaEliminadoAndCiaEstadoEquals(Long id, boolean ciaEliminado, Integer ciaEstado);
}