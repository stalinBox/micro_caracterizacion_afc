package ec.gob.mag.domain.constraint;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
//import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.domain.OrganizacionOfertaProductiva;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class OfertaDetalleCreate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@JsonProperty("oopdId")
	private Long oopdId;

	@ApiModelProperty(value = "Este campo es el id de la tabla oferta Produciva", example = "5")
	@JsonProperty("oopdCatIdOferta")
	@NotNull(message = "_error.validation_blank.message")
	private Integer oopdCatIdOferta;

	@ApiModelProperty(value = "Este campo es el di de la tabla variedad", example = "5")
	@JsonProperty("oopdAgrIdVariedad")
	@NotNull(message = "_error.validation_blank.message")
	private Integer oopdAgrIdVariedad;

	@ApiModelProperty(value = "Condicion", example = "Condición")
	@JsonProperty("oopdAgrCondicion")
	private String oopdAgrCondicion;

	@ApiModelProperty(value = "Este campo es el valor de la superficie", example = "5")
	@JsonProperty("oopdSuperficieSembrada")
	private Integer oopdSuperficieSembrada;

	@ApiModelProperty(value = "Unidad de medida de la siembra", example = "KG")
	@JsonProperty("oopdUnidadMedidaSiembra")
	private String oopdUnidadMedidaSiembra;

	@ApiModelProperty(value = "Este campo es el id del metodo de siembra", example = "5")
	@JsonProperty("oopdMetodoSiembraId")
	private Integer oopdMetodoSiembraId;

	@ApiModelProperty(value = "Valor en texto de la producción estimada", example = "1000")
	@JsonProperty("oopdProduccionEstimada")
	private String oopdProduccionEstimada;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para no posee certificación  y t= true para indicar que si posee certificacion", required = true, allowableValues = "false=>no posee Certificacion, true=> Posee Certificación", example = "")
	@JsonProperty("oopdDisponeCertificacion")
	private Boolean oopdDisponeCertificacion;

	@ApiModelProperty(value = "Este campo es el di de la caegoria de presentaión", example = "5")
	@JsonProperty("oopdIdCatPresentacion")
	private Integer oopdIdCatPresentacion;

	@ApiModelProperty(value = "Este campo es el id de la unidad de mediad", example = "5")
	@JsonProperty("oopdIdCatUnidadMedida")
	private Integer oopdIdCatUnidadMedida;

	@ApiModelProperty(value = "Este campo es el id de la actividad economica", example = "5")
	@JsonProperty("oopdIdCatActividadEconomica")
	private Integer oopdIdCatActividadEconomica;

	@ApiModelProperty(value = "Este campo es el id de la categoria del producto", example = "5")
	@JsonProperty("oopdIdCatProducto")
	@NotNull(message = "_error.validation_blank.message")
	private Integer oopdIdCatProducto;

	@ApiModelProperty(value = "Este campo es el id de la categoria del producto", example = "5")
	@JsonProperty("oopdCatIdsRuta")
	private String oopdCatIdsRuta;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "oopd_reg_usu", nullable = false)
	@JsonProperty("oopdRegUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Integer oopdRegUsu;

	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "oop_id")
	@JsonProperty("organizacionOfertaProductiva")
	@JsonBackReference
	private OrganizacionOfertaProductiva organizacionOfertaProductiva;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/

//	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
//	@Column(name = "oopd_estado", columnDefinition = "Integer default 11")
//	@JsonProperty("oopdEstado")
//	private Integer oopdEstado;
//
//	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "oopd_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//	@JsonProperty("oopdRegFecha")
//	private Date oopdRegFecha;
//
//	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
//	@Column(name = "oopd_reg_usu", nullable = false)
//	@JsonProperty("oopdRegUsu")
//	private Integer oopdRegUsu;
//
//	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "oopd_act_fecha")
//	@JsonProperty("oopdActFecha")
//	private Date oopdActFecha;
//
//	@ApiModelProperty(value = "Id de usuario que actualizacio del qi", example = "")
//	@Column(name = "oopd_act_usu")
//	@JsonProperty("oopdActUsu")
//	private Integer oopdActUsu;
//
//	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
//	@Column(name = "oopd_eliminado", columnDefinition = "boolean default false")
//	@JsonProperty("oopdEliminado")
//	private Boolean oopdEliminado;

}
