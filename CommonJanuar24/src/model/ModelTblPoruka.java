/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domain.Poruka;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author USER
 */
public class ModelTblPoruka extends AbstractTableModel {

    List<Poruka> poruke;
    String[] columns = {"poslao", "timestamp", "sadrzaj"};

    @Override
    public int getRowCount() {//NULL PROVERA!
        return poruke == null ? 0 : poruke.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Poruka p = poruke.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getPosiljalac();
            case 1:
                return p.getDatum();
            case 2:
                return p.getSadrzaj();
            default:
                throw new AssertionError();
        }

    }
//OVO SE DODAJE

    @Override
    public String getColumnName(int column) {

        return columns[column];
    }

    public Poruka getPoruka(int row) {//vrati poruku u redu

        return poruke.get(row);
    }

    public void setPoruke(List<Poruka> poruke) {
        this.poruke = poruke;
    }

    public List<Poruka> getPoruke() {
        return poruke;
    }

}
