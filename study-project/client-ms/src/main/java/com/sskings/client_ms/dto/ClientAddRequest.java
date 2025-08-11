package com.sskings.client_ms.dto;
import com.sskings.client_ms.model.Client;

import lombok.Data;

@Data
public class ClientAddRequest {
    
    private String name;
    private String email;
    
    public ClientAddRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    public Client toModel() {
        return new Client(name, email);
    }
}