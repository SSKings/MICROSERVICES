package com.sskings.card_ms.dto;

import java.math.BigDecimal;

import com.sskings.card_ms.model.ClientCard;
import com.sskings.card_ms.model.enums.CardBrandEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardByClientResponse {
    
    private String clientEmail;
    private String cardName;
    private CardBrandEnum cardBrand;
    private BigDecimal creditLimit;

    public static CardByClientResponse fromModel(ClientCard clientCard) {   
        return new CardByClientResponse(clientCard.getEmail(), 
                                clientCard.getCard().getName(), 
                                clientCard.getCard().getBrand(), 
                                clientCard.getCreditLimit());

    }
}
