package com.company.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static final int PORT = 5678;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static Socket clientSocket;

    public static void main(String[] args) {
        try {
            var serverSocket = new ServerSocket(Main.PORT);
            System.out.printf("Server listening on %s%n", Main.PORT);

            while (true) {
                try {
                    System.out.println("Waiting for client...");
                    clientSocket = serverSocket.accept();
                    System.out.println("Client connected");

                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    while (true) {
                        String message = in.readLine();

                        if (message == null || message.equals("exit") || message.isEmpty()) {
                            System.out.println("Client disconnected.");
                            break;
                        }

                        if (message.equals("group")) {
                            out.write("IT-91mp/IT-93mn\n");
                            out.flush();
                        }
                        else if (message.equals("members")) {
                            out.write("Anna Tsytovtseva, Vitalii Omelchenko, Illya Novohatskiy\n");
                            out.flush();
                        } else {
                            out.write("Unsupported command\n");
                            out.flush();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Got error during processing connection.");
                } finally {
                    in.close();
                    out.close();
                    clientSocket.close();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Probably can not bind port");
        }
    }
}
