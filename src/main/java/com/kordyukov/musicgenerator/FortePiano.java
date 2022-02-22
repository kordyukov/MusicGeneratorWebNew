package com.kordyukov.musicgenerator;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.*;
import java.io.File;

public class FortePiano {
    static int volume = 80;

    public FortePiano() {
    }

    public void PlayPiano(int rytmi) {
        PianoRythm pPiano = new PianoRythm();

        try {
            Synthesizer synth5 = MidiSystem.getSynthesizer();
            synth5.open();
            MidiChannel[] channels = synth5.getChannels();
            channels[6].programChange(2);


            PianoRythm.notei = pPiano.note[pPiano.a + (int) (Math.random() * (double) (pPiano.bn - pPiano.a + 1))];
            channels[6].noteOn(PianoRythm.notei, volume);
            Thread.sleep(rytmi);
            channels[6].noteOff(PianoRythm.notei);


            pPiano.randomPiano();

        } catch(Exception e)
        {
            System.out.println(e);
        }
    }

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

