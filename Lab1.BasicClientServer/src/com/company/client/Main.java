package com.company.client;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Main {
    private static final int SERVER_PORT = 5678;
    private static final String SERVER_ADDRESS = "0.0.0.0";
    private static Socket clientSocket;
    private static BufferedReader reader;
    private static BufferedReader inputFromServer;
    private static BufferedWriter outputToServer;

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                reader = new BufferedReader(new InputStreamReader(System.in));
                inputFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                outputToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                while (true) {
                    System.out.println("Type command('group', 'members', 'exit'):");
                    String command = reader.readLine();
                    if (command.equals("exit")) {
                        break;
                    }

                    outputToServer.write(command + "\n");
                    outputToServer.flush();

                    String response = inputFromServer.readLine();

                    if (response == null) {
                        System.out.println("Null response from server. Exiting...");
                        break;
                    }

                    System.out.println(response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                System.out.println("Cleaning resources");
                clientSocket.close();
                inputFromServer.close();
                outputToServer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exiting...");
            System.exit(1);
        }
    }
}