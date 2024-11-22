package com.example.Controller;

import java.util.List;

import javax.swing.JOptionPane;

import com.example.Entity.Client;
import com.example.Model.ClientModel;

public class ClientController {
    private static int currentPage = 1;
    private static final int pageSize = 5;

    public static void create() {
        // pedir datos por consola
        String full_name = JOptionPane.showInputDialog("Insert the full name: ");
        String email = JOptionPane.showInputDialog("Insert the email: ");
        String phone = JOptionPane.showInputDialog("Insert the phone: ");
        String address = JOptionPane.showInputDialog("Insert the address:  ");

        // instanciar el cliente
        Client newClient = new Client(full_name, email, phone, address);

        // crear el cliente
        Client instanceClient = (Client) instanceModel().create(newClient);
        JOptionPane.showMessageDialog(null, instanceClient.toString());

    }

    public static void getAll() {
        List<Object> clients = instanceModel().getAll(currentPage, pageSize);

        // Mostrar los clientes
        StringBuilder clientInfo = new StringBuilder();
        for (Object obj : clients) {
            Client client = (Client) obj;
            clientInfo.append(client.toString()).append("\n");
        }

        // Si no hay clientes, mostramos un mensaje
        if (clientInfo.length() == 0) {
            JOptionPane.showMessageDialog(null, "There are not more clients for show.");
            return;
        }

        // Mostrar los clientes de la página actual
        int option = JOptionPane.showOptionDialog(null,
                "Clients (Page " + currentPage + ")\n\n" + clientInfo.toString(),
                "Clients Paginados",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new String[] { "next", "before", "exit" }, "next");

        // Gestionar las opciones
        if (option == 0) { // "Siguiente"
            currentPage++;
            getAll();
        } else if (option == 1) { // "Anterior"
            if (currentPage > 1) {
                currentPage--;
                getAll();
            } else {
                JOptionPane.showMessageDialog(null, "Your are in the first page.");
            }
        } else if (option == 2) { // salir
            currentPage = 1;
            JOptionPane.showMessageDialog(null, "exit...");
            return;
        }
    }

    public static ClientModel instanceModel() {
        return new ClientModel();
    }
}
