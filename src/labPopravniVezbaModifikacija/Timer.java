package labPopravniVezbaModifikacija;

import java.awt.Label;

public class Timer implements Runnable{

	Thread nit;
	boolean radi=false;
	int s, m;
	Label vreme;
	
	public Timer(Label l) {
		vreme=l;
		vreme.setText(toString());
		s=0;
		m=0;
	}
	
	@Override
	public void run() {
		try {
			
			while(!nit.isInterrupted()) {
				 synchronized(this) {
					 while(!radi)
						 wait();
				 }
				 
				 azurirajVreme();
				 vreme.setText(toString());
				 vreme.repaint();
				 vreme.revalidate();
				 Thread.sleep(1000); 
				
			}
			
		}catch(InterruptedException e) {}
	}
	
	public synchronized void kreni() {
		if(!radi) {
			nit=new Thread(this);
			radi=true;
			nit.start();
			notify();
		}
		
	}
	
	public synchronized void zavrsi() {
		if(radi) {
			nit.interrupt();
			radi=false;
		}
	}
	
	
	private synchronized void azurirajVreme() {
		++s;
		if(s%60==0) {
			m++;
			s=0;
		}
	}

	@Override
	public String toString() {
		return String.format("%02d:%02d", m, s);
	}
	
	public boolean radi() {
		return radi;
	}

	
}
