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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class Config {

    private Properties konfiguracioniObjekat = new Properties();
    private static Config instance;
    private String putanja = "config.properties";

    private Config() {
        File konfiguracioniFajl = new File(putanja);
        if (konfiguracioniFajl.exists()) {//ako postoji procitaj
            ucitajFajl(konfiguracioniFajl);
        } else {//ako ne postoji fajl upisi
            konfiguracioniObjekat.setProperty("max_value", "5");
            konfiguracioniObjekat.setProperty("port", "9000");
            upisiFajl();
        }

    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    private void ucitajFajl(File konfiguracioniFajl) {
        try {
            konfiguracioniObjekat.load(new FileInputStream(konfiguracioniFajl));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void upisiFajl() {
        try {
            konfiguracioniObjekat.store(new FileOutputStream(putanja), null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String ispisi() {
         int max=Integer.parseInt(konfiguracioniObjekat.getProperty("max_value"));
        String m=String.valueOf(max);return m;
    } 

    public Properties getKonfiguracioniObjekat() {
        return konfiguracioniObjekat;
    }

    public void setKonfiguracioniObjekat(Properties konfiguracioniObjekat) {
        this.konfiguracioniObjekat = konfiguracioniObjekat;
    }
}
