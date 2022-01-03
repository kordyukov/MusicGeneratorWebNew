package com.kordyukov.MusicGenerator.Instruments;

import com.kordyukov.MusicGenerator.MusicGeneratorConst;
import lombok.Data;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

@Data
public class Hat {
    private int note = 42;
    private int temp;
    private int volume;

    public void playHat(int temp, int volume) {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            channels[MusicGeneratorConst.CHANNEL_HAT].programChange(MusicGeneratorConst.HAT);
            channels[MusicGeneratorConst.CHANNEL_HAT].noteOn(note, volume);
            Thread.sleep(temp); // in milliseconds
            channels[MusicGeneratorConst.CHANNEL_HAT].noteOff(note);
            synth.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
