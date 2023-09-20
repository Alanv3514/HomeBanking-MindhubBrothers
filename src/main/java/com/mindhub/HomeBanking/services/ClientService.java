package com.mindhub.HomeBanking.services;

import com.mindhub.HomeBanking.dtos.ClientDto;
import com.mindhub.HomeBanking.models.entities.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {
    List<ClientDto> getClientsDTO();

    void saveClient(Client client);

    ClientDto getClientsDTOById(Long id);

    ClientDto getCurrentClient(Authentication authentication);
}
