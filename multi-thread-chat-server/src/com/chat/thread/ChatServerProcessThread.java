package com.chat.thread;


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChatServerProcessThread implements Runnable{

    private String nickname = null;
    private Socket socket = null;
    private List<PrintWriter> listSocketWirter = null;
    FileOutputStream logFileStream = null;

    public ChatServerProcessThread(Socket socket, List<PrintWriter> listSocketWirter) {
        this.socket = socket;
        this.listSocketWirter = listSocketWirter;
    }

    @Override
    public void run() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curDate = dateFormat.format(new Date());

        try {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8) );
            PrintWriter printWriter = new PrintWriter( new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            logFileStream = new FileOutputStream("sentbe.txt", true);

            while(true) {
                String request = bufferedReader.readLine();


                if( request == null ) {
                    consoleLog(nickname + " 클라이언트로부터 연결 끊김");
                    doQuit(printWriter, curDate);
                    break;
                }

                String[] tokens = request.split(":");
                if("join".equals(tokens[0])) {
                    doJoin(tokens[1], printWriter);
                    fileWrite(" 님이 입장하였습니다.", curDate, logFileStream);
                } else if("message".equals(tokens[0])) {
                    doMessage(tokens[1]);
                    fileWrite( " : " + tokens[1], curDate, logFileStream);
                } else if("quit".equals(tokens[0])) {
                    doQuit(printWriter, curDate);
                    fileWrite(" 님이 퇴장했습니다.", curDate, logFileStream);
                }
            }
        } catch (IOException e) {
            consoleLog(this.nickname + " 님이 채팅방을 나갔습니다.");
        } finally {
            fileWrite(" 님이 퇴장했습니다.", curDate, logFileStream);
            fileClose(logFileStream);
        }
    }

    private void doQuit(PrintWriter writer, String curDate) {
        removeWriter(writer);

        String data = "[" + curDate + "] " + this.nickname + " 님이 퇴장했습니다.";
        broadCast(data);
    }

    private void removeWriter(PrintWriter writer) {
        synchronized (listSocketWirter) {
            listSocketWirter.remove(writer);
        }
    }

    private void doMessage(String data) {
        String message;
        if (data.isEmpty())
            message = "";
        else
            message = this.nickname + " : " + data;

        broadCast(message);
    }

    private void doJoin(String nickname, PrintWriter writer) {
        this.nickname = nickname;

        String data = nickname + " 님이 입장하였습니다.";
        broadCast(data);

        addWriter(writer);
    }

    private void addWriter(PrintWriter writer) {
        synchronized (listSocketWirter) {
            listSocketWirter.add(writer);
        }
    }

    private void broadCast(String data) {
        synchronized (listSocketWirter) {
            for(PrintWriter writer : listSocketWirter) {
                writer.println(data);
                writer.flush();
            }
        }
    }

    private void fileWrite(String data, String curDate, FileOutputStream fileOutputStream) {
        String message = "[" + curDate + "] " + this.nickname + data +"\n";
        synchronized (fileOutputStream){
            try{
                fileOutputStream.write(message.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void fileClose(FileOutputStream fileOutputStream) {
        synchronized (fileOutputStream) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void consoleLog(String log) {
        System.out.println(log);
    }
}
