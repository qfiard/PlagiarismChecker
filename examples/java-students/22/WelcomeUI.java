import javax.swing.*;
import java.awt.*;

public class WelcomeUI
{
    private static ImageIcon pic;
    
    public static JPanel buildWelcomePanel()
    {
	PanelPic jp = new PanelPic();
	pic = new ImageIcon("Data/welcomepicture.gif");
	return jp;
    }

    static class PanelPic extends JPanel
    {
	public void paintComponent(Graphics g)
	{
	    super.paintComponent(g);

	    /* Affichage de l'image */
	    int x=5;
	    int y=70;

	    g.drawImage(pic.getImage(), x, y, null);
	}
    }
}