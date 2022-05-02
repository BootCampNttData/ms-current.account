package com.bootcamp.currentaccount.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document

public class CurrentAccountMovement {
    @Id
    private String  id;
    private String  accountNumber;
    private String  movementType;
    private Date    movementDate;
    private Double  amount;
}
