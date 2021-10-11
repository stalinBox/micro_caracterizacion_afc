package ec.gob.mag.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Constante {

	REGISTRO_ACTIVO(11, "Activo"), REGISTRO_INACTIVO(12, "Inactivo"), DISABLE(0, "disable"), DELETE(2, "activate"),
	ACTIVATE(1, "activate");

	private Integer codigo;
	private String desc;
}
