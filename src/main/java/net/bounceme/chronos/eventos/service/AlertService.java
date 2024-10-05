package net.bounceme.chronos.eventos.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.eventos.model.Transfer;

@Service
@Slf4j
public class AlertService {

	@Value("${amountAlertLimit}")
	private Integer amountAlertLimit;

	@EventListener
	public void sendAlertIfNeeded(Transfer transfer) {
		if (transfer.getAmount() > amountAlertLimit) {
			log.info("La cantidad {} supera el límite establecido a {}. Se enviará alerta", transfer.getAmount(), amountAlertLimit);
		} else {
			log.info("Cantidad {} dentro del límite", transfer.getAmount());
		}
	}
}
