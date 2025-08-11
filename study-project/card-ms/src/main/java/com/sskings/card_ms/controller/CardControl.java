package com.sskings.card_ms.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sskings.card_ms.dto.CardAddRequest;
import com.sskings.card_ms.dto.CardByClientResponse;
import com.sskings.card_ms.model.Card;
import com.sskings.card_ms.service.CardService;
import com.sskings.card_ms.service.ClientCardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardControl {
    
    private final CardService cardService;
    private final ClientCardService clientCardService;

    @GetMapping
    public ResponseEntity<List<Card>> getHello() {
        return ResponseEntity.ok(cardService.getAllCards());
    }

    @GetMapping(params = "creditLimit")
    public ResponseEntity<List<Card>> getCardsWithLowerEqualCreditLimit(@RequestParam("creditLimit") Long creditLimit) {
        List<Card> cards = cardService.getCardsWithLowerEqualCeditLimLit(creditLimit);
        return ResponseEntity.ok(cards);
    }

    @PostMapping("/add")
    public ResponseEntity addCard(@RequestBody CardAddRequest card){
        log.info("Adding new card: {}", card.getName());
        Card newCard = card.toModel();
        cardService.addCard(newCard);
        log.info("Card added successfully: {}", newCard.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(params = "email")
    public ResponseEntity<List<CardByClientResponse>> getCardsByEmailClients(@RequestParam("email") String email) {
        List<CardByClientResponse> clientCards = clientCardService.getClientCardsByEmail(email)
        .stream()
        .map(CardByClientResponse::fromModel)
        .collect(Collectors.toList());
            
        return ResponseEntity.ok(clientCards);
    }
    
    
}
