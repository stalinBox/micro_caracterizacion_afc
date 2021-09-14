package ec.gob.mag.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ec.gob.mag.domain.OrganizacionCialco;

@Repository("organizacionCialcoRepository")
public interface OrganizacionCialcoRepository extends CrudRepository<OrganizacionCialco, Long> {

	List<OrganizacionCialco> findByOciEliminadoAndOciEstadoEquals(boolean ociEliminado, Integer ociEstado);

	Optional<OrganizacionCialco> findByOciIdAndOciEliminadoAndOciEstadoEquals(Long id, boolean ociEliminado,
			Integer ociEstado);
}
