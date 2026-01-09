package _23010310039_Mehmet_Ipek;

import java.io.*;
import java.util.*;

public class ComputerArchitectureAndOrganizationInstructionSetComputingProject {

    public static class BuyrukKomutu {
        String adres;
        String komut;
        String hedef;
        boolean dolayli;
        boolean deger;

        BuyrukKomutu(String adres, String komut, String hedef, boolean dolayli, boolean deger) {
            this.adres = adres;
            this.komut = komut;
            this.hedef = hedef;
            this.dolayli = dolayli;
            this.deger = deger;
        }
    }

    public static Map<String, String> bellek = new HashMap<>();
    public static Map<String, String> yazmac = new HashMap<>();
    public static Map<String, String> io = new HashMap<>();

    public static void KomutYukleme() {
        bellek.put("AND", "000"); bellek.put("ADD", "001"); bellek.put("LDA", "010");
        bellek.put("STA", "011"); bellek.put("BUN", "100"); bellek.put("BSA", "101");
        bellek.put("ISZ", "110");

        yazmac.put("CLA", "100000000000"); yazmac.put("CLE", "010000000000");
        yazmac.put("CMA", "001000000000"); yazmac.put("CME", "000100000000");
        yazmac.put("CIR", "000010000000"); yazmac.put("CIL", "000001000000");
        yazmac.put("INC", "000000100000"); yazmac.put("SPA", "000000010000");
        yazmac.put("SNA", "000000001000"); yazmac.put("SZA", "000000000100");
        yazmac.put("SZE", "000000000010"); yazmac.put("HLT", "000000000001");

        io.put("INP", "100000000000"); io.put("OUT", "010000000000");
        io.put("SKI", "001000000000"); io.put("SKO", "000100000000");
        io.put("ION", "000010000000"); io.put("IOF", "000001000000");
    }

    public static String ikiliCevir(int sayi, int uzunluk) {
        return String.format("%" + uzunluk + "s", Integer.toBinaryString(sayi))
                .replace(' ', '0');
    }

    public static String ikiliYazdir(String ikili) {
        int onluk = Integer.parseInt(ikili, 2);
        String hex = String.format("%04X", onluk);
        return ikili.substring(0, 4) + " " +
               ikili.substring(4, 8) + " " +
               ikili.substring(8, 12) + " " +
               ikili.substring(12, 16) + " (" + hex + ")";
    }

    public static void main(String[] args) {

        KomutYukleme();
        List<BuyrukKomutu> liste = new ArrayList<>();

        try {
            InputStream is = ComputerArchitectureAndOrganizationInstructionSetComputingProject
                    .class
                    .getResourceAsStream("/_23010310039_Mehmet_Ipek/Program.txt");

            if (is == null) {
                System.out.println("Program.txt BULUNAMADI!");
                System.out.println("src/_23010310039_Mehmet_Ipek/Program.txt konumunu kontrol et.");
                return;
            }

            BufferedReader oku = new BufferedReader(new InputStreamReader(is));
            String satir;

            while ((satir = oku.readLine()) != null) {
                satir = satir.trim();
                if (satir.isEmpty()) continue;

                String[] elemanlar = satir.split("\\s+");
                String adresDeger = elemanlar[0].toUpperCase();

                if (elemanlar.length == 2 && elemanlar[1].matches("[0-9A-Fa-f]{4}")) {
                    liste.add(new BuyrukKomutu(
                            adresDeger,
                            elemanlar[1].toUpperCase(),
                            null,
                            false,
                            true
                    ));
                } else {
                    String islemKodu = elemanlar[1].toUpperCase();

                    if (bellek.containsKey(islemKodu)) {
                        boolean dolayli = (elemanlar.length == 4 &&
                                           elemanlar[3].equalsIgnoreCase("I"));
                        liste.add(new BuyrukKomutu(
                                adresDeger,
                                islemKodu,
                                elemanlar[2].toUpperCase(),
                                dolayli,
                                false
                        ));
                    } else if (yazmac.containsKey(islemKodu)) {
                        liste.add(new BuyrukKomutu(adresDeger, islemKodu, null, false, false));
                    } else if (io.containsKey(islemKodu)) {
                        liste.add(new BuyrukKomutu(adresDeger, islemKodu, null, false, false));
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Program.txt okunurken hata: " + e.getMessage());
            return;
        }

        System.out.println("Program.txt dosyasi BASARIYLA okundu.\n");

        for (BuyrukKomutu b : liste) {
            String ikiliCikti;

            if (b.deger) {
                ikiliCikti = ikiliYazdir(
                        ikiliCevir(Integer.parseInt(b.komut, 16), 16)
                );
            } else if (bellek.containsKey(b.komut)) {
                String dolayliBit = b.dolayli ? "1" : "0";
                String kod = bellek.get(b.komut);
                String hedefIkili = ikiliCevir(
                        Integer.parseInt(b.hedef, 16), 12
                );
                ikiliCikti = ikiliYazdir(dolayliBit + kod + hedefIkili);
            } else if (yazmac.containsKey(b.komut)) {
                ikiliCikti = ikiliYazdir("0" + "111" + yazmac.get(b.komut));
            } else {
                ikiliCikti = ikiliYazdir("1" + "111" + io.get(b.komut));
            }

            int adresDegerInt = Integer.parseInt(b.adres, 16);
            System.out.println(
                    String.format("%03X", adresDegerInt) + " " + ikiliCikti
            );
        }
    }
}
