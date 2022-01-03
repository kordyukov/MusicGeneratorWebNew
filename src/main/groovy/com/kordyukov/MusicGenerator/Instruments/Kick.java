package com.kordyukov.MusicGenerator.Instruments;

import com.kordyukov.MusicGenerator.MusicGeneratorConst;
import lombok.Data;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;

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
            channels[0].programChange(MusicGeneratorConst.KICK);
            channels[MusicGeneratorConst.CHANNEL_KICK].noteOn(note, volume);
            Thread.sleep(temp); // in milliseconds
            channels[MusicGeneratorConst.CHANNEL_KICK].noteOff(note);
            synth.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


