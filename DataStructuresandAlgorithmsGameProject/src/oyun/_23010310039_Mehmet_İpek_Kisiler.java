package oyun;

import java.util.LinkedList;
public class _23010310039_Mehmet_İpek_Kisiler {
	 String isim;
	 LinkedList<Integer> kartnumarasi;
	 LinkedList<Integer> isaretlisayilar = new LinkedList<>();
	
	public _23010310039_Mehmet_İpek_Kisiler(String isim, LinkedList<Integer> kartnumarasi) {
		this.isim = isim;
		this.kartnumarasi = (LinkedList<Integer>) kartnumarasi;
	}
	
	public String getIsim() {
	return isim;
    }
	
	public LinkedList<Integer> getKartNumarasi(){
    return kartnumarasi;
	}
	
    public LinkedList<Integer> getIsaretliSayilar(){
	return (LinkedList<Integer>) isaretlisayilar;
    }

    public void IsaretliSayiEkle(int sayi) {
    	isaretlisayilar.add(sayi);
    }  
}