/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import domain.Korisnik;
import forme.ServerForm;
import java.util.ArrayList;
import java.util.List;
import niti.ClientThread;

/**
 *
 * @author USER
 */
public class KontrolerPrijava {

    private static KontrolerPrijava instance;
    ServerForm sf;
    protected List<Korisnik> prijavljeniKorisnici = new ArrayList<>();
    protected List<ClientThread> niti = new ArrayList<>();

    public static KontrolerPrijava getInstance() {
        if (instance == null) {
            instance = new KontrolerPrijava();
        }
        return instance;
    }

    public void setSf(ServerForm sf) {
        this.sf = sf;
    }

    public ServerForm getSf() {
        return sf;
    }

    public void dodajKorisnika(Korisnik k) {
        prijavljeniKorisnici.add(k);
        sf.osveziTabelu(prijavljeniKorisnici);
        //obavestiSveKlijente();

    }

    public void ukloniKorisnika(Korisnik k) {
        prijavljeniKorisnici.remove(k);
        sf.osveziTabelu(prijavljeniKorisnici);
        // obavestiSveKlijente();

    }

    public List<Korisnik> getPrijavljeniKorisnici() {
        return prijavljeniKorisnici;
    }

    public void setPrijavljeniKorisnikLista(List<Korisnik> prijavljeniKorisnik) {
        this.prijavljeniKorisnici = prijavljeniKorisnik;
    }

    public void dodajNit(ClientThread nit) {
        niti.add(nit);
    }

    public void ukloniNit(ClientThread nit) {
        niti.remove(nit);
    }

    public List<ClientThread> getNiti() {
        return niti;
    }
    

    /*
   Ako bi se direktno koristila prijavljeniKorisnici, 
    a u isto vreme se neki drugi korisnik prijavljuje/odjavljuje, 
    došlo bi do ConcurrentModificationException — 
    jer lista se menja dok je neko drugi koristi.ZATO KOPIJA LISTE NE ORIGINAL!
     */
    public void obavestiSveKlijente() {
        List<Korisnik> kopija = new ArrayList<>(prijavljeniKorisnici);
        for (ClientThread ct : niti) {
            try {
                ct.posaljiTabelu(kopija);
            } catch (Exception e) {
                // Ako dođe do greške, pretpostavi da nit više nije validna
                System.out.println("Nit više nije aktivna, uklanjam: " + ct);
            }
        }

    }

    public void odjavi(Korisnik k) {
        ClientThread nitizbaci=null;
        for (ClientThread ct : niti) {
            if (ct.getPrijavljeni().equals(k)) {
                ct.odjaviKorisnika();//LOGOUT POSALJI STO ZNC ZATVORI FORMU KLIJENTA
                ukloniKorisnika(k);
                nitizbaci=ct;
                
            }
        }
        ukloniNit(nitizbaci);
        obavestiSveKlijente();
    }
    
}
