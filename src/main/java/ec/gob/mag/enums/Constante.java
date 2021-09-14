package ec.gob.mag.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Constante {

	REGISTRO_ACTIVO(11, "Activo"), REGISTRO_INACTIVO(12, "Inactivo");

	private Integer codigo;
	private String desc;
}
