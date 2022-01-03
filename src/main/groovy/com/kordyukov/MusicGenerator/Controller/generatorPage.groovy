package com.kordyukov.MusicGenerator.Controller

import com.kordyukov.MusicGenerator.Instruments.Bass
import com.kordyukov.MusicGenerator.Instruments.Kick
import com.kordyukov.MusicGenerator.Instruments.Piano
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
    Musician musician

    Thread bassTh = new Thread(){
        @Override
        void run() {
            while (true) {
                bass.playBass(musician.noteTrigerBass(),musician.tempoTrigerBass(),100)
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
            while (true) {
                kick.playKick(musician.tempoTrigerKick(),100)
            }
        }

    }

    @GetMapping
    public String startPage(){
        ExecutorService pool = Executors.newFixedThreadPool(3);
         pool.submit(bassTh)
        //pool.submit(pianoTh)
        pool.submit(kickTh)
        return "Hello on MusicGenerator page by Kordyukov!"
    }


}
