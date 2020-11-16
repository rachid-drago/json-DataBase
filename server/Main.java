package server;

import com.beust.jcommander.JCommander;
import database.DataBase;
import jdk.jfr.DataAmount;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import com.beust.jcommander.Parameter;

public class Main {
    public static void main(String[] args) {
        new MainClass().main();
    }
}

class Args {
    @Parameter
    private List<String> parameters = new ArrayList<>();

    @Parameter(names = "-t", description = "command name")
    String command;

    @Parameter(names = "-k", description = "index in database (from 1 to 1000)")
    String index;

    @Parameter(names = "-v", variableArity = true, description = "text")
    List<String> text = new ArrayList<>();
}


class MainClass {
    void main() {
        String address = "127.0.0.1";
        int port = 23456;
        Database database = new Database();
        System.out.println("Server started!");


        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {

            while (!server.isClosed()) {
                try {
                    Socket client = server.accept();
                    if (client != null) {
                        new Thread(new ClientThread(client, server, database)).start();
                    }
                } catch (SocketException e) {
                    return;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}