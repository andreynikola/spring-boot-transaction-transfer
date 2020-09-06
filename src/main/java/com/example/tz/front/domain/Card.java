package com.example.tz.front.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Andrey Nikola
 * @version 1.0
 */
@Data
@Entity
@Table(name = "card")
public class Card {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "bin")
    private Integer bin;

    @Column(name = "cardholder", columnDefinition = "varchar(255)")
    private String cardholder;

}
