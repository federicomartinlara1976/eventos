package net.bounceme.chronos.eventos.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.eventos.model.Transfer;

@Service
@Slf4j
public class TransferService {
	
	private static final Integer SLEEP = 10000;

	@EventListener
	@SneakyThrows
	public void executeTransfer(Transfer transfer) {
		log.info("Iniciando transacción de {} a {}", transfer.getAccountIdFrom(), transfer.getAccountIdTo());
		
		log.info("Hago cosas...");
		
		// Simulación de una tarea que va a tardar
		Thread.sleep(SLEEP);
		
		log.info("He terminado de hacer mis cosas");
		
		log.info("Transacción terminada. Se ha transferido {} de {} a {}", transfer.getAmount(),
				transfer.getAccountIdFrom(), transfer.getAccountIdTo());
	}
}
