package labPopravniVezbaModifikacija;

import java.awt.Color;
import java.awt.Graphics;

public class Zid extends Polje {

	public Zid(Color boja, Teren vlasnik, Pozicija poz) {
		super(boja, vlasnik, poz);
	}


	@Override
	public boolean akterMozeDaStane() {
		return false;
	}

}
