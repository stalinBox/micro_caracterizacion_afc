package ec.gob.mag.repository;

import ec.gob.mag.domain.CertificacionOfertaProd;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("certificacionOfertaProdRepository")

public interface CertificacionOfertaProdRepository extends CrudRepository<CertificacionOfertaProd, Long> {

	List<CertificacionOfertaProd> findByCopEliminadoAndCopEstadoEquals(boolean copEliminado, Integer copEstado);

	Optional<CertificacionOfertaProd> findByCopIdAndCopEliminadoAndCopEstadoEquals(Long id, boolean copEliminado,
			Integer copEstado);
}
