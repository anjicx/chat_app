/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import controller.Controller;
import controller.KontrolerPrijava;
import domain.Korisnik;
import domain.Poruka;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import operations.Operations;
import transfer.KlijentZahtev;
import transfer.ServerOdgovor;

/**
 *
 * @author USER
 */
public class ClientThread extends Thread {
//obrada kz

    private boolean kraj = false;
    private Socket connectionSocket;
    private Korisnik prijavljeni;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public Korisnik getPrijavljeni() {
        return prijavljeni;
    }

    public void setPrijavljeni(Korisnik prijavljeni) {
        this.prijavljeni = prijavljeni;
    }

    public ClientThread(Socket connectionSocket) {//ovo dobija od serverthread
        KontrolerPrijava.getInstance().dodajNit(this);
        this.connectionSocket = connectionSocket;
        try {
            oos = new ObjectOutputStream(connectionSocket.getOutputStream());
            ois = new ObjectInputStream(connectionSocket.getInputStream());

        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (!kraj && !connectionSocket.isClosed()) {
            KlijentZahtev kz = primiZahtev();
            if (kz == null) {
                return;
            }
            ServerOdgovor so = new ServerOdgovor();
            switch (kz.getOperacija()) {
                case Operations.LOGIN://prijava
                    /*Znači ti koristiš pattern “Publisher-Subscriber” gde je:
                    KontrolerPrijava = publisher (objavljuje događaj)
                    ClientThread = subscriber (prima i šalje ka klijentu)
                     */
                    boolean daLiMoze = Controller.getInstance().mozeJos();
                    if (!daLiMoze) {
                        so.setObjekatOdgovora(null);
                        so.setOdgovor("NE MOZE VISE DA SE PRIJAVI KORISNIKA");
                        posaljiOdgovor(so);//
                        return;//BITNO DA NE BI NA SERVERU ONDA ISPISAO DA JE POVEZAN
                        //DA NE IDE POSLE IF OSTATAK DA IZVRSAVA
                    }
                    prijavljeni = Controller.getInstance().prijavi((Korisnik) kz.getObjekatOperacije());
                    if (prijavljeni != null) {
                        if (Controller.getInstance().getPrijavljeniKorisnici().contains(prijavljeni)) {
                            System.out.println(prijavljeni.getUsername());
                            so.setObjekatOdgovora(null);
                            so.setOdgovor("KORISNIK VEC PRIJAVLJEN!");
                            posaljiOdgovor(so);//

                        } else {
                            KontrolerPrijava.getInstance().dodajKorisnika(prijavljeni);
                            so.setObjekatOdgovora(prijavljeni);
                            so.setOdgovor("KORISNIK PRIJAVLJEN!");
                            posaljiOdgovor(so);//
                            KontrolerPrijava.getInstance().obavestiSveKlijente();
                        }
                    } else {
                        so.setObjekatOdgovora(prijavljeni);
                        so.setOdgovor("KORISNIK NIJE PRONADJEN");
                        posaljiOdgovor(so);//

                    }
                    break;
                case Operations.VRATI_PRIJAVLJENE_KORISNIKE://broadcast poruka
                    KontrolerPrijava.getInstance().obavestiSveKlijente();
                    break;
                case Operations.LOGOUT:
                    KontrolerPrijava.getInstance().odjavi(prijavljeni);
                    break;
                case Operations.POSALJI_PORUKU:
                    boolean b = Controller.getInstance().posaljiPoruku((Poruka) kz.getObjekatOperacije());
                    if (b) {
                        so.setOdgovor("POSLATO");
                        so.setOperacijaOdgovora(Operations.POSALJI_PORUKU);
                        System.out.println("POSLATO USPESNO");
                    }
                    posaljiOdgovor(so);
                    break;
                case Operations.UCITAJ_PORUKE:
                    System.out.println("niti.ClientThread.run()UCITAJPORUKE");
                    //za primaoca poslatog ucitaj poruke
                    Controller.getInstance().ucitajPrimalac((Korisnik) kz.getObjekatOperacije());
                    break;
                default:
                    System.err.println("GRESKA");
            }

        }

    }

    public void odjaviKorisnika() {
        ServerOdgovor so = new ServerOdgovor();
        so.setOperacijaOdgovora(Operations.LOGOUT);
        posaljiOdgovor(so);
        zatvoriSoket();
    }

    public void posaljiOdgovor(ServerOdgovor so) {
        try {
            oos.writeObject(so);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public KlijentZahtev primiZahtev() {
        try {
            //IZ SOCKETA SE CITA STREAM!!!!
            if (connectionSocket.isClosed()) {
                System.out.println("Socket je zatvoren. Nemoguće je primiti zahtev.");
                return null;
            }
            return (KlijentZahtev) ois.readObject();
        } catch (SocketException e) {
            System.out.println("Socket je zatvoren (verovatno od strane servera).");
        } catch (IOException ex) {
            System.err.println("ODJAVIO SE KLIJENT");
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void posaljiTabelu(List<Korisnik> listaKorisnika) {

        ServerOdgovor so = new ServerOdgovor();
        so.setOperacijaOdgovora(Operations.VRATI_PRIJAVLJENE_KORISNIKE);
        so.setObjekatOdgovora(listaKorisnika);
        posaljiOdgovor(so);//obav poslati odgovor
    }

    public void posaljiTabeluPoruka(List<Poruka> listaPoruka) {
        System.out.println("niti.ClientThread.posaljiTabeluPoruka()");
        ServerOdgovor so = new ServerOdgovor();
        so.setOperacijaOdgovora(Operations.UCITAJ_PORUKE);

        so.setObjekatOdgovora(listaPoruka);
        System.out.println(listaPoruka);
        posaljiOdgovor(so);//obav poslati odgovor
    }

    public void zatvoriSoket() {
        {
            try {
                connectionSocket.close();
                oos.close();
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        kraj = true;
    }
}
