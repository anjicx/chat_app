/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transfer;

import java.io.Serializable;
import operations.Operations;

public class KlijentZahtev implements Serializable{
    //LOGIN; USER(USERNAME,PASSWORD)
    private Operations operacija;
    private Object objekatOperacije;

    public KlijentZahtev(Operations operacija, Object objekatOperacije) {
        this.operacija = operacija;
        this.objekatOperacije = objekatOperacije;
    }

    public Object getObjekatOperacije() {
        return objekatOperacije;
    }

    public void setObjekatOperacije(Object objekatOperacije) {
        this.objekatOperacije = objekatOperacije;
    }

    public Operations getOperacija() {
        return operacija;
    }

    public void setOperacija(Operations operacija) {
        this.operacija = operacija;
    }
    
}
