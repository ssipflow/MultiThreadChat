package com.chat.thread;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class MultiThreadChatClient implements Runnable {

    private static final int SERVER_PORT = 5000;
    private int idx;
    private int message_count;

    public MultiThreadChatClient(int idx, int message_count) {
        this.idx = idx;
        this.message_count = message_count;
    }

    @Override
    public void run() {

        String SERVER_IP = "127.0.0.1";

        String name = "TEST - " + this.idx;

        Socket socket = new Socket();
        try {
            socket.connect( new InetSocketAddress(SERVER_IP, SERVER_PORT) );
            consoleLog("채팅방에 입장하였습니다.");

            ChatClientReceiveThread recieveThread = new ChatClientReceiveThread(socket, name);
            recieveThread.start();

            PrintWriter printWriter = new PrintWriter( new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8)  );
            String request = "join:" + name;
            printWriter.println(request);
            printWriter.flush();

            int loop = 0;
            while(loop < message_count) {
                Thread.sleep(500);
                String message = "message:Message - " + loop;
                //System.out.println(message);
                printWriter.println(message);
                printWriter.flush();

                loop++;
            }
            printWriter.println();
            printWriter.flush();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void consoleLog(String log) {
        System.out.println(log);
    }
}
