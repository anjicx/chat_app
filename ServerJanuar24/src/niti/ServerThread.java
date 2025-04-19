/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import controller.Controller;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread extends Thread {

    //POKRETANJE SERVERSKOG PROGRAMA
    //INT PORT=INTEGER.PARSEINT(KONFIGURACIJA.GETPROPERTIE(""));
    //1.DEO SOCKET=NEW SOCKET(PORT),
    //2.ACCEPT
    //NACIN FUNKCIONISANJA SERVERACCEPT-NITI PROJEKAT
    private boolean kraj = false; // Zastavica za kontrolu rada niti
    ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9000);
            System.out.println("Server pokrenut.");
            while (!kraj) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientThread nit = new ClientThread(clientSocket);
                    nit.start();
                } catch (IOException ex) {//ako se stavi na kraj zastavica izlazi iz petlje sa io izuz pa mora
                    //provera da ne bi bacao exception!!!
                    if (kraj) {
                        System.out.println("Server zatvoren.");
                    } else {
                        Logger.getLogger(ServerThread.class.getName()).log(Level.WARNING, "Greška pri radu", ex);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, "Server nije mogao da se startuje", ex);
        }
        zaustaviServer();

    }

    //KADA SE POKRENE SERVER
    public void zaustaviServer() {
        kraj = true;
        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
