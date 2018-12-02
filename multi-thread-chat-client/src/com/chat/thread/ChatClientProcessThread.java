package com.chat.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClientProcessThread extends Thread {

    private String name;
    private Socket socket;
    private BufferedReader bufferedReader;

    public ChatClientProcessThread(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run() {

        try {
            bufferedReader = new BufferedReader( new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8) );

            while(true) {
                String message = bufferedReader.readLine();
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
