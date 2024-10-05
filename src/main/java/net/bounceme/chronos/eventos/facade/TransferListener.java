package net.bounceme.chronos.eventos.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.eventos.dto.TransferDTO;
import net.bounceme.chronos.eventos.service.TransferService;

@Component
@Slf4j
public class TransferListener {
	
	@Autowired
	private TransferService transferService;
	
	@EventListener
	public void executeTransfer(TransferDTO transfer) {
		transferService.executeTransfer(transfer);
	}

}
