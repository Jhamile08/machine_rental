package com.example.Entity;

public class Machine {
    private int machine_id;
    private String brand;
    private String serial_number;
    private boolean state;

    public Machine(String brand, String serial_number, boolean state) {
        this.brand = brand;
        this.serial_number = serial_number;
        this.state = state;
    }

    public Machine() {
    }

    public int getMachine_id() {
        return machine_id;
    }

    public void setMachine_id(int machine_id) {
        this.machine_id = machine_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Machine{");
        sb.append("machine_id=").append(machine_id);
        sb.append(", brand=").append(brand);
        sb.append(", serial_number=").append(serial_number);
        sb.append(", state=").append(state);
        sb.append('}');
        return sb.toString();
    }

}
