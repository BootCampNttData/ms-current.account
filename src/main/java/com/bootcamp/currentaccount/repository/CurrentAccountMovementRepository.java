package com.bootcamp.currentaccount.repository;

import com.bootcamp.currentaccount.model.CurrentAccountMovement;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrentAccountMovementRepository extends ReactiveCrudRepository<com.bootcamp.currentaccount.model.CurrentAccountMovement, String> {
    Flux<CurrentAccountMovement> findByAccountNumber(String num);
}