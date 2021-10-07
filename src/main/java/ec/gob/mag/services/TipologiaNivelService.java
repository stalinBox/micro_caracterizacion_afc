package ec.gob.mag.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.domain.TipologiaNivel;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.TipologiaNivelRepository;

@Service("tipologiaNivelService")
public class TipologiaNivelService {

	@Autowired
	@Qualifier("tipologiaNivelRepository")
	private TipologiaNivelRepository tipologiaNivelRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(TipologiaNivel org) {
		org.getCialco().stream().map(u -> {
//			u.setTipologiaByTipId(null);
//			u.setFuncionamientoCialcoList(null);
//			u.setCialcoOfertaProductivaList(null);
//			u.setOrganizacionCialcoList(null);
			return u;
		}).collect(Collectors.toList());

	}

	public List<TipologiaNivel> clearListLazyVariables(List<TipologiaNivel> orgs) {
		if (orgs != null)
			orgs = orgs.stream().map(u -> {
				clearObjectLazyVariables(u);
				return u;
			}).collect(Collectors.toList());
		return orgs;
	}

	/**
	 * Metodo para encontrar todos los registros
	 * 
	 * @return Todos los registros de la tabla
	 */
	public List<TipologiaNivel> findAll() {
		List<TipologiaNivel> tipologianivel = tipologiaNivelRepository.findByTipEliminadoAndTipEstadoEquals(false, 11);
		if (tipologianivel.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(tipologianivel);
		return tipologianivel;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<TipologiaNivel> findByIdAll(Long id) {
		Optional<TipologiaNivel> topo = tipologiaNivelRepository.findById(id);
		if (!topo.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(topo.get());
		return topo;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<TipologiaNivel> findById(Long id) {
		Optional<TipologiaNivel> tipologianivel = tipologiaNivelRepository
				.findByTipIdAndTipEliminadoAndTipEstadoEquals(id, false, 11);
		if (!tipologianivel.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		return tipologianivel;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public TipologiaNivel save(TipologiaNivel tipologianivel) {
		return tipologiaNivelRepository.save(tipologianivel);
	}

	/**
	 * Update un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public TipologiaNivel update(TipologiaNivel tipologianivel) {
		Optional<TipologiaNivel> oldEntity = findById(tipologianivel.getTipId());
		copyNonNullProperties(tipologianivel, oldEntity.get());
		return tipologiaNivelRepository.save(oldEntity.get());
	}

	public static void copyNonNullProperties(Object src, Object target) {
		BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
		Set<String> emptyNames = new HashSet<String>();
		for (java.beans.PropertyDescriptor pd : pds) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null)
				emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}
}
