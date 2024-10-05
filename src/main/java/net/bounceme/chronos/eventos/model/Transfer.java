package net.bounceme.chronos.eventos.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transfer")
public class Transfer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "accountIdFrom")
	private String accountIdFrom;
	
	@Column(name = "accountIdTo")
	private String accountIdTo;
	
	@Column(name = "amount")
	private Integer amount;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "start")
	private Date start;
	
	@Column(name = "end")
	private Date end;
}
