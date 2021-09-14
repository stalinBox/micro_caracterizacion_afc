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

import ec.gob.mag.domain.CialcoOfertaProductiva;
import ec.gob.mag.domain.dto.CialcoOfertaProductivaDTO;
import ec.gob.mag.domain.pagination.AppUtil;
import ec.gob.mag.domain.pagination.DataTableRequest;
import ec.gob.mag.domain.pagination.DataTableResults;
import ec.gob.mag.domain.pagination.PaginationCriteria;
import ec.gob.mag.services.CialcoOfertaProductivaService;
import ec.gob.mag.util.ResponseController;
import ec.gob.mag.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/cialcofertaprod")
@Api(value = "Rest Api Cialco Oferta Productiva", tags = "CIALCO OFERTA PRODUCTIVA")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })

public class CialcoOfertaProductivaController implements ErrorController {

	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(CialcoOfertaProductivaController.class);

	@Autowired
	@Qualifier("cialcoOfertaProductivaService")
	private CialcoOfertaProductivaService cialcoOfertaProductivaService;

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

		DataTableRequest<CialcoOfertaProductivaDTO> dataTableInRQ = new DataTableRequest<CialcoOfertaProductivaDTO>(
				request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		String baseQuery = "SELECT ROW_NUMBER() OVER (ORDER BY ciop_id ) AS nro, ciop_id, cop.cia_id,  c.cia_nombre, ciop_cat_id_oferta, ciop_estado, ciop_eliminado, ciop_reg_usu, ciop_act_usu,\r\n"
				+ "(SELECT count (cop.ciop_id) FROM sc_gopagro.cialco_oferta_productiva cop INNER JOIN  sc_gopagro.cialco c ON cop.cia_id = c.cia_id\r\n"
				+ "WHERE cop.cia_id = " + ciaId + " ) as totalRecords\r\n"
				+ "FROM sc_gopagro.cialco_oferta_productiva cop INNER JOIN  sc_gopagro.cialco c ON cop.cia_id = c.cia_id WHERE cop.cia_id = "
				+ ciaId;
		String paginatedQuery = AppUtil.buildPaginatedQuery(baseQuery, pagination);
		Query query = entityManager.createNativeQuery(paginatedQuery, CialcoOfertaProductivaDTO.class);
		List<CialcoOfertaProductivaDTO> userList = query.getResultList();
		DataTableResults<CialcoOfertaProductivaDTO> dataTableResult = new DataTableResults<CialcoOfertaProductivaDTO>();
		dataTableResult.setDraw(dataTableInRQ.getDraw());
		dataTableResult.setListOfDataObjects(userList);
		if (!AppUtil.isObjectEmpty(userList)) {
			dataTableResult.setRecordsTotal(((CialcoOfertaProductivaDTO) userList.get(0)).getTotalRecords().toString());
			if (dataTableInRQ.getPaginationRequest().isFilterByEmpty())
				dataTableResult
						.setRecordsFiltered(((CialcoOfertaProductivaDTO) userList.get(0)).getTotalRecords().toString());
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
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = CialcoOfertaProductiva.class)
	public ResponseEntity<List<CialcoOfertaProductiva>> findAll(@RequestHeader(name = "Authorization") String token) {
		List<CialcoOfertaProductiva> cialcoofertaproductiva = cialcoOfertaProductivaService.findAll();
		LOGGER.info("cialcofertaprod FindAll: " + cialcoofertaproductiva.toString() + " usuario: "
				+ util.filterUsuId(token));
		return ResponseEntity.ok(cialcoofertaproductiva);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get CialcoOfertaProductiva by id", response = CialcoOfertaProductiva.class)
	public ResponseEntity<Optional<CialcoOfertaProductiva>> findById(
			@RequestHeader(name = "Authorization") String token, @Validated @PathVariable Long id) {
		Optional<CialcoOfertaProductiva> cialcoofertaproductiva = cialcoOfertaProductivaService.findById(id);
		LOGGER.info("cialcofertaprod FindById: " + cialcoofertaproductiva.toString() + " usuario: "
				+ util.filterUsuId(token));
		return ResponseEntity.ok(cialcoofertaproductiva);
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
			@Valid @RequestBody CialcoOfertaProductiva updateCialcoOfertaProductiva, @PathVariable Long usuId) {
		CialcoOfertaProductiva cialcoofertaproductiva = cialcoOfertaProductivaService
				.findById(updateCialcoOfertaProductiva.getCiopId())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("CialcoOfertaProductiva", "Id",
						updateCialcoOfertaProductiva.getCiopId().toString()));

		cialcoofertaproductiva.setCiopActUsu(usuId);
		// TODOS LOS CAMPOS A ACTUALIZAR

		CialcoOfertaProductiva cialcoofertaproductivaUpdate = cialcoOfertaProductivaService
				.save(cialcoofertaproductiva);
		LOGGER.info("cialcofertaprod Update: " + cialcoofertaproductivaUpdate + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(cialcoofertaproductivaUpdate.getCiopId(), "Actualizado"));
	}

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
	@RequestMapping(value = "/delete/{id}/{usuId}", method = RequestMethod.POST)
	@ApiOperation(value = "Remove cialcoofertaproductivas by id")
	public ResponseEntity<ResponseController> deleteCialcoOfertaProductiva(
			@RequestHeader(name = "Authorization") String token, @Validated @PathVariable Long id,
			@PathVariable Long usuId) {
		CialcoOfertaProductiva deleteCialcoOfertaProductiva = cialcoOfertaProductivaService.findById(id).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("CialcoOfertaProductiva", "Id", id.toString()));
		deleteCialcoOfertaProductiva.setCiopEliminado(true);
		deleteCialcoOfertaProductiva.setCiopActUsu(usuId);
		CialcoOfertaProductiva cialcoofertaproductivaDel = cialcoOfertaProductivaService
				.save(deleteCialcoOfertaProductiva);
		LOGGER.info("cialcofertaprod Delete id: " + id + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(cialcoofertaproductivaDel.getCiopId(), "eliminado"));
	}

	/**
	 * Inserta un nuevo registro en la entidad
	 * 
	 * @param entidad: entidad a insertar
	 * @return ResponseController: Retorna el id creado
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	@ApiOperation(value = "Crear nuevo registro", response = ResponseController.class)
	public ResponseEntity<ResponseController> postCialcoOfertaProductiva(
			@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody CialcoOfertaProductiva cialcoofertaproductiva) {
		CialcoOfertaProductiva off = cialcoOfertaProductivaService.save(cialcoofertaproductiva);
		LOGGER.info("cialcofertaprod create: " + cialcoofertaproductiva + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(off.getCiopId(), "Creado"));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
