package net.bounceme.chronos.eventos.utils;

import lombok.Getter;

public class Constants {
	
	public enum State {
		INITIATED("INITIATED"), ACCEPTED("ACCEPTED"), REJECTED("REJECTED"), COMPLETED("COMPLETED");
		
		@Getter
		private String value;
		
		State(String value) {
			this.value = value;
		}
	}
}
