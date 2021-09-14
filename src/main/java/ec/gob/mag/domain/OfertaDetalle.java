package ec.gob.mag.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "org_oferta_productiva_det", schema = "sc_gopagro")
public class OfertaDetalle {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "oopd_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("oopdId")
	private Long oopdId;

	@ApiModelProperty(value = "Este campo es el id de la tabla oferta Produciva", example = "5")
	@Column(name = "oopd_cat_id_oferta")
	@NotEmpty(message = "_error.validation_Fblank.message")
	@JsonProperty("oopdCatIdOferta")
	private Integer oopdCatIdOferta;

	@ApiModelProperty(value = "Este campo es el di de la tabla variedad", example = "5")
	@Column(name = "oopd_agr_id_variedad")
	// @NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("oopdAgrIdVariedad")
	private Integer oopdAgrIdVariedad;

	@ApiModelProperty(value = "Condicion", example = "Condición")
	// @Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "oopd_agr_condicion", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("oopdAgrCondicion")
	private String oopdAgrCondicion;

	@ApiModelProperty(value = "Este campo es el valor de la superficie", example = "5")
	@Column(name = "oopd_superficie_sembrada")
	// @NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("oopdSuperficieSembrada")
	private Integer oopdSuperficieSembrada;

	@ApiModelProperty(value = "Unidad de medida de la siembra", example = "KG")
	// @Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "oopd_unidad_medida_siembra", nullable = false)
	// @NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("oopdUnidadMedidaSiembra")
	private String oopdUnidadMedidaSiembra;

	@ApiModelProperty(value = "Este campo es el id del metodo de siembra", example = "5")
	@Column(name = "oopd_metodo_siembra_id")
	// @NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("oopdMetodoSiembraId")
	private Integer oopdMetodoSiembraId;

	@ApiModelProperty(value = "Valor en texto de la producción estimada", example = "1000")
	// @Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "oopd_produccion_estimada", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("oopdProduccionEstimada")
	private String oopdProduccionEstimada;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para no posee certificación  y t= true para indicar que si posee certificacion", required = true, allowableValues = "false=>no posee Certificacion, true=> Posee Certificación", example = "")
	@Column(name = "oopd_dispone_certificacion", columnDefinition = "boolean default false")
	@JsonProperty("oopdDisponeCertificacion")
	private Boolean oopdDisponeCertificacion;

	@ApiModelProperty(value = "Este campo es el di de la caegoria de presentaión", example = "5")
	@Column(name = "oopd_id_cat_presentacion")
	// @NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("oopdIdCatPresentacion")
	private Integer oopdIdCatPresentacion;

	@ApiModelProperty(value = "Este campo es el id de la unidad de mediad", example = "5")
	@Column(name = "oopd_id_cat_unidad_medida")
	// @NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("oopdIdCatUnidadMedida")
	private Integer oopdIdCatUnidadMedida;

	@ApiModelProperty(value = "Este campo es el id de la actividad economica", example = "5")
	@Column(name = "oopd_id_cat_actividad_economica")
	// @NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("oopdIdCatActividadEconomica")
	private Integer oopdIdCatActividadEconomica;

	@ApiModelProperty(value = "Este campo es el id de la categoria del producto", example = "5")
	@Column(name = "oopd_id_cat_producto")
	// @NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("oopdIdCatProducto")
	private Integer oopdIdCatProducto;

	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla persona tipo", position = 15)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "oop_id")
	@JsonProperty("organizacionOfertaProductiva")
	@JsonBackReference
	private OrganizacionOfertaProductiva organizacionOfertaProductiva;

	@ApiModelProperty(value = "Este campo es  la clave primaria de la tabla persona tipo", position = 16)
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cop_id")
	@JsonProperty("certificacionOfertaProd")
	private List<CertificacionOfertaProd> certificacionOfertaProd;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/

	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "oopd_estado", columnDefinition = "Integer default 11")
	@JsonProperty("oopdEstado")
	private Integer oopdEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "oopd_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("oopdRegFecha")
	private Date oopdRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "oopd_reg_usu", nullable = false)
	@JsonProperty("oopdRegUsu")
	private Integer oopdRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "oopd_act_fecha")
	@JsonProperty("oopdActFecha")
	private Date oopdActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del qi", example = "")
	@Column(name = "oopd_act_usu")
	@JsonProperty("oopdActUsu")
	private Integer oopdActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "oopd_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("oopdEliminado")
	private Boolean oopdEliminado;

	@PrePersist
	void prePersist() {
		this.oopdEstado = 11;
		this.oopdEliminado = false;
		this.oopdRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.oopdActFecha = new Date();
	}

}
