package ec.gob.mag.domain.constraint;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.domain.Cialco;
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
public class CialcoOfertaProductivaUpdate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@JsonProperty("ciopId")
	private Long ciopId;

	@ApiModelProperty(value = "Aqui se digita el id de lac ategoria de la oferta productiva")
	@JsonProperty("ciopCatIdOferta")
	private Integer ciopCatIdOferta;

	@ApiModelProperty(value = "Aqui se digita el id de lac ategoria de la oferta productiva", example = "5,5,5,5")
	@JsonProperty("ciopCatIdsRuta")
	private String ciopCatIdsRuta;

	@ApiModelProperty(value = "Id de usuario que actualizacio del registro")
	@JsonProperty("ciopActUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Integer ciopActUsu;

	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cia_id")
	@JsonBackReference
	private Cialco cialco;

}