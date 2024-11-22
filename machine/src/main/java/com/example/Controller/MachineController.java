package com.example.Controller;

import java.util.List;

import javax.swing.JOptionPane;

import com.example.Entity.Machine;
import com.example.Model.MachineModel;

public class MachineController {
    private static int currentPage = 1; // Página inicial
    private static final int pageSize = 5;

    public static void create() {
        // pedir datos por consola
        String brand = JOptionPane.showInputDialog("Insert the brand: ");
        String serial_number = JOptionPane.showInputDialog("Insert the serial number: ");

        // instanciar la maquina en true
        Machine newMachine = new Machine(brand, serial_number, true);

        // crear el maquina
        Machine instanceMachine = (Machine) instanceModel().create(newMachine);
        JOptionPane.showMessageDialog(null, instanceMachine.toString());

    }

    public static void getAll() {
        List<Object> machine = instanceModel().getAll(currentPage, pageSize);

        // Mostrar las maquinas
        StringBuilder machineInfo = new StringBuilder();
        for (Object obj : machine) {
            Machine machinee = (Machine) obj;
            machineInfo.append(machinee.toString()).append("\n");
        }

        // Si no hay maquinas, mostramos un mensaje
        if (machineInfo.length() == 0) {
            JOptionPane.showMessageDialog(null, "There are not more machine for show.");
            return;
        }

        // Mostrar los maquinas de la página actual
        int option = JOptionPane.showOptionDialog(null,
                "Machines (page " + currentPage + ")\n\n" + machineInfo.toString(),
                "Machines Paginados",
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

    // Método para gestionar la importación de máquinas desde un archivo Excel
    public static void importMachinesFromExcel(String filePath) {
        try {
            // Llamar al modelo para procesar el archivo Excel
            instanceModel().createMachinesFromExcel(filePath);

            // Mostrar un mensaje de éxito en la consola o en un cuadro de diálogo
            System.out.println("Machines imported successfully!");
        } catch (Exception e) {
            // Manejo de errores
            System.err.println("Error importing machines: " + e.getMessage());
        }
    }

    public static MachineModel instanceModel() {
        return new MachineModel();
    }
}
