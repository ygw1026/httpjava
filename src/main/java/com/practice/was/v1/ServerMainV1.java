package com.practice;

import java.io.IOException;

import com.practice.was.v1.HttpServerV1;

public class ServerMainV1 {

    private static final int PORT = 12345;
    public static void main(String[] args) throws IOException {
        HttpServerV1 server = new HttpServerV1(PORT);
        server.start();
    }
}