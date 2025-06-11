package labPopravniVezbaModifikacija;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Prolaz extends Polje {

	public Prolaz(Color boja, Teren vlasnik, Pozicija poz) {
		super(boja, vlasnik, poz);
		
	}

	@Override
	public boolean akterMozeDaStane() {
		return true;
	}

}
