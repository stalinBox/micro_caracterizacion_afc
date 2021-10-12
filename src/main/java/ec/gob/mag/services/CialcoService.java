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

import ec.gob.mag.domain.Cialco;
import ec.gob.mag.domain.constraint.CialcoUpdate;
import ec.gob.mag.enums.Constante;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.CialcoRepository;
import ec.gob.mag.util.ConvertEntityUtil;

@Service("cialcoService")
public class CialcoService {

	@Autowired
	@Qualifier("cialcoRepository")
	private CialcoRepository cialcoRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	@Qualifier("convertEntityUtil")
	private ConvertEntityUtil convertEntityUtil;

	public void clearObjectLazyVariables(Cialco org) {
		org.getCialcoOfertaProductiva().stream().map(u -> {
			u.setCialco(null);
			return u;
		}).collect(Collectors.toList());

		org.getFuncionamientoCialco().stream().map(u -> {
			u.setCialco(null);
			return u;
		}).collect(Collectors.toList());

		org.getOrganizacionCialco().stream().map(u -> {
			u.setCialco(null);
			return u;
		}).collect(Collectors.toList());
	}

	public List<Cialco> clearListLazyVariables(List<Cialco> orgs) {
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
	public Optional<Cialco> findByIdAll(Long id) {
		Optional<Cialco> cialco = cialcoRepository.findById(id);
		if (!cialco.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(cialco.get());
		return cialco;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<Cialco> findById(Long id, Boolean eliminado, Integer estado) {
		Optional<Cialco> cialco = cialcoRepository.findByCiaIdAndCiaEliminadoAndCiaEstadoEquals(id, eliminado, estado);
		if (!cialco.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(cialco.get());
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
	 * Actualiza un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public Cialco update(CialcoUpdate newCialco) {
		Optional<Cialco> oldCialco = findById(newCialco.getCiaId(), false, Constante.REGISTRO_ACTIVO.getCodigo());
		copyNonNullProperties(newCialco, oldCialco.get());
		return cialcoRepository.save(oldCialco.get());
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
