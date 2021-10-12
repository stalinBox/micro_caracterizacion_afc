package ec.gob.mag.domain.constraint;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.domain.OfertaDetalle;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder

public class OrganizacionOfertaProductivaUpdate {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@JsonProperty("oopId")
	@NotNull(message = "_error.validation_blank.message")
	private Long oopId;

	@ApiModelProperty(value = "Este campo es la clave foranea de la tabla Organización", required = true, readOnly = true)
	@JsonProperty("orgId")
	private Integer orgId;

	@ApiModelProperty(value = "Valor texto de la Identificación de la organización", example = "0000000000001")
	@Size(min = 0, max = 13, message = "_error.validation_range.message-[0, 13]")
	@JsonProperty("orgIdentificacion")
	private String orgIdentificacion;

	@ApiModelProperty(value = "Valor texto de la razon social de la Organización", example = "Razon social")
	@Size(min = 0, max = 500, message = "_error.validation_range.message-[0, 500]")
	@JsonProperty("orgRazonSocial")
	private String orgRazonSocial;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "0")
	@JsonProperty("oopActUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Integer oopActUsu;

	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "oopd_id")
	@JsonProperty("ofertaDetalle")
	private List<OfertaDetalle> ofertaDetalle;

}
