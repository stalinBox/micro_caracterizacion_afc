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
@Table(name = "cialco_oferta_productiva", schema = "sc_gopagro")
public class CialcoOfertaProductiva {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@Column(name = "ciop_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("ciopId")
	private Long ciopId;

	@ApiModelProperty(value = "Aqui se digita el id de lac ategoria de la oferta productiva", example = "5")
	@Column(name = "ciop_cat_id_oferta", nullable = false)
	@JsonProperty("ciopCatIdOferta")
	private Integer ciopCatIdOferta;

	@ApiModelProperty(value = "Aqui se digita el id de lac ategoria de la oferta productiva", example = "5")
	@Column(name = "ciop_cat_ids_ruta", nullable = false)
	@JsonProperty("ciopCatIdsRuta")
	private String ciopCatIdsRuta;
	/*****************************************************
	 * SECCION - RELACIONES JPA
	 *****************************************************/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cia_id")
	@ApiModelProperty(value = " Clave foranea de la tabla CIALCO", notes = "***")
	@JsonBackReference
	private Cialco cialco;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "ciop_estado", columnDefinition = "Integer default 11")
	@JsonProperty("ciopEstado")
	private Integer ciopEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ciop_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("ciopRegFecha")
	private Date ciopRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "ciop_reg_usu", nullable = false)
	@JsonProperty("ciopRegUsu")
	private Integer ciopRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ciop_act_fecha")
	@JsonProperty("ciopActFecha")
	private Date ciopActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del qi", example = "")
	@Column(name = "ciop_act_usu")
	@JsonProperty("ciopActUsu")
	private Integer ciopActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "ciop_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("ciopEliminado")
	private Boolean ciopEliminado;

	@PrePersist
	void prePersist() {
		this.ciopEstado = 11;
		this.ciopEliminado = false;
		this.ciopRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.ciopActFecha = new Date();
	}
}