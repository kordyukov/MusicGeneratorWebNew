package com.kordyukov.MusicGenerator.Controller

import com.kordyukov.MusicGenerator.Instruments.Bass
import com.kordyukov.MusicGenerator.Instruments.Hat
import com.kordyukov.MusicGenerator.Instruments.Kick
import com.kordyukov.MusicGenerator.Instruments.Piano
import com.kordyukov.MusicGenerator.Instruments.Snare
import com.kordyukov.MusicGenerator.Musician
import lombok.Data
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Data
@RequestMapping("MusicGenerator")
@RestController
class generatorPage  {
    @Autowired
    Bass bass
    @Autowired
    Kick kick
    @Autowired
    Piano piano
    @Autowired
    Snare snare
    @Autowired
    Hat hat

    @Autowired
    Musician musician



    Thread bassTh = new Thread(){
        private int tempBass;
        @Override
        void run() {
            File file = new File("Bass.wav")
           while (true){
               bass.play(file,musician.tempoTrigerBass(),musician.noteTrigerSpeedBass())
               Thread.sleep(musician.tempoTrigerBass())
           }
        }

    }

    Thread pianoTh = new Thread(){
        @Override
        void run() {
            while (true) {
                piano.playPiano(musician.noteTrigerPiano(),musician.tempoTrigerPiano(),100)
            }

        }
    }

    Thread kickTh = new Thread(){
        @Override
        void run() {
            File file = new File("Kick.wav")
            while (true) {
                kick.play(file,musician.tempoTrigerKick())
                Thread.sleep(musician.tempoTrigerKick())
            }
        }

    }

    Thread snareTh = new Thread(){
        @Override
        void run() {
            while (true){
          snare.playSnare(musician.tempoTrigerSnare(),100)
            }
        }
    }

    Thread hatTh = new Thread(){
        @Override
        void run() {
            while (true){
                hat.playHat(musician.tempoTrigerHat(),100)
            }
        }
    }

    @GetMapping
    public String startPage(){
        ExecutorService pool = Executors.newFixedThreadPool(4);
        pool.submit(bassTh)
//        //pool.submit(pianoTh)
        pool.submit(kickTh)
        pool.submit(snareTh)
        pool.submit(hatTh)
        return "Hello on MusicGenerator page by Kordyukov!"
    }


}
