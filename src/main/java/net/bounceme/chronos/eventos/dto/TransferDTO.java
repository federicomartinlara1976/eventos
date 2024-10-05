package net.bounceme.chronos.eventos.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferDTO {
	
	private Long id;
	
	private String accountIdFrom;
	
	private String accountIdTo;
	
	private Integer amount;
	
	private String state;
	
	private Date start;
	
	private Date end;
}
