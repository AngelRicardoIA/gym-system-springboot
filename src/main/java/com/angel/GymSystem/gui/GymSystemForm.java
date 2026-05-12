package com.angel.GymSystem.gui;

import com.angel.GymSystem.model.Client;
import com.angel.GymSystem.service.ClientService;
import com.angel.GymSystem.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class GymSystemForm extends JFrame {
    private JPanel mainPanel;
    private JTable clientsTable;
    private JTextField nameText;
    private JTextField lastNameText;
    private JTextField membershipText;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton clearButton;
    IClientService clientService;
    private DefaultTableModel clientsTableModel;
    private Integer idClient;

    @Autowired
    public GymSystemForm(ClientService clientService){
        this.clientService = clientService;
        initForm();

        saveButton.addActionListener(e -> saveClient());
        clientsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                loadSelectClient();
            }
        });
        deleteButton.addActionListener(e -> deleteClient());
        clearButton.addActionListener(e -> cleanForm());
    }

    private void initForm(){
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        //this.clientsTableModel = new DefaultTableModel(0, 4);
        this.clientsTableModel = new DefaultTableModel(0, 4){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        String[] headers = {"id", "Name", "Last Name", "Membership"};
        this.clientsTableModel.setColumnIdentifiers(headers);
        this.clientsTable = new JTable(clientsTableModel);

        this.clientsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listClients();
    }

    private void listClients(){
        this.clientsTableModel.setRowCount(0);
        var clients = this.clientService.listClients();
        clients.forEach(client -> {
            Object[] clientRow = {
                    client.getId(),
                    client.getNombre(),
                    client.getApellido(),
                    client.getMembresia()
            };
            this.clientsTableModel.addRow(clientRow);
        });
    }

    private void saveClient(){
        if (nameText.getText().equals("")){
            showMessage("Please, enter a name");
            nameText.requestFocusInWindow();
            return;
        }
        if (membershipText.getText().equals("")){
            showMessage("Please, enter a membership");
            membershipText  .requestFocusInWindow();
            return;
        }

        var name = nameText.getText();
        var lastName = lastNameText.getText();
        var membership = Integer.parseInt(membershipText.getText());
        var client = new Client(this.idClient, name, lastName, membership);
        this.clientService.saveClient(client);

        if (this.idClient == null){
            showMessage("Client added successfully");
        }
        else {
            showMessage("Client updated successfully");
        }

        cleanForm();
        listClients();
    }

    private void loadSelectClient(){
        var row = clientsTable.getSelectedRow();
        if(row != -1){
            var id = clientsTable.getModel().getValueAt(row, 0).toString();
            this.idClient = Integer.parseInt(id);
            var name = clientsTable.getModel().getValueAt(row, 1).toString();
            this.nameText.setText(name);
            var lastName = clientsTable.getModel().getValueAt(row, 2).toString();
            this.lastNameText.setText(lastName);
            var membership = clientsTable.getModel().getValueAt(row, 3).toString();
            this.membershipText.setText(membership);
        }
    }

    private void deleteClient(){
        var row = clientsTable.getSelectedRow();
        if(row != -1){
            var idClientStr = clientsTable.getModel().getValueAt(row, 0).toString();
            this.idClient = Integer.parseInt(idClientStr);
            var client = new Client();
            client.setId(this.idClient);

            clientService.deleteClient(client);
            showMessage("Client with ID " + this.idClient + " was deleted successfully");
            cleanForm();
            listClients();
        }
        else {
            showMessage("Please select a client to delete");
        }
    }

    private void cleanForm(){
        nameText.setText("");
        lastNameText.setText("");
        membershipText.setText("");

        this.idClient = null;

        this.clientsTable.getSelectionModel().clearSelection();
    }

    private void showMessage(String message){
        JOptionPane.showMessageDialog(this, message);
    }
}

