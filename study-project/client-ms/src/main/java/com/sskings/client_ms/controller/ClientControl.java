package com.sskings.client_ms.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.sskings.client_ms.dto.ClientAddRequest;
import com.sskings.client_ms.dto.ClientInfoResponse;
import com.sskings.client_ms.model.Client;
import com.sskings.client_ms.service.ClientService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientControl {

    private final ClientService clientService;
    
    @GetMapping
    public ResponseEntity<List<ClientInfoResponse>> getClients() {
        log.info("Getting all clients");
        List<ClientInfoResponse> clients = clientService.getAllClients()
                .stream()
                .map(client -> new ClientInfoResponse(client.getName(), client.getEmail()))
                .toList();
                
        return ResponseEntity.ok(clients);
    }

    @GetMapping(params = "email")
    public ResponseEntity<Client> getClient(@RequestParam("email") String email) {
        log.info("Getting client by email: {}", email);
        Optional<Client> clientOpt = clientService.getClientByEmail(email);
        if (clientOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Client client = clientOpt.get();
        log.info("Client found: {}", client);
        return ResponseEntity.ok(client);
    }
    
    @PostMapping("/add")
    public ResponseEntity addClient(@RequestBody ClientAddRequest client) {

        log.info("Adding new client: {}", client.getName());

        Client newClient = clientService.addClient(client.toModel());
        log.info("Client added successfully: {}", newClient.getId());
        
        URI headerLocation = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .query("email={email}")
                .buildAndExpand(newClient.getEmail())
                .toUri();

        return ResponseEntity.created(headerLocation).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long id) {
        log.info("Deleting client with id: {}", id);
        clientService.deleteClient(id);
        log.info("Client with id: {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
