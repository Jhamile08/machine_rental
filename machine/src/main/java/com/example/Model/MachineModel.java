package com.example.Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.Database.ConfigDB;
import com.example.Entity.Machine;
import com.example.Model.Interface.CRUD;

public class MachineModel implements CRUD {

    @Override
    public Machine create(Object object) {
        Connection objconnection = ConfigDB.openConnection();
        Machine objMachine = (Machine) object;
        try {
            String sql = "INSERT INTO machine(brand, serial_number, state) VALUES(?,?,?);";
            PreparedStatement objPrepare = objconnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            objPrepare.setString(1, objMachine.getBrand());
            objPrepare.setString(2, objMachine.getSerial_number());
            objPrepare.setBoolean(3, objMachine.isState());
            objPrepare.execute();
            ResultSet objRest = objPrepare.getGeneratedKeys();
            while (objRest.next()) {
                objMachine.setMachine_id(objRest.getInt(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        ConfigDB.closeConnection();
        return objMachine;
    }

    @Override
    public List<Object> getAll(int page, int size) {
        List<Object> machineList = new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();
        try {
            int offset = (page - 1) * size;

            String sql = "SELECT * FROM machine LIMIT ? OFFSET ?";

            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setInt(1, size);
            objPrepare.setInt(2, offset);

            ResultSet resultSet = objPrepare.executeQuery();

            // Recorrer los resultados y agregar los clientes a la lista
            while (resultSet.next()) {
                Machine machine = new Machine();
                machine.setMachine_id(resultSet.getInt("machine_id"));
                machine.setBrand(resultSet.getString("brand"));
                machine.setSerial_number(resultSet.getString("serial_number"));
                machine.setState(resultSet.getBoolean("state"));

                machineList.add(machine);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return machineList;
    }

    @Override
    public List<Object> getAll() {
        List<Object> listMachine = new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();
        try {
            String sql = "SELECT * FROM machine;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            ResultSet objResult = objPrepare.executeQuery();
            while (objResult.next()) {
                Machine objMachine = new Machine();
                objMachine.setMachine_id(objResult.getInt("machine_id"));
                objMachine.setBrand(objResult.getString("brand"));
                objMachine.setSerial_number(objResult.getString("serial_number"));
                objMachine.setState(objResult.getBoolean("state"));
                listMachine.add(objMachine);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        ConfigDB.closeConnection();
        return listMachine;
    }

    public List<Object> getAllAvailable() {
        List<Object> listMachine = new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();
        try {
            String sql = "SELECT * FROM machine WHERE state = true;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            ResultSet objResult = objPrepare.executeQuery();
            while (objResult.next()) {
                Machine objMachine = new Machine();
                objMachine.setMachine_id(objResult.getInt("machine_id"));
                objMachine.setBrand(objResult.getString("brand"));
                objMachine.setSerial_number(objResult.getString("serial_number"));
                objMachine.setState(objResult.getBoolean("state"));
                listMachine.add(objMachine);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        ConfigDB.closeConnection();
        return listMachine;
    }

    public void createMachinesFromExcel(String filePath) {
        try (FileInputStream f = new FileInputStream(filePath);
                XSSFWorkbook libro = new XSSFWorkbook(f)) {

            // Seleccionamos la primera hoja
            XSSFSheet hoja = libro.getSheetAt(0);

            // Iteramos sobre las filas de la hoja, saltando la fila de encabezado (si
            // existe)
            Iterator<Row> filas = hoja.iterator();

            // Si hay encabezados, omitir la primera fila
            if (filas.hasNext()) {
                filas.next(); // Saltamos la fila del encabezado
            }

            while (filas.hasNext()) {
                Row fila = filas.next();

                // Comprobamos si la fila no está vacía
                if (fila == null || fila.getPhysicalNumberOfCells() == 0) {
                    continue; // Saltamos filas vacías
                }

                // Creamos una nueva máquina
                Machine machine = new Machine();

                // Inicializamos state a true por defecto
                machine.setState(true);

                // Recorremos las celdas de la fila (asumimos 2 columnas: brand y serial_number)
                for (int columnIndex = 0; columnIndex < 2; columnIndex++) {
                    Cell celda = fila.getCell(columnIndex);

                    if (celda == null) {
                        continue; // Si la celda está vacía, la ignoramos
                    }

                    switch (celda.getCellType()) {
                        case STRING:
                            if (columnIndex == 0) { // Columna 0: brand
                                machine.setBrand(celda.getStringCellValue());
                            } else if (columnIndex == 1) { // Columna 1: serial_number
                                machine.setSerial_number(celda.getStringCellValue());
                            }
                            break;
                        default:
                            break;
                    }
                }

                // Guardar la máquina en la base de datos
                create(machine); // 'create' asigna el 'machine_id' automáticamente
            }

        } catch (IOException ex) {
            System.out.println("Error al leer el archivo Excel: " + ex.getMessage());
        } catch (Exception e) {
            System.out.println("Error al procesar las máquinas: " + e.getMessage());
        }
    }

}
