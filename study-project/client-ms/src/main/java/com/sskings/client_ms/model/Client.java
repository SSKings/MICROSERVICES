package com.sskings.client_ms.model;

import com.sskings.client_ms.dto.ClientAddRequest;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="clients")
@Data
@NoArgsConstructor
public class Client {   

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }
    
    public ClientAddRequest toClientAddRequest() {
        return new ClientAddRequest(name, email);
    }
}
