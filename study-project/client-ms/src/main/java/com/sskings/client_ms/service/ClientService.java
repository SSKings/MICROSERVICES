package com.sskings.client_ms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sskings.client_ms.model.Client;
import com.sskings.client_ms.repository.ClientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }
    
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    
    @Transactional
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
