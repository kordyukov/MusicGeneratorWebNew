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
    public int k;
    public byte[] data = new byte[1024];

    void play(File file, int tempo, float note) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat formatIn = audioInputStream.getFormat();
            AudioFormat format = new AudioFormat(formatIn.getSampleRate() * note as float, formatIn.getSampleSizeInBits(), formatIn.getChannels(), true, formatIn.isBigEndian());

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
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}