/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbb;

import domain.Korisnik;
import domain.Poruka;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class DBBroker {
    //konkretne fje rada nad bazom

    public Korisnik login(Korisnik korisnik) throws SQLException {
        String upit = "SELECT * FROM KORISNIK WHERE username=? and password=?";
        PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);
        ps.setString(1, korisnik.getUsername());
        ps.setString(2, korisnik.getPassword());
        System.out.println(upit);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {//nije while jer ima samo 1
            System.out.println("DA");
            korisnik.setId(rs.getInt("id"));
            return korisnik;
        } else {
            System.out.println("NE");
            return null;
        }

    }
//DATUM=DATUMVREME PORUKE

    public boolean posaljiPoruku(Poruka poruka) {
        String upit = "INSERT INTO PORUKA(DATUMVREME,TEKST,POSILJALAC,PRIMALAC)" + " VALUES(?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = Konekcija.getInstance().getConnection().prepareStatement(upit);
            Timestamp t = new Timestamp(poruka.getDatum().getTime());
            ps.setTimestamp(1, t);
            ps.setString(2, poruka.getSadrzaj());
            ps.setInt(3, poruka.getPosiljalac().getId());
            ps.setInt(4, poruka.getPrimalac().getId());
            ps.executeUpdate();
            Konekcija.getInstance().getConnection().commit();
            System.out.println("ISPIS");
            return true;

        } catch (SQLException ex) {
            try {
                Konekcija.getInstance().getConnection().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex1);
            }

            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public List<Poruka> ispisiPoruke(Korisnik k) {
        /*select from order by asc */
        String upit = "SELECT *"
                + " FROM PORUKA P JOIN KORISNIK K"
                + " ON P.posiljalac=K.ID WHERE P.PRIMALAC=? ORDER BY P.DATUMVREME DESC";
        List<Poruka> listaPoruka = new ArrayList<>();
        try {//k je primalac a posiljalac dobijamo kada ovako join uradimo
            PreparedStatement s = Konekcija.getInstance().getConnection().prepareStatement(upit);
            s.setInt(1, k.getId());
            System.out.println(upit);
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                Timestamp datumVreme = rs.getTimestamp("datumVreme");
                //ako Date stavis ispis samo vremena
                //
                java.util.Date datumVr = new java.util.Date(datumVreme.getTime());
                String tekst = rs.getString("tekst");
                //POSILJAOCA POSALJEM KAO ULOGOVANOG A PRIMAOCA ISCITAM IL OBRNUTO
                //OD ON ZAVISI CEGA USERNAME CITAS OVK OD POSILJAOCA
                Korisnik posiljalac = new Korisnik();
                posiljalac.setId(rs.getInt(4));
                posiljalac.setUsername(rs.getString("username"));//da bvi toString ispisao mora toString definisati u KORISNIKU!
                Korisnik primalac = k;
                Poruka p = new Poruka(datumVr, tekst, posiljalac, primalac);
                System.out.println(p.toString());
                listaPoruka.add(p);

            }
            return listaPoruka;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
