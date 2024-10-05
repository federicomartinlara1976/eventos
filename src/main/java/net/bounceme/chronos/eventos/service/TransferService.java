package net.bounceme.chronos.eventos.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bounceme.chronos.eventos.dto.TransferDTO;
import net.bounceme.chronos.eventos.model.Transfer;
import net.bounceme.chronos.eventos.repository.TransferRepository;
import net.bounceme.chronos.eventos.utils.Constants;

@Service
@Slf4j
public class TransferService {
	
	private static final Integer SLEEP = 1000;
	
	@Value("${amountAlertLimit}")
	private Integer amountAlertLimit;
	
	@Autowired
	private TransferRepository transferRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	@SneakyThrows
	public TransferDTO initializeTransfer(TransferDTO transfer) {
		Transfer t = new Transfer();
		t.setAccountIdFrom(transfer.getAccountIdFrom());
		t.setAccountIdTo(transfer.getAccountIdTo());
		t.setAmount(transfer.getAmount());
		t.setState(Constants.Estado.INICIADA.getValue());
		t.setStart(new Date());
		
		t = transferRepository.save(t);
		return modelMapper.map(t, TransferDTO.class);
	}
	
	@Transactional(readOnly = true)
	public List<TransferDTO> getTransfers() {
		List<Transfer> transfers = transferRepository.findAll();
		return CollectionUtils.isNotEmpty(transfers) ? transfers.stream()
				.map(transfer -> modelMapper.map(transfer, TransferDTO.class)).collect(Collectors.toList())
				: Collections.emptyList();
	}
	
	@Transactional(readOnly = true)
	public TransferDTO getTransfer(Long id) {
		Optional<Transfer> oTransfer = transferRepository.findById(id);
		return oTransfer.isPresent() ? modelMapper.map(oTransfer.get(), TransferDTO.class) : null;
	}

	@EventListener
	@Transactional
	@SneakyThrows
	public void executeTransfer(TransferDTO transfer) {
		Transfer t = transferRepository.getById(transfer.getId());
		t.setState(Constants.Estado.EN_PROGRESO.getValue());
		t = transferRepository.save(t);
		
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
		
		transferRepository.save(t);
	}
}
