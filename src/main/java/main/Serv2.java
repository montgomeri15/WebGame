package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serv2 extends Thread{

    private int port = 9090;
    private boolean check = true;
    private Socket socket;
    private ServerSocket serverSocket;

    public void clientServer() throws IOException {

        System.out.println("JJJJKKKKKLJKLJ");
        serverSocket = new ServerSocket(port);

        while (check==true){
            socket = serverSocket.accept();
        }

        serverSocket.close();
        socket.close();
    }

    public void clientStop() {
        check = false;
    }
}
