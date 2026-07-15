package com.angel.GymSystem.controller;

import com.angel.GymSystem.model.Client;
import com.angel.GymSystem.service.IClientService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.primefaces.PrimeFaces;
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
    private Client selectClient;
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

    public void addClient(){
        this.selectClient = new Client();
    }

    public void saveClient(){
        logger.info("Client: " + this.selectClient);
        //Add
        if(this.selectClient.getId() == null){
            this.clientService.saveClient(this.selectClient);
            this.clients.add(this.selectClient);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Client Added"));
        }
        //Modify
        else {
            this.clientService.saveClient(this.selectClient);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Client Updated"));
        }
        //Hide window
        PrimeFaces.current().executeScript("PF('windowModalClient').hide()");
        //Update table
        PrimeFaces.current().ajax().update("clients-form:messages",
                "client-form:clients-table");
        //Reset
        this.selectClient = null;
    }
}
