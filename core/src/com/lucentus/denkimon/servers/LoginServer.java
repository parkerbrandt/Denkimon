package com.lucentus.denkimon.servers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * The Log-In Server
 * Handles login and register requests from clients
 */
public class LoginServer {

    // Static Class Members
    private static final int PORT = 9876;

    // Properties
    private ServerSocket socket;


    /**
     * Start of Program Logic for the Login Server
     * @param args
     */
    public static void main(String[] args) {

        // Initialize the server
        LoginServer server = new LoginServer();

        try {
            server.socket = new ServerSocket();

            while(true) {
                System.out.println("Waiting for client request");

                // Create a socket for the client connection
                Socket socket = server.socket.accept();

                // Read from socket
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                String message = (String)objectInputStream.readObject();

                // TODO: Parse message and execute logic

                // Send message back
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                // Close
                objectInputStream.close();
                objectOutputStream.close();
                socket.close();
            }

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
