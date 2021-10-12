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

import ec.gob.mag.domain.CertificacionOfertaProd;
import ec.gob.mag.enums.Constante;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.CertificacionOfertaProdRepository;

@Service("certificacionOfertaProdService")
public class CertificacionOfertaProdService {

	@Autowired
	@Qualifier("certificacionOfertaProdRepository")
	private CertificacionOfertaProdRepository certificacionOfertaProdRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(CertificacionOfertaProd orgs) {
		orgs.getOfertaDetalle().getCertificacionOfertaProd().stream().map(u -> {
			u.setOfertaDetalle(null);
			return u;
		}).collect(Collectors.toList());
	}

	public List<CertificacionOfertaProd> clearListLazyVariables(List<CertificacionOfertaProd> orgs) {
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
	public List<CertificacionOfertaProd> findAll() {
		List<CertificacionOfertaProd> certificacionofertaprod = certificacionOfertaProdRepository
				.findByCopEliminadoAndCopEstadoEquals(false, Constante.REGISTRO_ACTIVO.getCodigo());
		if (certificacionofertaprod.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(certificacionofertaprod);
		return certificacionofertaprod;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<CertificacionOfertaProd> findByIdAll(Long id) {
		Optional<CertificacionOfertaProd> cialco = certificacionOfertaProdRepository.findById(id);
		if (!cialco.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
//		clearObjectLazyVariables(cialco.get());
		return cialco;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<CertificacionOfertaProd> findById(Long id) {
		Optional<CertificacionOfertaProd> certificacionofertaprod = certificacionOfertaProdRepository
				.findByCopIdAndCopEliminadoAndCopEstadoEquals(id, false, Constante.REGISTRO_ACTIVO.getCodigo());
		if (!certificacionofertaprod.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
//		clearObjectLazyVariables(certificacionofertaprod.get());
		return certificacionofertaprod;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return entidad: La entidad Guardada
	 */
	public CertificacionOfertaProd save(CertificacionOfertaProd certificacionofertaprod) {
		return certificacionOfertaProdRepository.save(certificacionofertaprod);
	}

	/**
	 * Acualiza un registro
	 * 
	 * @param entidad: Contiene todos los campos a actualizar
	 * @return entidad: devuelve el regitro actualizado
	 */
	public CertificacionOfertaProd update(CertificacionOfertaProd entidad) {
		Optional<CertificacionOfertaProd> oldCertifOfertaProd = findById(entidad.getCopId());
		copyNonNullProperties(entidad, oldCertifOfertaProd.get());
		return certificacionOfertaProdRepository.save(oldCertifOfertaProd.get());
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
