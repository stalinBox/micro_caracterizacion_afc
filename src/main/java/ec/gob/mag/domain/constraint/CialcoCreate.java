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
//import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import ec.gob.mag.domain.TipologiaNivel;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
//@Table(name = "cialco", schema = "sc_gopagro")
public class CialcoCreate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "cia_id", nullable = true, updatable = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ciaId")
	private Long ciaId;

	@ApiModelProperty(value = "Este campo es la clave foranea de la ubicación")
	@Column(name = "ubi_id_provincia")
	@JsonProperty("ubiIdProvincia")
	private Integer ubiIdProvincia;

	@ApiModelProperty(value = "Este campo es la clave foranea de la ubicación")
	@Column(name = "ubi_id_canton")
	@JsonProperty("ubiIdCanton")
	private Integer ubiIdCanton;

	@ApiModelProperty(value = "Este campo es la clave foranea de la ubicación")
	@Column(name = "ubi_id_parroquia")
	@JsonProperty("ubiIdParroquia")
	private Integer ubiIdParroquia;

	@ApiModelProperty(value = "Este campo es la clave foranea de la tabla organización")
	@Column(name = "org_id")
	@JsonProperty("orgId")
	private Integer orgId;

	@ApiModelProperty(value = "Este campo es la clave foranea de la tabla Sociedades")
	@Column(name = "soc_id")
	@JsonProperty("socId")
	private Integer socId;

	@ApiModelProperty(value = "Valor texto de la identificación de la persona", example = "etiqueta")
	@Column(name = "per_identificacion", nullable = false)
	@Size(max = 10, message = "_error.validation_rangeMax.message-[10]")
	@NotBlank(message = "_error.validation_blank.message")
	@NotNull(message = "_error.validation_blank.message")
	@JsonProperty("perIdentificacion")
	private String perIdentificacion;

	@ApiModelProperty(value = "Valor texto del nombre de la persona", example = "etiqueta")
	@Column(name = "per_nombres")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("perNombres")
	private String perNombres;

	@ApiModelProperty(value = "Valor texto del nombre del cialco", example = "etiqueta")
	@Column(name = "cia_nombre", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaNombre")
	private String ciaNombre;

	@ApiModelProperty(value = "Valor texto de la descripcion del cialco", example = "etiqueta")
	@Column(name = "cia_descripcion", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaDescripcion")
	private String ciaDescripcion;

	@ApiModelProperty(value = "Valor texto del sector de referencia del cialco", example = "etiqueta")
	@Size(min = 0, max = 64, message = "_error.validation_range.message-[0, 64]")
	@Column(name = "cia_sect_referencia", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaSectReferencia")
	private String ciaSectReferencia;

	@ApiModelProperty(value = "Aqui se digita el id de la categoria de la funcionalidad", example = "5")
	@Column(name = "cia_id_cat_frecuencia", nullable = false)
	@NotNull(message = "_error.validation_blank.message")
	@JsonProperty("ciaIdCatFrecuencia")
	private Integer ciaIdCatFrecuencia;

	@ApiModelProperty(value = "Valor texto de la direccion del cialco", example = "etiqueta")
	@Size(min = 0, max = 255, message = "_error.validation_range.message-[0, 255]")
	@Column(name = "cia_direccion", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaDireccion")
	private String ciaDireccion;

	@ApiModelProperty(value = "Valor texto de la hora de inicio", example = "etiqueta")
	@Size(max = 10, message = "_error.validation_rangeMax.message-[10]")
	@Column(name = "cia_telefono", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaTelefono")
	private String ciaTelefono;

	@ApiModelProperty(value = "Valor texto de la hora de inicio", example = "etiqueta")
	@Size(max = 10, message = "_error.validation_rangeMax.message-[10]")
	@Column(name = "cia_celular", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCelular")
	private String ciaCelular;

	@ApiModelProperty(value = "Correo de la persona cialco ", example = "usuario@domino.com", required = true)
	@Column(name = "cia_correo", nullable = false)
	@Email(message = "_error.validation_valid_mail.message")
	@NotBlank(message = "_error.validation_blank.message")
	@JsonProperty("ciaCorreo")
	private String ciaCorreo;

	@ApiModelProperty(value = "Coordenada X", example = "etiqueta")
	@Column(name = "cia_cord_x", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordX")
	private String ciaCordX;

	@ApiModelProperty(value = "Coordenada Y", example = "etiqueta")
	@Column(name = "cia_cord_y", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordY")
	private String ciaCordY;

	@ApiModelProperty(value = "Valor texto de la hora de inicio", example = "etiqueta")
	@Column(name = "cia_cord_Z", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordZ")
	private String ciaCordZ;

	@ApiModelProperty(value = "Coordenada Hemisferio", example = "etiqueta")
	@Column(name = "cia_cord_hemisferio", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordHemisferio")
	private String ciaCordHemisferio;

	@ApiModelProperty(value = "Coordenada ZONA", example = "etiqueta")
	@Column(name = "cia_cord_zona", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordZona")
	private String ciaCordZona;

	@ApiModelProperty(value = "Coordenada Latitud", example = "etiqueta")
	@Column(name = "cia_cord_latitud", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordLatitud")
	private String ciaCordLatitud;

	@ApiModelProperty(value = "Valor texto de la hora de inicio", example = "etiqueta")
	@Column(name = "cia_cord_longitud", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaCordLongitud")
	private String ciaCordLongitud;

	@ApiModelProperty(value = "Estado Negocio", example = "etiqueta")
	@Column(name = "cia_estado_negocio", nullable = false)
	@NotNull(message = "_error.validation_blank.message")
	@JsonProperty("ciaEstadoNegocio")
	private Integer ciaEstadoNegocio;

	@ApiModelProperty(value = "Valor texto de la hora de inicio", example = "etiqueta")
	@Column(name = "cia_negocio_observacion", nullable = false)
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("ciaNegocioObservacion")
	private String ciaNegocioObservacion;

	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "ciop_id")
//	@JsonProperty("cialcoOfertaProductiva")
//	private List<CialcoOfertaProductiva> cialcoOfertaProductiva;
//
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "fcia_id")
//	@JsonProperty("funcionamientoCialco")
//	private List<FuncionamientoCialco> funcionamientoCialco;
//
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "oci_id")
//	@JsonProperty("organizacionCialco")
//	private List<OrganizacionCialco> organizacionCialco;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tip_id")
	@JsonProperty("tipologiaNivel")
	@JsonBackReference
	private TipologiaNivel tipologiaNivel;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/

	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "cia_estado", columnDefinition = "Integer default 11")
	@JsonProperty("ciaEstado")
	private Integer ciaEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cia_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("ciaRegFecha")
	@NotNull(message = "_error.validation_blank.message")
	private Date ciaRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "cia_reg_usu", nullable = false)
	@JsonProperty("ciaRegUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Integer ciaRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "cia_act_fecha")
	@JsonProperty("ciaActFecha")
	private Date ciaActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del qi", example = "")
	@Column(name = "cia_act_usu")
	@JsonProperty("ciaActUsu")
	@JsonInclude(Include.NON_NULL)
	private Integer ciaActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "cia_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("ciaEliminado")
	private Boolean ciaEliminado;

	@PrePersist
	void prePersist() {
		this.ciaEstado = 11;
		this.ciaEliminado = false;
		this.ciaRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.ciaActFecha = new Date();
	}

}