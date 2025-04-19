/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domain.Korisnik;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author USER
 */
public class ModelTblKorisnika extends AbstractTableModel {

    private List<Korisnik> ulogovaniKorisnici;//vrsta
    private String[] nazivKolona = {"id korisnika", "username"};//kolona

    public ModelTblKorisnika(List<Korisnik> ulogovaniKorisnici) {
        this.ulogovaniKorisnici = ulogovaniKorisnici;
    }

    @Override
    public int getRowCount() {//NULL PROVERA!!
        return ulogovaniKorisnici == null ? 0 : ulogovaniKorisnici.size();
    }

    @Override
    public int getColumnCount() {
        return nazivKolona.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Korisnik k = ulogovaniKorisnici.get(rowIndex);//daje korisnika
        switch (columnIndex) {//KRECE OD 0!
            case 0:
                return k.getId();
            case 1:
                return k.getUsername();
            default:
                return null;
        }
    }
//OVO OBAVEZNO DA BI IMALI NAZIVE KOLONA!
    @Override
    public String getColumnName(int column) {
return nazivKolona[column];
        }
    
    
    
//KONKRETNO KORISNIK 

    public Korisnik getKorisnika(int rowIndex) {
        return ulogovaniKorisnici.get(rowIndex);
    }

    public void dodajKorisnika(Korisnik k) {
        ulogovaniKorisnici.add(k);
        fireTableDataChanged();
    }

    public void ukloniKorisnika(Korisnik k) {
        ulogovaniKorisnici.remove(k);
        fireTableDataChanged();

    }

    //SVI KORISNICI
    public List<Korisnik> getUlogovaniKorisnici() {
        return ulogovaniKorisnici;
    }

    public void setUlogovaniKorisnici(List<Korisnik> ulogovaniKorisnici) {
        this.ulogovaniKorisnici = ulogovaniKorisnici;
    }

}
