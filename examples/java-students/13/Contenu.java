import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



public class Contenu{
	private String titre;;
	private int id;
	private int type;

	public Contenu(String ti,int ty,int idd){
		titre = ti;
		id = idd;
		type = ty;
	}
	public String getTitre(){return titre;}
	public int getId(){return id;}
	public int getType(){return type;}
}