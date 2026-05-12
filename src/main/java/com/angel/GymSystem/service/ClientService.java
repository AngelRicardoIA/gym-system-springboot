package com.angel.GymSystem.service;

import com.angel.GymSystem.model.Client;
import com.angel.GymSystem.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService implements IClientService {

    @Autowired
    private ClientRepository clienteRepository;

    @Override
    public List<Client> listClients() {
        List<Client> clients = clienteRepository.findAll();
        return clients;
    }

    @Override
    public Client searchClientByID(Integer idClient) {
        Client client = clienteRepository.findById(idClient).orElse(null);
        return client;
    }

    @Override
    public void saveClient(Client client) {
        clienteRepository.save(client);
    }

    @Override
    public void deleteClient(Client client) {
        clienteRepository.delete(client);
    }
}
