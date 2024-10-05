package net.bounceme.chronos.eventos.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
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
public class TransferService {
	
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
	
	@Transactional
	@SneakyThrows
	public TransferDTO saveTransfer(TransferDTO transfer) {
		Transfer t = modelMapper.map(transfer, Transfer.class);
		t = transferRepository.save(t);
		return modelMapper.map(t, TransferDTO.class);
	}
}
