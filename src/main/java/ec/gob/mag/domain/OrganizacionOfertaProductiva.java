package ec.gob.mag.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
//import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "organizacion_oferta_productiva", schema = "sc_gopagro")
public class OrganizacionOfertaProductiva {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "oop_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("oopId")
	private Long oopId;

	@ApiModelProperty(value = "Este campo es la clave foranea de la tabla Organización", required = true, readOnly = true)
	@Column(name = "org_id")
	@JsonProperty("orgId")
	private Integer orgId;

	@ApiModelProperty(value = "Valor texto de la Identificación de la organización", example = "nombre categoria")
	@Column(name = "org_identificacion", nullable = false)
	@JsonProperty("orgIdentificacion")
	private String orgIdentificacion;

	@ApiModelProperty(value = "Valor texto de la razon social de la Organización", example = "nombre categoria")
	@Column(name = "org_razon_social", nullable = false)
	@JsonProperty("orgRazonSocial")
	private String orgRazonSocial;

	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "oopd_id")
	@JsonProperty("ofertaDetalle")
	private List<OfertaDetalle> ofertaDetalle;

	/*****************************************************
	 * SECCION - CAMPOSS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/

	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "oop_estado", columnDefinition = "Integer default 11")
	@JsonProperty("oopEstado")
	private Integer oopEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "oop_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("oopRegFecha")
	private Date oopRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "oop_reg_usu", nullable = false)
	@JsonProperty("oopRegUsu")
	private Integer oopRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "oop_act_fecha")
	@JsonProperty("oopActFecha")
	private Date oopActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del campo", example = "")
	@Column(name = "oop_act_usu")
	@JsonProperty("oopActUsu")
	private Integer oopActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "oop_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("oopEliminado")
	private Boolean oopEliminado;

	@PrePersist
	void prePersist() {
		this.oopEstado = 11;
		this.oopEliminado = false;
		this.oopRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.oopActFecha = new Date();
	}

}
