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
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.LineUnavailableException
import javax.sound.sampled.UnsupportedAudioFileException

@Data
class Bass {

    void playBass(int tempo) {
        try
        {
        File soundFile = new File("Bass.wav"); //Звуковой файл

        //Получаем AudioInputStream
        //Вот тут могут полететь IOException и UnsupportedAudioFileException
        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);

        //Получаем реализацию интерфейса Clip
        //Может выкинуть LineUnavailableException
        Clip clip = AudioSystem.getClip();

        //Загружаем наш звуковой поток в Clip
        //Может выкинуть IOException и LineUnavailableException
        clip.open(ais);

        clip.setFramePosition(0); //устанавливаем указатель на старт
        clip.start(); //Поехали!!!
        //Если не запущено других потоков, то стоит подождать, пока клип не закончится
        //В GUI-приложениях следующие 3 строчки не понадобятся
        Thread.sleep(tempo);
        clip.stop(); //Останавливаем
        clip.close(); //Закрываем
            println "bass real tempo :" + tempo

    } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
        exc.printStackTrace();
    } catch (InterruptedException exc) {}
    }

}
