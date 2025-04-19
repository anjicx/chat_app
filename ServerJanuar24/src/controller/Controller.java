/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dbb.DBBroker;
import domain.Korisnik;
import domain.Poruka;
import forme.ServerForm;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import niti.ClientThread;

/**
 *
 * @author USER
 */
public class Controller extends KontrolerPrijava {

    //poziva dbbroker i menja tbl korisnika prijavljenih na serverform sto se koriste
    //zavisno od login/logout korisnika
    private static Controller instance;
    private DBBroker dbb;

    private Controller() {
        dbb = new DBBroker();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }
//uvek mozeJOs umesot popunjeno
    public boolean mozeJos() {
        //OUT.CLOSE I IN.CLOSE ZATO BITNI KOD ODJAVE!
        //KontrolerPrijava.getInstance().getSf().getPrijavljeniBroj()ILI OVA METODA
        if (KontrolerPrijava.getInstance().prijavljeniKorisnici.size() <= KontrolerPrijava.getInstance().getSf().getBrojac()) {
            return true;
        }
        return false;
    }

    public Korisnik prijavi(Korisnik k) {
        try {

            return dbb.login(k);
        } catch (SQLException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean posaljiPoruku(Poruka poruka) {
        return dbb.posaljiPoruku(poruka);
    }

    public List<Poruka> ucitajPoruke(Korisnik k) {

        return dbb.ispisiPoruke(k);
    }
//OVAKO MORA DA BI PRIMAOCU SE UCITALE SVE PORUKE KADA SEND KLIKNE POSILJALAC

    public void ucitajPrimalac(Korisnik primalac) {
        System.out.println("controller.Controller.ucitajPrimalac()");
        System.out.println(getNiti());
        //ovde ne getNiti to je prazno onda nego getInstance pristupi klasi pa fji njenoj
        for (ClientThread ct : KontrolerPrijava.getInstance().getNiti()) {
            if (ct.getPrijavljeni().equals(primalac)) {
                System.out.println("controller.Controller.ucitajPrimalac()");
                List<Poruka> lista = ucitajPoruke(primalac);
                ct.posaljiTabeluPoruka(lista);//LOGOUT POSALJI STO ZNC ZATVORI FORMU KLIJENTA
                break;
            } else {
                System.out.println("controller.Controller.ucitajPrimalac()");
            }
        }
    }
}
