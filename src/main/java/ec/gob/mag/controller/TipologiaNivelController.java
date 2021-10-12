package ec.gob.mag.controller;

import java.io.IOException;
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

import ec.gob.mag.domain.TipologiaNivel;
import ec.gob.mag.domain.constraint.RegisterAudit;
import ec.gob.mag.domain.constraint.TipologiaNivelCreate;
import ec.gob.mag.domain.constraint.TipologiaNivelUpdate;
import ec.gob.mag.services.TipologiaNivelService;
import ec.gob.mag.util.ConvertEntityUtil;
import ec.gob.mag.util.ResponseController;
import ec.gob.mag.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/tipologiaNivel")
@Api(value = "Rest Api Tipologia Nivel", tags = "TIPOLOGIA NIVEL")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })

public class TipologiaNivelController implements ErrorController {

	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(TipologiaNivelController.class);

	@Autowired
	@Qualifier("tipologiaNivelService")
	private TipologiaNivelService tipologiaNivelService;

	@Autowired
	@Qualifier("responseController")
	private ResponseController responseController;

	@Autowired
	@Qualifier("convertEntityUtil")
	private ConvertEntityUtil convertEntityUtil;

	@Autowired
	@Qualifier("util")
	private Util util;

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
		TipologiaNivel topologia = tipologiaNivelService.findByIdAll(audit.getId()).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("TipologiaNivel", "Id", audit.getId().toString()));

		topologia.setTipEliminado(audit.getEliminado());
		topologia.setTipEstado(audit.getEstado());
		topologia.setTipActUsu(audit.getActUsu());

		TipologiaNivel top = tipologiaNivelService.save(topologia);
		LOGGER.info("cialco state-record : " + audit.getId() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(top.getTipId(), audit.getDesc()));
	}

	/**
	 * Busca todos los registros de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return Entidad: Retorna todos los registros
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = TipologiaNivel.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<TipologiaNivel>> findAll(@RequestHeader(name = "Authorization") String token) {
		List<TipologiaNivel> tipologianivel = tipologiaNivelService.findAll();
		LOGGER.info("tipologiaNivel FindAll: " + tipologianivel.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(tipologianivel);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get TipologiaNivel by id", response = TipologiaNivel.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Optional<TipologiaNivel>> findById(@RequestHeader(name = "Authorization") String token,
			@Validated @PathVariable Long id) {
		Optional<TipologiaNivel> tipologianivel = tipologiaNivelService.findById(id);
		LOGGER.info("tipologiaNivel FindById: " + tipologianivel.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(tipologianivel);
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
			@Validated @RequestBody TipologiaNivelUpdate updateTipologiaNivel) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException, IOException {
		TipologiaNivel topologiaValidado = convertEntityUtil.ConvertSingleEntityGET(TipologiaNivel.class,
				(Object) updateTipologiaNivel);
		TipologiaNivel tipologianivelUpdate = tipologiaNivelService.update(topologiaValidado);
		LOGGER.info("tipologiaNivel Update: " + tipologianivelUpdate + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(tipologianivelUpdate.getTipId(), "Actualizado"));
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
	public ResponseEntity<ResponseController> postTipologiaNivel(@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody TipologiaNivelCreate tipologianivel) throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, IOException {
		TipologiaNivel topologiaValidado = convertEntityUtil.ConvertSingleEntityGET(TipologiaNivel.class,
				(Object) tipologianivel);
		TipologiaNivel off = tipologiaNivelService.save(topologiaValidado);
		LOGGER.info("TipologiaNivel create: " + tipologianivel + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(off.getTipId(), "Creado"));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
