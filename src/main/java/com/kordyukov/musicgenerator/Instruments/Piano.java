package com.kordyukov.musicgenerator.Instruments;

import com.kordyukov.musicgenerator.MusicGeneratorConst;
import lombok.Data;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.*;
import java.io.File;

@Data
public class Piano {
    private int note;
    private int temp;
    private int volume;

    public void playPiano(int note, int temp, int volume) {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            channels[0].programChange(MusicGeneratorConst.PIANO);
            channels[MusicGeneratorConst.CHANNEL_PIANO].noteOn(note, volume);
            Thread.sleep(temp); // in milliseconds
            channels[MusicGeneratorConst.CHANNEL_PIANO].noteOff(note);
            synth.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play(File file, int tempo, float note) {

        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat formatIn = audioInputStream.getFormat();
            AudioFormat format = new AudioFormat(formatIn.getSampleRate()*note, formatIn.getSampleSizeInBits(), formatIn.getChannels(), true, formatIn.isBigEndian());
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
