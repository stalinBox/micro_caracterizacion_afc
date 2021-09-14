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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ec.gob.mag.domain.CertificacionOfertaProd;
import ec.gob.mag.services.CertificacionOfertaProdService;
import ec.gob.mag.util.ResponseController;
import ec.gob.mag.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/certofertaprod")
@Api(value = "Rest Api Certificacion Oferta Productiva", tags = "CERTIFICACION OFERTA PRODUCTIVA")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })

public class CertificacionOfertaProdController implements ErrorController {

	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(CertificacionOfertaProdController.class);

	@Autowired
	@Qualifier("certificacionOfertaProdService")
	private CertificacionOfertaProdService certificacionOfertaProdService;

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
	@GetMapping(value = "/findAll")
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = CertificacionOfertaProd.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<CertificacionOfertaProd>> findAll(@RequestHeader(name = "Authorization") String token) {
		List<CertificacionOfertaProd> certificacionofertaprod = certificacionOfertaProdService.findAll();
		LOGGER.info("CertificacionOfertaProd FindAll: " + certificacionofertaprod.toString() + " usuario: "
				+ util.filterUsuId(token));
		return ResponseEntity.ok(certificacionofertaprod);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get CertificacionOfertaProd by id", response = CertificacionOfertaProd.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Optional<CertificacionOfertaProd>> findById(
			@RequestHeader(name = "Authorization") String token, @Validated @PathVariable Long id) {
		Optional<CertificacionOfertaProd> certificacionofertaprod = certificacionOfertaProdService.findById(id);
		LOGGER.info("CertificacionOfertaProd FindById: " + certificacionofertaprod.get().getCopId() + " usuario: "
				+ util.filterUsuId(token));
		return ResponseEntity.ok(certificacionofertaprod);
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
			@Validated @RequestBody CertificacionOfertaProd updateCertificacionOfertaProd, @PathVariable Long usuId) {
		CertificacionOfertaProd certificacionofertaprod = certificacionOfertaProdService
				.findById(updateCertificacionOfertaProd.getCopId())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("CertificacionOfertaProd", "Id",
						updateCertificacionOfertaProd.getCopId().toString()));

		certificacionofertaprod.setCopActUsu(usuId);
		// TODOS LOS CAMPOS A ACTUALIZAR
		//
		CertificacionOfertaProd certificacionofertaprodUpdate = certificacionOfertaProdService
				.save(certificacionofertaprod);
		LOGGER.info("CertificacionOfertaProd update: " + certificacionofertaprodUpdate + " usuario: "
				+ util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(certificacionofertaprodUpdate.getCopId(), "Actualizado"));
	}

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
	@RequestMapping(value = "/delete/{id}/{usuId}", method = RequestMethod.POST)
	@ApiOperation(value = "Remove certificacionofertaprods by id")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseController> deleteCertificacionOfertaProd(
			@RequestHeader(name = "Authorization") String token, @Validated @PathVariable Long id,
			@PathVariable Long usuId) {
		CertificacionOfertaProd deleteCertificacionOfertaProd = certificacionOfertaProdService.findById(id).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("CertificacionOfertaProd", "Id", id.toString()));
		deleteCertificacionOfertaProd.setCopEliminado(true);
		deleteCertificacionOfertaProd.setCopActUsu(usuId);
		CertificacionOfertaProd certificacionofertaprodDel = certificacionOfertaProdService
				.save(deleteCertificacionOfertaProd);
		LOGGER.info("CertificacionOfertaProd Delete id: " + id + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(certificacionofertaprodDel.getCopId(), "eliminado"));
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
	public ResponseEntity<ResponseController> postCertificacionOfertaProd(
			@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody CertificacionOfertaProd certificacionofertaprod) {
		CertificacionOfertaProd off = certificacionOfertaProdService.save(certificacionofertaprod);
		LOGGER.info(
				"CertificacionOfertaProd create: " + certificacionofertaprod + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(off.getCopId(), "Creado"));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
