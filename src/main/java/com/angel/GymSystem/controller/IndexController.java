package com.angel.GymSystem.controller;

import com.angel.GymSystem.model.Client;
import com.angel.GymSystem.service.IClientService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import org.slf4j.Logger;

@Component("indexController")
@Data
@ViewScoped
public class IndexController {

    @Autowired
    IClientService clientService;
    private List<Client> clients;
    private static final Logger logger =
            LoggerFactory.getLogger(IndexController.class);

    @PostConstruct
    public void init(){
        loadData();
    }

    public void loadData(){
        this.clients = this.clientService.listClients();
        this.clients.forEach(client -> logger.info(client.toString()));
    }

}
