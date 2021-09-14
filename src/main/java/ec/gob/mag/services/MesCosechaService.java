package ec.gob.mag.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import ec.gob.mag.domain.MesCosecha;
import ec.gob.mag.enums.Constante;
import ec.gob.mag.exception.MyNotFoundException;
import ec.gob.mag.repository.MesCosechaRepository;

@Service("mesCosechaService")
public class MesCosechaService {

	@Autowired
	@Qualifier("mesCosechaRepository")
	private MesCosechaRepository mesCosechaRepository;

	@Autowired
	private MessageSource messageSource;

	public void clearObjectLazyVariables(MesCosecha org) {
//		org.setOfertaDetalleByOopdId(null);
	}

	public List<MesCosecha> clearListLazyVariables(List<MesCosecha> orgs) {
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
	public List<MesCosecha> findAll() {
		List<MesCosecha> mescosecha = mesCosechaRepository.findByMcoEliminadoAndMcoEstadoEquals(false, 11);
		if (mescosecha.isEmpty())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					this.getClass().getName()));
		clearListLazyVariables(mescosecha);
		return mescosecha;
	}

	/**
	 * Busca un registro por Id
	 * 
	 * @param id: Identificador del registro
	 * @return entidad: Retorna todos los registros filtrados por el parámetros de
	 *         entrada
	 */
	public Optional<MesCosecha> findById(Long id) {
		Optional<MesCosecha> mescosecha = mesCosechaRepository.findByMcoIdAndMcoEliminadoAndMcoEstadoEquals(id, false,
				Constante.REGISTRO_ACTIVO.getCodigo());
		if (!mescosecha.isPresent())
			throw new MyNotFoundException(String.format(
					messageSource.getMessage("error.entity_cero_exist.message", null, LocaleContextHolder.getLocale()),
					id));
		return mescosecha;
	}

	/**
	 * Guarda un registro
	 * 
	 * @param entidad: Contiene todos campos de la entidad para guardar
	 * @return catalogo: La entidad Guardada
	 */
	public MesCosecha save(MesCosecha mescosecha) {
		return mesCosechaRepository.save(mescosecha);
	}
}
