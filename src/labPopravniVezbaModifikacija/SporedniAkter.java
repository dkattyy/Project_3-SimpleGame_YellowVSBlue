package labPopravniVezbaModifikacija;

import java.awt.Color;
import java.awt.Graphics;

public class SporedniAkter extends Akter implements Runnable{

	Thread nit;
	boolean radi=false;
	double smer;
	
	public SporedniAkter(Teren t, Pozicija p) {
		super(t, p);
		izaberiSmer();
		
	}

	@Override
	public void iscrtajAktera(Polje p) {
		Graphics g=p.getGraphics();
		
		g.setColor(Color.black);
		g.drawOval(p.getWidth()/4, p.getWidth()/4, p.getWidth()/2, p.getWidth()/2);
		
		g.setColor(Color.BLUE);
		g.fillOval(p.getWidth()/4, p.getWidth()/4, p.getWidth()/2, p.getWidth()/2);
		
	}

	@Override
	public void run() {
		try {
			
			while(!nit.isInterrupted()) {
				
				synchronized(this) {
					while(!radi) {
						wait();
					}
				}
				
				
				azurirajPoziciju();
				Thread.sleep(500);
				teren.repaint();
				
			}
			
		}catch(InterruptedException e) {}
	}
	
	private void izaberiSmer() {
		smer=Math.random();
		if(smer<0.25) {
			smer=1;  //gore
		}else if(smer<0.5) {
			smer=2;  //dole
		}else if(smer<0.75) {
			smer=3;  //levo
		}else {
			smer=4;  //desno
		}
	}
	
	private void azurirajPoziciju() {
		//ovo za promenu smera malo odraditi elegantnije
		//nema potrebe da menja smer svaki put vec ako naidje na zid/ivicu onda menja smer
		
		switch((int)smer) {
			case 1:{
				//gore
				if(this.poz.getVrsta() > 0 && this.teren.grid[this.poz.getVrsta()-1][this.poz.getKolona()] instanceof Prolaz) {
					this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()].ukloniSporednogAktera();
					
					if(this.teren.grid[this.poz.getVrsta()-1][this.poz.getKolona()].tuJeGlavniAkter()) {
						//onda treba da ,,pojedemo" igraca i da zavrsimo igru
						this.teren.grid[this.poz.getVrsta()-1][this.poz.getKolona()].dodajSporednogAktera(this);
						this.teren.grid[this.poz.getVrsta()-1][this.poz.getKolona()].pojediIgraca();
						zavrsi();
						break;
					}
					
					this.teren.grid[this.poz.getVrsta()-1][this.poz.getKolona()].dodajSporednogAktera(this);
					this.poz=new Pozicija(this.poz.getVrsta()-1, this.poz.getKolona());
				}else izaberiSmer();
				break;
			}
			case 2:{
				//dole
				if(this.poz.getVrsta() < teren.rows-1 && this.teren.grid[this.poz.getVrsta()+1][this.poz.getKolona()] instanceof Prolaz) {
					this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()].ukloniSporednogAktera();
					
					if(this.teren.grid[this.poz.getVrsta()+1][this.poz.getKolona()].tuJeGlavniAkter()) {
						//onda treba da ,,pojedemo" igraca i da zavrsimo igru
						this.teren.grid[this.poz.getVrsta()+1][this.poz.getKolona()].dodajSporednogAktera(this);
						this.teren.grid[this.poz.getVrsta()+1][this.poz.getKolona()].pojediIgraca();
						zavrsi();
						break;
					}
					
					this.teren.grid[this.poz.getVrsta()+1][this.poz.getKolona()].dodajSporednogAktera(this);
					this.poz=new Pozicija(this.poz.getVrsta()+1, this.poz.getKolona());
				}else izaberiSmer();
				break;
			}
			case 3:{
				//levo
				if(this.poz.getKolona() > 0 && this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()-1] instanceof Prolaz) {
					this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()].ukloniSporednogAktera();
					
					if(this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()-1].tuJeGlavniAkter()) {
						this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()-1].dodajSporednogAktera(this);
						this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()-1].pojediIgraca();
						zavrsi();
						break;
					}
					
					this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()-1].dodajSporednogAktera(this);
					this.poz=new Pozicija(this.poz.getVrsta(), this.poz.getKolona()-1);
				}else izaberiSmer();
				break;
			}
			case 4:{
				//desno
				if(this.poz.getKolona() < teren.columns-1 && this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()+1] instanceof Prolaz) {
					this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()].ukloniSporednogAktera();
					
					if(this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()+1].tuJeGlavniAkter()) {
						this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()+1].dodajSporednogAktera(this);
						this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()+1].pojediIgraca();
						//nit.interrupt();
						zavrsi();
						break;
					}
					
					this.teren.grid[this.poz.getVrsta()][this.poz.getKolona()+1].dodajSporednogAktera(this);
					this.poz=new Pozicija(this.poz.getVrsta(), this.poz.getKolona()+1);
				}else izaberiSmer();
				break;
			}
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
			nit.interrupt();
			//nit=null;
			radi=false;
		}
	}

}
