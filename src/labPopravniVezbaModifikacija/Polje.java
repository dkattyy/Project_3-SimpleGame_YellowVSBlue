package labPopravniVezbaModifikacija;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Polje extends Canvas {
	
	protected Color boja;
	protected Teren vlasnik;
	protected Pozicija poz;
	protected boolean oznaceno;
	protected Akter akter;
	protected SporedniAkter sporedni;
	
	public Polje(Color boja, Teren vlasnik, Pozicija poz) {
		super();
		this.boja = boja;
		this.vlasnik = vlasnik;
		this.poz = poz;
		oznaceno=false;
		
		this.setPreferredSize(new Dimension(40, 40));
		this.setBackground(boja);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(oznaceno) oznaciPolje(false);
				else oznaciPolje(true);
				repaint();
			}
		});
	}

	public void oznaciPolje(boolean b) {
		oznaceno=b;
		repaint();
	}

	public Pozicija getPoz() {
		return poz;
	}


	public void postaviAktera(Akter a) {
		
		if(akter!=null) ukloniAktera();
		
		if(akterMozeDaStane()) {
			akter=a;
			repaint();
		}
	}
	
	public void ukloniAktera() {
		if(akter!=null) {
			if(akter instanceof Novcic) {
				this.vlasnik.azurirajRezultat();
				
			}
			akter=null;
			repaint();
		}
	}
	
	public void paint(Graphics g) {

		super.paint(g);
		
		if(oznaceno){
			g.setColor(Color.RED);
			g.drawRect(0, 0, getWidth()-1, getHeight()-1);  //ova nula se odnosi na koordinate SAMO TEKUCEG POLJA, ne celog frame-a

		}
		if(akter!=null) {
			akter.iscrtajAktera(this);
		}
		
		if(sporedni!=null) {
			sporedni.iscrtajAktera(this);
		}
		
	}
	public abstract boolean akterMozeDaStane();

	public void ukloniSporednogAktera() {
		sporedni=null;
		repaint();
	}

	public void dodajSporednogAktera(SporedniAkter sa) {
		sporedni=sa;
		this.repaint();
	}

	public boolean tuJeGlavniAkter() {
		return (akter!=null && akter instanceof GlavniAkter);
	}

	public synchronized void pojediIgraca() {
		if(akter instanceof GlavniAkter) {
			((GlavniAkter) akter).zavrsi();
			akter=null;
			if(sporedni!=null) sporedni.zavrsi();
			repaint();
			vlasnik.igracPojeden();
		}
	}

	public synchronized boolean tuJeSporedni() {
		return (sporedni!=null);
	}
}
