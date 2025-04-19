/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package transfer;

import java.io.Serializable;
import operations.Operations;

public class ServerOdgovor implements Serializable {

    //objekat objekatOdgovora,poruka uspesno pa da ispise je objekatOdgovora i boolean uspesnost
    private Object objekatOdgovora;
    private Operations operacijaOdgovora;
    private String odgovor;

    public Operations getOperacijaOdgovora() {
        return operacijaOdgovora;
    }

    public void setOperacijaOdgovora(Operations operacijaOdgovora) {
        this.operacijaOdgovora = operacijaOdgovora;
    }

    public ServerOdgovor(Object objekatOdgovora) {
        this.objekatOdgovora = objekatOdgovora;
    }

    public ServerOdgovor() {
    }

    public String getOdgovor() {
        return odgovor;
    }

    public void setOdgovor(String odgovor) {
        this.odgovor = odgovor;
    }

    public Object getObjekatOdgovora() {
        return objekatOdgovora;
    }

    public void setObjekatOdgovora(Object objekatOdgovora) {
        this.objekatOdgovora = objekatOdgovora;
    }

}
