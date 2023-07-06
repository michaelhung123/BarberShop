package com.example.barbershop.Domain;

public class UserDomain {
    private int id;

    private String username;
    private String password;


    public UserDomain(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public UserDomain() {
    }

    //toString


    @Override
    public String toString() {
        return "UserDomain{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    //Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
