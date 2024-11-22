package com.example.Entity;

import java.util.Date;

public class Rental {
    private int rental_id;
    private int client_id;
    private int machine_id;
    private Date lease_start_date;
    private Date lease_end_date;
    private boolean status;

    public Rental(int client_id, Date lease_end_date, Date lease_start_date, int machine_id, boolean status) {
        this.client_id = client_id;
        this.lease_end_date = lease_end_date;
        this.lease_start_date = lease_start_date;
        this.machine_id = machine_id;
        this.status = status;
    }

    public Rental() {
    }

    public int getRental_id() {
        return rental_id;
    }

    public void setRental_id(int rental_id) {
        this.rental_id = rental_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(int machine_id) {
        this.machine_id = machine_id;
    }

    public Date getLease_start_date() {
        return lease_start_date;
    }

    public void setLease_start_date(Date lease_start_date) {
        this.lease_start_date = lease_start_date;
    }

    public Date getLease_end_date() {
        return lease_end_date;
    }

    public void setLease_end_date(Date lease_end_date) {
        this.lease_end_date = lease_end_date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rental{");
        sb.append("rental_id=").append(rental_id);
        sb.append(", client_id=").append(client_id);
        sb.append(", machine_id=").append(machine_id);
        sb.append(", lease_start_date=").append(lease_start_date);
        sb.append(", lease_end_date=").append(lease_end_date);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

}
