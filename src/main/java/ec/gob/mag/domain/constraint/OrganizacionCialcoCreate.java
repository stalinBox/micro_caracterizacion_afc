package ec.gob.mag.domain.constraint;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.domain.Cialco;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class OrganizacionCialcoCreate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@JsonProperty("ociId")
	private Long ociId;

	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla")
	@JsonProperty("orgId")
	@NotNull(message = "_error.validation_blank.message")
	private Integer orgId;

	@ApiModelProperty(value = "Nombre de la razon social", example = "nombre de la razon social")
	@JsonProperty("orgRazonSocial")
	@Size(min = 0, max = 500, message = "_error.validation_range.message-[0, 500]")
	@NotEmpty(message = "_error.validation_blank.message")
	private String orgRazonSocial;

	@ApiModelProperty(value = "Ruc de la organizacion", example = "0000000000001")
	@Size(min = 0, max = 13, message = "_error.validation_range.message-[0, 13]")
	@JsonProperty("orgIdentificacion")
	@NotEmpty(message = "_error.validation_blank.message")
	private String orgIdentificacion;

	@ApiModelProperty(value = "Estado Negocio")
	@JsonProperty("ociEstadoNegocio")
	@NotNull(message = "_error.validation_blank.message")
	private Integer ociEstadoNegocio;

	@ApiModelProperty(value = "Valor texto del nombre de la categoria", example = "nombre categoria")
	@JsonProperty("ociNegocioObservacion")
	private String ociNegocioObservacion;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@JsonProperty("ociRegUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Integer ociRegUsu;

	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cia_id")
	@JsonBackReference
	private Cialco cialco;

}