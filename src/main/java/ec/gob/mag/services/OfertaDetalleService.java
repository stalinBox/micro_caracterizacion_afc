package ec.gob.mag.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.domain.OfertaDetalle;
import ec.gob.mag.enums.Constante;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.OfertaDetalleRepository;

@Service("ofertaDetalleService")
public class OfertaDetalleService {

	@Autowired
	@Qualifier("ofertaDetalleRepository")
	private OfertaDetalleRepository ofertaDetalleRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(OfertaDetalle org) {
		org.getCertificacionOfertaProd().stream().map(u -> {
			u.setOfertaDetalle(null);
			return u;
		}).collect(Collectors.toList());
	}

	public List<OfertaDetalle> clearListLazyVariables(List<OfertaDetalle> orgs) {
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
	public List<OfertaDetalle> findAll() {
		List<OfertaDetalle> ofertadetalle = ofertaDetalleRepository.findByOopdEliminadoAndOopdEstadoEquals(false,
				Constante.REGISTRO_ACTIVO.getCodigo());
		if (ofertadetalle.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(ofertadetalle);
		return ofertadetalle;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<OfertaDetalle> findById(Long id) {
		Optional<OfertaDetalle> ofertadetalle = ofertaDetalleRepository
				.findByOopdIdAndOopdEliminadoAndOopdEstadoEquals(id, false, Constante.REGISTRO_ACTIVO.getCodigo());
		if (!ofertadetalle.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		clearObjectLazyVariables(ofertadetalle.get());
		return ofertadetalle;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public OfertaDetalle save(OfertaDetalle ofertadetalle) {
		return ofertaDetalleRepository.save(ofertadetalle);
	}
}
