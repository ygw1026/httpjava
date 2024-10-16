package com.practice.was.v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpRequestHandlerV2 implements Runnable{
    private final Socket socket;

    public HttpRequestHandlerV2(Socket socket){
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            process(socket);
        }catch (Exception e){
            throw new IllegalArgumentException();
        }
    }

    private void process(Socket socket) throws IOException {
        try (socket;
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), false, StandardCharsets.UTF_8)) {

                String requestString = requestToString(reader);
            if(requestString.contains("/favicon.ico")) {
                log.debug("favicon 요청");
                return;
            }
            
            log.debug("HTTP 요청 정보 출력");
            System.out.println(requestString);

            log.debug("HTTP 응답 생성중...");
            sleep(5000);
            responseToClient(writer);

            log.debug("HTTP 응답 전달 완료");
        }    
    }

    private static String requestToString(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if(line.isEmpty()){
                    break;
                }
                sb.append(line).append("\n");
            }
            return sb.toString();
    }

    private void responseToClient(PrintWriter writer){
        String body = "<h1>Hello World</h1>";
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append("Content-Type: text/html\r\n");
        sb.append("Content-Length: ").append(length).append("\r\n");
        sb.append("\r\n");
        sb.append(body);

        log.debug("HTTP 응답 정보 출력");
        System.out.println(sb);

        writer.println(sb);
        writer.flush();
    }


    private static void sleep(int millis){
        try {
            Thread.sleep(millis);
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
