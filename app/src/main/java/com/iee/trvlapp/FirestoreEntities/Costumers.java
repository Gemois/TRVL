package com.iee.trvlapp.FirestoreEntities;

public class Costumers {

    int cid;
    private String name;
    private String surname;
    private long phone;
    private String email;
    private int pid;
    private int hotel;

    public Costumers() {
    }

    public Costumers(int cid, String name, String surname, long phone, String email, int pid, int hotel) {
        this.cid = cid;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = email;
        this.pid = pid;
        this.hotel = hotel;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getHotel() {
        return hotel;
    }

    public void setHotel(int hotel) {
        this.hotel = hotel;
    }


    @Override
    public String toString() {
        return "\ncid     : " + cid +
                "\nname    :  " + name +
                "\nsurname :  " + surname +
                "\nphone   :  " + phone +
                "\nemail   :  " + email +
                "\npid     :  " + pid +
                "\nhotel   :  " + hotel;
    }
}
