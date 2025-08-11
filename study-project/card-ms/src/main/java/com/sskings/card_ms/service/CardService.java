package com.sskings.card_ms.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sskings.card_ms.model.Card;
import com.sskings.card_ms.repository.CardRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardService {
    
    private final CardRepository cardRepository;

    @Transactional
    public Card addCard(Card card){
        return cardRepository.save(card);
    }

    public List<Card> getAllCards(){
        return cardRepository.findAll();
    }

    public List<Card> getCardsWithLowerEqualCeditLimLit(Long value) {
        var creditLimit = BigDecimal.valueOf(value);
        return cardRepository.findByCreditLimitLessThanEqual(creditLimit);
    }

    @Transactional
    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }
}
