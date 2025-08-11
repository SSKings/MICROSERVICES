package com.sskings.card_ms.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sskings.card_ms.model.Card;

@Repository
public interface CardRepository extends JpaRepository<Card, Long>{
    List<Card> findByCreditLimitLessThanEqual(BigDecimal creditLimit);
}
