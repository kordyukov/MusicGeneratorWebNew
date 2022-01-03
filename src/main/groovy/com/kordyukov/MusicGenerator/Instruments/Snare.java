package com.kordyukov.MusicGenerator.Instruments;

import com.kordyukov.MusicGenerator.MusicGeneratorConst;
import lombok.Data;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

@Data
public class Snare {
    private int note = 38;
    private int temp;
    private int volume;

    public void playSnare(int temp, int volume) {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            channels[MusicGeneratorConst.CHANNEL_SNARE].programChange(MusicGeneratorConst.SNARE);
            channels[MusicGeneratorConst.CHANNEL_SNARE].noteOn(note, volume);
            Thread.sleep(temp); // in milliseconds
            channels[MusicGeneratorConst.CHANNEL_SNARE].noteOff(note);
            synth.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
