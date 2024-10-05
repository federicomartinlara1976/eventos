package net.bounceme.chronos.eventos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.eventos.dto.TransferDTO;
import net.bounceme.chronos.eventos.service.TransferService;

@RestController
public class AccountController {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private TransferService transferService;
	
	@PostMapping("/transfers/asincrona")
	public TransferDTO addTransferAsincrona(@RequestBody TransferDTO transfer) {
		TransferDTO t = transferService.initializeTransfer(transfer);
		applicationEventPublisher.publishEvent(t);
		
		return t;
	}
	
	@PostMapping("/transfers/sincrona")
	public TransferDTO addTransferSincrona(@RequestBody TransferDTO transfer) {
		TransferDTO t = transferService.initializeTransfer(transfer);
		transferService.executeTransfer(t);
		
		return transferService.getTransfer(t.getId());
	}
	
	@GetMapping("/transfers")
	public List<TransferDTO> getTransfers() {
		return transferService.getTransfers();
	}
	
	@GetMapping("/transfers/{id}")
	public TransferDTO getTransfer(@PathVariable Long id) {
		return transferService.getTransfer(id);
	}
}
