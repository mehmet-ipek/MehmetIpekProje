package _23010310039_Mehmet_İpek;
import java.io.*;
import java.util.*;
class Proses {
    String ad;
    int gelis;
    int burst;
    int baslama = 0;
    int bekleme = 0;

    Proses(String ad, int gelis, int burst) {
        this.ad = ad;
        this.gelis = gelis;
        this.burst = burst;
    }
}

class Scheduler {
    List<Proses> liste;
    Scheduler(List<Proses> liste) {
        this.liste = liste;
    }

    public void calistir() {
        System.out.println("SJF (non-preemptive) Simülasyonu:");
        int zaman = 0;
        int biten = 0;
        Queue<Proses> kuyruk = new LinkedList<>();
        Proses aktif = null;
        int aktifSure = 0;
        while (true) {

            if (biten >= liste.size()) {
                break;
            }

            for (int i = 0; i < liste.size(); i++) {
                Proses p = liste.get(i);
                if (p.gelis == zaman) {
                    kuyruk.add(p);
                    System.out.println(p.ad + " prosesi " + zaman + ". Saniyede kuyruğa girdi.");
                }
            }
            if (aktif == null && !kuyruk.isEmpty()) {
                Proses sec = null;
                List<Proses> temp = new ArrayList<>(kuyruk);
                for (int i = 0; i < temp.size(); i++) {
                    Proses p = temp.get(i);
                    if (sec == null) {
                        sec = p;
                    }
                    else {
                        if (p.burst < sec.burst) {
                            sec = p;
                        }
                        else if (p.burst == sec.burst) {
                            if (p.gelis < sec.gelis) {
                                sec = p;
                            }
                            else if (p.gelis == sec.gelis) {

                                if (temp.indexOf(p) < temp.indexOf(sec)) {
                                    sec = p;
                                }
                            }
                        }
                    }
                }

                Queue<Proses> yeniKuyruk = new LinkedList<>();
                for (Proses p : kuyruk) {
                    if (p != sec) {
                        yeniKuyruk.add(p);
                    }
                }
                kuyruk = yeniKuyruk;
                aktif = sec;
                aktif.baslama = zaman;
                aktif.bekleme = aktif.baslama - aktif.gelis;
                aktifSure = 0;
                System.out.println(aktif.ad + " prosesi " + zaman + ". Saniyede çalışmaya başladı.");
            }

            try {
                Thread.sleep(1000);
            } catch (Exception e) {}

            if (aktif != null) {
                aktifSure++;
                System.out.println(aktif.ad + " prosesi toplamda " + aktifSure + " saniye çalıştı.");
                if (aktifSure == aktif.burst) {
                    aktif = null;
                    biten++;
                }
            }
            zaman++;
        }
        System.out.print("Bekleme Zamanları: ");
        double toplam = 0;
        for (int i = 0; i < liste.size(); i++) {
            Proses p = liste.get(i);
            toplam = toplam + p.bekleme;
            System.out.print(p.ad + " " + p.bekleme + " saniye");
            if (i < liste.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.println();
        double ort = toplam / liste.size();
        String sonuc = String.format("%.2f", ort);
        sonuc = sonuc.replace('.', ',');
        System.out.println("Ortalama Bekleme Süresi: " + sonuc + " Saniye");
    }
}

public class _23010310039_Mehmet_İpek {
    public static void main(String[] args) {
        List<Proses> liste = new ArrayList<>();
        try {
            File f = new File("prosesler.txt");
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String satir = sc.nextLine();
                if (satir.trim().isEmpty()) continue;
                String[] p = satir.split("\\s+");
                String ad = p[0];
                int gelis = Integer.parseInt(p[1]);
                int burst = Integer.parseInt(p[2]);
                liste.add(new Proses(ad, gelis, burst));
            }

            sc.close();
            System.out.println("prosesler.txt dosyası okundu.");
        } catch (Exception e) {
            return;
        }
        Scheduler s = new Scheduler(liste);
        s.calistir();
    }
}