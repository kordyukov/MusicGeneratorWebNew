package com.kordyukov.MusicGenerator.SimpleHttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class SimpleHttpServer {


    private static final int PORT = 9999;

    private HttpServer server;


    public void start()  {
        try {
            HttpHandler httpHandler = new StaticFileHandler("src/main/resources/templates/MusicGenerator.wav");
            InetAddress addr = InetAddress.getLocalHost();
            InetSocketAddress socketAddress = new InetSocketAddress(addr, PORT);
            //System.out.println("socketAddress.toString()" + socketAddress.toString());
            server = HttpServer.create(socketAddress, 3);

            server.createContext("/", new StaticFileHandler("src/main/resources/templates/MusicGenerator.wav"));

            //System.out.println(" httpHandler " + httpHandler);
            server.start();
            System.out.println("server.start()");
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public void stop() {
        server.stop(0);
    }
}