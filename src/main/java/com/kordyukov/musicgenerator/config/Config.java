package com.kordyukov.musicgenerator.config;

import com.kordyukov.musicgenerator.FortePiano;
import com.kordyukov.musicgenerator.Instruments.*;
import com.kordyukov.musicgenerator.Instruments.Trigers.Note;
import com.kordyukov.musicgenerator.Instruments.Trigers.Trigers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.kordyukov.musicgenerator")
public class Config {
    @Bean
    @Scope("prototype")
    public Note note() {
        return new Note();
    }

    @Bean
    public Trigers trigers() {
        return new Trigers();
    }

    @Bean
    public Bass bass() {
        return new Bass();
    }

    @Bean
    public Kick kick() {
        return new Kick();
    }

    @Bean
    public Piano piano() {
        return new Piano();
    }

    @Bean
    public Snare snare() {
        return new Snare();
    }

    @Bean
    public Hat hat() {
        return new Hat();
    }

    @Bean
    public Forte forte() {
        return new Forte();
    }
    @Bean
    public FortePiano fortePiano(){ return new FortePiano();}


}
