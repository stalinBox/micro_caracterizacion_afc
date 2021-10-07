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

import ec.gob.mag.domain.OrganizacionCialco;
import ec.gob.mag.domain.constraint.RegisterAudit;
import ec.gob.mag.services.OrganizacionCialcoService;
import ec.gob.mag.util.ResponseController;
import ec.gob.mag.util.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/orgcialco")
@Api(value = "Rest Api Organizacion Cialco", tags = "ORGANIZACION CIALCO")
@ApiResponses(value = { @ApiResponse(code = 200, message = "Objeto recuperado"),
		@ApiResponse(code = 200, message = "SUCESS"), @ApiResponse(code = 404, message = "RESOURCE NOT FOUND"),
		@ApiResponse(code = 400, message = "BAD REQUEST"), @ApiResponse(code = 201, message = "CREATED"),
		@ApiResponse(code = 401, message = "UNAUTHORIZED"),
		@ApiResponse(code = 415, message = "UNSUPPORTED TYPE - Representation not supported for the resource"),
		@ApiResponse(code = 500, message = "SERVER ERROR") })

public class OrganizacionCialcoController implements ErrorController {

	private static final String PATH = "/error";
	public static final Logger LOGGER = LoggerFactory.getLogger(OrganizacionCialcoController.class);

	@Autowired
	@Qualifier("organizacionCialcoService")
	private OrganizacionCialcoService organizacionCialcoService;

	@Autowired
	@Qualifier("responseController")
	private ResponseController responseController;

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
		OrganizacionCialco oc = organizacionCialcoService.findByIdAll(audit.getId())
				.orElseThrow(() -> new InvalidConfigurationPropertyValueException("OrganizacionCialco", "Id",
						audit.getId().toString()));

		oc.setOciEliminado(audit.getEliminado());
		oc.setOciEstado(audit.getEstado());
		oc.setOciActUsu(audit.getActUsu());

		OrganizacionCialco cialcoDel = organizacionCialcoService.save(oc);
		LOGGER.info("cialco state-record : " + audit.getId() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(cialcoDel.getOciId(), audit.getDesc()));
	}

	/**
	 * Busca todos los registros de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return Entidad: Retorna todos los registros
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	@ApiOperation(value = "Obtiene todos los registros activos no eliminados logicamente", response = OrganizacionCialco.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<OrganizacionCialco>> findAll(@RequestHeader(name = "Authorization") String token) {
		List<OrganizacionCialco> organizacioncialco = organizacionCialcoService.findAll();
		LOGGER.info("orgcialco FindAll: " + organizacioncialco.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(organizacioncialco);
	}

	/**
	 * Busca los registros por Id de la entidad
	 * 
	 * @param id: Identificador de la entidad
	 * @return parametrosCarga: Retorna el registro encontrado
	 */
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get OrganizacionCialco by id", response = OrganizacionCialco.class)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Optional<OrganizacionCialco>> findById(@RequestHeader(name = "Authorization") String token,
			@Validated @PathVariable Long id) {
		Optional<OrganizacionCialco> organizacioncialco = organizacionCialcoService.findById(id);
		LOGGER.info("orgcialco FindById: " + organizacioncialco.toString() + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(organizacioncialco);
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
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseController> update(@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody OrganizacionCialco updateOrganizacionCialco, @PathVariable Integer usuId) {
		updateOrganizacionCialco.setOciActUsu(usuId);
		OrganizacionCialco organizacioncialcoUpdate = organizacionCialcoService.update(updateOrganizacionCialco);
		LOGGER.info("orgcialco Update: " + organizacioncialcoUpdate + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(organizacioncialcoUpdate.getOciId(), "Actualizado"));
	}

	/**
	 * Realiza un eliminado logico del registro
	 * 
	 * @param id:    Identificador del registro
	 * @param usuId: Identificador del usuario que va a eliminar
	 * @return ResponseController: Retorna el id eliminado
	 */
	@RequestMapping(value = "/delete/{id}/{usuId}", method = RequestMethod.DELETE)
	@ApiOperation(value = "Remove organizacioncialcos by id")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<ResponseController> deleteOrganizacionCialco(
			@RequestHeader(name = "Authorization") String token, @Validated @PathVariable Long id,
			@PathVariable Integer usuId) {
		OrganizacionCialco deleteOrganizacionCialco = organizacionCialcoService.findById(id).orElseThrow(
				() -> new InvalidConfigurationPropertyValueException("OrganizacionCialco", "Id", id.toString()));
		deleteOrganizacionCialco.setOciEliminado(true);
		deleteOrganizacionCialco.setOciActUsu(usuId);
		OrganizacionCialco organizacioncialcoDel = organizacionCialcoService.save(deleteOrganizacionCialco);
		LOGGER.info("orgcialco Delete : " + id + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(organizacioncialcoDel.getOciId(), "eliminado"));
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
	public ResponseEntity<ResponseController> postOrganizacionCialco(
			@RequestHeader(name = "Authorization") String token,
			@Validated @RequestBody OrganizacionCialco organizacioncialco) {
		OrganizacionCialco off = organizacionCialcoService.save(organizacioncialco);
		LOGGER.info("orgcialco Save: " + organizacioncialco + " usuario: " + util.filterUsuId(token));
		return ResponseEntity.ok(new ResponseController(off.getOciId(), "Creado"));
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

}
