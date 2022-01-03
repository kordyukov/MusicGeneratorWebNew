package com.kordyukov.MusicGenerator.Instruments.Trigers

import com.kordyukov.MusicGenerator.Musician
import lombok.Data
import org.springframework.beans.factory.annotation.Autowired

import java.util.stream.IntStream

@Data
class Trigers implements Musician {
    @Autowired
    Note note

    int [] tempBass = IntStream.of(200, 400, 800).toArray()
    int [] tempKick = IntStream.of(200,400, 800).toArray()
    int [] tempPiano = IntStream.of(100, 200,400,800,1600).toArray()
    int [] tempSnare = IntStream.of( 400,800).toArray()
    int [] tempHat = IntStream.of( 100,200,400).toArray()

    int tempo,tempo1;
    int tempoKick;
    int tempoSnare;
    int tempoHat;
    int tonicNoteCount;


    int tempoTrigerBass(){
        tempo = tempBass[(0 + (int) (Math.random() * 2))];
        tempo1 = tempBass[(0 + (int) (Math.random() * 2))];
        if(tempo == tempo1) {
            printf "\ntemp bass " + tempo
            return tempo
        } else {
            return tempo1
        }

    }

    int tempoTrigerPiano(){
        tempo = tempPiano[(0 + (int) (Math.random() * 4))];
        printf "\ntemp piano " + tempo
            return tempo
    }

    int tempoTrigerKick(){
        tempoKick = tempKick[(0 + (int) (Math.random() * 2))];
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

    int noteTrigerPiano(){

        int a = 0 + (int) (Math.random() * 6);
        int b = 0 + (int) (Math.random() * 8);
        int note = note.notes[a][b]
        return note
    }
}
