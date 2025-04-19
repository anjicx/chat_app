/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import forms.MainForm;

/**
 *
 * @author USER
 */
public class Controller {

    private static Controller instance;
    private MainForm sf;

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void setSf(MainForm sf) {
        this.sf = sf;
    }

    public MainForm getSf() {
        return sf;
    }

}
