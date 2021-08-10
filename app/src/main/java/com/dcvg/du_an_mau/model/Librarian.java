package com.dcvg.du_an_mau.model;

public class Librarian {
    public String librarian_id;
    public String password;
    public String librarian_name;

    public String getLibrarian_id() {
        return librarian_id;
    }

    public void setLibrarian_id(String librarian_id) {
        this.librarian_id = librarian_id;
    }

    public String getPasword() {
        return password;
    }

    public void setPasword(String pasword) {
        this.password = pasword;
    }

    public String getLibrarian_name() {
        return librarian_name;
    }

    public void setLibrarian_name(String librarian_name) {
        this.librarian_name = librarian_name;
    }

    public Librarian(String librarian_id, String password, String librarian_name) {
        this.librarian_id = librarian_id;
        this.password = password;
        this.librarian_name = librarian_name;
    }
}
