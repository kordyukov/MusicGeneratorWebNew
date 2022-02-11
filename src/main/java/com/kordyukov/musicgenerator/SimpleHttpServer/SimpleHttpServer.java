package com.kordyukov.musicgenerator.SimpleHttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.kordyukov.musicgenerator.Controller.generatorPage;
import com.kordyukov.musicgenerator.MusicGeneratorConst;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class SimpleHttpServer {


    private static final int PORT = 66;

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