package com.sskings.client_ms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sskings.client_ms.model.Client;


public interface  ClientRepository extends JpaRepository<Client, Long> { 

    Optional<Client> findByEmail(String email);
}
