package com.practice;

import java.io.IOException;

import com.practice.was.v2.HttpServerV2;

public class ServerMainV2 {
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        HttpServerV2 server = new HttpServerV2(PORT);
        server.start();
    }
}
