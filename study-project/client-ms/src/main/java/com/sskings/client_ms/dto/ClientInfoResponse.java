package com.sskings.client_ms.dto;

import com.sskings.client_ms.model.Client;

public record ClientInfoResponse(
    String name,
    String email
) {

    public ClientInfoResponse fromModel(Client client) {
        return new ClientInfoResponse(client.getName(), client.getEmail());
    }
}
