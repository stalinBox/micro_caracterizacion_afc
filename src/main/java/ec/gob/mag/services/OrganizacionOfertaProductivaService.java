package ec.gob.mag.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.domain.OrganizacionOfertaProductiva;
import ec.gob.mag.enums.Constante;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.OrganizacionOfertaProductivaRepository;

@Service("organizacionOfertaProductivaService")
public class OrganizacionOfertaProductivaService {

	@Autowired
	@Qualifier("organizacionOfertaProductivaRepository")
	private OrganizacionOfertaProductivaRepository organizacionOfertaProductivaRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(OrganizacionOfertaProductiva org) {
		org.getOfertaDetalle().stream().map(u -> {
			u.setCertificacionOfertaProd(null);
			return u;
		}).collect(Collectors.toList());
	}

	public List<OrganizacionOfertaProductiva> clearListLazyVariables(List<OrganizacionOfertaProductiva> orgs) {
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
	public List<OrganizacionOfertaProductiva> findAll() {
		List<OrganizacionOfertaProductiva> organizacionofertaproductiva = organizacionOfertaProductivaRepository
				.findByOopEliminadoAndOopEstadoEquals(false, Constante.REGISTRO_ACTIVO.getCodigo());
		if (organizacionofertaproductiva.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(organizacionofertaproductiva);
		return organizacionofertaproductiva;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<OrganizacionOfertaProductiva> findById(Long id) {
		Optional<OrganizacionOfertaProductiva> organizacionofertaproductiva = organizacionOfertaProductivaRepository
				.findByOopIdAndOopEliminadoAndOopEstadoEquals(id, false, Constante.REGISTRO_ACTIVO.getCodigo());
		if (!organizacionofertaproductiva.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		return organizacionofertaproductiva;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public OrganizacionOfertaProductiva save(OrganizacionOfertaProductiva organizacionofertaproductiva) {
		return organizacionOfertaProductivaRepository.save(organizacionofertaproductiva);
	}
}
