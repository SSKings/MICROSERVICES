package com.sskings.card_ms.model;

import java.math.BigDecimal;

import com.sskings.card_ms.model.enums.CardBrandEnum;
import com.sskings.card_ms.model.enums.CardTypeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cards")
@Data
@NoArgsConstructor
public class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CardBrandEnum brand;

    @Enumerated(EnumType.STRING)
    private CardTypeEnum type;

    private BigDecimal creditLimit;

    private BigDecimal availableCredit;

    public Card(String name, CardBrandEnum brand, CardTypeEnum type, BigDecimal creditLimit) {
        this.name = name;
        this.brand = brand;
        this.type = type;
        this.creditLimit = creditLimit;
        this.availableCredit = creditLimit; // Assuming available credit starts as the full credit limit
    }

}
