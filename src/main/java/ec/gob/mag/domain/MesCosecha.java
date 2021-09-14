package ec.gob.mag.domain;

import lombok.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "mes_cosecha_oferta_prod", schema = "sc_gopagro")
public class MesCosecha {

	@Id
	@Column(name = "mco_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("mcoId")
	private Long mcoId;

	@Column(name = "mco_enero")
	@JsonProperty("mcoEnero")
	private Boolean mcoEnero;

	@Column(name = "mco_febrero")
	@JsonProperty("mcoFebrero")
	private Boolean mcoFebrero;

	@Column(name = "mco_marzo")
	@JsonProperty("mcoMarzo")
	private Boolean mcoMarzo;

	@Column(name = "mco_abril")
	@JsonProperty("mcoAbril")
	private Boolean mcoAbril;

	@Column(name = "mco_mayo")
	@JsonProperty("mcoMayo")
	private Boolean mcoMayo;

	@Column(name = "mco_junio")
	@JsonProperty("mcoJunio")
	private Boolean mcoJunio;

	@Column(name = "mco_julio")
	@JsonProperty("mcoJulio")
	private Boolean mcoJulio;

	@Column(name = "mco_agosto")
	@JsonProperty("mcoAgosto")
	private Boolean mcoAgosto;

	@Column(name = "mco_septiembre")
	@JsonProperty("mcoSeptiembre")
	private Boolean mcoSeptiembre;

	@Column(name = "mco_octubre")
	@JsonProperty("mcoOctubre")
	private Boolean mcoOctubre;

	@Column(name = "mco_noviembre")
	@JsonProperty("mcoNoviembre")
	private Boolean mcoNoviembre;

	@Column(name = "mco_diciembre")
	@JsonProperty("mcoDiciembre")
	private Boolean mcoDiciembre;

	/*****************************************************
	 * SECCION - CAMPOS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/

	@ApiModelProperty(value = "11=activo  12=inactivo", required = true, allowableValues = "11=>activo, 12=>inactivo", example = "11")
	@Column(name = "mco_estado", columnDefinition = "Integer default 11")
	@JsonProperty("mcoEstado")
	private Integer mcoEstado;

	@ApiModelProperty(value = "Fecha de registro del campo", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "mco_reg_fecha", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@JsonProperty("mcoRegFecha")
	private Date mcoRegFecha;

	@ApiModelProperty(value = "Id de usuario que creó el regristro", example = "")
	@Column(name = "mco_reg_usu", nullable = false)
	@JsonProperty("mcoRegUsu")
	private Long mcoRegUsu;

	@ApiModelProperty(value = "Fecha en la que hizo la actualización del registro", example = "")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "mco_act_fecha")
	@JsonProperty("mcoActFecha")
	private Date mcoActFecha;

	@ApiModelProperty(value = "Id de usuario que actualizacio del qi", example = "")
	@Column(name = "mco_act_usu")
	@JsonProperty("mcoActUsu")
	private Long mcoActUsu;

	@ApiModelProperty(value = "Este campo almacena los valores f =false para eliminado logico  y t= true para indicar que está activo", required = true, allowableValues = "false=>no eliminado lógico, true=> eliminado lógico", example = "")
	@Column(name = "mco_eliminado", columnDefinition = "boolean default false")
	@JsonProperty("mcoEliminado")
	private Boolean mcoEliminado;

	@PrePersist
	void prePersist() {
		this.mcoEstado = 11;
		this.mcoEliminado = false;
		this.mcoRegFecha = new Date();
	}

	@PreUpdate
	void preUpdate() {
		this.mcoActFecha = new Date();
	}

}
