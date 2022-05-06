package com.bootcamp.currentaccount.controller;

import com.bootcamp.currentaccount.exception.CurrentAccountValidationException;
import com.bootcamp.currentaccount.model.CurrentAccount;
import com.bootcamp.currentaccount.model.CurrentAccountMovement;
import com.bootcamp.currentaccount.service.CurrentAccountMovementService;
import com.bootcamp.currentaccount.service.CurrentAccountService;
import com.bootcamp.currentaccount.util.Constants;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RestController
@RequestMapping("/currentaccount")
@RequiredArgsConstructor
public class CurrentAccountController {
    private final Constants constants;
    public final CurrentAccountService currentAccountService;
    public final CurrentAccountMovementService currentAccountMovementService;
    Logger logger = LoggerFactory.getLogger(CurrentAccountController.class);

    @GetMapping
    public Flux<CurrentAccount> getAll(){
        return currentAccountService.findAll();
    }

    @GetMapping("/accountNumber/{num}")
    public Mono<CurrentAccount> findByAccountNumber(@PathVariable("num") String num){
        return currentAccountService.findByAccountNumber(num);
    }

    /**
     * Obtiene una lista de las cuentas Corrientes que posea el Cliente segun su Documento
     * @param clientRuc Documento del Cliente (RUC)
     * @return Lista con las cuentas pertenecientes al Documento
     */
    @GetMapping("/findAcountsByClientRuc/{clientRuc}")
    public Flux<String> findAcountsByClientId(@PathVariable("clientRuc") String clientRuc) {
        var accounts = currentAccountService.findByClientRuc(clientRuc);
        var lst = accounts.map(acc -> {
            return acc.getAccountNumber();
        });
        return lst;
    }

    /**
     * Crea una nueva cuenta Corriente
     * Tambien valida si la cuenta ya existe en la BD
     * En caso que no se cumplan las condiciones retornara una excepcion con los mensajes correspondientes.
     * @param currentAccount
     * @return
     */
    @PostMapping
    public Mono<CurrentAccount> create(@RequestBody CurrentAccount currentAccount){

        return Mono.just(currentAccount)
                .then(check(currentAccount, cur -> Optional.of(cur).isEmpty(), "Saving Account has not data"))
                .then(check(currentAccount, cur -> ObjectUtils.isEmpty(cur.getClientId()), "Client Id is required"))
                .then(check(currentAccount, cur -> ObjectUtils.isEmpty(cur.getAccountNumber()), "Account Number required"))
                .then((isPyme(currentAccount.getClientId())
                        .map(m -> m.booleanValue()?findCreditCardByClient(currentAccount.getClientId()):(new CurrentAccountValidationException("")))
                        .<String>handle((record, sink) -> sink.error(new CurrentAccountValidationException("The Client does no have a Credit Card")))))
                .then(currentAccountService.findByAccountNumber(currentAccount.getAccountNumber())
                        .<CurrentAccount>handle((record, sink) -> sink.error(new CurrentAccountValidationException("The account already exists")))
                        .switchIfEmpty(currentAccountService.create(currentAccount)))
                ;
    }

    @PostMapping("/update")
    public Mono<CurrentAccount> update(@RequestBody CurrentAccount currentAccount){
        return currentAccountService.create(currentAccount);
    }

    @DeleteMapping
    public Mono<CurrentAccount> delete(@RequestBody CurrentAccount currentAccount){
        return currentAccountService.delete(currentAccount);
    }

    @DeleteMapping("/byId/{id}")
    public Mono<CurrentAccount> deleteById(@PathVariable String id){
        return currentAccountService.deleteById(id);
    }

    /** *****************************************/
    @GetMapping("/findByClientRuc/{ruc}")
    public Flux<CurrentAccount> findByClientRuc(@PathVariable("ruc") String ruc){
        return currentAccountService.findByClientRuc(ruc);
    }


    @GetMapping("/movement")
    public Flux<CurrentAccountMovement> getAllMovements(){
        return currentAccountMovementService.findAll();
    }

    @GetMapping("movement/find/{num}")
    public Flux<CurrentAccountMovement> getByAccountNumber(@PathVariable("num") String num){
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

    private <T> Mono<Void> check(T account, Predicate<T> predicate, String messageForException) {
        return Mono.create(sink -> {
            if (predicate.test(account)) {
                sink.error(new Exception(messageForException));
                return;
            } else {
                sink.success();
            }
        });
    }

    public Mono<String> findCreditCardByClient(String clientId){
        WebClient webClient = WebClient.create(constants.gwServer);
        logger.info("Saving Accounts");
        List<Integer> savAccLst=new ArrayList<>();

        return   webClient.get()
                .uri("/creditcard/findCreditCardByClientId/{id}",clientId)
                .retrieve()
                .bodyToMono(String.class);

    }
    public Mono<Boolean> isPyme(String clientId){
        WebClient webClient = WebClient.create(constants.gwServer);
        return   webClient.get()
                .uri("/businessclient/isPyme/{clientId}",clientId)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

    public Mono<Boolean> isVip(String clientId){
        WebClient webClient = WebClient.create(constants.gwServer);
        return   webClient.get()
                .uri("/retailclient/isVip/{clientId}",clientId)
                .retrieve()
                .bodyToMono(Boolean.class);
    }

}