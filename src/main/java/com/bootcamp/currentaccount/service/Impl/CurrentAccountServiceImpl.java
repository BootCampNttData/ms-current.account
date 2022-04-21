package com.bootcamp.currentaccount.service.Impl;


import com.bootcamp.currentaccount.model.CurrentAccount;
import com.bootcamp.currentaccount.repository.CurrentAccountRepository;
import com.bootcamp.currentaccount.service.CurrentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor

public class CurrentAccountServiceImpl implements CurrentAccountService {
    public final CurrentAccountRepository repository;

    @Override
    public Mono<com.bootcamp.currentaccount.model.CurrentAccount> create(CurrentAccount currentAccount) {
        return repository.save(currentAccount);

    }

    @Override
    public Mono<CurrentAccount> update(CurrentAccount currentAccount) {
        return repository.save(currentAccount);
    }

    @Override
    public Mono deleteById(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono delete(CurrentAccount currentAccount) {
        return repository.delete(currentAccount);
    }

    @Override
    public Flux<CurrentAccount> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<CurrentAccount> findByAccountNumber(String num) {
        return repository.findByAccountNumber(num);
    }

    @Override
    public Flux<CurrentAccount> findByClientRuc(String ruc) {
        return repository.findByClientRuc(ruc);
    }
//    @Override
//    public Mono<CurrentAccount> findByClientIdAndCurrentAccountype(String num,String type) {
//
//        return repository.findByClientIdAndCurrentAccountype( num, type);
//    }
}