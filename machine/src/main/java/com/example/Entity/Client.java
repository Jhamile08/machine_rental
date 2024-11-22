package com.example.Entity;

public class Client {
    private int client_id;
    private String full_name;
    private String email;
    private String phone;
    private String address;

    public Client(String address, String email, String full_name, String phone) {
        this.address = address;
        this.email = email;
        this.full_name = full_name;
        this.phone = phone;
    }

    public Client() {
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client [client_id=" + client_id + ", full_name=" + full_name + ", email=" + email + ", phone=" + phone
                + ", address=" + address + "]";
    }

}
