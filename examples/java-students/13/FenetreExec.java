import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class FenetreExec{
  public static void main(String[] args){
	int type = 0;
	Fenetre fenetre = nouvelleFenetre(type);
/*
	while (type == fenetre.getType()){}

	fenetre.setVisible(false);
	type = fenetre.getType();
	fenetre.dispose();
	
	fenetre = nouvelleFenetre(type);*/


    }

	public static Fenetre nouvelleFenetre(int type){
		Fenetre fenetre = new Fenetre(type);
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.show();
		return fenetre;
	}
  }