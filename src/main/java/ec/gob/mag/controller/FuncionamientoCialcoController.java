package ec.gob.mag.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import ec.gob.mag.domain.FuncionamientoCialco;
import ec.gob.mag.domain.dto.FuncionamientoCialcoDTO;
import ec.gob.mag.domain.pagination.AppUtil;
import ec.gob.mag.domain.pagination.DataTableRequest;
import ec.gob.mag.domain.pagination.DataTableResults;
import ec.gob.mag.domain.pagination.PaginationCriteria;
import ec.gob.mag.services.FuncionamientoCialcoService;
import ec.gob.mag.util.ResponseController;
import ec.gob.mag.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/funcionamientocialco")
@Api(value = "Rest Api FuncionamientoCialco", tags = "FUNCIONAMIENTO CIALCO")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })
public class FuncionamientoCialcoController implements ErrorController {

	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(FuncionamientoCialcoController.class);

	@Autowired
	@Qualifier("funcionamientoCialcoService")
	private FuncionamientoCialcoService funcionamientoCialcoService;

	@Autowired
	@Qualifier("responseController")
	private ResponseController responseController;

	@Autowired
	@Qualifier("util")
	private Util util;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findAllPaginated/{ciaId}", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity<?> listAplicationPaginated(@PathVariable Long ciaId, HttpServletRequest request,
			@RequestHeader(name = "Authorization") String token) {

		DataTableRequest<FuncionamientoCialcoDTO> dataTableInRQ = new DataTableRequest<FuncionamientoCialcoDTO>(
				request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		String baseQuery = "SELECT ROW_NUMBER() OVER (ORDER BY fcia_id ) AS nro, fcia_id, f.cia_id, c.cia_nombre, fcia_id_cat_dia_funcionamiento, fcia_id_cat_hora_inicio, \r\n"
				+ "fcia_id_cat_hora_fin, fcia_estado, fcia_eliminado, fcia_reg_usu, (SELECT count (f.fcia_id) FROM sc_gopagro.funcionamiento_cialco f \r\n"
				+ "INNER JOIN  sc_gopagro.cialco c ON f.cia_id = c.cia_id WHERE f.cia_id = " + ciaId
				+ ") as totalRecords \r\n"
				+ "FROM sc_gopagro.funcionamiento_cialco f INNER JOIN  sc_gopagro.cialco c ON f.cia_id = c.cia_id WHERE f.cia_id = "
				+ ciaId;
		String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
		Query query = entityManager.createNativeQuery(paginatedQuery, FuncionamientoCialcoDTO.class);
		List<FuncionamientoCialcoDTO> userList = query.getResultList();
		DataTableResults<FuncionamientoCialcoDTO> dataTableResult = new DataTableResults<FuncionamientoCialcoDTO>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(userList);
		if (!AppUtil.isObjectEmpty(userList)) {
			dataTableResult.setRecordsTotal(((FuncionamientoCialcoDTO) userList.get(0)).getTotalRecords().toString());
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty())
				dataTableResult
						.setRecordsFiltered(((FuncionamientoCialcoDTO) userList.get(0)).getTotalRecords().toString());
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
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = FuncionamientoCialco.class)
	public ResponseEntity<List<FuncionamientoCialco>> findAll(@RequestHeader(name = "Authorization") String token) {
		List<FuncionamientoCialco> funcionamientocialco = funcionamientoCialcoService.findAll();
		LOGGER.info("funcionamientocialco FindAll: " + funcionamientocialco.toString() + " usuario: "
				+ util.filterUsuId(token));
		return ResponseEntity.ok(funcionamientocialco);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get FuncionamientoCialco by id", response = FuncionamientoCialco.class)
	public ResponseEntity<Optional<FuncionamientoCialco>> findById(@RequestHeader(name = "Authorization") String token,
			@Validated @PathVariable Long id) {
		Optional<FuncionamientoCialco> funcionamientocialco = funcionamientoCialcoService.findById(id);
		LOGGER.info("funcionamientocialco FindById: " + funcionamientocialco.toString() + " usuario: "
				+ util.filterUsuId(token));
		return ResponseEntity.ok(funcionamientocialco);
	}

	/**
	 * Busca los registros por cia_Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@RequestMapping(value = "/findByCiaId/{ciaId}", method = RequestMethod.GET)
	@ApiOperation(value = "Get FuncionamientoCialco by id", response = FuncionamientoCialco.class)
	public ResponseEntity<?> findByCiaId(@RequestHeader(name = "Authorization") String token,
			@Validated @PathVariable Long ciaId) {
		List<FuncionamientoCialco> funcionamientocialco = funcionamientoCialcoService.findByCiaId(ciaId);
		LOGGER.info("funcionamientocialco FindById: " + funcionamientocialco.toString() + " usuario: "
				+ util.filterUsuId(token));
		return ResponseEntity.ok(funcionamientocialco);
	}

	/**
	 * Actualiza un registro
	 * 
	 * @param usuId:   Identificador del usuario que va a actualizar
	 * 
	 * @param entidad: entidad a actualizar
	 * @return ResponseController: Retorna el id actualizado
	 */
	@RequestMapping(value = "/update/{usuId}", method = RequestMethod.POST)
	@ApiOperation(value = "Actualizar los registros", response = ResponseController.class)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<ResponseController> update(@RequestHeader(name = "Authorization") String token,
			@Valid @RequestBody FuncionamientoCialco updateFuncionamientoCialco, @PathVariable Integer usuId) {
		FuncionamientoCialco funcionamientocialco = funcionamientoCialcoService
				.findById(updateFuncionamientoCialco.getFciaId())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("FuncionamientoCialco", "Id",
						updateFuncionamientoCialco.getFciaId().toString()));

		funcionamientocialco.setFciaActUsu(usuId);
		// TODOS LOS CAMPOS A ACTUALIZAR
		//
		FuncionamientoCialco funcionamientocialcoUpdate = funcionamientoCialcoService.save(funcionamientocialco);
		LOGGER.info(
				"funcionamientocialco Update: " + funcionamientocialcoUpdate + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(funcionamientocialcoUpdate.getFciaId(), "Actualizado"));
	}

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
	@RequestMapping(value = "/delete/{id}/{usuId}", method = RequestMethod.POST)
	@ApiOperation(value = "Remove funcionamientocialcos by id")
	public ResponseEntity<ResponseController> deleteFuncionamientoCialco(
			@RequestHeader(name = "Authorization") String token, @Validated @PathVariable Long id,
			@PathVariable Integer usuId) {
		FuncionamientoCialco deleteFuncionamientoCialco = funcionamientoCialcoService.findById(id).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("FuncionamientoCialco", "Id", id.toString()));
		deleteFuncionamientoCialco.setFciaEliminado(true);
		deleteFuncionamientoCialco.setFciaActUsu(usuId);
		FuncionamientoCialco funcionamientocialcoDel = funcionamientoCialcoService.save(deleteFuncionamientoCialco);
		LOGGER.info("funcionamientocialco Delete id: " + id + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(funcionamientocialcoDel.getFciaId(), "eliminado"));
	}

	/**
	 * Inserta un nuevo registro en la entidad
	 * 
	 * @param entidad: entidad a insertar
	 * @return ResponseController: Retorna el id creado
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	@ApiOperation(value = "Crear nuevo registro", response = ResponseController.class)
	public ResponseEntity<ResponseController> postFuncionamientoCialco(
			@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody FuncionamientoCialco funcionamientocialco) {
		FuncionamientoCialco off = funcionamientoCialcoService.save(funcionamientocialco);
		LOGGER.info("funcionamientocialco create: " + funcionamientocialco + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(off.getFciaId(), "Creado"));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
