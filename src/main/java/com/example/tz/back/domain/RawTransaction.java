package com.example.tz.back.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Andrey Nikola
 * @version 1.0
 */
@Data
@Entity
@Table(name = "raw_transaction")
public class RawTransaction {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Column(name="card_id")
    private Long cardId;

    @Column(name = "bin")
    private Integer bin;

    @Column(name = "cardholder_name", columnDefinition = "varchar(255)")
    private String cardholderName;

}
