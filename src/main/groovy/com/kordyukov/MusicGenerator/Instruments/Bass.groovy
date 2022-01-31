package com.kordyukov.MusicGenerator.Instruments

import com.kordyukov.MusicGenerator.Instruments.Trigers.Trigers
import com.kordyukov.MusicGenerator.MusicGeneratorConst
import com.kordyukov.MusicGenerator.Musician
import lombok.Data
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.sound.midi.MidiChannel
import javax.sound.midi.MidiSystem
import javax.sound.midi.Synthesizer
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.DataLine
import javax.sound.sampled.FloatControl
import javax.sound.sampled.Line
import javax.sound.sampled.LineUnavailableException
import javax.sound.sampled.Mixer
import javax.sound.sampled.SourceDataLine
import javax.sound.sampled.TargetDataLine
import javax.sound.sampled.UnsupportedAudioFileException
import javax.swing.JOptionPane

@Data
class Bass {
    float a = 1.0;

    void playBass(int tempo) {
        try {
            File soundFile = new File("Bass.wav"); //Звуковой файл

            //Получаем AudioInputStream
            //Вот тут могут полететь IOException и UnsupportedAudioFileException
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);

            //Получаем реализацию интерфейса Clip
            //Может выкинуть LineUnavailableException
            Clip clip = AudioSystem.getClip()


            //Загружаем наш звуковой поток в Clip
            //Может выкинуть IOException и LineUnavailableException
            clip.open(ais);

            clip.setFramePosition(0); //устанавливаем указатель на стар
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN)
            double gain =.5D; // number between 0 and 1 (loudest)
            float dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB)
            clip.start(); //Поехали!!!
            //Если не запущено других потоков, то стоит подождать, пока клип не закончится
            //В GUI-приложениях следующие 3 строчки не понадобятся
            Thread.sleep(tempo);
            clip.stop(); //Останавливаем
            clip.close(); //Закрываем
            println "bass real tempo :" + tempo
            play(soundFile, tempo);

        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();
        } catch (InterruptedException exc) {
        }
    }

    void play(File file, int tempo, float note) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat formatIn = audioInputStream.getFormat();
            AudioFormat format = new AudioFormat(formatIn.getSampleRate() * note as float, formatIn.getSampleSizeInBits(), formatIn.getChannels(), true, formatIn.isBigEndian());
            byte[] data = new byte[1024];
            DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(dinfo);

            if (line != null) {
                line.open(format);
                line.start();


                while (true) {
                    int k = audioInputStream.read(data, 0, data.length);
                    if (k < 0) break;
                    line.write(data, 0, k);

                }
                Thread.sleep(tempo);
                line.stop();
                line.close();


            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void lineMic(AudioFormat format, byte[] data, int k) {
        TargetDataLine mike;
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
            // открываем линию соединения с указанным
            // форматом и размером буфера
            println " mike.toString() " + mike.toString()
            mike.open(mike.format, mike.getBufferSize());
            println "mike.format.toString()" + mike.format.toString()
            mike.addLineListener()
            mike.stop();
            mike.close()
            //mike.close();
            println "ok!!!"
        }catch(Exception e){print(e)}

    }
}