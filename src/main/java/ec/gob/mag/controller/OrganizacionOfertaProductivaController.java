package ec.gob.mag.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.mag.domain.OrganizacionOfertaProductiva;
import ec.gob.mag.services.OrganizacionOfertaProductivaService;
import ec.gob.mag.util.ResponseController;
import ec.gob.mag.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/orgofertaproductiva")
@Api(value = "Rest Api Organizacion Oferta Prodcutiva", tags = "ORGANIZACION OFERTA PRODUCTIVA")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })
public class OrganizacionOfertaProductivaController implements ErrorController {

	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(OrganizacionOfertaProductivaController.class);

	@Autowired
	@Qualifier("organizacionOfertaProductivaService")
	private OrganizacionOfertaProductivaService organizacionOfertaProductivaService;

	@Autowired
	@Qualifier("responseController")
	private ResponseController responseController;

	@Autowired
	@Qualifier("util")
	private Util util;

	/**
	 * Busca todos los registros de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return Entidad: Retorna todos los registros
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = OrganizacionOfertaProductiva.class)
	public ResponseEntity<List<OrganizacionOfertaProductiva>> findAll(
			@RequestHeader(name = "Authorization") String token) {
		List<OrganizacionOfertaProductiva> organizacionofertaproductiva = organizacionOfertaProductivaService.findAll();
		LOGGER.info("orgofertaproductiva FindAll: " + organizacionofertaproductiva.toString() + " usuario: "
				+ util.filterUsuId(token));
		return ResponseEntity.ok(organizacionofertaproductiva);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get OrganizacionOfertaProductiva by id", response = OrganizacionOfertaProductiva.class)
	public ResponseEntity<Optional<OrganizacionOfertaProductiva>> findById(
			@RequestHeader(name = "Authorization") String token, @Validated @PathVariable Long id) {
		Optional<OrganizacionOfertaProductiva> organizacionofertaproductiva = organizacionOfertaProductivaService
				.findById(id);
		LOGGER.info("orgofertaproductiva FindById: " + organizacionofertaproductiva.toString() + " usuario: "
				+ util.filterUsuId(token));
		return ResponseEntity.ok(organizacionofertaproductiva);
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
			@Validated @RequestBody OrganizacionOfertaProductiva updateOrganizacionOfertaProductiva,
			@PathVariable Integer usuId) {
		OrganizacionOfertaProductiva organizacionofertaproductiva = organizacionOfertaProductivaService
				.findById(updateOrganizacionOfertaProductiva.getOopId())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("OrganizacionOfertaProductiva", "Id",
						updateOrganizacionOfertaProductiva.getOopId().toString()));

		organizacionofertaproductiva.setOopActUsu(usuId);
		// TODOS LOS CAMPOS A ACTUALIZAR
		//
		OrganizacionOfertaProductiva organizacionofertaproductivaUpdate = organizacionOfertaProductivaService
				.save(organizacionofertaproductiva);
		LOGGER.info("orgofertaproductiva Update: " + organizacionofertaproductivaUpdate + " usuario: "
				+ util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(organizacionofertaproductivaUpdate.getOopId(), "Actualizado"));
	}

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
	@RequestMapping(value = "/delete/{id}/{usuId}", method = RequestMethod.POST)
	@ApiOperation(value = "Remove organizacionofertaproductivas by id")
	public ResponseEntity<ResponseController> deleteOrganizacionOfertaProductiva(
			@RequestHeader(name = "Authorization") String token, @Validated @PathVariable Long id,
			@PathVariable Integer usuId) {
		OrganizacionOfertaProductiva deleteOrganizacionOfertaProductiva = organizacionOfertaProductivaService
				.findById(id)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("OrganizacionOfertaProductiva", "Id",
						id.toString()));
		deleteOrganizacionOfertaProductiva.setOopEliminado(true);
		deleteOrganizacionOfertaProductiva.setOopActUsu(usuId);
		OrganizacionOfertaProductiva organizacionofertaproductivaDel = organizacionOfertaProductivaService
				.save(deleteOrganizacionOfertaProductiva);
		LOGGER.info("orgofertaproductiva Delete: " + id + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(organizacionofertaproductivaDel.getOopId(), "eliminado"));
	}

	/**
	 * Inserta un nuevo registro en la entidad
	 * 
	 * @param entidad: entidad a insertar
	 * @return ResponseController: Retorna el id creado
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	@ApiOperation(value = "Crear nuevo registro", response = ResponseController.class)
	public ResponseEntity<ResponseController> postOrganizacionOfertaProductiva(
			@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody OrganizacionOfertaProductiva organizacionofertaproductiva) {
		OrganizacionOfertaProductiva off = organizacionOfertaProductivaService.save(organizacionofertaproductiva);
		LOGGER.info(
				"orgofertaproductiva CREATE: " + organizacionofertaproductiva + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(off.getOopId(), "Creado"));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
