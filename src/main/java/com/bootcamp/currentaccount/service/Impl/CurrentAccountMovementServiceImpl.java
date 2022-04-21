package com.bootcamp.currentaccount.service.Impl;

import com.bootcamp.currentaccount.model.CurrentAccountMovement;
import com.bootcamp.currentaccount.repository.CurrentAccountMovementRepository;
import com.bootcamp.currentaccount.service.CurrentAccountMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor

public class CurrentAccountMovementServiceImpl implements CurrentAccountMovementService {
    public final CurrentAccountMovementRepository repository;

    /**
     * Movimiento Retiro o deposito a cuenta corriente o Ahorro
     * @param currentAccountMovement
     * @return
     */
    @Override
    public Mono<CurrentAccountMovement> create(CurrentAccountMovement currentAccountMovement) {
        return repository.save(currentAccountMovement);
    }

    @Override
    public Mono<CurrentAccountMovement> update(CurrentAccountMovement currentAccountMovement) {
        return repository.save(currentAccountMovement);
    }

    @Override
    public Mono deleteById(String id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono delete(CurrentAccountMovement currentAccountMovement) {
        return repository.delete(currentAccountMovement);
    }

    @Override
    public Flux<CurrentAccountMovement> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<CurrentAccountMovement> findByAccountNumber(Integer mun) {
        return repository.findByAccountNumber(mun);
    }


    @Override
    public Mono<Double> getBalanceByAccount(Integer num) {
        var movements = this.findByAccountNumber(num);
        var balance = movements
                .map(mov -> {
                    return ("D".equals(mov.getMovementType()) ? 1 : -1 ) * mov.getAmount();
                }).reduce(0d, (a, b) -> a + b);
        return balance;
    }


}