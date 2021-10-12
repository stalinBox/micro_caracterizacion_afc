package ec.gob.mag.controller;

import java.io.IOException;
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
import ec.gob.mag.domain.constraint.CialcoOfertaProductivaCreate;
import ec.gob.mag.domain.constraint.CialcoOfertaProductivaUpdate;
import ec.gob.mag.domain.constraint.RegisterAudit;
import ec.gob.mag.domain.dto.CialcoOfertaProductivaDTO;
import ec.gob.mag.domain.pagination.AppUtil;
import ec.gob.mag.domain.pagination.DataTableRequest;
import ec.gob.mag.domain.pagination.DataTableResults;
import ec.gob.mag.domain.pagination.PaginationCriteria;
import ec.gob.mag.services.CialcoOfertaProductivaService;
import ec.gob.mag.util.ConvertEntityUtil;
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

	@Autowired
	@Qualifier("convertEntityUtil")
	private ConvertEntityUtil convertEntityUtil;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param RegisterAudit: Identificador del registro contiene
	 *                       id,actUsu,eliminado,estado,desc
	 * @return ResponseController: Retorna el id eliminado
	 */
	@RequestMapping(value = "/state-record/", method = RequestMethod.PUT)
	@ApiOperation(value = "Gestionar estado del registro ciaEstado={11 ACTIVO,12 INACTIVO}, ciaEliminado={false, true}, state: {disable, delete, activate}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseController> stateCialco(@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody RegisterAudit audit) {
		CialcoOfertaProductiva ciop = cialcoOfertaProductivaService.findByIdAll(audit.getId())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("CialcoOfertaProductiva", "Id",
						audit.getId().toString()));

		ciop.setCiopEliminado(audit.getEliminado());
		ciop.setCiopEstado(audit.getEstado());
		ciop.setCiopActUsu(audit.getActUsu());

		CialcoOfertaProductiva cialcoDel = cialcoOfertaProductivaService.save(ciop);
		LOGGER.info("Cialco OfertaProductiva state-record : " + audit.getId() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(cialcoDel.getCiopId(), audit.getDesc()));
	}

	/**
	 * Busca todos los registros de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return Entidad: Retorna todos los registros
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = CialcoOfertaProductiva.class)
	@ResponseStatus(HttpStatus.OK)
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
	@ResponseStatus(HttpStatus.OK)
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
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 */
	@RequestMapping(value = "/update/", method = RequestMethod.PUT)
	@ApiOperation(value = "Actualizar los registros", response = ResponseController.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseController> update(@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody CialcoOfertaProductivaUpdate updateCialcoOfertaProductiva)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException,
			IOException {

		CialcoOfertaProductiva ccopValidado = convertEntityUtil.ConvertSingleEntityGET(CialcoOfertaProductiva.class,
				(Object) updateCialcoOfertaProductiva);

		ccopValidado.setCialco(updateCialcoOfertaProductiva.getCialco());
		CialcoOfertaProductiva cialcoofertaproductivaUpdate = cialcoOfertaProductivaService.update(ccopValidado);

		cialcoofertaproductivaUpdate.setCialco(updateCialcoOfertaProductiva.getCialco());
		LOGGER.info("cialcofertaprod Update: " + cialcoofertaproductivaUpdate + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(cialcoofertaproductivaUpdate.getCiopId(), "Actualizado"));
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
	public ResponseEntity<ResponseController> postCialcoOfertaProductiva(
			@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody CialcoOfertaProductivaCreate cialcoofertaproductiva) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException, IOException {
		CialcoOfertaProductiva ccopValidado = convertEntityUtil.ConvertSingleEntityGET(CialcoOfertaProductiva.class,
				(Object) cialcoofertaproductiva);
		ccopValidado.setCialco(cialcoofertaproductiva.getCialco());
		CialcoOfertaProductiva off = cialcoOfertaProductivaService.save(ccopValidado);
		LOGGER.info("cialcofertaprod create: " + cialcoofertaproductiva + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(off.getCiopId(), "Creado"));
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findAllPaginated/{ciaId}", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity<?> listAplicationPaginated(@PathVariable Long ciaId, HttpServletRequest request,
			@RequestHeader(name = "Authorization") String token) {

		DataTableRequest<CialcoOfertaProductivaDTO> dataTableInRQ = new DataTableRequest<CialcoOfertaProductivaDTO>(
				request);
		PaginationCriteria pagination = dataTableInRQ.getPaginationRequest();
		String baseQuery = "SELECT ROW_NUMBER() OVER (ORDER BY ciop_id ) AS nro, \n" + "CAST(ciop_id AS VARCHAR), \n"
				+ "CAST(cop.cia_id AS VARCHAR),  \n" + "CAST(c.cia_nombre AS VARCHAR), \n"
				+ "CAST(ciop_cat_ids_ruta AS VARCHAR), \n" + "CAST(ciop_cat_id_oferta AS VARCHAR), \n"
				+ "CAST(ciop_estado AS VARCHAR), \n" + "CAST(ciop_eliminado AS VARCHAR), \n"
				+ "CAST(ciop_reg_usu AS VARCHAR), \n" + "CAST(ciop_act_usu AS VARCHAR),\n"
				+ "(SELECT count (cop.ciop_id) FROM sc_gopagro.cialco_oferta_productiva cop INNER JOIN  sc_gopagro.cialco c ON cop.cia_id = c.cia_id WHERE cop.cia_id = "
				+ ciaId + " ) as totalRecords\n"
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

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
