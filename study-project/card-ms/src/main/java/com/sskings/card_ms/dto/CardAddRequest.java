package com.sskings.card_ms.dto;

import java.math.BigDecimal;

import com.sskings.card_ms.model.Card;
import com.sskings.card_ms.model.enums.CardBrandEnum;
import com.sskings.card_ms.model.enums.CardTypeEnum;

import lombok.Data;

@Data
public class CardAddRequest {

    private String name;

    private CardBrandEnum brand;

    private CardTypeEnum type;

    private BigDecimal creditLimit;

    public Card toModel(){
        return new Card(name, brand, type, creditLimit);
    }
    
}
