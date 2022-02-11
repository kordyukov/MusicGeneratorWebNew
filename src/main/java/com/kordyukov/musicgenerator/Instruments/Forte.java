package com.kordyukov.musicgenerator.Instruments;



import javax.sound.sampled.*;
import java.io.File;

public class Forte {
    public void play(File file, int tempo, float note) {
        float a = 1.0F;
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat formatIn = audioInputStream.getFormat();
            AudioFormat format = new AudioFormat(formatIn.getSampleRate() * note, formatIn.getSampleSizeInBits(), formatIn.getChannels(), true, formatIn.isBigEndian());

            byte[] data = new byte[1024];
            DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(dinfo);
            //SourceDataLine line1 = (SourceDataLine) AudioSystem.getLine();
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
                // println line1.toString()


            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
