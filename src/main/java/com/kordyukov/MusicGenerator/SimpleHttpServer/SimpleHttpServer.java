package com.kordyukov.MusicGenerator.SimpleHttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.kordyukov.MusicGenerator.Controller.generatorPage;
import com.kordyukov.MusicGenerator.MusicGeneratorConst;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("restriction")
public class SimpleHttpServer {


    private static final int PORT = 9999;

    private HttpServer server;

    public void start()  {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            InetSocketAddress socketAddress = new InetSocketAddress(addr, PORT);
            server = HttpServer.create(socketAddress, 3);

            server.createContext("/", new StaticFileHandler(generatorPage.checkBaseDirTomcat == true ? MusicGeneratorConst.baseDirIdea : MusicGeneratorConst.baseDirIdea));

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