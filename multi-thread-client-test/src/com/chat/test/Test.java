package com.chat.test;

import com.chat.thread.MultiThreadChatClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        if(args.length < 2) {
            System.out.println("$ java com.chat.test.Test [USER_COUNT] [MESSAGE_COUNT]");
            System.exit(1);
        }

        Integer USER_COUNT = Integer.parseInt(args[0]);
        Integer MESSAGE_COUNT = Integer.parseInt(args[1]);

        ExecutorService executorService = Executors.newFixedThreadPool(USER_COUNT);

        for(int i = 0; i < USER_COUNT; i++) {
            MultiThreadChatClient multiThreadChatClient = new MultiThreadChatClient(i, MESSAGE_COUNT);
            executorService.execute(multiThreadChatClient);
        }

        executorService.shutdown();
    }
}
