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

import ec.gob.mag.domain.CialcoOfertaProductiva;
import ec.gob.mag.enums.Constante;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.CialcoOfertaProductivaRepository;

@Service("cialcoOfertaProductivaService")
public class CialcoOfertaProductivaService {

	@Autowired
	@Qualifier("cialcoOfertaproductivaRepository")
	private CialcoOfertaProductivaRepository cialcoOfertaproductivaRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(CialcoOfertaProductiva org) {
//		org.setCialcoByciaId(null);
	}

	public List<CialcoOfertaProductiva> clearListLazyVariables(List<CialcoOfertaProductiva> orgs) {
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
	public List<CialcoOfertaProductiva> findAll() {
		List<CialcoOfertaProductiva> cialcoofertaproductiva = cialcoOfertaproductivaRepository
				.findByCiopEliminadoAndCiopEstadoEquals(false, Constante.REGISTRO_ACTIVO.getCodigo());
		if (cialcoofertaproductiva.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(cialcoofertaproductiva);
		return cialcoofertaproductiva;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<CialcoOfertaProductiva> findByIdAll(Long id) {
		Optional<CialcoOfertaProductiva> cop = cialcoOfertaproductivaRepository.findById(id);
		if (!cop.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(cop.get());
		return cop;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<CialcoOfertaProductiva> findById(Long id) {
		Optional<CialcoOfertaProductiva> cialcoofertaproductiva = cialcoOfertaproductivaRepository
				.findByCiopIdAndCiopEliminadoAndCiopEstadoEquals(id, false, Constante.REGISTRO_ACTIVO.getCodigo());
		if (!cialcoofertaproductiva.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		return cialcoofertaproductiva;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public CialcoOfertaProductiva save(CialcoOfertaProductiva cialcoofertaproductiva) {
		return cialcoOfertaproductivaRepository.save(cialcoofertaproductiva);
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public CialcoOfertaProductiva update(CialcoOfertaProductiva newEntity) {
		Optional<CialcoOfertaProductiva> oldEntity = findById(newEntity.getCiopId());
		copyNonNullProperties(newEntity, oldEntity.get());
		return cialcoOfertaproductivaRepository.save(oldEntity.get());
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
