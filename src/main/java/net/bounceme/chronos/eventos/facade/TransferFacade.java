package net.bounceme.chronos.eventos.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.eventos.dto.TransferDTO;
import net.bounceme.chronos.eventos.service.TransferDataService;
import net.bounceme.chronos.eventos.service.TransferService;

@Component
@Slf4j
public class TransferFacade {
	
	@Value("${amountAlertLimit}")
	private Integer amountAlertLimit;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private TransferDataService transferDataService;
	
	@Autowired
	private TransferService transferService;
	
	public TransferDTO addTransferAsincrona(TransferDTO transfer) {
		TransferDTO t = transferDataService.initializeTransfer(transfer);
		applicationEventPublisher.publishEvent(t);
		
		return t;
	}
	
	@SneakyThrows
	public Optional<TransferDTO> addTransferSincrona(TransferDTO transfer) {
		TransferDTO t = transferDataService.initializeTransfer(transfer);
		transferService.executeTransfer(t);
		return transferDataService.getTransfer(t.getId());
	}
	
	public List<TransferDTO> getTransfers() {
		return transferDataService.getTransfers();
	}
	
	public Optional<TransferDTO> getTransfer(Long id) {
		return transferDataService.getTransfer(id);
	}
}
