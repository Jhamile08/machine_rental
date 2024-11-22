package com.example.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.example.Database.ConfigDB;
import com.example.Entity.Rental;
import com.example.Model.Interface.CRUD;

public class RentalModel implements CRUD {

    @Override
    public Rental create(Object object) {
        Connection objconnection = ConfigDB.openConnection();
        Rental objRental = (Rental) object;
        try {
            String sql = "INSERT INTO rental(client_id, machine_id, lease_start_date,lease_end_date, status) VALUES(?,?,?,?,?);";
            PreparedStatement objPrepare = objconnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            objPrepare.setInt(1, objRental.getClient_id());
            objPrepare.setInt(2, objRental.getMachine_id());
            objPrepare.setDate(3, new Date(objRental.getLease_start_date().getTime())); // Conversión a java.sql.Date
            objPrepare.setDate(4, new Date(objRental.getLease_end_date().getTime()));
            objPrepare.setBoolean(5, objRental.isStatus());
            objPrepare.execute();
            ResultSet objRest = objPrepare.getGeneratedKeys();
            while (objRest.next()) {
                objRental.setRental_id(objRest.getInt(1));
            }
            // cambiar estado de la maquina
            String updateMachineSql = "UPDATE machine SET state = false WHERE machine_id = ?;";
            PreparedStatement updateMachineStmt = objconnection.prepareStatement(updateMachineSql);
            updateMachineStmt.setInt(1, objRental.getMachine_id()); // Usar el machine_id de objRental
            updateMachineStmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        ConfigDB.closeConnection();
        return objRental;
    }

    public List<Object> getAllByStatusTrue(int page, int size) {
        List<Object> rentalList = new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();
        try {
            int offset = (page - 1) * size;

            String sql = "SELECT * FROM rental WHERE status = true LIMIT ? OFFSET ?";

            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setInt(1, size);
            objPrepare.setInt(2, offset);

            ResultSet resultSet = objPrepare.executeQuery();

            // Recorrer los resultados y agregar los clientes a la lista
            while (resultSet.next()) {
                Rental rental = new Rental();
                rental.setRental_id(resultSet.getInt("rental_id"));
                rental.setClient_id(resultSet.getInt("client_id"));
                rental.setMachine_id(resultSet.getInt("machine_id"));
                rental.setLease_start_date(resultSet.getDate("lease_start_date"));
                rental.setLease_end_date(resultSet.getDate("lease_end_date"));
                rental.setStatus(resultSet.getBoolean("status"));

                rentalList.add(rental);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return rentalList;
    }

    public List<Object> getAllByStatusFalse(int page, int size) {
        List<Object> rentalList = new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();
        try {
            int offset = (page - 1) * size;

            String sql = "SELECT * FROM rental WHERE status = false LIMIT ? OFFSET ?";

            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setInt(1, size);
            objPrepare.setInt(2, offset);

            ResultSet resultSet = objPrepare.executeQuery();

            // Recorrer los resultados y agregar los clientes a la lista
            while (resultSet.next()) {
                Rental rental = new Rental();
                rental.setRental_id(resultSet.getInt("rental_id"));
                rental.setClient_id(resultSet.getInt("client_id"));
                rental.setMachine_id(resultSet.getInt("machine_id"));
                rental.setLease_start_date(resultSet.getDate("lease_start_date"));
                rental.setLease_end_date(resultSet.getDate("lease_end_date"));
                rental.setStatus(resultSet.getBoolean("status"));

                rentalList.add(rental);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return rentalList;
    }

    public void changeStatus(Object obj) {
        Connection objConnection = ConfigDB.openConnection();
        Rental objRental = (Rental) obj;

        try {
            String sql = "UPDATE rental SET status = false WHERE rental_id = ?;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setInt(1, objRental.getRental_id());
            objPrepare.executeUpdate();

            String updateMachineSql = "UPDATE machine SET state = true WHERE machine_id = ?;";
            PreparedStatement updateMachineStmt = objConnection.prepareStatement(updateMachineSql);
            updateMachineStmt.setInt(1, objRental.getMachine_id()); // Usar el machine_id de objRental
            updateMachineStmt.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }

    }

    @Override
    public List<Object> getAll() {
        List<Object> listRentals = new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();
        try {
            String sql = "SELECT * FROM rental;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            ResultSet resultSet = objPrepare.executeQuery();
            while (resultSet.next()) {
                Rental rental = new Rental();
                rental.setRental_id(resultSet.getInt("rental_id"));
                rental.setClient_id(resultSet.getInt("client_id"));
                rental.setMachine_id(resultSet.getInt("machine_id"));
                rental.setLease_start_date(resultSet.getDate("lease_start_date"));
                rental.setLease_end_date(resultSet.getDate("lease_end_date"));
                rental.setStatus(resultSet.getBoolean("status"));
                listRentals.add(rental);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        ConfigDB.closeConnection();
        return listRentals;
    }

    @Override
    public List<Object> getAll(int page, int size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
