package ec.gob.mag.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.domain.Cialco;
import ec.gob.mag.enums.Constante;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.CialcoRepository;

@Service("cialcoService")
public class CialcoService {

	@Autowired
	@Qualifier("cialcoRepository")
	private CialcoRepository cialcoRepository;

	@Autowired
	private MessageSource messageSource;

//	public void clearObjectLazyVariables(Cialco org) {
//		org.getCialcoOfertaProductiva().stream().map(u -> {
//			u.setCialco(null);
//			return u;
//		}).collect(Collectors.toList());
//
//		org.getFuncionamientoCialco().stream().map(u -> {
//			u.setCialco(null);
//			return u;
//		}).collect(Collectors.toList());
//
//		org.getOrganizacionCialco().stream().map(u -> {
//			u.setCialco(null);
//			return u;
//		}).collect(Collectors.toList());
//	}

	public List<Cialco> clearListLazyVariables(List<Cialco> orgs) {
		if (orgs != null)
			orgs = orgs.stream().map(u -> {
//				clearObjectLazyVariables(u);
				return u;
			}).collect(Collectors.toList());
		return orgs;
	}

	/**
	 * Metodo para encontrar todos los registros
	 * 
	 * @return Todos los registros de la tabla
	 */
	public List<Cialco> findAll() {
		List<Cialco> cialco = cialcoRepository.findByCiaEliminadoAndCiaEstadoEquals(false,
				Constante.REGISTRO_ACTIVO.getCodigo());
		if (cialco.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(cialco);
		return cialco;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Cialco> findById(Long id) {
		Optional<Cialco> cialco = cialcoRepository.findByCiaIdAndCiaEliminadoAndCiaEstadoEquals(id, false,
				Constante.REGISTRO_ACTIVO.getCodigo());
		if (!cialco.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
//		clearObjectLazyVariables(cialco.get());
		return cialco;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public Cialco save(Cialco cialco) {
		return cialcoRepository.save(cialco);
	}

	/**
	 * Update un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
//	public Optional<Cialco> Update(Cialco cialco) {
//		return cialcoRepository.findByCiaIdAndCiaEliminadoAndCiaEstadoEquals(cialco.getCiaId(), false,Constante.REGISTRO_ACTIVO.getCodigo()).map(oldItem ->{
//		})
//	}

//	   public Optional<Item> update( Long id, Item newItem) {
//	        // Only update an item if it can be found first.
//	        return repository.findById(id)
//	                .map(oldItem -> {
//	                   Item updated = oldItem.updateWith(newItem);
//	                   return repository.save(updated);
//	                });
//	    }  
}
