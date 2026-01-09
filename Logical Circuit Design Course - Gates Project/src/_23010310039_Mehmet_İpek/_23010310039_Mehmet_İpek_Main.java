package _23010310039_Mehmet_İpek;

import java.io.*;
import java.util.*;

public class _23010310039_Mehmet_İpek_Main {
    public static void main(String[] args) {
        try {
        	_23010310039_Mehmet_İpek_DevreSonucu builder = new _23010310039_Mehmet_İpek_DevreSonucu("C:\\Users\\meomo\\OneDrive\\Masaüstü\\java uygulamaları\\_23010310039_Mehmet_İpek\\boole.txt");
            builder.buildCircuit();
            builder.printCircuitInfo();
            builder.evaluateCircuitWithUserInput();
        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }
}