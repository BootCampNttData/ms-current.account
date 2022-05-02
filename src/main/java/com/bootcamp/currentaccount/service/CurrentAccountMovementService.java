package com.bootcamp.currentaccount.service;

import com.bootcamp.currentaccount.model.CurrentAccountMovement;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrentAccountMovementService {
    Flux<CurrentAccountMovement> findAll();
    Mono<CurrentAccountMovement> create(com.bootcamp.currentaccount.model.CurrentAccountMovement currentAccountMovement);
    Flux<CurrentAccountMovement> findByAccountNumber(String num);
    Mono<CurrentAccountMovement> update(com.bootcamp.currentaccount.model.CurrentAccountMovement currentAccountMovement);
    Mono<CurrentAccountMovement> deleteById(String id);
    Mono delete(CurrentAccountMovement currentAccountMovement);
    Mono<Double> getBalanceByAccount(String num);
}
