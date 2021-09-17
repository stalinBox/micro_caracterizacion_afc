package ec.gob.mag.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

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
@Table(name = "tipologia_nivel", schema = "sc_gopagro")
public class TipologiaNivel {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "tip_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("tipId")
	private Long tipId;

	@ApiModelProperty(value = "Este campo es la clave de la catalogo", required = true, readOnly = true)
	@Column(name = "tip_cat_id", unique = true, nullable = false)
	@JsonProperty("tipCatId")
	private Integer tipCatId;

	@ApiModelProperty(value = "Valor texto del nombre de la categoria", example = "nombre categoria")
	// @Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "tip_cat_nombre", nullable = false)
//	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("tipCatNombre")
	private String tipCatNombre;

	@ApiModelProperty(value = "Valor texto de la descripcion de la categoria", example = "descripcion categoria")
	// @Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "tip_cat_descripcion", nullable = false)
//	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("tipCatDescripcion")
	private String tipCatDescripcion;

	@ApiModelProperty(value = "Aqui se digita el numero del tipo nivel (Consultar)", example = "3")
	@Column(name = "tip_tipo_nivel", nullable = false)
//	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("tipTipoNivel")
	private Integer tipTipoNivel;

	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cia_id")
	@JsonProperty("cialco")
	private List<Cialco> cialco;

	/*****************************************************
	 * SECCION - CAMPOSS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "tip_estado", columnDefinition = "Integer default 11")
	@JsonProperty("tipEstado")
	private Integer tipEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tip_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("tipRegFecha")
	private Date tipRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "tip_reg_usu", nullable = false)
	@JsonProperty("tipRegUsu")
	private Integer tipRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "tip_act_fecha")
	@JsonProperty("tipActFecha")
	private Date tipActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "tip_act_usu")
	@JsonProperty("tipActUsu")
	private Integer tipActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "tip_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("tipEliminado")
	private Boolean tipEliminado;

	@PrePersist
	void prePersist() {
		this.tipEstado = 11;
		this.tipEliminado = false;
		this.tipRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.tipActFecha = new Date();
	}
}