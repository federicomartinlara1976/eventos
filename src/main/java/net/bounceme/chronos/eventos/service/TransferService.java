package net.bounceme.chronos.eventos.service;

import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.eventos.dto.TransferDTO;
import net.bounceme.chronos.eventos.utils.ConditionalExecutor;
import net.bounceme.chronos.eventos.utils.Constants;

@Service
@Slf4j
public class TransferService {

	@Value("${amountAlertLimit}")
	private Integer amountAlertLimit;

	Predicate<TransferDTO> isBelowLimit = t -> t.getAmount() < amountAlertLimit;
	Consumer<TransferDTO> approvedAction = t -> acceptTransfer(t);
	Consumer<TransferDTO> rejectedAction = t -> rejectTransfer(t);

	@Autowired
	private TransferDataService transferDataService;

	@SneakyThrows
	public void executeTransfer(TransferDTO transfer) {
		transferDataService.getTransfer(transfer.getId()).ifPresent(t -> {
			t.setState(Constants.Estado.EN_PROGRESO.getValue());
			t = transferDataService.saveTransfer(t);

			ConditionalExecutor.executeIfOrElse(isBelowLimit, transfer, approvedAction,
					rejectedAction);
		});
	}

	private void acceptTransfer(TransferDTO t) {
		log.info("Iniciando transacción de {} a {}", t.getAccountIdFrom(), t.getAccountIdTo());

		t.setEnd(new Date());
		t.setState(Constants.Estado.COMPLETADA.getValue());
		
		transferDataService.saveTransfer(t);

		log.info("Transacción terminada. Se ha transferido {} de {} a {}", t.getAmount(), t.getAccountIdFrom(),
				t.getAccountIdTo());
	}

	private void rejectTransfer(TransferDTO t) {
		t.setEnd(new Date());
		t.setState(Constants.Estado.RECHAZADA.getValue());
		
		transferDataService.saveTransfer(t);

		log.info("La cantidad {} supera el límite establecido. Transacción rechazada", t.getAmount());
	}
}
