package labPopravniVezbaModifikacija;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Teren extends Panel{

	public Igra igraVlasnik;
	public int rows, columns;
	public Polje grid[][];
	private Polje oznaceno=null;
	private ArrayList<Akter> sporedniAkteri=new ArrayList<>();
	private GlavniAkter igrac=null; 
	private int rezultat=0;
	
	public Teren(int rows, int columns, Igra v) {
		igraVlasnik=v;
		this.rows=rows;
		this.columns=columns;
		this.setLayout(new GridLayout(rows, columns, 1,1));
		
		grid=new Polje[rows][columns];
		
		for(int i=0; i<rows;i ++) {
			for(int j=0;j<columns;j++) {
				Pozicija p=new Pozicija(i, j);
				grid[i][j]=new Prolaz(Color.gray, this, p);
				this.add(grid[i][j]);
				
				grid[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						oznaciPolje((Polje) e.getSource());
					}
				});
			}
		}
		
		setFocusable(true);
		requestFocusInWindow();
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				requestFocusInWindow();
				int key = e.getKeyCode();
	            switch (key) {
					case KeyEvent.VK_W: {
						igrac.postaviSmer(1);
						break;
					} 
					case KeyEvent.VK_S:{
						igrac.postaviSmer(2);
						break;
					}
					case KeyEvent.VK_A:{
						igrac.postaviSmer(3);
						break;
					}
					case KeyEvent.VK_D:{
						igrac.postaviSmer(4);
						break;
					}
				}
				startujSporedneAktere();
				if (!igraVlasnik.timer.radi()) igraVlasnik.timer.kreni();
				revalidate();
				repaint();
			}
		});
	}
	
	public void funckijeZaPomeranjeIgraca() {
//		if(igrac!=null) {
//			Pozicija p=igrac.getPozicija();
//			int x=p.getVrsta();
//			int y=p.getKolona();
//			if(grid[x-1][y] instanceof Prolaz && x>0) {
//				grid[x-1][y].postaviAktera(igrac);
//				grid[x][y].ukloniAktera();
//				igrac.poz=new Pozicija(x-1, y);
//			}else return;
//		}
//	}
//
//	public void pomeriIgracaDole() {
//		if(igrac!=null) {
//			Pozicija p=igrac.getPozicija();
//			int x=p.getVrsta();
//			int y=p.getKolona();
//			if(grid[x+1][y] instanceof Prolaz && x<rows-1 ) {
//				grid[x+1][y].postaviAktera(igrac);
//				grid[x][y].ukloniAktera();
//				igrac.poz=new Pozicija(x+1, y);
//			}else return;
//		}
//	}
//	public void pomeriIgracaLevo() {
//	    if (igrac != null) {
//	        Pozicija p = igrac.getPozicija();
//	        int x = p.getVrsta();
//	        int y = p.getKolona();
//	        if (y > 0 && grid[x][y - 1] instanceof Prolaz) {
//	            grid[x][y - 1].postaviAktera(igrac);
//	            grid[x][y].ukloniAktera();
//	            igrac.poz = new Pozicija(x, y - 1);
//	        }else return;
//	    }
//	}
//
//	public void pomeriIgracaDesno() {
//	    if (igrac != null) {
//	        Pozicija p = igrac.getPozicija();
//	        int x = p.getVrsta();
//	        int y = p.getKolona();
//	        if (y < columns - 1 && grid[x][y + 1] instanceof Prolaz) {
//	            grid[x][y + 1].postaviAktera(igrac);
//	            grid[x][y].ukloniAktera();
//	            igrac.poz = new Pozicija(x, y + 1);
//	        }else return;
//	    }
	}

	
	public void azurirajRezultat() {
		rezultat++;
		this.igraVlasnik.rezultat.setText("" + rezultat);
	    this.igraVlasnik.rezultat.revalidate();
	    this.igraVlasnik.rezultat.repaint();
	    this.revalidate();
	    this.repaint();
	}
	
	protected void oznaciPolje(Polje polje) {
		if(oznaceno==null) {
			polje.oznaciPolje(true);
			oznaceno=polje;
			oznaceno.oznaciPolje(true);
		}else {
			for(int i=0; i<rows;i ++) {
				for(int j=0;j<columns;j++) {
					if(oznaceno==grid[i][j]) {
						grid[i][j].oznaciPolje(false);
						break;
					}
				}
			}
			
			for(int i=0; i<rows;i ++) {
				for(int j=0;j<columns;j++) {
					if(polje==grid[i][j]) {
						oznaceno=polje;
						grid[i][j].oznaciPolje(true);
						break;
					}
				}
			}
		}
		
	}
	
	
	public void generisiSlucajno() {
		removeAll();
		if(igrac!=null) { 
			igrac.zavrsi();
			igrac=null;
		}
		
		for(Akter a: sporedniAkteri) {
			if(a instanceof SporedniAkter) {
				((SporedniAkter) a).zavrsi();
			}
		}
		
		sporedniAkteri.removeAll(sporedniAkteri);
		
		rezultat=0;
		igraVlasnik.rezultat.setText("0");
		igraVlasnik.rezultat.repaint();
		igraVlasnik.rezultat.revalidate();
		igraVlasnik.kraj.setText("SRECNO!");
		igraVlasnik.kraj.repaint();
		igraVlasnik.kraj.revalidate();
		igraVlasnik.timer.zavrsi();
		
		grid=new Polje[rows][columns];
		for(int i=0; i<rows;i ++) {
			for(int j=0;j<columns;j++) {
				Pozicija p=new Pozicija(i, j);
				double r=Math.random();
				if(r<=0.2) grid[i][j]=new Zid(Color.DARK_GRAY,this, p);
				else grid[i][j]=new Prolaz(Color.gray, this, p);
				this.add(grid[i][j]);
				
				grid[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						oznaciPolje((Polje) e.getSource());
					}
				});
				
			}
		}
		
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns;j++) {
				if(grid[i][j] instanceof Prolaz) {
					if(igrac==null) {
						Pozicija p=grid[i][j].getPoz();
						igrac=new GlavniAkter(this, p);
						grid[i][j].postaviAktera(igrac);
						
					}
					else{//dodavanje novcica na sve ostale prolaze
					Pozicija p =grid[i][j].getPoz();
					Novcic n =new Novcic(this, p);
					grid[i][j].postaviAktera(n);
					}
				}
			}
		}
		revalidate();
		repaint();
	}
	
	public void startujSporedneAktere() {
		for(Akter a: sporedniAkteri) {
			if(a instanceof SporedniAkter) {
				((SporedniAkter) a).kreni();
			}
		}
	}
	
	public void dodajGlavnogAktera() {
		//dodajemo na oznaceno polje
		if(oznaceno!=null) {
			Pozicija p=oznaceno.getPoz();
			int red=p.getVrsta();
			int kolona=p.getKolona();
			
			if(grid[red][kolona].akterMozeDaStane()) {
				GlavniAkter ga=new GlavniAkter(this, p);
				grid[red][kolona].postaviAktera(ga);
				grid[red][kolona].oznaciPolje(false);
				//akteri.add(ga);
				revalidate();
				repaint();
			}else return;
		}
	}
	
	public void dodajSporednogAktera() {
		if(oznaceno!=null) {
			Pozicija p=oznaceno.getPoz();
			int x=p.getVrsta();
			int y=p.getKolona();
			
			if(grid[x][y].akterMozeDaStane()) {
				SporedniAkter sa=new SporedniAkter(this, p);
				grid[x][y].dodajSporednogAktera(sa);
				grid[x][y].oznaciPolje(false);
				sporedniAkteri.add(sa);
				revalidate();
				repaint();
			}
		}
	}
	
	public void dodajZid() {
		if(oznaceno!=null) {
			Pozicija p=oznaceno.getPoz();
			int red=p.getVrsta();
			int kolona=p.getKolona();
			
			remove(grid[red][kolona]);
			
			grid[red][kolona].oznaciPolje(false);
			grid[red][kolona]=new Zid(Color.DARK_GRAY, this, p);
			
			grid[red][kolona].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					oznaciPolje((Polje) e.getSource());
				}
			});
			
			this.add(grid[red][kolona], red*columns+kolona);
			grid[red][kolona].oznaciPolje(false);
			oznaceno=null;
			
			revalidate();
			repaint();
		}
	}
	
	public void dodajProlaz() {
		if(oznaceno!=null) {
			Pozicija p=oznaceno.getPoz();
			int red=p.getVrsta();
			int kolona=p.getKolona();
			
			this.remove(grid[red][kolona]);
			
			grid[red][kolona].oznaciPolje(false);
			grid[red][kolona]=new Prolaz(Color.gray, this, p);
			
			grid[red][kolona].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					oznaciPolje((Polje) e.getSource());
				}
			});
			
			this.add(grid[red][kolona], red*columns + kolona);
			grid[red][kolona].oznaciPolje(false);
			oznaceno=null;
			revalidate();
			repaint();
			
		}
	}
	public Polje dohvatiSaPozicije(Pozicija p) {
		int row=p.getVrsta();
		int column=p.getKolona();

		return grid[row][column];
	}

	public synchronized void igracPojeden() {
		igrac.zavrsi();
		igrac=null;
		for(Akter a: sporedniAkteri) {
			if(a instanceof SporedniAkter) {
				((SporedniAkter) a).zavrsi();
			}
		}
		repaint();
		
		igraVlasnik.prikaziDijalog();
		
		igraVlasnik.kraj.setText("Zao nam je! Izgubili ste!");
		igraVlasnik.kraj.revalidate();
		igraVlasnik.kraj.repaint();
	}

}
