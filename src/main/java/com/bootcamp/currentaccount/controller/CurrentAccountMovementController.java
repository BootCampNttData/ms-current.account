package com.bootcamp.currentaccount.controller;

import com.bootcamp.currentaccount.model.CurrentAccountMovement;
import com.bootcamp.currentaccount.service.CurrentAccountMovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/currentAccountMovement")
@RequiredArgsConstructor
public class CurrentAccountMovementController {
    @Value("${passiveproducts.server.url}")
    private String passPrdUrl;
    
    public final CurrentAccountMovementService service;
    @GetMapping
    public Flux<CurrentAccountMovement> getAll(){
        return service.findAll();
    }

    @GetMapping("/find/{num}")
    public Flux<CurrentAccountMovement> getBycurrentAccountNumber(@PathVariable("num") String num){
        return service.findByAccountNumber(num);
    }

    @GetMapping("/currentAccountBalance/{currentAccount}")
    public String getAccountBalance(@PathVariable("currentAccount") String currentAccount){
        double balance=0;
        RestTemplate restTemplate=new RestTemplate();
        String urlDp = passPrdUrl +"/currentAccountMovement/find/" + currentAccount;
        ResponseEntity<CurrentAccountMovement[]> currentAccountMovements = restTemplate.getForEntity(urlDp, CurrentAccountMovement[].class);
        for(CurrentAccountMovement am: currentAccountMovements.getBody()){
            if (am.getMovementType().equals("D")){
                balance += am.getAmount();
            }else if (am.getMovementType().equals("R")){
                balance -= am.getAmount();
            }
        }
        return String.valueOf(balance);
    }

    @PostMapping
    public Mono<CurrentAccountMovement> create(@RequestBody CurrentAccountMovement currentAccountMovement){
        return service.create(currentAccountMovement);
    }

    @PostMapping("/update")
    public Mono<CurrentAccountMovement> update(@RequestBody CurrentAccountMovement currentAccountMovement){
        return service.create(currentAccountMovement);
    }

    @DeleteMapping
    public Mono<CurrentAccountMovement> delete(@RequestBody CurrentAccountMovement currentAccountMovement){
        return service.delete(currentAccountMovement);
    }

    @DeleteMapping("/byId/{id}")
    public Mono<CurrentAccountMovement> deleteById(@RequestBody String id){
        return service.deleteById(id);
    }

}