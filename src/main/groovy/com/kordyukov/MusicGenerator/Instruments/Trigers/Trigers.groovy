package com.kordyukov.MusicGenerator.Instruments.Trigers

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers
import com.kordyukov.MusicGenerator.Musician
import lombok.Data
import org.springframework.beans.factory.annotation.Autowired

import java.util.stream.IntStream

@Data
class Trigers implements Musician {
    @Autowired
    Note note

    int [] tempBass = IntStream.of(50,200,400).toArray()
    int [] tempForte = IntStream.of(50,200,400).toArray()
    int [] tempKick = IntStream.of(325, 650).toArray()
    int [] tempPiano = IntStream.of(100, 200,400,800,1600).toArray()
    int [] tempSnare = IntStream.of( 325, 650).toArray()
    int [] tempHat = IntStream.of( 162, 325, 650).toArray()
    float [] noteSpeed = new float[] {0.50, 0.56, 0.63, 0.66, 0.75, 0.84, 0.94, 1.0 ,1.12, 1.27, 1.34, 1.50, 1.66, 1.86, 1.98}

    int tempo,tempo1;
    int tempoKick;
    int tempoSnare;
    int tempoHat;
    int tonicNoteCount;
    float noteRand = noteSpeed[0 + (int) (Math.random() * 14)]


    int tempoTrigerBass(){
        tempo = tempBass[(0 + (int) (Math.random() * 0))];
        println "temb bass " + tempo
            return tempo * 4

    }
    int tempoTrigerForte(){
        tempo = tempForte[(0 + (int) (Math.random() * 0))];
        println "temb forte " + tempo
        return tempo * 4

    }

    int tempoTrigerPiano(){
        tempo = tempPiano[(0 + (int) (Math.random() * 4))];
        printf "\ntemp piano " + tempo
            return tempo
    }

    int tempoTrigerKick(){
        tempoKick = tempKick[(0 + (int) (Math.random() * 1))];
        printf "\ntemp kick " + tempoKick
            return tempoKick
    }

    int tempoTrigerSnare(){
        tempoSnare = tempSnare[(0 + (int) (Math.random() * 1))];
        printf "\ntemp snare " + tempoSnare
        return tempoSnare
    }

    int tempoTrigerHat(){
        tempoHat = tempHat[(0 + (int) (Math.random() * 2))];
        printf "\ntemp Hat " + tempoHat
        return tempoHat
    }

    int noteTrigerBass(){

        int a = 0 + (int) (Math.random() * 6);
        int b = 0 + (int) (Math.random() * 8);
        int note = note.notes[a][b]

        if(note < 12){
            note += 12
        } else if (note > 48)
           {
            note -= 24
           }

        return note
    }

    float noteTrigerSpeedBass(){

        float note = noteSpeed[0 + (int) (Math.random() * 14)]
        int shans = 0 + (int) (Math.random() * 2)
        int shans1 = 0 + (int) (Math.random() * 2)

        boolean fort = shans1 == shans
        println fort

        if (!fort) {
            println "do return " + noteRand
            noteRand = noteRand
            return noteRand
        } else {
            return note
        }
        return noteSpeed[0]

    }

    float noteTrigerSpeedForte(){

        float note = noteSpeed[0 + (int) (Math.random() * 14)]
        int shans = 0 + (int) (Math.random() * 2)
        int shans1 = 0 + (int) (Math.random() * 2)

        boolean fort = shans1 == shans
        println fort

        if (!fort) {
            println "do return " + noteRand
            noteRand = noteRand
            return noteRand
        } else {
            return note
        }
        return noteSpeed[0]

    }



    int noteTrigerPiano(){

        int a = 0 + (int) (Math.random() * 6);
        int b = 0 + (int) (Math.random() * 8);
        int note = note.notes[a][b]
        return note
    }
}
