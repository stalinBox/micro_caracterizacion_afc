package ec.gob.mag.domain;

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
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name = "certificacion_oferta_prod", schema = "sc_gopagro")
public class CertificacionOfertaProd {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "cop_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("copId")
	private Long copId;

	@ApiModelProperty(value = "Aqui se digita el id de la categoria de la Certificación", example = "5")
	@Column(name = "id_cat_certificacion", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("idcatcertificacion")
	private Integer idCatCertificacion;

	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "oopd_id")
	@ApiModelProperty(value = " Clave foranea de la tabla Oferta Detalle", notes = "***")
	@JsonManagedReference
	private OfertaDetalle ofertaDetalle;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/

	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "cop_estado", columnDefinition = "Integer default 11")
	@JsonProperty("copEstado")
	private Integer copEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cop_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("copRegFecha")
	private Date copRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "cop_reg_usu", nullable = false)
	@JsonProperty("copRegUsu")
	private Long copRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cop_act_fecha")
	@JsonProperty("copActFecha")
	private Date copActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del qi", example = "")
	@Column(name = "cop_act_usu")
	@JsonProperty("copActUsu")
	private Long copActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "cop_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("copEliminado")
	private Boolean copEliminado;

	@PrePersist
	void prePersist() {
		this.copEstado = 11;
		this.copEliminado = false;
		this.copRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.copActFecha = new Date();
	}

}
