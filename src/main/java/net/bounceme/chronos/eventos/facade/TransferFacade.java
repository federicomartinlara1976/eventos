package net.bounceme.chronos.eventos.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.eventos.dto.TransferDTO;
import net.bounceme.chronos.eventos.service.TransferService;

@Component
public class TransferFacade {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private TransferService transferService;
	
	public TransferDTO addTransferAsincrona(TransferDTO transfer) {
		TransferDTO t = transferService.initializeTransfer(transfer);
		applicationEventPublisher.publishEvent(t);
		
		return t;
	}
	
	public TransferDTO addTransferSincrona(TransferDTO transfer) {
		TransferDTO t = transferService.initializeTransfer(transfer);
		transferService.executeTransfer(t);
		
		return transferService.getTransfer(t.getId());
	}
	
	public List<TransferDTO> getTransfers() {
		return transferService.getTransfers();
	}
	
	public TransferDTO getTransfer(Long id) {
		return transferService.getTransfer(id);
	}
}
