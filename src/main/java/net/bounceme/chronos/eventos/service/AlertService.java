package net.bounceme.chronos.eventos.service;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.eventos.model.Transfer;

@Service
@Slf4j
public class AlertService {

	private static final int ALERT_AMOUNT = 3000;

	@EventListener
	public void sendAlertIfNeeded(Transfer transfer) {
		if (transfer.getAmount() > ALERT_AMOUNT) {
			log.info("La cantidad {} supera el límite establecido a {}. Se enviará alerta", transfer.getAmount(), ALERT_AMOUNT);
		} else {
			log.info("Cantidad {} dentro del límite", transfer.getAmount());
		}
	}
}
