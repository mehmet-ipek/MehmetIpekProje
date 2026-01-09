package oyun;

import java.util.LinkedList;
public class _23010310039_Mehmet_İpek_Kartlar {
	  LinkedList<Integer> sayilar;
	  LinkedList<Integer> isaretlisayilar;
	 
	 public _23010310039_Mehmet_İpek_Kartlar(LinkedList<Integer> sayilar) { 
	     this.sayilar = sayilar;
		 this.isaretlisayilar = new LinkedList<>();
	 }
	 
	 public LinkedList<Integer> getSayilar(){
		 return sayilar;	 
	 }
	 
	 public void isaretliSayi(int sayi) {		    
		    boolean SayiListede = false; 
		    if (sayilar.contains(sayi)) { 
		        SayiListede = true;
		    }
		    boolean IsaretliSayiListede = false; 
		    if (isaretlisayilar.contains(sayi)) {
		    	IsaretliSayiListede = true; 
		    }   
		    boolean IsaretliSayiListedeDegil = false; 
		    if (!IsaretliSayiListede) { 
		    	IsaretliSayiListedeDegil = true;
		    }		   
		    if (SayiListede) { 
		        if (IsaretliSayiListedeDegil) { 		          
		            isaretlisayilar.add(sayi);
		        }
		    }
		}
	 
	 public boolean TumSayilarIsaretliMi() { 
		  int IsaretliSayilarBoyutu = isaretlisayilar.size();	
		  int SayilarBoyutu = sayilar.size();
		  boolean TumSayilarIsaretliMi = false;
		  if (IsaretliSayilarBoyutu == SayilarBoyutu) {
		      TumSayilarIsaretliMi = true; 
		    }		    		  
		  return TumSayilarIsaretliMi;
		}

	 public String toString() {		   
		    String sonuc = "";
		    for (int i = 0; i < sayilar.size(); i++) {		   
		        int sayi = sayilar.get(i);		        		       
		        sonuc = sonuc + sayi;		        		    
		        boolean isaretliMi = isaretlisayilar.contains(sayi);		        		 
		        if (isaretliMi) {
		            sonuc = sonuc + "#";
		        }		        
		        sonuc = sonuc + " ";
		    }		    
		    sonuc = sonuc.trim();		    		   
		    return sonuc;
		}
	} 	