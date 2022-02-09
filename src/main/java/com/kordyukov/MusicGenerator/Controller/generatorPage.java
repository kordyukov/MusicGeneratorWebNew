package com.kordyukov.MusicGenerator.Controller;

import com.kordyukov.MusicGenerator.Instruments.*;
import com.kordyukov.MusicGenerator.Musician;
import com.kordyukov.MusicGenerator.SimpleHttpServer.SimpleHttpServer;
import lombok.Data;
import lombok.SneakyThrows;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private Thread bassTh = new Thread() {
        @SneakyThrows
        @Override
        public void run() {

            File file = new File("src/main/resources/Bass.wav");
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

                File file = new File("src/main/resources/forte.wav");
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
                File file = new File("src/main/resources/pad.wav");
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
                File file = new File("src/main/resources/Kick.wav");
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
                File file = new File("src/main/resources/Snare.wav");
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
                File file = new File("src/main/resources/Hat.wav");
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
                file = new File("src/main/resources/music/MusicGenerator.wav");

                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    System.out.println("File already exists");
                }


                // линию соединения
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                // проверить, поддерживается ли линия
                if (!AudioSystem.isLineSupported(info)) {
                    JOptionPane.showMessageDialog(null, "Line not supported" + info, "Line not supported", JOptionPane.ERROR_MESSAGE);
                }

                try {
                    // получить подходящую линию
                    mike = (TargetDataLine) AudioSystem.getLine(info);
                    DefaultGroovyMethods.println(this, "info.toString()" + info.toString());
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

            }

        };
        private Thread serverStart = new Thread() {
            @Override
            public void run() {
                SimpleHttpServer server = new SimpleHttpServer();
                server.start();
            }

        };

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
                DefaultGroovyMethods.println(this, "pool potoc " + DefaultGroovyMethods.getProperties(pool).toString());
                attemptUser = 0;
            }


            attemptUser = attemptUser++;

            DefaultGroovyMethods.println(this, "attemptUser" + attemptUser);
            return "index";

        }

    }
