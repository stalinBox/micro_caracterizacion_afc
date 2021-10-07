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

import ec.gob.mag.domain.OrganizacionCialco;
import ec.gob.mag.enums.Constante;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.OrganizacionCialcoRepository;

@Service("organizacionCialcoService")
public class OrganizacionCialcoService {

	@Autowired
	@Qualifier("organizacionCialcoRepository")
	private OrganizacionCialcoRepository organizacionCialcoRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(OrganizacionCialco org) {
//		org.setCialcoByciaId(null);
	}

	public List<OrganizacionCialco> clearListLazyVariables(List<OrganizacionCialco> orgs) {
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
	public List<OrganizacionCialco> findAll() {
		List<OrganizacionCialco> organizacioncialco = organizacionCialcoRepository
				.findByOciEliminadoAndOciEstadoEquals(false, Constante.REGISTRO_ACTIVO.getCodigo());
		if (organizacioncialco.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(organizacioncialco);
		return organizacioncialco;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<OrganizacionCialco> findById(Long id) {
		Optional<OrganizacionCialco> organizacioncialco = organizacionCialcoRepository
				.findByOciIdAndOciEliminadoAndOciEstadoEquals(id, false, Constante.REGISTRO_ACTIVO.getCodigo());
		if (!organizacioncialco.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		return organizacioncialco;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public OrganizacionCialco save(OrganizacionCialco organizacioncialco) {
		return organizacionCialcoRepository.save(organizacioncialco);
	}

	/**
	 * Update un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public OrganizacionCialco update(OrganizacionCialco organizacioncialco) {
		Optional<OrganizacionCialco> oldEntity = findById(organizacioncialco.getOciId());
		copyNonNullProperties(organizacioncialco, oldEntity.get());
		return organizacionCialcoRepository.save(oldEntity.get());
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
