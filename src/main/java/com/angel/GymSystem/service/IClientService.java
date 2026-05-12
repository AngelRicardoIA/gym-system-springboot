package com.angel.GymSystem.service;

import com.angel.GymSystem.model.Client;
import java.util.List;

public interface IClientService {
    public List<Client> listClients();

    public Client searchClientByID(Integer idClient);

    public void saveClient(Client client);

    public void deleteClient(Client client);

}
