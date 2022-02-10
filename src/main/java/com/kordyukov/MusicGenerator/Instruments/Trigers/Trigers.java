package com.kordyukov.MusicGenerator.Instruments.Trigers;

import com.kordyukov.MusicGenerator.Musician;
import lombok.Data;


import java.util.stream.IntStream;

import static com.kordyukov.MusicGenerator.Instruments.Trigers.Note.notes;

@Data
public class Trigers implements Musician {
    private Note note;
    private int[] tempBass = IntStream.of(50, 200, 400).toArray();
    private int[] tempForte = IntStream.of(50, 200, 400).toArray();
    private int[] tempKick = IntStream.of(325, 650).toArray();
    private int[] tempPiano = IntStream.of(100, 200, 400, 800, 1600).toArray();
    private int[] tempSnare = IntStream.of(325, 650).toArray();
    private int[] tempHat = IntStream.of(162, 325, 650).toArray();
    float [] noteSpeed = new float[] {0.50f, 0.56f, 0.63f, 0.66f, 0.75f, 0.84f, 0.94f, 1.0f ,1.12f, 1.27f, 1.34f, 1.50f, 1.66f, 1.86f, 1.98f};
    private int tempo;
    private int tempo1;
    private int tempoKick;
    private int tempoSnare;
    private int tempoHat;
    private int tonicNoteCount;
    private float noteRand = noteSpeed[0 + (int) (Math.random() * 14)];

    public int tempoTrigerBass() {
        tempo = tempBass[(0 + (int) (Math.random() * 0))];

        return tempo * 4;

    }

    public int tempoTrigerForte() {
        tempo = tempForte[(0 + (int) (Math.random() * 0))];

        return tempo * 4;

    }

    public int tempoTrigerPiano() {
        tempo = tempPiano[(0 + (int) (Math.random() * 4))];

        return tempo;
    }

    public int tempoTrigerKick() {
        tempoKick = tempKick[(0 + (int) (Math.random() * 1))];

        return tempoKick;
    }

    public int tempoTrigerSnare() {
        tempoSnare = tempSnare[(0 + (int) (Math.random() * 1))];

        return tempoSnare;
    }

    public int tempoTrigerHat() {
        tempoHat = tempHat[(0 + (int) (Math.random() * 2))];

        return tempoHat;
    }

    public int noteTrigerBass() {

        int a = 0 + (int) (Math.random() * 6);
        int b = 0 + (int) (Math.random() * 8);
        int note;
        note = notes[a][b];

        if (note < 12) {
            note += 12;
        } else if (note > 48) {
            note -= 24;
        }


        return note;
    }

    public float noteTrigerSpeedBass() {
        float note,noteF;
        int shans,shans1;
        boolean fort;
        note = noteSpeed[0 + (int) (Math.random() * 14)];
        shans = 0 + (int) (Math.random() * 2);
        shans1  = 0 + (int) (Math.random() * 2);

        fort = shans1 == shans;


        if (!fort) {

            noteRand = noteRand;
            return noteRand;
        } else {
            return note;
        }
        //return noteSpeed[0];
    }

    public float noteTrigerSpeedForte() {

        float note = noteSpeed[0 + (int) (Math.random() * 14)];
        int shans = 0 + (int) (Math.random() * 2);
        int shans1 = 0 + (int) (Math.random() * 2);

        boolean fort = shans1 == shans;


        if (!fort) {

            noteRand = noteRand;
            return noteRand;
        } else {
            return note;
        }

        //return noteSpeed[0];

    }

    public int noteTrigerPiano() {

        int a = 0 + (int) (Math.random() * 6);
        int b = 0 + (int) (Math.random() * 8);
        int note = notes[a][b];
        return note;
    }

}
