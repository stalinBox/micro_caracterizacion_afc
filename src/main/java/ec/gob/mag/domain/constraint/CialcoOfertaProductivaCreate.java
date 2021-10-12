package ec.gob.mag.domain.constraint;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
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
public class CialcoOfertaProductivaCreate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ciopId")
	private Long ciopId;

	@ApiModelProperty(value = "Aqui se digita el id de lac ategoria de la oferta productiva", example = "5")
	@NotNull(message = "_error.validation_blank.message")
	@JsonProperty("ciopCatIdOferta")
	private Integer ciopCatIdOferta;

	@ApiModelProperty(value = "Aqui se digita el id de lac ategoria de la oferta productiva", example = "5")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciopCatIdsRuta")
	private String ciopCatIdsRuta;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@JsonProperty("ciopRegUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Integer ciopRegUsu;

	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cia_id")
	@JsonBackReference
	private Cialco cialco;

}