/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author USER
 */
public class Korisnik implements Serializable {
    private int id;
    private String username;
    private String password;

    public Korisnik(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Korisnik(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Korisnik() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return getUsername();
    }
    

  
//proverava dva obj po username i passw da li su jednaki

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.username);
        hash = 37 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Korisnik other = (Korisnik) obj;
        return Objects.equals(this.username, other.username);
    }

  
    
}
