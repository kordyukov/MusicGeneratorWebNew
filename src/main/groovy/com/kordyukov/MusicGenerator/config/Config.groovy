package com.kordyukov.MusicGenerator.config


import com.kordyukov.MusicGenerator.Instruments.Bass
import com.kordyukov.MusicGenerator.Instruments.Kick
import com.kordyukov.MusicGenerator.Instruments.Piano
import com.kordyukov.MusicGenerator.Instruments.Trigers.Note
import com.kordyukov.MusicGenerator.Instruments.Trigers.Trigers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
@ComponentScan("com.kordyukov.MusicGenerator")
class Config {

    @Bean
    @Scope("prototype")
    public Note note(){
     return new Note()
    }

    @Bean
    public Trigers trigers(){
        return new Trigers()
    }

    @Bean
    public Bass bass(){
        return new Bass()
    }

    @Bean
    public Kick kick(){
        return new Kick()
    }

    @Bean
    public Piano piano(){
        return new Piano()
    }
}
