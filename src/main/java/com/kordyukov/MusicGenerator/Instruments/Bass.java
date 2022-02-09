package com.kordyukov.MusicGenerator.Instruments;

import lombok.Data;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

import javax.sound.sampled.*;
import java.io.File;

@Data
public class Bass {
    public void play(File file, int tempo, float note) {

        try {
            float a = 1.0f;
            int k;
            byte[] data;
            data = new byte[1024];
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat formatIn = audioInputStream.getFormat();
            AudioFormat format = new AudioFormat(formatIn.getSampleRate() * note, formatIn.getSampleSizeInBits(), formatIn.getChannels(), true, formatIn.isBigEndian());

            DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(dinfo);

            if (line != null) {
                line.open(format);
                line.start();

                while (true) {
                    k = audioInputStream.read(data, 0, data.length);
                    if (k < 0) break;
                    line.write(data, 0, k);

                }

                Thread.sleep(tempo);
                line.stop();
                line.close();


            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
