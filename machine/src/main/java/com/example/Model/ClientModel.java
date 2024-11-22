package com.example.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.example.Database.ConfigDB;
import com.example.Entity.Client;
import com.example.Model.Interface.CRUD;

public class ClientModel implements CRUD {

    @Override
    public Client create(Object object) {
        Connection objconnection = ConfigDB.openConnection();
        Client objClient = (Client) object;
        try {
            String sql = "INSERT INTO clients(full_name, email, phone, address) VALUES(?,?,?,?);";
            PreparedStatement objPrepare = objconnection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            objPrepare.setString(1, objClient.getFull_name());
            objPrepare.setString(2, objClient.getEmail());
            objPrepare.setString(3, objClient.getPhone());
            objPrepare.setString(4, objClient.getAddress());
            objPrepare.execute();
            ResultSet objRest = objPrepare.getGeneratedKeys();
            while (objRest.next()) {
                objClient.setClient_id(objRest.getInt(1));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        ConfigDB.closeConnection();
        return objClient;
    }

    @Override
    public List<Object> getAll(int page, int size) {
        List<Object> clientsList = new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();
        try {
            int offset = (page - 1) * size;

            String sql = "SELECT * FROM clients LIMIT ? OFFSET ?";

            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            objPrepare.setInt(1, size);
            objPrepare.setInt(2, offset);

            ResultSet resultSet = objPrepare.executeQuery();

            // Recorrer los resultados y agregar los clientes a la lista
            while (resultSet.next()) {
                Client client = new Client();
                client.setClient_id(resultSet.getInt("client_id"));
                client.setFull_name(resultSet.getString("full_name"));
                client.setEmail(resultSet.getString("email"));
                client.setPhone(resultSet.getString("phone"));
                client.setAddress(resultSet.getString("address"));

                clientsList.add(client);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return clientsList;
    }

    @Override
    public List<Object> getAll() {
        List<Object> listClients = new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();
        try {
            String sql = "SELECT * FROM clients;";
            PreparedStatement objPrepare = objConnection.prepareStatement(sql);
            ResultSet objResult = objPrepare.executeQuery();
            while (objResult.next()) {
                Client objClient = new Client();
                objClient.setClient_id(objResult.getInt("client_id"));
                objClient.setFull_name(objResult.getString("full_name"));
                objClient.setEmail(objResult.getString("email"));
                objClient.setPhone(objResult.getString("phone"));
                objClient.setAddress(objResult.getString("address"));
                listClients.add(objClient);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        ConfigDB.closeConnection();
        return listClients;
    }

}
