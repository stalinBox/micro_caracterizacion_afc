package ec.gob.mag.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ec.gob.mag.controller.CialcoController;
import ec.gob.mag.domain.Cialco;
import ec.gob.mag.domain.pagination.DataTableRequest;
import ec.gob.mag.domain.dto.CialcoDTO;
import ec.gob.mag.domain.dto.CialcoTest;
import ec.gob.mag.domain.pagination.AppUtil;
import ec.gob.mag.domain.pagination.DataTableResults;
import ec.gob.mag.domain.pagination.PaginationCriteria;
import ec.gob.mag.services.CialcoService;
import ec.gob.mag.util.ResponseController;
import ec.gob.mag.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/cialco")
@Api(value = "Rest Api Cialco", tags = "CIALCO")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 201, message = "Objeto creado"),
		@ApiResponse(code = 404, message = "Recurso no encontrado") })
public class CialcoController implements ErrorController {

	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(CialcoController.class);

	@Autowired
	@Qualifier("cialcoService")
	private CialcoService cialcoService;

	@Autowired
	@Qualifier("responseController")
	private ResponseController responseController;

	@Autowired
	@Qualifier("util")
	private Util util;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findAllPaginated/", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity<?> listAplicationPaginated(HttpServletRequest request,
			@RequestHeader(name = "Authorization") String token) {

		DataTableRequest<CialcoDTO> dataTableInRQ = new DataTableRequest<CialcoDTO>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		String baseQuery = "SELECT  ROW_NUMBER() OVER (ORDER BY c.cia_id ) AS nro, c.cia_id, cop.ciop_cat_id_oferta,  ubi_id_provincia, ubi_id_canton, ubi_id_parroquia,  \r\n"
				+ "org_id,   soc_id,   tip_id,   per_identificacion,   per_nombres, \r\n"
				+ "cia_nombre,   cia_descripcion,   cia_sect_referencia, \r\n"
				+ "cia_id_cat_frecuencia,   cia_direccion,   cia_telefono,   cia_celular, \r\n"
				+ "cia_correo,   cia_cord_x,   cia_cord_y,   cia_cord_z, \r\n"
				+ "cia_cord_hemisferio,   cia_cord_zona,   cia_cord_latitud, \r\n"
				+ "cia_cord_longitud,   cia_estado_negocio,   cia_negocio_observacion, \r\n"
				+ "c.cia_estado,   c.cia_eliminado, (SELECT count(c.cia_id) \r\n"
				+ "FROM sc_gopagro.cialco c inner join sc_gopagro.cialco_oferta_productiva cop ON c.cia_id = cop.cia_id ) AS totalRecords\r\n"
				+ "FROM sc_gopagro.cialco c inner join sc_gopagro.cialco_oferta_productiva cop ON c.cia_id = cop.cia_id ORDER BY c.cia_id";
		String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
		Query query = entityManager.createNativeQuery(paginatedQuery, CialcoDTO.class);
		List<CialcoDTO> userList = query.getResultList();
		DataTableResults<CialcoDTO> dataTableResult = new DataTableResults<CialcoDTO>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(userList);
		if (!AppUtil.isObjectEmpty(userList)) {
			dataTableResult.setRecordsTotal(((CialcoDTO) userList.get(0)).getTotalRecords().toString());
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty())
				dataTableResult.setRecordsFiltered(((CialcoDTO) userList.get(0)).getTotalRecords().toString());
			else
				dataTableResult.setRecordsFiltered(Integer.toString(userList.size()));
		}
		return ResponseEntity.ok((new Gson()).toJson(dataTableResult));
	}

	/**
	 * Busca todos los registros de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return Entidad: Retorna todos los registros
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = Cialco.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Cialco>> findAll(@RequestHeader(name = "Authorization") String token) {
		List<Cialco> cialco = cialcoService.findAll();
		LOGGER.info("cialco findAll: " + cialco.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(cialco);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Cialco by id", response = Cialco.class)
	public ResponseEntity<Optional<Cialco>> findById(@RequestHeader(name = "Authorization") String token,
			@Validated @PathVariable Long id) {
		Optional<Cialco> cialco = cialcoService.findById(id);
		LOGGER.info("cialco FindById: " + id + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(cialco);
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param usuId:   Identificador del usuario que va a actualizar
	 * 
	 * @param entidad: entidad a actualizar
	 * @return ResponseController: Retorna el id actualizado
	 */
	@PutMapping(value = "/update/{usuId}")
	@ApiOperation(value = "Actualizar los registros", response = ResponseController.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@RequestHeader(name = "Authorization") String token, @PathVariable Integer usuId,
			@RequestBody Cialco updateCialco) {
		updateCialco.setCiaActUsu(usuId);
		Cialco cialcoUpdate = cialcoService.save(updateCialco);
		LOGGER.info("cialco Update: " + cialcoUpdate + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(cialcoUpdate.getCiaId(), "Actualizado"));
	}

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
	@RequestMapping(value = "/delete/{id}/{usuId}", method = RequestMethod.POST)
	@ApiOperation(value = "Remove cialcos by id")
	public ResponseEntity<ResponseController> deleteCialco(@RequestHeader(name = "Authorization") String token,
			@Validated @PathVariable Long id, @PathVariable Integer usuId) {
		Cialco deleteCialco = cialcoService.findById(id)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("Cialco", "Id", id.toString()));
		deleteCialco.setCiaEliminado(true);
		deleteCialco.setCiaActUsu(usuId);
		Cialco cialcoDel = cialcoService.save(deleteCialco);
		LOGGER.info("cialco Delete : " + id + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(cialcoDel.getCiaId(), "eliminado"));
	}

	/**
	 * Inserta un nuevo registro en la entidad
	 * 
	 * @param entidad: entidad a insertar
	 * @return ResponseController: Retorna el id creado
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	@ApiOperation(value = "Crear nuevo registro", response = ResponseController.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> postCialco(@Validated @RequestBody Cialco cialco,
			@RequestHeader(name = "Authorization") String token) {
		Cialco off = cialcoService.save(cialco);
		LOGGER.info("cialco Cialco create: " + cialco + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(off.getCiaId(), "Creado"));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
