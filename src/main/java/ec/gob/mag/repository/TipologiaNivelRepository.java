//package ec.gob.mag.repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import ec.gob.mag.domain.TipologiaNivel;
//
//@Repository("tipologiaNivelRepository")
//
//public interface TipologiaNivelRepository extends CrudRepository<TipologiaNivel, Long> {
//
//	List<TipologiaNivel> findByTipEliminadoAndTipEstadoEquals(boolean tipEliminado, Integer tipEstado);
//
//	Optional<TipologiaNivel> findByTipIdAndTipEliminadoAndTipEstadoEquals(Long id, boolean tipEliminado,
//			Integer tipEstado);
//}
