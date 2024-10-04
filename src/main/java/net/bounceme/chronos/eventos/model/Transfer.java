package net.bounceme.chronos.eventos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transfer {
	
	private String accountIdFrom;
	
	private String accountIdTo;
	
	private Integer amount;
}
