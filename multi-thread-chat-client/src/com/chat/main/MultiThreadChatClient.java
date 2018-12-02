package com.chat.main;

import com.chat.thread.ChatClientProcessThread;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MultiThreadChatClient {

    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {

        if(args.length == 0) {
            System.out.println("$ java com.chat.main.MultiThreadChatClient [SERVER_IP]");
            System.exit(1);
        }

        String SERVER_IP = args[0];

        String name = null;
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("대화명을 입력하세요.");
            System.out.print("USER NAME >>> ");

            name = scanner.nextLine();
            if(name.isEmpty())
                System.out.println("대화명은 한글자 이상 입력해야 합니다.");
            else
                break;
        }

        Socket socket = new Socket();
        try {
            socket.connect( new InetSocketAddress(SERVER_IP, SERVER_PORT) );
            consoleLog("채팅방에 입장하였습니다.");

            ChatClientProcessThread recieveThread = new ChatClientProcessThread(socket, name);
            recieveThread.start();

            PrintWriter printWriter = new PrintWriter( new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)  );
            String request = "join:" + name;
            printWriter.println(request);
            printWriter.flush();

            while(true) {
                String message = scanner.nextLine();

                if(message.isEmpty()) {
                    consoleLog("메세지는 최소 한글자 이상입니다.");
                    continue;
                }

                if("quit".equals(message)) {
                    System.exit(1);
                } else {
                    message = "message:" + message;
                }
                printWriter.println(message);
                printWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                scanner.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consoleLog(String log) {
        System.out.println(log);
    }
}
