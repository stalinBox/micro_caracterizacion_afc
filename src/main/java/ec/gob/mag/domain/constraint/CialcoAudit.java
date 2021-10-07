package ec.gob.mag.domain.constraint;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class CialcoAudit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "cia_id", nullable = true, updatable = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ciaId")
	@NotNull(message = "_error.validation_blank.message")
	private Long ciaId;

	@ApiModelProperty(value = "Estado enviado del registro: disable, delete, activate ", required = true, allowableValues = "disable, delete, activate")
	@JsonProperty("estado")
	@NotBlank(message = "_error.validation_blank.message")
	@OneOfString(value = { "disable", "delete", "activate" })
	private String state;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/

	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "cia_estado", columnDefinition = "Integer default 11")
	@JsonProperty("ciaEstado")
	@OneOfInteger(value = { 11, 12 }, domainShow = "[11, 12]")
	@NotNull(message = "_error.validation_blank.message")
	private Integer ciaEstado;

	@ApiModelProperty(value = "Id de usuario que actualizacio del qi", example = "")
	@Column(name = "cia_act_usu")
	@JsonProperty("ciaActUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Integer ciaActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "cia_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("ciaEliminado")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean ciaEliminado;

}