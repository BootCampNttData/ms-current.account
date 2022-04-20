package com.bootcamp.currentaccount.controller;

import com.bootcamp.currentaccount.model.CurrentAccount;
import com.bootcamp.currentaccount.service.CurrentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/currentAccount")
@RequiredArgsConstructor
public class CurrentAccountController {
    @Value("${passiveproducts.server.url}")
    private String passPrdUrl;

    public final CurrentAccountService service;
    @GetMapping
    public Flux<CurrentAccount> getAll(){
        return service.findAll();
    }

    @GetMapping("/currentAccountNumber/{num}")
    public Mono<CurrentAccount> findByAccountNumber(@PathVariable("num") String num){
        return service.findByAccountNumber(num);
    }

    /**
     * Crea una nueva cuenta Ahorro o Corriente dependiendo del pararameto ingresado en CurrentAccountype [C|A]
     * Tambien valida si el cliente es una Empresa [E] o persona natural [P]
     * En caso que no se cumplan las condiciones retornara un objeto vacio y no se almacenara en la BD.
     * @param currentAccount
     * @return
     */
    @PostMapping
    public Mono<CurrentAccount> create(@RequestBody CurrentAccount currentAccount){

        RestTemplate restTemplate=new RestTemplate();
        Mono<CurrentAccount> mono=Mono.just(new CurrentAccount());

        boolean haveCurrentAcc=false;

//            String url = passPrdUrl +"/currentAccount/findByClientId/" + currentAccount.getClientId();
//            ResponseEntity<CurrentAccount[]> currentAccounts = restTemplate.getForEntity(url,CurrentAccount[].class);

        return service.create(currentAccount);
    }

    @PostMapping("/update")
    public Mono<CurrentAccount> update(@RequestBody CurrentAccount currentAccount){
        return service.create(currentAccount);
    }

    @DeleteMapping
    public Mono<CurrentAccount> delete(@RequestBody CurrentAccount currentAccount){
        return service.delete(currentAccount);
    }

    @DeleteMapping("/byId/{id}")
    public Mono<CurrentAccount> deleteById(@RequestBody String id){
        return service.deleteById(id);
    }

    /** *****************************************/
    @GetMapping("/findByClientRuc/{ruc}")
    public Flux<CurrentAccount> findByClientRuc(@PathVariable("ruc") String ruc){
        return service.findByRucNumber(ruc);
    }



}