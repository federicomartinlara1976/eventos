package net.bounceme.chronos.eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.bounceme.chronos.eventos.model.Transfer;

@RestController
public class AccountController {

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@PostMapping("/transfer")
	public String addTransfer(@RequestBody Transfer transfer) {
		applicationEventPublisher.publishEvent(transfer);
		
		return "Solicitud realizada correctamente";
	}
}
