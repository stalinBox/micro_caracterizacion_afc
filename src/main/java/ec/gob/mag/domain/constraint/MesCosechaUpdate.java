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

public class MesCosechaUpdate {

	@Id
	@JsonProperty("mcoId")
	@NotNull(message = "_error.validation_blank.message")
	private Long mcoId;

	@JsonProperty("mcoEnero")
	private Boolean mcoEnero;

	@JsonProperty("mcoFebrero")
	private Boolean mcoFebrero;

	@JsonProperty("mcoMarzo")
	private Boolean mcoMarzo;

	@JsonProperty("mcoAbril")
	private Boolean mcoAbril;

	@JsonProperty("mcoMayo")
	private Boolean mcoMayo;

	@JsonProperty("mcoJunio")
	private Boolean mcoJunio;

	@JsonProperty("mcoJulio")
	private Boolean mcoJulio;

	@JsonProperty("mcoAgosto")
	private Boolean mcoAgosto;

	@JsonProperty("mcoSeptiembre")
	private Boolean mcoSeptiembre;

	@JsonProperty("mcoOctubre")
	private Boolean mcoOctubre;

	@JsonProperty("mcoNoviembre")
	private Boolean mcoNoviembre;

	@JsonProperty("mcoDiciembre")
	private Boolean mcoDiciembre;

	@ApiModelProperty(value = "Id de usuario que actualizacio del qi", example = "")
	@JsonProperty("mcoActUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Long mcoActUsu;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "oopd_id")
	@JsonBackReference
	private OfertaDetalle ofertaDetalle;

}
