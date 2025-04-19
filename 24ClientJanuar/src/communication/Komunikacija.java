/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;

import domain.Korisnik;
import domain.Poruka;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kontroler.Controller;
import operations.Operations;
import transfer.KlijentZahtev;
import transfer.ServerOdgovor;

/**
 *
 * @author USER
 */
public class Komunikacija extends Thread {

    //deo instance genericki+dodatak posaljiZahtev primiOdgovor isti kao kod ClientTrhead
    private static Komunikacija instance;
    private Socket connectionsocket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    private boolean kraj = false;

    private Komunikacija() {
        try {
            connectionsocket = new Socket("localhost", 9000);
            oos = new ObjectOutputStream(connectionsocket.getOutputStream());
            ois = new ObjectInputStream(connectionsocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Komunikacija getInstance() {
        if (instance == null) {
            instance = new Komunikacija();
        }
        return instance;
    }

    /*u run je ono sto stalno se ponavlja-poruke/prijvaljeni korisnici*/
    @Override
    public void run() {
        while (!kraj) {
            ServerOdgovor so = primiOdgovor();
            if (so == null) {
                continue;
            }
            switch (so.getOperacijaOdgovora()) {
                case Operations.VRATI_PRIJAVLJENE_KORISNIKE:
                    List<Korisnik> listaNova = (List<Korisnik>) so.getObjekatOdgovora();
                    Controller.getInstance().getSf().osveziTabelu(listaNova);
                    break;
                case Operations.LOGOUT:
                    Controller.getInstance().getSf().dispose();
                    zaustavi();
                    break;
                case Operations.POSALJI_PORUKU:
                    Controller.getInstance().getSf().jOptionPoruka();
                    break;
                case Operations.UCITAJ_PORUKE:
                    System.out.println("communication.Komunikacija.porukeucitaj()");
                    List<Poruka> listaPoruke = (List<Poruka>) so.getObjekatOdgovora();
                    Controller.getInstance().getSf().osveziTabeluPoruka(listaPoruke);
                    //getLat/getFirst ne ubacuje se redom na lista pristup
                    Controller.getInstance().getSf().dodajIspisPoslednjaPoruka();
                    break;
                default:
                    System.out.println("NEDEFINISANO");
                    throw new AssertionError();
            }

        }
    }

    public void posaljiZahtev(KlijentZahtev kz) {
        try {
            oos.writeObject(kz);//ceo kz se upisuje ne samo obj oper!!!
            System.out.println("communication.Komunikacija.posaljiZahtev()");
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ServerOdgovor primiOdgovor() {
        try {
            return (ServerOdgovor) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void zaustavi() {
        kraj = true;
        zatvoriKonekciju();
    }

    public void zatvoriKonekciju() {
        try {

            if (connectionsocket != null && !connectionsocket.isClosed()) {
                connectionsocket.close();
            }
            System.out.println("Konekcija sa serverom zatvorena.");
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
