package net.bounceme.chronos.eventos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.eventos.dto.TransferDTO;
import net.bounceme.chronos.eventos.facade.TransferFacade;

@RestController
public class AccountController {

	@Autowired
	private TransferFacade transferFacade;
	
	@PostMapping("/transfers/asincrona")
	public TransferDTO addTransferAsincrona(@RequestBody TransferDTO transfer) {
		return transferFacade.addTransferAsincrona(transfer);
	}
	
	@PostMapping("/transfers/sincrona")
	public TransferDTO addTransferSincrona(@RequestBody TransferDTO transfer) {
		return transferFacade.addTransferSincrona(transfer);
	}
	
	@GetMapping("/transfers")
	public List<TransferDTO> getTransfers() {
		return transferFacade.getTransfers();
	}
	
	@GetMapping("/transfers/{id}")
	public TransferDTO getTransfer(@PathVariable Long id) {
		return transferFacade.getTransfer(id);
	}
}
