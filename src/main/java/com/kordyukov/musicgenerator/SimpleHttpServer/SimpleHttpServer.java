package com.kordyukov.musicgenerator.SimpleHttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.kordyukov.musicgenerator.Controller.generatorPage;
import com.kordyukov.musicgenerator.MusicGeneratorConst;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class SimpleHttpServer {


    private static final int PORT = 66;

    private HttpServer server;
    public boolean isExeption;

    public void start()  {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            InetSocketAddress socketAddress = new InetSocketAddress(addr, PORT);
            server = HttpServer.create(socketAddress, 3);
            boolean isBaseDirIdea;

            Path path = Paths.get(MusicGeneratorConst.pathTomcat);

            isBaseDirIdea = !Files.exists(path);

            if(isBaseDirIdea){
                server.createContext("/", new StaticFileHandler(MusicGeneratorConst.baseDirIdea ));
                System.out.println("SimpleHttpServer baseDirIdea");
            } else {
                server.createContext("/", new StaticFileHandler(MusicGeneratorConst.baseDirTomcat ));
                System.out.println("SimpleHttpServer baseDirTomcat");
            }
            server.start();
            System.out.println("server.start()");
            isExeption = false;
        }catch (IOException e){
            isExeption = true;
            System.out.println(e);
        }
    }

    public void stop() {
        server.stop(0);
    }
}