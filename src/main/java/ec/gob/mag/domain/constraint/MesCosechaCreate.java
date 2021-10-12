package ec.gob.mag.domain.constraint;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.domain.OfertaDetalle;
import io.swagger.annotations.ApiModelProperty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder

public class MesCosechaCreate {

	@Id
	@JsonProperty("mcoId")
	private Long mcoId;

	@JsonProperty("mcoEnero")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean mcoEnero;

	@JsonProperty("mcoFebrero")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean mcoFebrero;

	@JsonProperty("mcoMarzo")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean mcoMarzo;

	@JsonProperty("mcoAbril")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean mcoAbril;

	@JsonProperty("mcoMayo")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean mcoMayo;

	@JsonProperty("mcoJunio")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean mcoJunio;

	@JsonProperty("mcoJulio")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean mcoJulio;

	@JsonProperty("mcoAgosto")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean mcoAgosto;

	@JsonProperty("mcoSeptiembre")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean mcoSeptiembre;

	@JsonProperty("mcoOctubre")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean mcoOctubre;

	@JsonProperty("mcoNoviembre")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean mcoNoviembre;

	@JsonProperty("mcoDiciembre")
	@NotNull(message = "_error.validation_blank.message")
	private Boolean mcoDiciembre;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@JsonProperty("mcoRegUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Long mcoRegUsu;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "oopd_id")
	@JsonBackReference
	@NotNull(message = "_error.validation_blank.message")
	private OfertaDetalle ofertaDetalle;

}
