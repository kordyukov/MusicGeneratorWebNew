package com.kordyukov.MusicGenerator.Controller

import com.kordyukov.MusicGenerator.Instruments.Bass
import com.kordyukov.MusicGenerator.Instruments.Forte
import com.kordyukov.MusicGenerator.Instruments.Hat
import com.kordyukov.MusicGenerator.Instruments.Kick
import com.kordyukov.MusicGenerator.Instruments.Piano
import com.kordyukov.MusicGenerator.Instruments.Snare
import com.kordyukov.MusicGenerator.Musician
import com.kordyukov.MusicGenerator.SimpleHttpServer.SimpleHttpServer
import lombok.Data
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

import javax.lang.model.element.Element
import javax.sound.sampled.AudioFileFormat
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.LineUnavailableException
import javax.sound.sampled.TargetDataLine
import javax.swing.JOptionPane
import javax.swing.text.Document
import java.applet.AudioClip
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Data
@Controller
class generatorPage {
    @Autowired
    Bass bass
    @Autowired
    Kick kick
    @Autowired
    Piano piano
    @Autowired
    Snare snare
    @Autowired
    Hat hat
    @Autowired
    Forte forte

    @Autowired
    Musician musician

    int temp = 0
    int attemptUser = 0;


    Thread bassTh = new Thread() {
        @Override
        void run() {

            File file = new File("src/main/resources/Bass.wav")
            int temp = 0
            while (true) {
                temp = musician.tempoTrigerBass()
                bass.play(file, temp, musician.noteTrigerSpeedBass())
                Thread.sleep(temp)
            }
        }

    }

    Thread forteTh = new Thread() {
        @Override
        void run() {

            File file = new File("src/main/resources/forte.wav")
            int temp = 0
            while (true) {
                temp = musician.tempoTrigerForte()
                forte.play(file, temp, musician.noteTrigerSpeedForte())
                Thread.sleep(temp)
            }
        }

    }

    Thread pianoTh = new Thread() {
        @Override
        void run() {
            File file = new File("src/main/resources/pad.wav")
            while (true) {
                temp = musician.tempoTrigerBass()
                piano.play(file, temp, musician.noteTrigerSpeedBass())
                Thread.sleep(temp)
            }

        }
    }

    Thread kickTh = new Thread() {
        @Override
        void run() {
            File file = new File("src/main/resources/Kick.wav")
            while (true) {
                kick.play(file, musician.tempoTrigerKick())
                Thread.sleep(musician.tempoTrigerKick())
            }
        }

    }

    Thread snareTh = new Thread() {
        @Override
        void run() {
            File file = new File("src/main/resources/Snare.wav")
            while (true) {
                snare.play(file, musician.tempoTrigerSnare())
                Thread.sleep(musician.tempoTrigerSnare())
            }
        }
    }

    Thread hatTh = new Thread() {
        @Override
        void run() {
            File file = new File("src/main/resources/Hat.wav")
            while (true) {
                hat.play(file, musician.tempoTrigerHat())
                Thread.sleep(musician.tempoTrigerHat())

            }
        }
    }

    Thread socketRec = new Thread() {

        @Override
        void run() {
            // текущий звуковой файл
            File file;
            // аудио формат
            AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
            int MONO = 1;
            // определение формата аудио данных
            AudioFormat format = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED, 44100, 16, MONO, 2, 44100, true);
            // микрофонный вход
            TargetDataLine mike;
            file = new File("src/main/resources/music/MusicGenerator.wav")

            if(!file.exists()){
                file.createNewFile();
            }else{
                System.out.println("File already exists");
            }

            // линию соединения
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            // проверить, поддерживается ли линия
            if (!AudioSystem.isLineSupported(info)) {
                JOptionPane.showMessageDialog(null, "Line not supported" +
                        info, "Line not supported",
                        JOptionPane.ERROR_MESSAGE);
            }
            try {
                // получить подходящую линию
                mike = (TargetDataLine) AudioSystem.getLine(info);
                println "info.toString()" + info.toString()
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
                JOptionPane.showMessageDialog(null, "Line not available" +
                        ex, "Line not available",
                        JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "I/O Error " + ex,
                        "I/O Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

        Thread serverStart = new Thread(){
            @Override
            void run() {
                SimpleHttpServer server = new SimpleHttpServer();
                server.start();
            }
        }

        @GetMapping
        String startPage() {
            ExecutorService pool;
            pool = Executors.newFixedThreadPool(10);

            if (attemptUser == 0) {
                pool.submit(bassTh)
                pool.submit(pianoTh)
                pool.submit(kickTh)
// //       pool.submit(snareTh)
                pool.submit(hatTh)
                //pool.submit(forteTh)
                pool.submit(socketRec)
                pool.submit(serverStart)
            } else {
                pool.shutdown()
                println "pool potoc " + pool.properties.toString()
                attemptUser = 0
            }

            attemptUser++

            println "attemptUser" + attemptUser
            return "index"

        }

}