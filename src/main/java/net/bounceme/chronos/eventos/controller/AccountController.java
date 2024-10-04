package net.bounceme.chronos.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.eventos.model.Transfer;
import net.bounceme.chronos.eventos.service.TransferService;

@RestController
public class AccountController {

	// Se usa para transmitir eventos de forma asíncrona
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
	
	// Se usa para acceder al servicio directamente
	@Autowired
	private TransferService transferService;

	@PostMapping("/transfer")
	public String addTransferAsincrono(@RequestBody Transfer transfer) {
		applicationEventPublisher.publishEvent(transfer);
		
		return "Solicitud realizada correctamente";
	}
	
	@PostMapping("/transfer_s")
	public String addTransferSincrono(@RequestBody Transfer transfer) {
		transferService.executeTransfer(transfer);
		
		return "Solicitud realizada correctamente";
	}
}
