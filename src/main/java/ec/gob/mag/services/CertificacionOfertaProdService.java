package ec.gob.mag.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	public Optional<CertificacionOfertaProd> findById(Long id) {
		Optional<CertificacionOfertaProd> certificacionofertaprod = certificacionOfertaProdRepository
				.findByCopIdAndCopEliminadoAndCopEstadoEquals(id, false, Constante.REGISTRO_ACTIVO.getCodigo());
		if (!certificacionofertaprod.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(certificacionofertaprod.get());
		return certificacionofertaprod;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public CertificacionOfertaProd save(CertificacionOfertaProd certificacionofertaprod) {
		return certificacionOfertaProdRepository.save(certificacionofertaprod);
	}
}
