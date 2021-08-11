package com.dcvg.du_an_mau.model;

public class Librarian {
    public int librarian_id;
    public String password;
    public String librarian_name, username;

    public int getLibrarian_id() {
        return librarian_id;
    }

    public void setLibrarian_id(int librarian_id) {
        this.librarian_id = librarian_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pasword) {
        this.password = pasword;
    }

    public String getLibrarian_name() {
        return librarian_name;
    }

    public void setLibrarian_name(String librarian_name) {
        this.librarian_name = librarian_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Librarian(String password, String librarian_name, String username) {
        this.password = password;
        this.librarian_name = librarian_name;
        this.username = username;
    }
}
