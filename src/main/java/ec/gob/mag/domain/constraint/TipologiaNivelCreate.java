package ec.gob.mag.domain.constraint;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class TipologiaNivelCreate {

	@Id
	@ApiModelProperty(value = "Este campo es la clave primaria de la tabla", required = true, readOnly = true)
	@JsonProperty("tipId")
	private Long tipId;

	@ApiModelProperty(value = "Este campo es la clave de la catalogo")
	@JsonProperty("tipCatId")
	@NotNull(message = "_error.validation_blank.message")
	private Integer tipCatId;

	@ApiModelProperty(value = "Valor texto del nombre de la categoria", example = "Nombre Categoria")
	@Size(min = 3, max = 200, message = "_error.validation_range.message-[3, 200]")
	@NotEmpty(message = "_error.validation_blank.message")
	@JsonProperty("tipCatNombre")
	private String tipCatNombre;

	@ApiModelProperty(value = "Valor texto de la descripcion de la categoria", example = "Descripcion")
	@Size(min = 0, max = 255, message = "_error.validation_range.message-[0, 255]")
	@JsonProperty("tipCatDescripcion")
	private String tipCatDescripcion;

	@ApiModelProperty(value = "Aqui se digita el numero del tipo nivel (Consultar)", example = "2")
	@NotNull(message = "_error.validation_blank.message")
	@JsonProperty("tipTipoNivel")
	private Integer tipTipoNivel;

	/*****************************************************
	 * SECCION - CAMPOSS POR DEFECTO EN TODAS LAS ENTIDADES
	 *****************************************************/
	@ApiModelProperty(value = "Id de usuario que creó el regristro")
	@JsonProperty("tipRegUsu")
	@NotNull(message = "_error.validation_blank.message")
	private Integer tipRegUsu;

}