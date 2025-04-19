/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author USER
 */
public class  Poruka implements Serializable {
    private int id;
    private Date datum;
    private String sadrzaj;
    private Korisnik posiljalac;
    private Korisnik primalac;

    public Poruka(int id, Date datum, String sadrzaj, Korisnik posiljalac, Korisnik primalac) {
        this.id = id;
        this.datum = datum;
        this.sadrzaj = sadrzaj;
        this.posiljalac = posiljalac;
        this.primalac = primalac;
    }

    public Poruka(Date datum, String sadrzaj, Korisnik posiljalac, Korisnik primalac) {
        this.datum = datum;
        this.sadrzaj = sadrzaj;
        this.posiljalac = posiljalac;
        this.primalac = primalac;
    }

    public Korisnik getPrimalac() {
        return primalac;
    }

    public void setPrimalac(Korisnik primalac) {
        this.primalac = primalac;
    }

    public Korisnik getPosiljalac() {
        return posiljalac;
    }

    public void setPosiljalac(Korisnik posiljalac) {
        this.posiljalac = posiljalac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    @Override
    public String toString() {
        return " posiljalac: " + posiljalac+"\n sadrzaj: "+sadrzaj;
    }
    
}
