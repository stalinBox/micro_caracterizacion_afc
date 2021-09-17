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
//import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@Table(name = "organizacion_cialco", schema = "sc_gopagro")
public class OrganizacionCialco {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "oci_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ociId")
	private Long ociId;

	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla")
	@Column(name = "org_id")
	@JsonProperty("orgId")
	private Integer orgId;

	@ApiModelProperty(value = "Nombre de la razon social", example = "nombre de la razon social")
	// @Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "org_razon_social", nullable = false)
//	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("orgRazonSocial")
	private String orgRazonSocial;

	@ApiModelProperty(value = "Ruc de la organizacion", example = "0000000000001")
	// @Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "org_identificacion", nullable = false)
//	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("orgIdentificacion")
	private String orgIdentificacion;

	@ApiModelProperty(value = "Estado Negocio", example = "Estado Negocio")
	// @Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "oci_estado_negocio", nullable = false)
//	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ociEstadoNegocio")
	private Integer ociEstadoNegocio;

	@ApiModelProperty(value = "Valor texto del nombre de la categoria", example = "nombre categoria")
	// @Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "oci_negocio_observacion", nullable = false)
//	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ociNegocioObservacion")
	private String ociNegocioObservacion;

	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cia_id")
	@ApiModelProperty(value = " Clave foranea de la tabla CIALCO", notes = "***")
	@JsonBackReference
	private Cialco cialco;

	/*****************************************************
	 * SECCION - CAMPOSS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/

	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "oci_estado", columnDefinition = "Integer default 11")
	@JsonProperty("ociEstado")
	@JsonInclude(Include.NON_NULL)
	private Integer ociEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "oci_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("ociRegFecha")
	private Date ociRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "oci_reg_usu", nullable = false)
	@JsonProperty("ociRegUsu")
	private Integer ociRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "oci_act_fecha")
	@JsonProperty("ociActFecha")
	private Date ociActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "oci_act_usu")
	@JsonProperty("ociActUsu")
	private Integer ociActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "oci_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("ociEliminado")
	private Boolean ociEliminado;

	@PrePersist
	void prePersist() {
		this.ociEstado = 11;
		this.ociEliminado = false;
		this.ociRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.ociActFecha = new Date();
	}

}