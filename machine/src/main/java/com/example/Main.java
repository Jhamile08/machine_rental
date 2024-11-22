package com.example;

import javax.swing.JOptionPane;

import com.example.Controller.ClientController;
import com.example.Controller.MachineController;
import com.example.Controller.RentalController;
import com.example.Database.ConfigDB;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        ConfigDB.openConnection();
        String option = "";

        do {
            option = JOptionPane.showInputDialog(
                    """
                            Welcome to the rental machines

                            1.manage clients
                            2.manage machines
                            3.manage rentals
                            4.exit
                                 """);
            switch (option) {
                case "1":
                    String option2 = "";
                    do {
                        option2 = JOptionPane.showInputDialog(
                                """
                                        manage clients

                                        1.create client
                                        2.show clients
                                        3.back
                                                     """);
                        switch (option2) {
                            case "1":
                                ClientController.create();
                                break;
                            case "2":
                                ClientController.getAll();
                                break;
                            default:
                            case "3":

                                break;
                        }
                    } while (!option2.equals("3"));
                    break;
                case "2":
                    String option3 = "";
                    do {
                        option3 = JOptionPane.showInputDialog(
                                """
                                        manage machines

                                        1.create machines
                                        2.show machines
                                        3.create by excel
                                        4.back
                                                     """);
                        switch (option3) {
                            case "1":
                                MachineController.create();
                                break;
                            case "2":
                                MachineController.getAll();
                                break;
                            case "3":
                                MachineController.importMachinesFromExcel("Book.xlsx");

                                break;
                            case "4":

                                break;
                        }
                    } while (!option3.equals("3"));
                    break;
                case "3":
                    String option4 = "";
                    do {
                        option4 = JOptionPane.showInputDialog(
                                """
                                        manage rentals

                                        1.create rental
                                        2.show rentals active
                                        3.show rentals desactives
                                        4.change rental status
                                        5.back
                                                     """);
                        switch (option4) {
                            case "1":
                                RentalController.create();
                                break;
                            case "2":
                                RentalController.getAllByStatusTrue();
                                break;
                            case "3":
                                RentalController.getAllByStatusFalse();
                                break;
                            case "4":
                                RentalController.changeStatus();
                                break;
                            case "5":

                                break;
                        }
                    } while (!option4.equals("5"));
                    break;
                default:
                    throw new AssertionError();
            }
        } while (!option.equals("4"));

    }
}