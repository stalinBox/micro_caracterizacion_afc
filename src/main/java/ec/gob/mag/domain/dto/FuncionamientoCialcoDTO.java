package ec.gob.mag.domain.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

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
public class FuncionamientoCialcoDTO {

	@Id
	private String nro;
	private String fcia_id;
	private String cia_id;
	private String cia_nombre;
	private String fcia_id_cat_dia_funcionamiento;
	private String fcia_id_cat_hora_inicio;
	private String fcia_id_cat_hora_fin;
	private String fcia_estado;
	private String fcia_reg_usu;
	private String fcia_eliminado;
	private Integer totalRecords;

}
