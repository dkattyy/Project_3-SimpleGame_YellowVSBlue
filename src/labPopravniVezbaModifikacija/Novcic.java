package labPopravniVezbaModifikacija;

import java.awt.Color;
import java.awt.Graphics;

public class Novcic extends Akter {

	public Novcic(Teren t, Pozicija p) {
		super(t, p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void iscrtajAktera(Polje p) {
		Graphics g=p.getGraphics();
		
		g.setColor(Color.black);
		g.drawOval(p.getWidth()/3, p.getWidth()/3, p.getWidth()/4, p.getWidth()/4);
		
		g.setColor(Color.red);
		g.fillOval(p.getWidth()/3, p.getWidth()/3, p.getWidth()/4, p.getWidth()/4);
	}

}
