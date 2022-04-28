package com.bootcamp.currentaccount.controller;

import com.bootcamp.currentaccount.model.CurrentAccount;
import com.bootcamp.currentaccount.model.CurrentAccountMovement;
import com.bootcamp.currentaccount.service.CurrentAccountMovementService;
import com.bootcamp.currentaccount.service.CurrentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/currentaccount")
@RequiredArgsConstructor
public class CurrentAccountController {

    public final CurrentAccountService service;
    public final CurrentAccountMovementService currentAccountMovementService;
    @GetMapping
    public Flux<CurrentAccount> getAll(){
        return service.findAll();
    }

    @GetMapping("/accountNumber/{num}")
    public Mono<CurrentAccount> findByAccountNumber(@PathVariable("num") String num){
        return service.findByAccountNumber(num);
    }

    /**
     * Obtiene una lista de las cuentas Corrientes que posea el Cliente segun su Documento
     * @param clientRuc Documento del Cliente (RUC)
     * @return Lista con las cuentas pertenecientes al Documento
     */
    @GetMapping("/findAcountsByClientRuc/{clientRuc}")
    public Flux<Integer> findAcountsByClientId(@PathVariable("clientRuc") String clientRuc) {
        var accounts = service.findByClientRuc(clientRuc);
        var lst = accounts.map(acc -> {
            return acc.getAccountNumber();
        });
        return lst;
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
        return service.findByClientRuc(ruc);
    }


    @GetMapping("/movement")
    public Flux<CurrentAccountMovement> getAllMovements(){
        return currentAccountMovementService.findAll();
    }

    @GetMapping("movement/find/{num}")
    public Flux<CurrentAccountMovement> getByAccountNumber(@PathVariable("num") Integer num){
        return currentAccountMovementService.findByAccountNumber(num);
    }

    @GetMapping("/currentAccountBalance/{numberAccount}")
    public String getAccountBalance(@PathVariable("numberAccount") String numberAccount){
        double balance=0;
//        RestTemplate restTemplate=new RestTemplate();
//        String urlDp = passPrdUrl +"/currentAccountMovement/find/" + currentAccount;
//        ResponseEntity<CurrentAccountMovement[]> currentAccountMovements = restTemplate.getForEntity(urlDp, CurrentAccountMovement[].class);
//        for(CurrentAccountMovement am: currentAccountMovements.getBody()){
//            if (am.getMovementType().equals("D")){
//                balance += am.getAmount();
//            }else if (am.getMovementType().equals("R")){
//                balance -= am.getAmount();
//            }
//        }
        return String.valueOf(balance);
    }

    @PostMapping("/movement")
    public Mono<CurrentAccountMovement> create(@RequestBody CurrentAccountMovement currentAccountMovement){
        return currentAccountMovementService.create(currentAccountMovement);
    }

    @PostMapping("/movement/update")
    public Mono<CurrentAccountMovement> update(@RequestBody CurrentAccountMovement currentAccountMovement){
        return currentAccountMovementService.create(currentAccountMovement);
    }

    @DeleteMapping("movement")
    public Mono<CurrentAccountMovement> delete(@RequestBody CurrentAccountMovement currentAccountMovement){
        return currentAccountMovementService.delete(currentAccountMovement);
    }

    @DeleteMapping("movement/byId/{id}")
    public Mono<CurrentAccountMovement> deleteMovementById(@PathVariable("id") String id){
        return currentAccountMovementService.deleteById(id);
    }

}