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

import ec.gob.mag.domain.OfertaDetalle;
import ec.gob.mag.services.OfertaDetalleService;
import ec.gob.mag.util.ResponseController;
import ec.gob.mag.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/ofertadetalle")
@Api(value = "Rest Api OfertaDetalle", tags = "OFERTA DETALLE")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })
public class OfertaDetalleController implements ErrorController {

	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(OfertaDetalleController.class);

	@Autowired
	@Qualifier("ofertaDetalleService")
	private OfertaDetalleService ofertaDetalleService;

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
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = OfertaDetalle.class)
	public ResponseEntity<List<OfertaDetalle>> findAll(@RequestHeader(name = "Authorization") String token) {
		List<OfertaDetalle> ofertadetalle = ofertaDetalleService.findAll();
		LOGGER.info("ofertadetalle FindAll: " + ofertadetalle.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(ofertadetalle);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get OfertaDetalle by id", response = OfertaDetalle.class)
	public ResponseEntity<Optional<OfertaDetalle>> findById(@RequestHeader(name = "Authorization") String token,
			@Validated @PathVariable Long id) {
		Optional<OfertaDetalle> ofertadetalle = ofertaDetalleService.findById(id);
		LOGGER.info("ofertadetalle FindById: " + ofertadetalle.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(ofertadetalle);
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
			@Validated @RequestBody OfertaDetalle updateOfertaDetalle, @PathVariable Integer usuId) {
		OfertaDetalle ofertadetalle = ofertaDetalleService.findById(updateOfertaDetalle.getOopdId())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("OfertaDetalle", "Id",
						updateOfertaDetalle.getOopdId().toString()));

		ofertadetalle.setOopdActUsu(usuId);
		// TODOS LOS CAMPOS A ACTUALIZAR
		//
		OfertaDetalle ofertadetalleUpdate = ofertaDetalleService.save(ofertadetalle);
		LOGGER.info("ofertadetalle Update: " + ofertadetalleUpdate + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(ofertadetalleUpdate.getOopdId(), "Actualizado"));
	}

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
	@RequestMapping(value = "/delete/{id}/{usuId}", method = RequestMethod.POST)
	@ApiOperation(value = "Remove ofertadetalles by id")
	public ResponseEntity<ResponseController> deleteOfertaDetalle(@RequestHeader(name = "Authorization") String token,
			@Validated @PathVariable Long id, @PathVariable Integer usuId) {
		OfertaDetalle deleteOfertaDetalle = ofertaDetalleService.findById(id).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("OfertaDetalle", "Id", id.toString()));
		deleteOfertaDetalle.setOopdEliminado(true);
		deleteOfertaDetalle.setOopdActUsu(usuId);
		OfertaDetalle ofertadetalleDel = ofertaDetalleService.save(deleteOfertaDetalle);
		LOGGER.info("ofertadetalle Delete: " + id + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(ofertadetalleDel.getOopdId(), "eliminado"));
	}

	/**
	 * Inserta un nuevo registro en la entidad
	 * 
	 * @param entidad: entidad a insertar
	 * @return ResponseController: Retorna el id creado
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	@ApiOperation(value = "Crear nuevo registro", response = ResponseController.class)
	public ResponseEntity<ResponseController> postOfertaDetalle(@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody OfertaDetalle ofertadetalle) {
		OfertaDetalle off = ofertaDetalleService.save(ofertadetalle);
		LOGGER.info("ofertadetalle create: " + ofertadetalle + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(off.getOopdId(), "Creado"));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
