package com.bootcamp.currentaccount.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrentAccountMovementService {
    Flux<com.bootcamp.currentaccount.model.CurrentAccountMovement> findAll();
    Mono<com.bootcamp.currentaccount.model.CurrentAccountMovement> create(com.bootcamp.currentaccount.model.CurrentAccountMovement currentAccountMovement);
    Flux<com.bootcamp.currentaccount.model.CurrentAccountMovement> findByAccountNumber(String num);
    Mono<com.bootcamp.currentaccount.model.CurrentAccountMovement> update(com.bootcamp.currentaccount.model.CurrentAccountMovement currentAccountMovement);
    Mono<com.bootcamp.currentaccount.model.CurrentAccountMovement> deleteById(String id);
    Mono delete(com.bootcamp.currentaccount.model.CurrentAccountMovement currentAccountMovement);
}
