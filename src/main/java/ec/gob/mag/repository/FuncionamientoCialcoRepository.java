package ec.gob.mag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.domain.FuncionamientoCialco;

@Repository("funcionamientoCialcoRepository")
public interface FuncionamientoCialcoRepository extends CrudRepository<FuncionamientoCialco, Long> {

	List<FuncionamientoCialco> findByFciaEliminadoAndFciaEstadoEquals(boolean fciaEliminado, Integer fciaEstado);

	List<FuncionamientoCialco> findByCialco_CiaIdAndFciaEliminadoAndFciaEstadoEquals(Long ciaId, boolean fciaEliminado,
			Integer fciaEstado);

	Optional<FuncionamientoCialco> findByFciaIdAndFciaEliminadoAndFciaEstadoEquals(Long id, boolean fciaEliminado,
			Integer fciaEstado);
}
