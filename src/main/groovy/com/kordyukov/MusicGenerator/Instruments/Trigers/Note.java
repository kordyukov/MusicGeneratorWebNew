package com.kordyukov.MusicGenerator.Instruments.Trigers;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class Note {
    public static final int[] c = { 12 , 24 , 36 , 48 , 60 , 72 , 84 , 96 , 108 };
    public static final int[] d = { 14 , 26 , 38 , 50 , 62 , 74 , 86 , 98 , 110 };
    public static final int[] e = { 16 , 28 , 40 , 52 , 64 , 76 , 88 , 100 , 112 };
    public static final int[] f = { 17 , 29 , 41 , 53 , 65 , 77 , 89 , 101 , 113 };
    public static final int[] g = { 19 , 31 , 43 , 55 , 67 , 79 , 91 , 103 , 115 };
    public static final int[] a = { 21 , 33 , 45 , 57 , 69 , 81 , 93 , 105 , 117 };
    public static final int[] h = { 23 , 35 , 47 , 59 , 71 , 83 , 95 , 107 , 119 };
    public static final int[][] notes = { c , d , e , f , g , a , h };
}
