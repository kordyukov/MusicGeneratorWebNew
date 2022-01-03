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

@Data
class Bass {
    private int note;
    private int temp;
    private int volume;


    void playBass(int note, int temp, int volume) {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            MidiChannel[] channels = synth.getChannels();
            channels[0].programChange(MusicGeneratorConst.BASS);
            channels[MusicGeneratorConst.CHANNEL_BASS].noteOn(note, volume);
            Thread.sleep(temp); // in milliseconds
            channels[MusicGeneratorConst.CHANNEL_BASS].noteOff(note);
            synth.close();
            printf " " + note + " ";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
