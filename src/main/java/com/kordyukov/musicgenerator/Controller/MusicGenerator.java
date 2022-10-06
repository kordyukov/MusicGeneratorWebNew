package com.kordyukov.musicgenerator.Controller;

import com.kordyukov.musicgenerator.FortePiano;
import com.kordyukov.musicgenerator.Instruments.*;
import com.kordyukov.musicgenerator.Instruments.Trigers.Trigers;
import com.kordyukov.musicgenerator.MusicGeneratorConst;
import com.kordyukov.musicgenerator.Musician;
import com.kordyukov.musicgenerator.SimpleHttpServer.SimpleHttpServer;
import lombok.SneakyThrows;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MusicGenerator {
    public static void main(String[] args) {
        Bass bass = new Bass();
        Kick kick = new Kick();
        Piano piano = new Piano();
        Snare snare = new Snare();
        Hat hat = new Hat();
        Forte forte = new Forte();
        Musician musician = new Trigers();
        FortePiano fortePiano = new FortePiano();
        final int[] temp = {0};
        final boolean[] leadPlay = {false};

        Thread tomTh = new Thread(){
            @SneakyThrows
            @Override
            public void run() {
                File file;
                file = new File("Tom.wav");
                int temp;
                while (true){
                    temp = musician.tempoTrigerBass() *4;
                    bass.play(file, temp, musician.noteTrigerSpeedBass());
                    Thread.sleep(temp);
                }
            }
        };

        Thread bassTh = new Thread() {
            @Override
            public void run() {
                File file;

                file = new File("Bass.wav");

                int temp = 0;
                float note;
                while (true) {

                    temp = musician.tempoTrigerBass();
                    note = musician.noteTrigerSpeedBass();
                    bass.play(file, temp, note);
                    try {
                        Thread.sleep(temp);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("play bass");


                }
            }
        };

        Thread forteTh = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                File file, file1;

                file = new File("forte.wav");

                int temp = 0;
                while (true) {
                    temp = musician.tempoTrigerForte();
                    forte.play(file, temp,  musician.noteTrigerSpeedForte());
                    forte.play(file, temp, musician.noteTrigerSpeedForte());

                    Thread.sleep(temp);
                }

            }

        };
        Thread pianoTh = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                File file;

                file = new File("pad.wav");

                while (true) {

                    temp[0] = musician.tempoTrigerBass();
                    piano.play(file, temp[0], leadPlay[0] ? musician.noteTrigerSpeedBass() : musician.noteTrigerSpeedBass()*2);
                    //piano.play(file, temp, musician.noteTrigerSpeedBass());
                    Thread.sleep(temp[0]);
                    leadPlay[0] = true;
                }
            }

        };
        Thread kickTh = new Thread() {
            @Override
            public void run() {
                File file,file1,file2;
                file1 = new File("Bass.wav");

                file = new File("Kick.wav");

                file2 = new File("Bass1.wav");

                int temp;
                float note;
                boolean kickPlay = false;

                while (true) {

                    if (kickPlay) {
                        kick.play(file, musician.tempoTrigerKick());
                        try {
                            Thread.sleep(musician.tempoTrigerKick());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        kickPlay = false;
                    }else {
                        temp = musician.tempoTrigerBass();
                        note = musician.noteTrigerSpeedBass();
                        bass.play(file1, temp, note);
                        //bass.play(file2, temp, note);
                        try {
                            Thread.sleep(temp);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        kickPlay = true;
                    }

                }

            }

        };
        Thread snareTh = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                File file;

                file = new File("Snare.wav");

                int temp = 0;
                while (true) {
                    temp = musician.tempoTrigerSnare();
                    snare.play(file, temp);
                    Thread.sleep(temp);
                }

            }

        };

         Thread clapTh = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                File file;

                file = new File("clap.wav");

                while (true) {
                    snare.play(file, musician.tempoTrigerSnare());
                    Thread.sleep(musician.tempoTrigerSnare());
                }

            }

        };

        Thread hatTh = new Thread() {
            @SneakyThrows
            @Override
            public void run() {
                File file;

                file = new File("Hat.wav");

                while (true) {
                    hat.play(file, musician.tempoTrigerHat());
                    Thread.sleep(musician.tempoTrigerHat());

                }

            }

        };

        Thread Lead = new Thread(){
            @SneakyThrows
            @Override
            public void run() {
                File file;

                file = new File("lead.wav");

                while (true) {
                    temp[0] = musician.tempoTrigerBass();
                    piano.play(file, temp[0], musician.noteTrigerSpeedBass());
                    //Thread.sleep(temp[0] *2);
                }
            }
        };



         Thread Hats = new Thread() {

            @SneakyThrows
            @Override
            public void run() {
                File file;

                file = new File("hats.wav");

                FortePiano hats = new FortePiano();

                while (true) {
                    temp[0] = musician.tempoTrigerFortePiano() * 2;
                    hats.play(file, temp[0], musician.noteTrigerFortePiano());
                    Thread.sleep(temp[0]);
                }

            }
        };
        Thread socketRec = new Thread() {
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

                    file = new File(MusicGeneratorConst.baseDirTomcat);
                    if (!file.exists()) file.createNewFile();

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
        Thread serverStart = new Thread() {

            @Override
            public void run() {
                SimpleHttpServer server = new SimpleHttpServer();
                server.start();
            }

        };

    Thread pianoP = new Thread(){
        @SneakyThrows
        @Override
        public void run() {
            while (true){
                fortePiano.PlayPiano(1300);
                Thread.sleep(1300);
            }
        }
    };

        ExecutorService pool;
        pool = Executors.newFixedThreadPool(50);
            //pool.submit(socketRec);
            //pool.submit(serverStart);
            //bassTh.start();
            kickTh.start();
            pool.submit(pianoTh);
//            pool.submit(pianoTh);
//            pool.submit(pianoTh);
//            pool.submit(pianoTh);
            pool.submit(hatTh);
            pool.submit(Hats);
            pool.submit(Hats);
           pool.submit(forteTh);
//            pool.submit(forteTh);
            pool.submit(clapTh);
            pool.submit(Lead);
            pool.submit(Lead);
            pool.submit(Lead);
            pool.submit(Lead);
            pool.submit(Lead);
            pool.submit(Lead);
            pool.submit(Lead);
            pool.submit(Lead);
            pool.submit(pianoP);
            pool.submit(pianoP);
            pool.submit(pianoP);
            pool.submit(pianoP);
            pool.submit(pianoP);
            pool.submit(tomTh);
            pool.submit(tomTh);

    }
}
