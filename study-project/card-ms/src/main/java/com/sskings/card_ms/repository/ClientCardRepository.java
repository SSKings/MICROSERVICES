package com.sskings.card_ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sskings.card_ms.model.ClientCard;

@Repository
public interface ClientCardRepository extends JpaRepository<ClientCard, Long> {

    List<ClientCard> findByEmail(String email);
}