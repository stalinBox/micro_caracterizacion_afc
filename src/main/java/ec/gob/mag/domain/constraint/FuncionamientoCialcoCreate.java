package ec.gob.mag.domain.constraint;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class FuncionamientoCialcoCreate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@JsonProperty("fciaId")
	private Long fciaId;

	@ApiModelProperty(value = "ID de la categoria del dia de funcionamiento")
	@JsonProperty("fciaIdCatdiaFuncionamiento")
	@NotNull(message = "_error.validation_blank.message")
	private Integer fciaIdCatdiaFuncionamiento;

	@ApiModelProperty(value = "Hora de Inicio de Actividades")
	@JsonProperty("fciaIdCatHoraInicio")
	private Integer fciaIdCatHoraInicio;

	@ApiModelProperty(value = "Hora de Fin de Actividades")
	@JsonProperty("fciaIdCatHoraFin")
	private Integer fciaIdCatHoraFin;

	@ApiModelProperty(value = "Id de usuario que creó el regristro")
	@JsonProperty("fciaRegUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Integer fciaRegUsu;

	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cia_id")
	@ApiModelProperty(value = " Clave foranea de la tabla CIALCO", notes = "***")
	@JsonBackReference
	private Cialco cialco;

}