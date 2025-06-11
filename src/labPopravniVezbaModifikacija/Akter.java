package labPopravniVezbaModifikacija;

import java.awt.Graphics;

public abstract class Akter {

	Teren teren;
	Pozicija poz; //vrsta i kolona
	
	public Akter(Teren t, Pozicija p) {
		teren=t;
		poz=p;
	}
	
	public Pozicija getPozicija() {
		return poz;
	}
	
	public abstract void iscrtajAktera(Polje p);
	
}
