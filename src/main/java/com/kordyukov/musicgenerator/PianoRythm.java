package com.kordyukov.musicgenerator;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class PianoRythm {
    int a = 0;
    int b = 5;
    int bn = 27;
    int[] udar = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
    int[] rythm = new int[]{2000, 1000, 500, 250, 125, 62};
    int rytmi;
    int udari;
    int[] note;
    static int notei;

    public PianoRythm() {
        this.rytmi = this.rythm[this.a + (int)(Math.random() * (double)(this.b - this.a + 1))];
        this.note = new int[]{60, 62, 64, 65, 67, 69, 71, 72, 74, 76, 77, 79, 81, 83, 84, 86, 88, 89, 91, 93, 95, 96, 98, 100, 101, 103, 105, 107};
    }

    public void randomPiano() {
        notei = this.note[this.a + (int)(Math.random() * (double)(this.bn - this.a + 1))];
        int noten = notei;
        int notens = this.a + (int)(Math.random() * (double)(this.bn - this.a + 1));
        this.rytmi = this.rythm[this.a + (int)(Math.random() * (double)(this.b - this.a + 1))];
        this.rytmi = this.rythm[this.a + (int)(Math.random() * (double)(this.b - this.a + 1))];
        this.udari = this.udar[0 + (int)(Math.random() * 21.0D)];
    }
}

