import javax.swing.*;
import java.awt.*;

public class DisplayRqstUI extends JFrame
{
    private QueryDB  q;
    private int      nb_column;
    private String[] col;
    
    public DisplayRqstUI(QueryDB _query, String info, String[] data)
    {
	super();
	setTitle(info); // Titre indique affichage 
	setVisible(true);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setResizable(false);
	
	/* On définit la taille de la fenêtre */
	Toolkit tk    = Toolkit.getDefaultToolkit();
	Dimension dim = tk.getScreenSize(); // on récup taille écran
	setSize(dim.width, dim.height); // on ajuste la taille

	/* La requête à afficher */
	q = _query;
	/* Le nom des colonnes */
	col = data;
	/* Le nombre de colonne */
	nb_column = col.length;
	

	/* dispJP est le panneau qui va permettre l'affichage */
	DispPanel dispJP = new DispPanel();
	getContentPane().add(dispJP);
	dispJP.repaint();
	dispJP.validate();
	dispJP.repaint();
    }

    class DispPanel extends JPanel
    {
	public void paintComponent(Graphics g)
	{
	    super.paintComponent(g);
	    
	    /* On procède à l'affichage */
	    int x = 20; // coordonnées du premier String à afficher
	    int y = 20; // x : abscisse, y : ordonnée
	    for (int i=0; i<nb_column; i++)
		{
		    g.drawString(col[i], x, y);
		    x += 150;
		}
	    try
		{
		    while (q.rst.next())
			{
			    FontMetrics fm = g.getFontMetrics();
			    y += fm.getHeight(); // on descend d'une rangée
			    x  = 20; // retour au début de la ligne
			    for (int i=1; i<=nb_column; i++)
				{
				    g.drawString(q.rst.getString(i), x, y);
				    x += 150;
				}
			}
		}
	    catch (Exception e)
		{
		    System.out.println("Enable to display the result :\n\t" + e);
		}
	    //paintAll(g);
	}
    }
}