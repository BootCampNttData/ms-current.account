package com.bootcamp.currentaccount.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class CurrentAccount {
    @Id
    private String  id;
    private String  clientRuc;
    private Integer accountNumber;
    private Double  feeAmount;
    private Double  minimalAmount;
    private Integer movementLimit;
    private Date    creationDate;
}
