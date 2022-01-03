package com.kordyukov.MusicGenerator.Instruments;

import com.kordyukov.MusicGenerator.MusicGeneratorConst;
import lombok.Data;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

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
}
