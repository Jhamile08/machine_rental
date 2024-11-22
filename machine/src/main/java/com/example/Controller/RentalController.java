package com.example.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.example.Entity.Client;
import com.example.Entity.Machine;
import com.example.Entity.Rental;
import com.example.Model.ClientModel;
import com.example.Model.MachineModel;
import com.example.Model.RentalModel;
import com.example.Utils.Utils;

public class RentalController {
    private static int currentPage = 1;
    private static final int pageSize = 5;

    public static void create() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        ClientModel objClientModel = new ClientModel();
        MachineModel objMachineModel = new MachineModel();
        // pedir datos por consola
        Object[] option = Utils.listToArray(objClientModel.getAll());
        Client clientSelected = (Client) JOptionPane.showInputDialog(null, "Select the client: ",
                "",
                JOptionPane.QUESTION_MESSAGE, null,
                option, option[0]);
        if (clientSelected == null) {
            JOptionPane.showMessageDialog(null, "Client not found");
        }
        Object[] option2 = Utils.listToArray(objMachineModel.getAllAvailable());
        Machine machineSelected = (Machine) JOptionPane.showInputDialog(null, "Select the machine: ",
                "",
                JOptionPane.QUESTION_MESSAGE, null,
                option2, option2[0]);

        if (machineSelected == null) {
            JOptionPane.showMessageDialog(null, "Machine not found");
        }

        String lease_start_date = JOptionPane.showInputDialog("Insert the start time(dd/MM/yyyy): ");
        String lease_end_date = JOptionPane.showInputDialog("Insert the end time(dd/MM/yyyy): ");

        try {
            // Convertir las cadenas a objetos Date
            Date leaseStartDate = dateFormat.parse(lease_start_date);
            Date leaseEndDate = dateFormat.parse(lease_end_date);

            Rental rental = new Rental(clientSelected.getClient_id(), leaseStartDate, leaseEndDate,
                    machineSelected.getMachine_id(), true);
            Rental instanceRental = (Rental) instanceModel().create(rental);
            JOptionPane.showMessageDialog(null, instanceRental.toString());
            JOptionPane.showMessageDialog(null, "Rental created successfully!");

        } catch (ParseException e) {
            // Si el formato no es válido, muestra un mensaje de error
            JOptionPane.showMessageDialog(null, "Invalid date format. Please use dd/MM/yyyy.");
        }

    }

    public static void getAllByStatusTrue() {
        List<Object> rental = instanceModel().getAllByStatusTrue(currentPage, pageSize);

        // Mostrar las maquinas
        StringBuilder rentalInfo = new StringBuilder();
        for (Object obj : rental) {
            Rental objRental = (Rental) obj;
            rentalInfo.append(objRental.toString()).append("\n");
        }

        // Si no hay maquinas, mostramos un mensaje
        if (rentalInfo.length() == 0) {
            JOptionPane.showMessageDialog(null, "There are not more rental for show.");
            return;
        }

        // Mostrar los maquinas de la página actual
        int option = JOptionPane.showOptionDialog(null,
                "Rental (page " + currentPage + ")\n\n" + rentalInfo.toString(),
                "Rental Paginados",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new String[] { "next", "before", "exit" }, "next");

        // Gestionar las opciones
        if (option == 0) { // "Siguiente"
            currentPage++;
            getAllByStatusTrue();
        } else if (option == 1) { // "Anterior"
            if (currentPage > 1) {
                currentPage--;
                getAllByStatusTrue();
            } else {
                JOptionPane.showMessageDialog(null, "Your are in the first page.");
            }
        } else if (option == 2) { // salir
            currentPage = 1;
            JOptionPane.showMessageDialog(null, "exit...");
            return;
        }
    }

    public static void getAllByStatusFalse() {
        List<Object> rental = instanceModel().getAllByStatusFalse(currentPage, pageSize);

        // Mostrar las maquinas
        StringBuilder rentalInfo = new StringBuilder();
        for (Object obj : rental) {
            Rental objRental = (Rental) obj;
            rentalInfo.append(objRental.toString()).append("\n");
        }

        // Si no hay maquinas, mostramos un mensaje
        if (rentalInfo.length() == 0) {
            JOptionPane.showMessageDialog(null, "There are not more rental for show.");
            return;
        }

        // Mostrar los maquinas de la página actual
        int option = JOptionPane.showOptionDialog(null,
                "Rental (page " + currentPage + ")\n\n" + rentalInfo.toString(),
                "Rental Paginados",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, new String[] { "next", "before", "exit" }, "next");

        // Gestionar las opciones
        if (option == 0) { // "Siguiente"
            currentPage++;
            getAllByStatusTrue();
        } else if (option == 1) { // "Anterior"
            if (currentPage > 1) {
                currentPage--;
                getAllByStatusTrue();
            } else {
                JOptionPane.showMessageDialog(null, "Your are in the first page.");
            }
        } else if (option == 2) { // salir
            currentPage = 1;
            JOptionPane.showMessageDialog(null, "exit...");
            return;
        }
    }

    public static void changeStatus() {
        Object[] option = Utils.listToArray(instanceModel().getAll());
        Rental rentalSelected = (Rental) JOptionPane.showInputDialog(null,
                "Select the rental that you want to change the status: ",
                "",
                JOptionPane.QUESTION_MESSAGE, null,
                option, option[0]);
        if (rentalSelected == null) {
            JOptionPane.showMessageDialog(null, "Rental not found");
        }
        instanceModel().changeStatus(rentalSelected);
        JOptionPane.showMessageDialog(null, "changed correctly");
    }

    public static RentalModel instanceModel() {
        return new RentalModel();
    }
}
