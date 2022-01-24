package com.kordyukov.MusicGenerator.Instruments;

import com.kordyukov.MusicGenerator.MusicGeneratorConst;
import lombok.Data;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.*;
import java.io.File;

@Data
public class Kick {

    private int note = 36;
    private int temp;
    private int volume;

    public void playKick(int temp, int volume) {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            channels[MusicGeneratorConst.CHANNEL_KICK].programChange(MusicGeneratorConst.KICK);
            channels[MusicGeneratorConst.CHANNEL_KICK].noteOn(note, volume);
            Thread.sleep(temp); // in milliseconds
            channels[MusicGeneratorConst.CHANNEL_KICK].noteOff(note);
            synth.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void play(File file, int tempo) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat formatIn = audioInputStream.getFormat();
            AudioFormat format = new AudioFormat((float) (formatIn.getSampleRate()*1.0), formatIn.getSampleSizeInBits(), formatIn.getChannels(), true, formatIn.isBigEndian());
            //a = a + 0.01;

            System.out.println(formatIn.toString());
            System.out.println(format.toString());
            byte[] data = new byte[1024];
            DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine)AudioSystem.getLine(dinfo);
            if(line!=null) {
                line.open(format);
                line.start();
                while(true) {
                    int k = audioInputStream.read(data, 0, data.length);
                    if(k<0) break;
                    line.write(data, 0, k);
                }
                Thread.sleep(tempo);
                line.stop();
                line.close();
            }
        }
        catch(Exception ex) { ex.printStackTrace(); }
    }

}


