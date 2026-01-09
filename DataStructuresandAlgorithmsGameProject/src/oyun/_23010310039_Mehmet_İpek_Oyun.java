package oyun;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
public class _23010310039_Mehmet_İpek_Oyun {
             LinkedList<_23010310039_Mehmet_İpek_Kisiler>kisiler = new LinkedList<>();                                	
             LinkedList<Integer> cizilmissayilar = new LinkedList<>();
             Random random = new Random();

            public _23010310039_Mehmet_İpek_Oyun() {}
      
            public void readFromFile(String fileName) {
            	try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            		String line;
            		while((line = reader.readLine()) != null) {
            			String[] parts = line.split(" ");
            			String kisiIsmi = parts[0];
            			LinkedList<Integer> kartsayisi = new LinkedList<>();
            			for(int i = 1; i < parts.length; i++) { 
            				kartsayisi.add(Integer.parseInt(parts[i]));
            			}
            			kisiler.add(new _23010310039_Mehmet_İpek_Kisiler(kisiIsmi, kartsayisi));
            		}
            		} catch (IOException e ) {
            		System.err.println("Dosya okuma sırasinda bir hata oluştu: " + e.getMessage());
            	}
            }
            
            public void oyunbaslangici() {
            	System.out.println("'Bilgiler.txt' dosyası okundu...");
            	while(true) {
            		int rastgelesayi = RastgeleSayiCek();
            		System.out.println();
            		System.out.println();
            		System.out.println("Çekilen Sayı:" + rastgelesayi);
            		KisileriGuncelle(rastgelesayi);
            		OyunDurumu();
            		
            		if(OyunBitimi()) {
            			break;
            		}           		
            		try {
            			Thread.sleep(500);
            		}catch (InterruptedException e) {
            			Thread.currentThread().interrupt();
            		}
            	}
            	System.out.println();
                System.out.println();
            	System.out.println("Oyun bitti.");
            	Kazanan();           	
            }   
            
            public int RastgeleSayiCek() {            
                int sayi = random.nextInt(20) + 1;
                while (true) {                 
                    if (cizilmissayilar.contains(sayi)) {                                                              
                        sayi = random.nextInt(20) + 1;
                    } else {                     
                        break;
                    }
                }                     
                cizilmissayilar.add(sayi);                                                       
                return sayi;
            }
          
            public void KisileriGuncelle(int rastgelesayi) {
            	for (int i = 0; i < kisiler.size(); i++) {
            		_23010310039_Mehmet_İpek_Kisiler kisi = kisiler.get(i);
            		if (kisi.getKartNumarasi().contains(rastgelesayi)) {
            			kisi.IsaretliSayiEkle(rastgelesayi);
            		}
            	}
           	}
            
            public void OyunDurumu() {
        	    for (int i = 0; i < kisiler.size(); i++) {   	        
        	        _23010310039_Mehmet_İpek_Kisiler kisi = kisiler.get(i);       	             	   
        	        System.out.print(kisi.getIsim() + " ");       	            	       
        	        for (int j = 0; j < kisi.getKartNumarasi().size(); j++) {       	           
        	            int num = kisi.getKartNumarasi().get(j);       	                   	           
        	            if (kisi.getIsaretliSayilar().contains(num)) {
        	                System.out.print(num + "# ");
        	            } else {     	                
      	                System.out.print(num + " ");
        	            }
        	        }       	               	      
        	        System.out.println();
        	    }
        	}
          
            public boolean OyunBitimi() {       	    
        	    for (int i = 0; i < kisiler.size(); i++) {        	        
        	        _23010310039_Mehmet_İpek_Kisiler kisi = kisiler.get(i);       	        
        	        LinkedList<Integer> isaretliSayilar = kisi.getIsaretliSayilar();
        	        LinkedList<Integer> kartNumaralari = kisi.getKartNumarasi();        	                	       
        	        boolean tumSayilarIsaretliMi = true;        	                	     
        	        for (int j = 0; j < kartNumaralari.size(); j++) {        	           
        	            int numara = kartNumaralari.get(j);        	                   	         
        	            if (!isaretliSayilar.contains(numara)) {
        	                tumSayilarIsaretliMi = false;
        	                break; 
        	            }
        	        }        	             	        
        	        if (tumSayilarIsaretliMi) {
        	            return true;
        	        }
        	    }       	           	   
        	    return false;
        	 }
      
             public void Kazanan() {
        	    for (int i = 0; i < kisiler.size(); i++) {
        	        _23010310039_Mehmet_İpek_Kisiler kisi = kisiler.get(i);
        	        LinkedList<Integer> isaretliSayilar = kisi.getIsaretliSayilar();
        	        LinkedList<Integer> kartNumaralari = kisi.getKartNumarasi();
        	        boolean tumSayilarIsaretliMi = true;
        	        for (int j = 0; j < kartNumaralari.size(); j++) {       	         
        	            int numara = kartNumaralari.get(j);       
        	            if (!isaretliSayilar.contains(numara)) {
        	                tumSayilarIsaretliMi = false;
        	                break; 
        	            }
        	        }
        	        if (tumSayilarIsaretliMi == true) {
        	            System.out.println("Kazanan: " + kisi.getIsim());
        	            break; 
        	        }
        	    }
        	}
         }