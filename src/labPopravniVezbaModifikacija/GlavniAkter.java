package labPopravniVezbaModifikacija;

import java.awt.Color;
import java.awt.Graphics;

public class GlavniAkter extends Akter implements Runnable{

	Thread nit;
	private boolean radi=false;
	private int trenutniSmer=0;
	
	
	public GlavniAkter(Teren t, Pozicija p) {
		super(t, p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void iscrtajAktera(Polje p) {
		Graphics g=p.getGraphics();
		
		g.setColor(Color.black);
		g.drawOval(p.getWidth()/4, p.getWidth()/4, p.getWidth()/2, p.getWidth()/2);
		
		g.setColor(Color.yellow);
		g.fillOval(p.getWidth()/4, p.getWidth()/4, p.getWidth()/2, p.getWidth()/2);
		
	}

	@Override
	public void run() {
		try {
		
			while(!nit.isInterrupted()) {
				
				synchronized(this) {
					while(!radi) 
						wait();
				}
				
				azurirajPoziciju();
				Thread.sleep(300);
				teren.repaint();
			}
			
			
		}catch(InterruptedException e) {}
	}

	private synchronized void azurirajPoziciju() {
		switch(trenutniSmer) {
		case 1:{
			//pomeramo gore
			if(this.poz.getVrsta()>0 && teren.grid[this.poz.getVrsta()-1][this.poz.getKolona()] instanceof Prolaz) {
				teren.grid[this.poz.getVrsta()][this.poz.getKolona()].ukloniAktera();
				
				//provera da li je sporedni akter na tom polju
				if(teren.grid[this.poz.getVrsta()-1][this.poz.getKolona()].tuJeSporedni()) {
					//gasi igru ,kraj
					teren.grid[this.poz.getVrsta()-1][this.poz.getKolona()].postaviAktera(this);
					teren.grid[this.poz.getVrsta()-1][this.poz.getKolona()].pojediIgraca();
					zavrsi();
					break;
				}
				
				teren.grid[this.poz.getVrsta()-1][this.poz.getKolona()].postaviAktera(this);
				this.poz=new Pozicija(this.poz.getVrsta()-1, this.poz.getKolona());
			}else break;
			break;
			}
		
		case 2:{
			//pomeramo dole
			if( this.poz.getVrsta()<teren.rows-1 && teren.grid[this.poz.getVrsta()+1][this.poz.getKolona()] instanceof Prolaz ) {
				teren.grid[this.poz.getVrsta()][this.poz.getKolona()].ukloniAktera();
				
				if(teren.grid[this.poz.getVrsta()+1][this.poz.getKolona()].tuJeSporedni()) {
					//gasi igru ,kraj
					teren.grid[this.poz.getVrsta()+1][this.poz.getKolona()].postaviAktera(this);
					teren.grid[this.poz.getVrsta()+1][this.poz.getKolona()].pojediIgraca();
					zavrsi();
					break;
				}
				
				teren.grid[this.poz.getVrsta()+1][this.poz.getKolona()].postaviAktera(this);
				this.poz=new Pozicija(this.poz.getVrsta()+1, this.poz.getKolona());
			}else break;
			break;
			}
		case 3:{
			//levo
			if(this.poz.getKolona()>0 && teren.grid[this.poz.getVrsta()][this.poz.getKolona()-1] instanceof Prolaz) {
				teren.grid[this.poz.getVrsta()][this.poz.getKolona()].ukloniAktera();
				
				if(teren.grid[this.poz.getVrsta()][this.poz.getKolona()-1].tuJeSporedni()) {
					//gasi igru ,kraj
					teren.grid[this.poz.getVrsta()][this.poz.getKolona()-1].postaviAktera(this);
					teren.grid[this.poz.getVrsta()][this.poz.getKolona()-1].pojediIgraca();
					zavrsi();
					break;
				}
				
				teren.grid[this.poz.getVrsta()][this.poz.getKolona()-1].postaviAktera(this);
				this.poz=new Pozicija(this.poz.getVrsta(), this.poz.getKolona()-1);
			}else break;
			break;
			}
		case 4:{
			if(this.poz.getKolona()<teren.columns - 1 && teren.grid[this.poz.getVrsta()][this.poz.getKolona()+1] instanceof Prolaz) {
				teren.grid[this.poz.getVrsta()][this.poz.getKolona()].ukloniAktera();
				
				if(teren.grid[this.poz.getVrsta()][this.poz.getKolona()+1].tuJeSporedni()) {
					//gasi igru ,kraj
					teren.grid[this.poz.getVrsta()][this.poz.getKolona()+1].postaviAktera(this);
					teren.grid[this.poz.getVrsta()][this.poz.getKolona()+1].pojediIgraca();
					zavrsi();
					break;
				}
				teren.grid[this.poz.getVrsta()][this.poz.getKolona()+1].postaviAktera(this);
				
				this.poz=new Pozicija(this.poz.getVrsta(), this.poz.getKolona()+1);
			}else break;
			break;
			}
		}
	}
	
	public void postaviSmer(int smer) {
		trenutniSmer=smer;
		if(!radi) {
			kreni();
		}
	}
	
	public synchronized void kreni() {
		if(nit==null) {
			nit=new Thread(this);
			nit.start();
			radi=true;
			notify();
		}
		
	}
	
	public synchronized void stani() {
		radi=false;
	}
	
	public synchronized void zavrsi() {
		if(nit!=null) {
			radi=false;
			nit.interrupt();
			//nit=null;
		}
	}

}
