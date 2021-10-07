package ec.gob.mag.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

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
import ec.gob.mag.domain.constraint.CialcoAudit;
import ec.gob.mag.domain.constraint.CialcoCreate;
import ec.gob.mag.domain.constraint.CialcoUpdate;
import ec.gob.mag.domain.pagination.DataTableRequest;
import ec.gob.mag.domain.dto.CialcoDTO;
import ec.gob.mag.domain.pagination.AppUtil;
import ec.gob.mag.domain.pagination.DataTableResults;
import ec.gob.mag.domain.pagination.PaginationCriteria;
import ec.gob.mag.enums.Constante;
import ec.gob.mag.services.CialcoService;
import ec.gob.mag.util.ConvertEntityUtil;
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
	@Qualifier("convertEntityUtil")
	private ConvertEntityUtil convertEntityUtil;

	@Autowired
	@Qualifier("util")
	private Util util;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
	@RequestMapping(value = "/state-record/", method = RequestMethod.PUT)
	@ApiOperation(value = "Gestionar estado del registro disable={11 ACTIVO,12 INACTIVO}, delete={false, true}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseController> stateCialco(@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody CialcoAudit cialcoAudit) {
		Cialco auditCialco = cialcoService.findByIdAll(cialcoAudit.getCiaId())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("Cialco", "Id",
						cialcoAudit.getCiaId().toString()));

		auditCialco.setCiaEliminado(cialcoAudit.getCiaEliminado());
		auditCialco.setCiaEstado(cialcoAudit.getCiaEstado());
		auditCialco.setCiaActUsu(cialcoAudit.getCiaActUsu());

		Cialco cialcoDel = cialcoService.save(auditCialco);
		LOGGER.info("cialco Delete : " + cialcoAudit.getCiaId() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(cialcoDel.getCiaId(), cialcoAudit.getState()));
	}

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
//	@RequestMapping(value = "/delete/{id}/{usuId}", method = RequestMethod.DELETE)
//	@ApiOperation(value = "Remove cialcos by id")
//	@ResponseStatus(HttpStatus.OK)
//	public ResponseEntity<ResponseController> deleteCialco(@RequestHeader(name = "Authorization") String token,
//			@Validated @PathVariable Long id, @PathVariable Integer usuId) {
//		Cialco deleteCialco = cialcoService.findById(id, true, Constante.REGISTRO_INACTIVO.getCodigo())
//				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("Cialco", "Id", id.toString()));
//		deleteCialco.setCiaEliminado(true);
//		deleteCialco.setCiaEstado(Constante.REGISTRO_INACTIVO.getCodigo());
//		deleteCialco.setCiaActUsu(usuId);
//		Cialco cialcoDel = cialcoService.save(deleteCialco);
//		LOGGER.info("cialco Delete : " + id + " usuario: " + util.filterUsuId(token));
//		return ResponseEntity.ok(new ResponseController(cialcoDel.getCiaId(), "eliminado"));
//	}

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
//	@RequestMapping(value = "/inhabilitar/{id}/{usuId}", method = RequestMethod.DELETE)
//	@ApiOperation(value = "Remove cialcos by id")
//	@ResponseStatus(HttpStatus.OK)
//	public ResponseEntity<ResponseController> inhabilitarCialco(@RequestHeader(name = "Authorization") String token,
//			@Validated @PathVariable Long id, @PathVariable Integer usuId) {
//		Cialco deleteCialco = cialcoService.findById(id, true, Constante.REGISTRO_ACTIVO.getCodigo())
//				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("Cialco", "Id", id.toString()));
//		deleteCialco.setCiaEstado(Constante.REGISTRO_INACTIVO.getCodigo());
//		deleteCialco.setCiaActUsu(usuId);
//		Cialco cialcoDel = cialcoService.save(deleteCialco);
//		LOGGER.info("cialco inhabilitar : " + id + " usuario: " + util.filterUsuId(token));
//		return ResponseEntity.ok(new ResponseController(cialcoDel.getCiaId(), "eliminado"));
//	}

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
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get Cialco by id", response = Cialco.class)
	public ResponseEntity<Optional<Cialco>> findById(@RequestHeader(name = "Authorization") String token,
			@Validated @PathVariable Long id) {
		Optional<Cialco> cialco = cialcoService.findById(id, false, Constante.REGISTRO_ACTIVO.getCodigo());
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
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> update(@RequestHeader(name = "Authorization") String token, @PathVariable Integer usuId,
			@RequestBody CialcoUpdate updateCialco) {
		Cialco cialcoUpdate = cialcoService.update(updateCialco, usuId);
		cialcoUpdate.setCiaActUsu(usuId);
		LOGGER.info("cialco Update: " + cialcoUpdate + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(cialcoUpdate.getCiaId(), "Actualizado"));
	}

	/**
	 * Inserta un nuevo registro en la entidad
	 * 
	 * @param entidad: entidad a insertar
	 * @return ResponseController: Retorna el id creado
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	@ApiOperation(value = "Crear nuevo registro", response = ResponseController.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> postCialco(@Validated @RequestBody CialcoCreate cialco,
			@RequestHeader(name = "Authorization") String token) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, IOException {
		Cialco productorValidado = convertEntityUtil.ConvertSingleEntityGET(Cialco.class, (Object) cialco);
		Cialco off = cialcoService.save(productorValidado);
		LOGGER.info("cialco Cialco create: " + cialco + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(off.getCiaId(), "Creado"));
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findAllPaginated/", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity<?> listAplicationPaginated(HttpServletRequest request,
			@RequestHeader(name = "Authorization") String token) {
		DataTableRequest<CialcoDTO> dataTableInRQ = new DataTableRequest<CialcoDTO>(request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		String baseQuery = "SELECT  ROW_NUMBER() OVER (ORDER BY c.cia_id ) AS nro, \n"
				+ "CAST (c.cia_id AS VARCHAR) , \n" + "CAST (cop.ciop_cat_id_oferta AS VARCHAR) ,  \n"
				+ "CAST (ubi_id_provincia AS VARCHAR) , \n" + "CAST (ubi_id_canton AS VARCHAR) , \n"
				+ "CAST (ubi_id_parroquia AS VARCHAR) ,  \n" + "CAST (org_id AS VARCHAR) ,\n"
				+ "CAST (soc_id AS VARCHAR) ,\n" + "CAST (tip_id AS VARCHAR) ,\n"
				+ "CAST (per_identificacion AS VARCHAR) ,\n" + "CAST (per_nombres AS VARCHAR) , \n"
				+ "CAST (cia_nombre AS VARCHAR) ,\n" + "CAST (cia_descripcion AS VARCHAR) ,\n"
				+ "CAST (cia_sect_referencia AS VARCHAR) , \n" + "CAST (cia_id_cat_frecuencia AS VARCHAR) ,\n"
				+ "CAST (cia_direccion AS VARCHAR) ,\n" + "CAST (cia_telefono AS VARCHAR) ,\n"
				+ "CAST (cia_celular AS VARCHAR) , \n" + "CAST (cia_correo AS VARCHAR) ,\n"
				+ "CAST (cia_cord_x AS VARCHAR) ,\n" + "CAST (cia_cord_y AS VARCHAR) ,\n"
				+ "CAST (cia_cord_z AS VARCHAR) , \n" + "CAST (cia_cord_hemisferio AS VARCHAR) ,\n"
				+ "CAST (cia_cord_zona AS VARCHAR) ,\n" + "CAST (cia_cord_latitud AS VARCHAR) , \n"
				+ "CAST (cia_cord_longitud AS VARCHAR) ,\n" + "CAST (cia_estado_negocio AS VARCHAR) , \n"
				+ "CAST (cia_negocio_observacion AS VARCHAR) , \n" + "CAST (c.cia_estado AS VARCHAR) ,\n"
				+ "CAST (c.cia_eliminado AS VARCHAR) ,\n"
				+ "(SELECT count(c.cia_id)  FROM sc_gopagro.cialco c inner join sc_gopagro.cialco_oferta_productiva cop ON c.cia_id = cop.cia_id ) AS totalRecords\n"
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

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
