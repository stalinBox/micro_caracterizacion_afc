package ec.gob.mag.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.mag.domain.MesCosecha;
import ec.gob.mag.services.MesCosechaService;
import ec.gob.mag.util.ResponseController;
import ec.gob.mag.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/mescosecha")
@Api(value = "Rest Api MesCosecha", tags = "MES COSECHA")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })
public class MesCosechaController implements ErrorController {

	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(MesCosechaController.class);

	@Autowired
	@Qualifier("mesCosechaService")
	private MesCosechaService mesCosechaService;

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
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = MesCosecha.class)
	public ResponseEntity<List<MesCosecha>> findAll(@RequestHeader(name = "Authorization") String token) {
		List<MesCosecha> mescosecha = mesCosechaService.findAll();
		LOGGER.info("mescosecha FindAll: " + mescosecha.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(mescosecha);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get MesCosecha by id", response = MesCosecha.class)
	public ResponseEntity<Optional<MesCosecha>> findById(@RequestHeader(name = "Authorization") String token,
			@Validated @PathVariable Long id) {
		Optional<MesCosecha> mescosecha = mesCosechaService.findById(id);
		LOGGER.info("mescosecha FindById: " + mescosecha.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(mescosecha);
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
			@Valid @RequestBody MesCosecha updateMesCosecha, @PathVariable Long usuId) {
		MesCosecha mescosecha = mesCosechaService.findById(updateMesCosecha.getMcoId())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("MesCosecha", "Id",
						updateMesCosecha.getMcoId().toString()));

		mescosecha.setMcoActUsu(usuId);
		// TODOS LOS CAMPOS A ACTUALIZAR
		//
		MesCosecha mescosechaUpdate = mesCosechaService.save(mescosecha);
		LOGGER.info("mescosecha Update: " + mescosechaUpdate + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(mescosechaUpdate.getMcoId(), "Actualizado"));
	}

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
	@RequestMapping(value = "/delete/{id}/{usuId}", method = RequestMethod.POST)
	@ApiOperation(value = "Remove mescosechas by id")
	public ResponseEntity<ResponseController> deleteMesCosecha(@RequestHeader(name = "Authorization") String token,
			@Validated @PathVariable Long id, @PathVariable Long usuId) {
		MesCosecha deleteMesCosecha = mesCosechaService.findById(id)
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("MesCosecha", "Id", id.toString()));
		deleteMesCosecha.setMcoEliminado(true);
		deleteMesCosecha.setMcoActUsu(usuId);
		MesCosecha mescosechaDel = mesCosechaService.save(deleteMesCosecha);
		LOGGER.info("mescosecha Delete: " + id + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(mescosechaDel.getMcoId(), "eliminado"));
	}

	/**
	 * Inserta un nuevo registro en la entidad
	 * 
	 * @param entidad: entidad a insertar
	 * @return ResponseController: Retorna el id creado
	 */
	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	@ApiOperation(value = "Crear nuevo registro", response = ResponseController.class)
	public ResponseEntity<ResponseController> postMesCosecha(@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody MesCosecha mescosecha) {
		MesCosecha off = mesCosechaService.save(mescosecha);
		LOGGER.info("MesCosecha Create: " + mescosecha + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(off.getMcoId(), "Creado"));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}
}
