# Multi Thread Chat
* 멀티스레드 소켓 프로그래밍
* client 별 스레드를 할당
* thread pool 로 최대 접속 인원 제한 가능.
* sentbe.txt 채팅로그

### 개발 환경
* java 1.8.0_111

### 테스트
1. repository clone 및 디렉토리 이동  
    ```$ cd ~ && git clone https://github.com/ssipflow/MultiThreadChat.git && cd ~/MultiThreadChat```
    
2. multi-thread-chat-server 빌드  
    ```$ cd ~/MultiThreadChat/multi-thread-chat-server/src && javac com/chat/main/MultiThreadChatServer.java```

3. multi-thread-chat-server 실행  
    ```$ cd ~/MultiThreadChat/multi-thread-chat-server/src && java com.chat.main.MultiThreadChatServer [MAX_USER]```  
    MAX_USER 는 int형 파라미터

4. multi-thread-client-test 빌드  
    ```$ cd ~/MultiThreadChat/multi-thread-client-test/src && javac com/chat/test/Test.java```

5. multi-thread-client-test 실행  
    ```$ cd ~/MultiThreadChat/multi-thread-client-test/src && java com.chat.test.Test [CLIENT_COUNT] [MESSAGE_COUNT]```  
    CLIENT_COUNT, MESSAGE_COUNT 는 int형 파라미터

6. sentbe.txt 로그파일 확인  
    ```$ cat ~/MultiThreadChat/multi-thread-chat-server/src/sentbe.txt```

### 채팅 시나리오
1. multi-thread-chat-server 실행  
    ```$ cd ~/MultiThreadChat/multi-thread-chat-server/src && java com.chat.main.MultiThreadChatServer [MAX_USER]```  
    MAX_USER 는 int형 파라미터

2. multi-thread-chat-client 빌드  
    ```$ cd ~/MultiThreadChat/multi-thread-chat-client/src && javac com/chat/main/MultiThreadChatClient.java```

3. multi-thread-chat-client 실행  
    ```$ cd ~/MultiThreadChat/multi-thread-chat-client/src && java com.chat.main.MultiThreadChatClient```  
4. USER NAME 설정 후 채팅, 종료 시 quit 명령어 입력.

6. sentbe.txt 로그파일 확인  
    ```$ cat ~/MultiThreadChat/multi-thread-chat-server/src/sentbe.txt```

Hello
