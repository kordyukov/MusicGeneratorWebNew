package com.kordyukov.musicgenerator.Controller;

import com.kordyukov.musicgenerator.FortePiano;
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
    @Autowired
    FortePiano fortePiano;

    private int temp = 0;
    private int attemptUser = 0;
    private boolean checkProject;
    public static boolean checkBaseDirTomcat;
    private boolean leadPlay;
    private boolean kickPlay;


    Thread tomTh = new Thread(){
        @SneakyThrows
        @Override
        public void run() {
            File file;
            file = searchFile(MusicGeneratorConst.pathIdea,"Tom.wav");
            int temp;
            if (!checkProject) file = searchFile(MusicGeneratorConst.pathTomcat,"Tom.wav");
            while (true){
                temp = musician.tempoTrigerBass() *4;
                bass.play(file, temp, musician.noteTrigerSpeedBass());
                Thread.sleep(temp);
            }
        }
    };

    private Thread bassTh = new Thread() {
        @SneakyThrows
        @Override
        public void run() {
            File file,file1;


            file = searchFile(MusicGeneratorConst.pathIdea,"Bass.wav");
            if (!checkProject) file = searchFile(MusicGeneratorConst.pathTomcat,"Bass.wav");

            int temp = 0;
            float note;
            while (true) {
                if(!kickPlay) {
                    temp = musician.tempoTrigerBass();
                    note = musician.noteTrigerSpeedBass();
                    bass.play(file, temp, note);
                    Thread.sleep(temp);
                    leadPlay = true;
                    kickPlay = true;
                }
            }
        }
    };

        private Thread forteTh = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                File file,file1;

                file = searchFile(MusicGeneratorConst.pathIdea,"forte.wav");
                if (!checkProject) file = searchFile(MusicGeneratorConst.pathTomcat,"forte.wav");
                file1 = searchFile(MusicGeneratorConst.pathIdea,"forte1.wav");
                if (!checkProject) file1 = searchFile(MusicGeneratorConst.pathTomcat,"forte1.wav");

                int temp = 0;
                while (true) {
                    temp = musician.tempoTrigerForte();
                    forte.play(file, temp, musician.noteTrigerSpeedForte());
                    forte.play(file, temp, musician.noteTrigerSpeedForte());
                    //forte.play(file1, temp, musician.noteTrigerSpeedForte());
                   // forte.play(file, temp, musician.noteTrigerSpeedForte());
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
                    piano.play(file, temp, leadPlay ? musician.noteTrigerSpeedBass() : musician.noteTrigerSpeedBass()*2);
                    //piano.play(file, temp, musician.noteTrigerSpeedBass());
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
                    if (kickPlay) {
                        kick.play(file, musician.tempoTrigerKick());
                        Thread.sleep(musician.tempoTrigerKick());
                        kickPlay = false;
                    }
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
                    snare.play(file, musician.tempoTrigerKick());
                    Thread.sleep(musician.tempoTrigerKick());
                }

            }

        };

    private Thread clapTh = new Thread() {
        @SneakyThrows
        @Override
        public void run() {
            File file;

            file = searchFile(MusicGeneratorConst.pathIdea,"clap.wav");
            if (!checkProject) file = searchFile(MusicGeneratorConst.pathTomcat,"clap.wav");

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
    private Thread Lead = new Thread(){
        @SneakyThrows
        @Override
        public void run() {

//                while (true) {
//                    temp = musician.tempoTrigerBass();
//                    fortePiano.PlayPiano(temp);
//                    Thread.sleep(temp);
//                }
            File file;

            file = searchFile(MusicGeneratorConst.pathIdea,"lead.wav");
            if (!checkProject) file = searchFile(MusicGeneratorConst.pathTomcat,"lead.wav");

            while (true) {
                temp = musician.tempoTrigerBass();
                piano.play(file, temp, musician.noteTrigerSpeedBass());
                piano.play(file, temp, musician.noteTrigerSpeedBass());
                Thread.sleep(temp*2);
            }
        }
    };



        private Thread Hats = new Thread() {

            @SneakyThrows
            @Override
            public void run() {
                File file;

                file = searchFile(MusicGeneratorConst.pathIdea, "hats.wav");
                if (!checkProject) file = searchFile(MusicGeneratorConst.pathTomcat,"hats.wav");

                FortePiano hats = new FortePiano();

                while (true) {
                    temp = musician.tempoTrigerFortePiano() * 2;
                    hats.play(file, temp, musician.noteTrigerFortePiano());
                    Thread.sleep(temp);
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

//    Thread pianoP = new Thread(){
//        @SneakyThrows
//        @Override
//        public void run() {
//            while (true){
//                fortePiano.PlayPiano(1300);
//                Thread.sleep(1300);
//            }
//        }
//    };

        @GetMapping
        public String startPage() {
            ExecutorService pool;
            pool = Executors.newFixedThreadPool(20);

            if (attemptUser == 0) {
                //pool.submit(socketRec);
                //В разработке pool.submit(serverStart);
                pool.submit(bassTh);
                pool.submit(pianoTh);
                pool.submit(pianoTh);
                pool.submit(pianoTh);
                pool.submit(pianoTh);
                pool.submit(kickTh);
                pool.submit(hatTh);
                pool.submit(Hats);
                pool.submit(forteTh);
                pool.submit(forteTh);
                pool.submit(forteTh);
                pool.submit(snareTh);
                pool.submit(clapTh);
                //pool.submit(Lead);
               // pool.submit(pianoP);
                pool.submit(tomTh);

            } else {
                pool.shutdown();

                attemptUser = 0;
            }

            attemptUser++;

            return "index";

        }

    }
