package net.bounceme.chronos.eventos.utils;

import lombok.Getter;

public class Constants {
	
	public enum Estado {
		INICIADA("INICIADA"), EN_PROGRESO("EN_PROGRESO"), RECHAZADA("RECHAZADA"), COMPLETADA("COMPLETADA");
		
		@Getter
		private String value;
		
		Estado(String value) {
			this.value = value;
		}
	}
}
