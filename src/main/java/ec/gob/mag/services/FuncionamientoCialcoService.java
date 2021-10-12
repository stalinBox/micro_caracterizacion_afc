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

import ec.gob.mag.domain.FuncionamientoCialco;
import ec.gob.mag.enums.Constante;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.FuncionamientoCialcoRepository;

@Service("funcionamientoCialcoService")
public class FuncionamientoCialcoService {

	@Autowired
	@Qualifier("funcionamientoCialcoRepository")
	private FuncionamientoCialcoRepository funcionamientoCialcoRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(FuncionamientoCialco org) {
//		org.setCialcoByciaId(null);
	}

	public List<FuncionamientoCialco> clearListLazyVariables(List<FuncionamientoCialco> orgs) {
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
	public List<FuncionamientoCialco> findAll() {
		List<FuncionamientoCialco> funcionamientocialco = funcionamientoCialcoRepository
				.findByFciaEliminadoAndFciaEstadoEquals(false, Constante.REGISTRO_ACTIVO.getCodigo());
		if (funcionamientocialco.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(funcionamientocialco);
		return funcionamientocialco;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<FuncionamientoCialco> findByIdAll(Long id) {
		Optional<FuncionamientoCialco> fun = funcionamientoCialcoRepository.findById(id);
		if (!fun.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(fun.get());
		return fun;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<FuncionamientoCialco> findById(Long id) {
		Optional<FuncionamientoCialco> funcionamientocialco = funcionamientoCialcoRepository
				.findByFciaIdAndFciaEliminadoAndFciaEstadoEquals(id, false, Constante.REGISTRO_ACTIVO.getCodigo());
		if (!funcionamientocialco.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		return funcionamientocialco;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public List<FuncionamientoCialco> findByCiaId(Long id) {
		List<FuncionamientoCialco> funcionamientocialco = funcionamientoCialcoRepository
				.findByCialco_CiaIdAndFciaEliminadoAndFciaEstadoEquals(id, false,
						Constante.REGISTRO_ACTIVO.getCodigo());
		if (funcionamientocialco.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		return funcionamientocialco;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public FuncionamientoCialco save(FuncionamientoCialco funcionamientocialco) {
		return funcionamientoCialcoRepository.save(funcionamientocialco);
	}

	/**
	 * Update un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public FuncionamientoCialco update(FuncionamientoCialco newEntity) {
		Optional<FuncionamientoCialco> oldEntity = findById(newEntity.getFciaId());
		copyNonNullProperties(newEntity, oldEntity.get());
		return funcionamientoCialcoRepository.save(oldEntity.get());
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
