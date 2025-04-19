/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.String.valueOf;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class Konfiguracija {

    private Properties configObjekat = new Properties();//objekat vezan za konfig fajl
    //moras inicijal!
    private static Konfiguracija instance;//ovde 
    //dodaj .properties da bude dbr fajl!
    private String putanja = "app.config.properties";

    public static Konfiguracija getInstance() throws IOException {
        if (instance == null) {
            instance = new Konfiguracija();
        }
        return instance;
    }

    private Konfiguracija(){//u konstr pravis fajl/ucitavas
        File f = new File(putanja);//properties fajl na putanji je obican fajl
        if (!f.exists()) {
            configObjekat.setProperty("user", "root");
            configObjekat.setProperty("password", "");
            configObjekat.setProperty("port", "9000");
            configObjekat.setProperty("max_broj_klijenata", "5");
            sacuvajIzmenu();
        } else {
            ucitajFajl();
        }
    }
//cuvanje svake izmene config fajla koda
    public void sacuvajIzmenu()  {
        try {
            configObjekat.store(new FileOutputStream(putanja), null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Properties getConfigObjekat() {
        return configObjekat;
    }

    public void setConfigObjekat(Properties configObjekat) {
        this.configObjekat = configObjekat;
    }

    @Override
    public String toString() {//zbog ispisa u labeli
        try {
            int max = Integer.parseInt(configObjekat.getProperty("max_broj_klijenata"));
            return valueOf(max);//ili max+""
        } catch (NumberFormatException e) {
            System.out.println("LOS FORMAT");
        }
        return null;

    }

    private void ucitajFajl() {
        try {
            configObjekat.load(new FileInputStream(putanja));//ucitaj u configObjekat vrednosti iz fajla
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
