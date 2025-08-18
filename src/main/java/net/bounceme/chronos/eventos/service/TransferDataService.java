package net.bounceme.chronos.eventos.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TransferDataService {

	@Autowired
	private TransferRepository transferRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Transactional
	@SneakyThrows
	public TransferDTO initializeTransfer(TransferDTO transfer) {
		Transfer t = Transfer.builder()
			.accountIdFrom(transfer.getAccountIdFrom())
			.accountIdTo(transfer.getAccountIdTo())
			.amount(transfer.getAmount())
			.state(Constants.Estado.INICIADA.getValue())
			.start(new Date())
			.build();

		t = transferRepository.save(t);
		return modelMapper.map(t, TransferDTO.class);
	}

	@Transactional(readOnly = true)
	public List<TransferDTO> getTransfers() {
		return transferRepository.findAll()
				.stream()
				.map(transfer -> modelMapper.map(transfer, TransferDTO.class))
				.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public Optional<TransferDTO> getTransfer(Long id) {
		return transferRepository.findById(id)
				.map( tr-> modelMapper.map(tr, TransferDTO.class));
	}

	@Transactional
	@SneakyThrows
	public TransferDTO saveTransfer(TransferDTO transfer) {
		Transfer t = modelMapper.map(transfer, Transfer.class);
		t = transferRepository.save(t);
		return modelMapper.map(t, TransferDTO.class);
	}
}
