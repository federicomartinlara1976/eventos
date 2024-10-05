package net.bounceme.chronos.eventos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.bounceme.chronos.eventos.model.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
