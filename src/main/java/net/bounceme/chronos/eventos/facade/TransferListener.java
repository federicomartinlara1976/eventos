package net.bounceme.chronos.eventos.facade;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.eventos.dto.TransferDTO;
import net.bounceme.chronos.eventos.service.TransferService;
import net.bounceme.chronos.eventos.utils.Constants;

@Component
@Slf4j
public class TransferListener {
	
	private static final Integer SLEEP = 1000;
	
	@Value("${amountAlertLimit}")
	private Integer amountAlertLimit;
	
	@Autowired
	private TransferService transferService;
	
	@EventListener
	@SneakyThrows
	public void executeTransfer(TransferDTO transfer) {
		TransferDTO t = transferService.getTransfer(transfer.getId());
		t.setState(Constants.Estado.EN_PROGRESO.getValue());
		t = transferService.saveTransfer(t);
		
		if (transfer.getAmount() < amountAlertLimit) {
			log.info("Iniciando transacción de {} a {}", transfer.getAccountIdFrom(), transfer.getAccountIdTo());
			
			// Simulación de una tarea que va a tardar
			for (int i=0;i<20;i++) {
				log.info("Realizando tarea {}", i);
				Thread.sleep(SLEEP);
			}
			
			t.setEnd(new Date());
			t.setState(Constants.Estado.COMPLETADA.getValue());
			
			log.info("Transacción terminada. Se ha transferido {} de {} a {}", transfer.getAmount(),
					transfer.getAccountIdFrom(), transfer.getAccountIdTo());
		}
		else {
			t.setEnd(new Date());
			t.setState(Constants.Estado.RECHAZADA.getValue());
			
			log.info("La cantidad {} supera el límite establecido. Transacción rechazada", transfer.getAmount());
		}
		
		transferService.saveTransfer(t);
	}

}
