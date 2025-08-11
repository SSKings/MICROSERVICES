package com.sskings.card_ms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sskings.card_ms.model.ClientCard;
import com.sskings.card_ms.repository.ClientCardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientCardService {

    private ClientCardRepository clientCardRepository;

    public List<ClientCard> getClientCardsByEmail(String email) {
        return clientCardRepository.findByEmail(email);
    }
}
