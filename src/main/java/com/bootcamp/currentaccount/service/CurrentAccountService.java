package com.bootcamp.currentaccount.service;


import com.bootcamp.currentaccount.model.CurrentAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrentAccountService {
    Flux<CurrentAccount> findAll();
    Mono<CurrentAccount> create(CurrentAccount currentAccount);
    Mono<CurrentAccount> findByAccountNumber(String num);
    Flux<CurrentAccount> findByClientRuc(String ruc);

    Mono<CurrentAccount> update(CurrentAccount currentAccount);
    Mono<CurrentAccount> deleteById(String id);
    Mono delete(CurrentAccount currentAccount);
}
