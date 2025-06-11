package labPopravniVezbaModifikacija;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dialog.ModalityType;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {

	Teren teren;
	Panel desniPanel=new Panel();
	Checkbox zid=new Checkbox("Zid");
	Checkbox prolaz=new Checkbox("Prolaz");
	Checkbox glavniAkter=new Checkbox("GAkter");
	Checkbox sporedniAkter=new Checkbox("SAkter");
	
	Panel donjiPanel=new Panel();
	Label rez = new Label("Rezultat: ");
	public Label rezultat = new Label("0");
	
	public Label kraj=new Label("Pokrenite igru");
	
	public int rezultatVrednost=0;

	CheckboxGroup grupa=new CheckboxGroup();
	
	Button postavi=new Button("Postavi");
	Button nasumicno=new Button("Nasumicno");

	Label vreme=new Label();
	public Timer timer;
	
	//Dialog dialogZaKrajIgre=new Dialog(this);
	
	
	public Igra() {
		setBounds(700, 200, 600, 200);
		setResizable(false);
		
		setTitle("Igra");
		
		Menu meni1=new Menu("Meni");
		MenuItem novaigra=new MenuItem("Nova igra");
		MenuItem kraj=new MenuItem("Kraj");
		meni1.add(novaigra);
		meni1.add(kraj);
		MenuBar m=new MenuBar();
		m.add(meni1);
		this.setMenuBar(m);
		
		
		
		populateWindow();
		pack();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		teren.requestFocusInWindow();
		setVisible(true);
	}
	
	
	private void populateWindow() {
		teren=new Teren(6, 12, this);
		this.add(teren, BorderLayout.CENTER);
		
		desniPanel.setLayout(new GridLayout(3, 1, 10, 10));
		zid.setCheckboxGroup(grupa);
		prolaz.setCheckboxGroup(grupa);
		glavniAkter.setCheckboxGroup(grupa);
		sporedniAkter.setCheckboxGroup(grupa);

		
		
		Panel radioDugmad=new Panel();
		radioDugmad.add(zid);
		radioDugmad.add(prolaz);
		radioDugmad.add(glavniAkter);
		radioDugmad.add(sporedniAkter);

		
		nasumicno.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				teren.requestFocus();
				teren.generisiSlucajno();
				timer=new Timer(vreme);
			}
			
		});
		
		postavi.addActionListener(ae->{
			teren.requestFocus();
			if(zid.getState()) {
				teren.dodajZid();
				teren.repaint();
			}
			else if(prolaz.getState()) {
				teren.dodajProlaz();
				teren.repaint();
			}
			else if(glavniAkter.getState()) {
				teren.dodajGlavnogAktera();
				teren.repaint();
			}
			else if(sporedniAkter.getState()) {
				teren.dodajSporednogAktera();
				teren.repaint();
			}
		});
		
		timer=new Timer(vreme);
		donjiPanel.add(vreme);
		donjiPanel.add(rez);
		donjiPanel.add(rezultat);
		donjiPanel.add(kraj);
		
		this.add(donjiPanel, BorderLayout.SOUTH);
		
		desniPanel.add(radioDugmad);
		desniPanel.add(postavi);
		desniPanel.add(nasumicno);
		
		this.add(desniPanel, BorderLayout.EAST);
	}

	public void prikaziDijalog() {
		Dialog dijalogZaKrajIgre=new Dialog(this, ModalityType.APPLICATION_MODAL);
		dijalogZaKrajIgre.setBackground(Color.red);
		dijalogZaKrajIgre.setTitle("KRAJ IGRE!");
		
		Label labelazakraj=new Label("IZGUBILI STE");
		
		dijalogZaKrajIgre.add(labelazakraj,BorderLayout.CENTER);
		dijalogZaKrajIgre.revalidate();
		dijalogZaKrajIgre.setBounds(700, 200, 100, 100);
		
		dijalogZaKrajIgre.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dijalogZaKrajIgre.dispose();
			}
		});
		
		dijalogZaKrajIgre.setVisible(true);
	}
	

	public static void main(String[] args) {
		new Igra();
	}



}
