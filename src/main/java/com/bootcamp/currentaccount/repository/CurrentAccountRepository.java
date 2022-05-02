package com.bootcamp.currentaccount.repository;

import com.bootcamp.currentaccount.model.CurrentAccount;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrentAccountRepository extends ReactiveCrudRepository<CurrentAccount, String> {
    Mono<CurrentAccount> findByAccountNumber(String num);
    Flux<CurrentAccount> findByClientId(String clientId);

}
