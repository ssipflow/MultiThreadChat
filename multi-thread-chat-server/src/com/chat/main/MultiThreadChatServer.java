package com.chat.main;

import com.chat.thread.ChatServerProcessThread;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadChatServer {
    public static final int PORT = 5000;

    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("$ java com.chat.main.MultiThreadChatServer [MAX_USER]");
            System.exit(1);
        }

        Integer MAX_THREADS = Integer.parseInt(args[0]);

        ServerSocket serverSocket = null;
        List<PrintWriter> listSocketWriters = new ArrayList<>();

        ExecutorService executorService = null;

        try {
            executorService = Executors.newFixedThreadPool(MAX_THREADS);

            serverSocket = new ServerSocket();

            String hostAddr = "127.0.0.1";
            serverSocket.bind( new InetSocketAddress(hostAddr, PORT) );
            consoleLog("연결 대기 - " + hostAddr + ":" + PORT);

            while(true) {
                Socket socket = serverSocket.accept();
                ChatServerProcessThread thread = new ChatServerProcessThread(socket, listSocketWriters);
                executorService.execute(thread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            try {
                if( serverSocket != null && !serverSocket.isClosed())
                    serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consoleLog(String log) {
        System.out.println("[server " + Thread.currentThread().getId() + "] " + log);
    }
}
