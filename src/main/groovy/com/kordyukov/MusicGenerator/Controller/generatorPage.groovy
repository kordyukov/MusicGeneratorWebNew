package com.kordyukov.MusicGenerator.Controller

import com.kordyukov.MusicGenerator.Instruments.Bass
import com.kordyukov.MusicGenerator.Instruments.Hat
import com.kordyukov.MusicGenerator.Instruments.Kick
import com.kordyukov.MusicGenerator.Instruments.Piano
import com.kordyukov.MusicGenerator.Instruments.Snare
import com.kordyukov.MusicGenerator.Musician
import lombok.Data
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.EnableWebMvc

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Data
@Controller
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

    int temp = 0

    Thread bassTh = new Thread(){
        private int tempBass;
        @Override
        void run() {

            File file = new File("Bass.wav")
            int temp = 0
           while (true){
               temp = musician.tempoTrigerBass()
               bass.play(file,temp,musician.noteTrigerSpeedBass())
               Thread.sleep(temp)
           }
        }

    }

    Thread pianoTh = new Thread(){
        @Override
        void run() {
            File file = new File("pad.wav")
            while (true) {
                temp = musician.tempoTrigerBass()
                piano.play(file,temp,musician.noteTrigerSpeedBass())
                Thread.sleep(temp)
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
            File file = new File("Snare.wav")
            while (true){
            snare.play(file,musician.tempoTrigerSnare())
                Thread.sleep(musician.tempoTrigerSnare())
            }
        }
    }

    Thread hatTh = new Thread(){
        @Override
        void run() {
            File file = new File("Hat.wav")
            while (true){
                hat.play(file,musician.tempoTrigerHat())
                Thread.sleep(musician.tempoTrigerHat())

            }
        }
    }

    @GetMapping
     String startPage(){
        ExecutorService pool;
       pool = Executors.newFixedThreadPool(4);
//            pool.submit(bassTh)
//            pool.submit(pianoTh)
//            pool.submit(kickTh)
//// //       pool.submit(snareTh)
//            pool.submit(hatTh)

            return "index"

    }

}
