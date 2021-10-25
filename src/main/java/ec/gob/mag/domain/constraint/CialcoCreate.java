package ec.gob.mag.domain.constraint;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class CialcoCreate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ciaId")
	private Long ciaId;

	@ApiModelProperty(value = "Este campo es la clave foranea de la ubicación")
	@JsonProperty("ubiIdProvincia")
	private Integer ubiIdProvincia;

	@ApiModelProperty(value = "Este campo es la clave foranea de la ubicación")
	@JsonProperty("ubiIdCanton")
	private Integer ubiIdCanton;

	@ApiModelProperty(value = "Este campo es la clave foranea de la ubicación")
	@JsonProperty("ubiIdParroquia")
	private Integer ubiIdParroquia;

	@ApiModelProperty(value = "Este campo es la clave foranea de la tabla organización")
	@JsonProperty("orgId")
	private Integer orgId;

	@ApiModelProperty(value = "Este campo es la clave foranea de la tabla Sociedades")
	@JsonProperty("socId")
	private Integer socId;

	@ApiModelProperty(value = "Valor texto de la identificación de la persona", example = "etiqueta")
	@Size(max = 10, message = "_error.validation_rangeMax.message-[10]")
	@NotBlank(message = "_error.validation_blank.message")
	@NotNull(message = "_error.validation_blank.message")
	@JsonProperty("perIdentificacion")
	private String perIdentificacion;

	@ApiModelProperty(value = "Valor texto del nombre de la persona", example = "etiqueta")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("perNombres")
	private String perNombres;

	@ApiModelProperty(value = "Valor texto del nombre del cialco", example = "etiqueta")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaNombre")
	private String ciaNombre;

	@ApiModelProperty(value = "Valor texto de la descripcion del cialco", example = "etiqueta")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaDescripcion")
	private String ciaDescripcion;

	@ApiModelProperty(value = "Valor texto del sector de referencia del cialco", example = "etiqueta")
	@Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaSectReferencia")
	private String ciaSectReferencia;

	@ApiModelProperty(value = "Aqui se digita el id de la categoria de la funcionalidad", example = "5")
	@NotNull(message = "_error.validation_blank.message")
	@JsonProperty("ciaIdCatFrecuencia")
	private Integer ciaIdCatFrecuencia;

	@ApiModelProperty(value = "Valor texto de la direccion del cialco", example = "etiqueta")
	@Size(min = 0, max = 255, message = "_error.validation_range.message-[0, 255]")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaDireccion")
	private String ciaDireccion;

	@ApiModelProperty(value = "Valor texto de la hora de inicio", example = "etiqueta")
	@Size(max = 10, message = "_error.validation_rangeMax.message-[10]")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaTelefono")
	private String ciaTelefono;

	@ApiModelProperty(value = "Valor texto de la hora de inicio", example = "etiqueta")
	@Size(max = 10, message = "_error.validation_rangeMax.message-[10]")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCelular")
	private String ciaCelular;

	@ApiModelProperty(value = "Correo de la persona cialco ", example = "usuario@domino.com", required = true)
	@Email(message = "_error.validation_valid_mail.message")
	@NotBlank(message = "_error.validation_blank.message")
	@JsonProperty("ciaCorreo")
	private String ciaCorreo;

	@ApiModelProperty(value = "Coordenada X", example = "etiqueta")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordX")
	private String ciaCordX;

	@ApiModelProperty(value = "Coordenada Y", example = "etiqueta")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordY")
	private String ciaCordY;

	@ApiModelProperty(value = "Valor texto de la hora de inicio", example = "etiqueta")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordZ")
	private String ciaCordZ;

	@ApiModelProperty(value = "Coordenada Hemisferio", example = "etiqueta")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordHemisferio")
	private String ciaCordHemisferio;

	@ApiModelProperty(value = "Coordenada ZONA", example = "etiqueta")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordZona")
	private String ciaCordZona;

	@ApiModelProperty(value = "Coordenada Latitud", example = "etiqueta")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordLatitud")
	private String ciaCordLatitud;

	@ApiModelProperty(value = "Valor texto de la hora de inicio", example = "etiqueta")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordLongitud")
	private String ciaCordLongitud;

	@ApiModelProperty(value = "Estado Negocio", example = "etiqueta")
	@NotNull(message = "_error.validation_blank.message")
	@JsonProperty("ciaEstadoNegocio")
	private Integer ciaEstadoNegocio;

	@ApiModelProperty(value = "Valor texto de la hora de inicio", example = "etiqueta")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaNegocioObservacion")
	private String ciaNegocioObservacion;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@JsonProperty("ciaRegUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Integer ciaRegUsu;

	@ApiModelProperty(value = "Estado Negocio", example = "id")
	@JsonProperty("tipCatId")
	@NotNull(message = "_error.validation_blank.message")
	private Integer tipCatId;
	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "tip_id")
//	@JsonProperty("tipologiaNivel")
//	@JsonBackReference
//	private TipologiaNivel tipologiaNivel;

//	/*****************************************************
//	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
//	 *****************************************************/
//
//	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
//	@Column(name = "cia_estado", columnDefinition = "Integer default 11")
//	@JsonProperty("ciaEstado")
//	private Integer ciaEstado;
//
//	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "cia_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
//	@JsonProperty("ciaRegFecha")
//	@NotNull(message = "_error.validation_blank.message")
//	private Date ciaRegFecha;
//
//	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
//	@Column(name = "cia_reg_usu", nullable = false)
//	@JsonProperty("ciaRegUsu")
//	@NotNull(message = "_error.validation_blank.message")
//	private Integer ciaRegUsu;
//
//	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "cia_act_fecha")
//	@JsonProperty("ciaActFecha")
//	private Date ciaActFecha;
//
//	@ApiModelProperty(value = "Id de usuario que actualizacio del qi", example = "")
//	@Column(name = "cia_act_usu")
//	@JsonProperty("ciaActUsu")
//	@JsonInclude(Include.NON_NULL)
//	private Integer ciaActUsu;
//
//	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
//	@Column(name = "cia_eliminado", columnDefinition = "boolean default false")
//	@JsonProperty("ciaEliminado")
//	private Boolean ciaEliminado;
//
//	@PrePersist
//	void prePersist() {
//		this.ciaEstado = 11;
//		this.ciaEliminado = false;
//		this.ciaRegFecha = new Date();
//	}
//
//	@PreUpdate
//	void preUpdate() {
//		this.ciaActFecha = new Date();
//	}

}