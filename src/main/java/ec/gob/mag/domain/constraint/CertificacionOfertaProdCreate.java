package ec.gob.mag.domain.constraint;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.domain.OfertaDetalle;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
public class CertificacionOfertaProdCreate {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@JsonProperty("copId")
	private Long copId;

	@ApiModelProperty(value = "Aqui se digita el id de la categoria de la Certificación", example = "0")
	@JsonProperty("idcatcertificacion")
	@NotNull(message = "_error.validation_blank.message")
	private Integer idCatCertificacion;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "0")
	@JsonProperty("copRegUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Long copRegUsu;

	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "oopd_id")
	@JsonBackReference
	@NotNull(message = "_error.validation_blank.message")
	private OfertaDetalle ofertaDetalle;

}
