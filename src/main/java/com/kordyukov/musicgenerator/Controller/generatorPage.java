package com.kordyukov.musicgenerator.Controller;

import com.kordyukov.musicgenerator.Instruments.*;
import com.kordyukov.musicgenerator.MusicGeneratorConst;
import com.kordyukov.musicgenerator.Musician;
import com.kordyukov.musicgenerator.SimpleHttpServer.SimpleHttpServer;
import lombok.Data;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@Data
@Controller
public class generatorPage {

    @Autowired
    private Bass bass;
    @Autowired
    private Kick kick;
    @Autowired
    private Piano piano;
    @Autowired
    private Snare snare;
    @Autowired
    private Hat hat;
    @Autowired
    private Forte forte;
    @Autowired
    private Musician musician;
    private int temp = 0;
    private int attemptUser = 0;
    private boolean checkProject;
    public static boolean checkBaseDirTomcat;

    private Thread bassTh = new Thread() {
        @SneakyThrows
        @Override
        public void run() {
            File file;

            file = searchFile(MusicGeneratorConst.pathIdea,"Bass.wav");
            if (!checkProject) file = searchFile(MusicGeneratorConst.pathTomcat,"Bass.wav");
            System.out.println("checkProject  bassTh " + checkProject);

            int temp = 0;
            while (true) {
                temp = musician.tempoTrigerBass();
                bass.play(file, temp, musician.noteTrigerSpeedBass());
                Thread.sleep(temp);
            }
        }
    };

        private Thread forteTh = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                File file;

                file = searchFile(MusicGeneratorConst.pathIdea,"forte.wav");
                if (!checkProject) file = searchFile(MusicGeneratorConst.pathTomcat,"forte.wav");

                int temp = 0;
                while (true) {
                    temp = musician.tempoTrigerForte();
                    forte.play(file, temp, musician.noteTrigerSpeedForte());
                    Thread.sleep(temp);
                }

            }

        };
        private Thread pianoTh = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                File file;

                file = searchFile(MusicGeneratorConst.pathIdea,"pad.wav");
                if (!checkProject) file = searchFile(MusicGeneratorConst.pathTomcat,"pad.wav");

                while (true) {
                    temp = musician.tempoTrigerBass();
                    piano.play(file, temp, musician.noteTrigerSpeedBass());
                    Thread.sleep(temp);
                }


            }

        };
        private Thread kickTh = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                File file;

                file = searchFile(MusicGeneratorConst.pathIdea,"Kick.wav");
                if (!checkProject) file = searchFile(MusicGeneratorConst.pathTomcat,"Kick.wav");

                while (true) {
                    kick.play(file, musician.tempoTrigerKick());
                    Thread.sleep(musician.tempoTrigerKick());
                }

            }

        };
        private Thread snareTh = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                File file;

                file = searchFile(MusicGeneratorConst.pathIdea,"Snare.wav");
                if (!checkProject) file = searchFile(MusicGeneratorConst.pathTomcat,"Snare.wav");

                while (true) {
                    snare.play(file, musician.tempoTrigerSnare());
                    Thread.sleep(musician.tempoTrigerSnare());
                }

            }

        };
        private Thread hatTh = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                File file;

                file = searchFile(MusicGeneratorConst.pathIdea,"Hat.wav");
                if (!checkProject) file = searchFile(MusicGeneratorConst.pathTomcat,"Hat.wav");

                while (true) {
                    hat.play(file, musician.tempoTrigerHat());
                    Thread.sleep(musician.tempoTrigerHat());

                }

            }

        };
        private Thread socketRec = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                // текущий звуковой файл
                File file;
                // аудио формат
                AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
                int MONO = 1;
                // определение формата аудио данных
                AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, MONO, 2, 44100, true);
                // микрофонный вход
                TargetDataLine mike;
                boolean isBaseDirIdea;

                Path path = Paths.get(MusicGeneratorConst.pathTomcat);

                isBaseDirIdea = !Files.exists(path);

                if (isBaseDirIdea) {
                    file = new File(MusicGeneratorConst.baseDirIdea);
                    if (!file.exists()) file.createNewFile();
                    checkBaseDirTomcat = false;
                    System.out.println("isBaseDirIdea " + isBaseDirIdea);
                } else {
                    file = new File(MusicGeneratorConst.baseDirTomcat);
                    if (!file.exists()) file.createNewFile();
                    checkBaseDirTomcat = true;
                    System.out.println("isBaseDirIdea " + isBaseDirIdea);
                }

                System.out.println("checkBaseDirTomcat " + checkBaseDirTomcat);

                // линию соединения
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                // проверить, поддерживается ли линия
                if (!AudioSystem.isLineSupported(info)) {
                    JOptionPane.showMessageDialog(null, "Line not supported" + info, "Line not supported", JOptionPane.ERROR_MESSAGE);
                }

                try {
                    // получить подходящую линию
                    mike = (TargetDataLine) AudioSystem.getLine(info);

                    // открываем линию соединения с указанным
                    // форматом и размером буфера
                    mike.open(format, mike.getBufferSize());
                    // поток микрофона
                    AudioInputStream sound = new AudioInputStream(mike);
                    // запустить линию соединения
                    mike.start();
                    // записать содержимое потока в файл
                    AudioSystem.write(sound, fileType, file);
                } catch (LineUnavailableException ex) {
                    JOptionPane.showMessageDialog(null, "Line not available" + ex, "Line not available", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "I/O Error " + ex, "I/O Error", JOptionPane.ERROR_MESSAGE);
                }
                System.out.println("socketRec ok!");
            }
        };
        private Thread serverStart = new Thread() {
            @Override
            public void run() {
                SimpleHttpServer server = new SimpleHttpServer();
                server.start();
            }

        };

    public File searchFile(String rootFolder, String fileName) {
        File target = null;
        Path root = Paths.get(rootFolder);
        try (Stream<Path> stream = Files.find(root, Integer.MAX_VALUE, (path, attr) ->
                path.getFileName().toString().equals(fileName))) {
            Optional<Path> path = stream.findFirst();
            if(path.isPresent()) {
                target = path.get().toFile();
            }
            checkProject = true;
        }
        catch (IOException e) {
            checkProject = false;
        }

        return target;
    }

        @GetMapping
        public String startPage() {
            ExecutorService pool;
            pool = Executors.newFixedThreadPool(10);

            if (attemptUser == 0) {
                pool.submit(bassTh);
                pool.submit(pianoTh);
                pool.submit(kickTh);
// //       pool.submit(snareTh)
                pool.submit(hatTh);
                //pool.submit(forteTh)
                pool.submit(socketRec);
                pool.submit(serverStart);
            } else {
                pool.shutdown();

                attemptUser = 0;
            }

            attemptUser++;

            return "index";

        }

    }
