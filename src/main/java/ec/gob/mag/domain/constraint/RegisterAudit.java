package ec.gob.mag.domain.constraint;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.exception.constraint.OneOfInteger;
import ec.gob.mag.exception.constraint.OneOfString;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class RegisterAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@JsonProperty("id")
	@NotNull(message = "_error.validation_blank.message")
	private Long id;

	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11/12")
	@OneOfInteger(value = { 11, 12 }, domainShow = "[11, 12]")
	@NotNull(message = "_error.validation_blank.message")
	private Integer estado;

	@ApiModelProperty(value = "Id de usuario que actualizacio del registro", example = "0")
	@NotNull(message = "_error.validation_blank.message")
	private Integer actUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado, true=> eliminado")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean eliminado;

	@ApiModelProperty(value = "Estado enviado del registro: disable, delete, activate ", required = true, allowableValues = "disable, delete, activate", example = "delete/delete/activate")
	@NotBlank(message = "_error.validation_blank.message")
	@OneOfString(value = { "disable", "delete", "activate" })
	private String desc;

}