package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;

import Share.FiredException;
import Share.IdentificationException;
import Share.User;


public abstract class Window extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8453432465855126769L;
	public static boolean debug = false;
	private JMenuBar menuBar;
	protected JMenu file;
	protected JMenuItem quit;
	protected JMenuItem logout;
	protected JMenuItem changeLogin;
	protected JMenuItem lookWithOrder;
	protected JMenuItem lookWithDestination;
	protected JMenuItem lookWithContents;

	private JMenu print;
	private JMenuItem fullScreen;
	private JMenuItem litleWindow;

	private JMenu help;
	private JMenuItem about;

	/**
	 * Lorsque u = null connexion non effectué
	 * Sinon 
	 */
	public User u;


	/**
	 * Crée une nouvelle fenêtre, avec un nom <code>name</code>, de largeur
	 * <code>width</code> et de longueur <code>length</code>.
	 * 
	 * @param name Le nom de la fenêtre.
	 * @param width La largeur de la fenêtre.
	 * @param length La longeur de la fenêtre.
	 */
	public Window(String name, int width, int length) {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle(name);
		setBounds(450, 300, width, length);
		menuBar();
		u = null;
		this.setVisible(true);
	}

	public Window(String name, User user) {
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle(name);
		setBounds(450, 300, 500, 300);
		menuBar();
		u = user;
		this.setVisible(true);
	}

	/**
	 * Permet d'intégrer une barre de menus à une fenêtre.
	 */
	public void menuBar() {

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		file = new JMenu("Fichier");
		menuBar.add(file);

		quit = new JMenuItem("Quitter");
		file.add(quit);
		quit.addActionListener(this);

		print = new JMenu("Affichage");
		menuBar.add(print);

		fullScreen = new JMenuItem("Pleine écran");
		print.add(fullScreen);
		fullScreen.addActionListener(this);

		litleWindow = new JMenuItem("Réduire");
		print.add(litleWindow);
		litleWindow.addActionListener(this);

		help = new JMenu("Aide");
		menuBar.add(help);

		about = new JMenuItem("A propos");
		help.add(about);
		about.addActionListener(this);


	}

	/**
	 * Permet d'afficher une fenêtre d'information.
	 * 
	 * @param s le message
	 * @param title le titre de la fenêtre
	 */
	public void showInformationMessage(String s, String title) {
		JOptionPane.showMessageDialog(this, s, title,
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (debug) {
			System.out.println("Action avec la chaine de commande = "
					+ e.getActionCommand());
		}

		if (source == quit) {
			if (this.u != null)
				try {
					this.u.q.close();
				} catch (SQLException e1) {
					if (debug)
						e1.printStackTrace();
					System.exit(0);
				}
			System.exit(0);
		}

		if (source == fullScreen) {

			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension dimScreen = tk.getScreenSize();
			setBounds(0, 0, dimScreen.width, dimScreen.height);
		}

		if (source == litleWindow) {
			setBounds(450, 300, 500, 350);
		}

		if (source == about) {
			this.showInformationMessage("Crée par Evrard & Karboul", "Import Export v1.0");
		}

		if (source == logout) {
			logout.setVisible(false);
			this.setVisible(false);
			try {
				this.u.q.close();
			} catch (SQLException e1) {
				if (debug) 
					e1.printStackTrace();
				this.showInformationMessage("Problème avec la base de donnée", "Erreur");
				this.setVisible(false);
				gui.NewAccountWindow.main(null);
			}
			this.u = null;
			gui.NewAccountWindow.main(null);
		}
		
    	if (source == lookWithOrder) {
    		JLabel jLabelOrder_number = new JLabel("Numéro de commande : ");
    		JTextField order_number = new JTextField();
    		Object[] e2 = new Object[]{jLabelOrder_number, order_number};
    		this.setVisible(false);
    		int r = JOptionPane.showOptionDialog(this, e2, "Recherche", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
    		if (r == JOptionPane.OK_OPTION) {
    			// on ajoute à la liste le numéro de commande
    			this.u.history.add(order_number.getText());
    			this.u.level = 10;
    			new Customs(this.u);
    		}
			if (r == JOptionPane.CANCEL_OPTION) {
				if (debug) 
					System.out.println(" L'utilisateur a annlé");
			}
    	}
    	
    	if (source == lookWithDestination) {
    		JLabel jLabelD = new JLabel("Pays : ");
    		JTextField country = new JTextField();
    		Object e3 = new Object[]{jLabelD, country};
    		this.setVisible(false);
    		int r1 = JOptionPane.showOptionDialog(this, e3, "Pays de destination", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
    		if (r1 == JOptionPane.OK_OPTION) {
    			// on ajoute à le pays
    			this.u.history.add(country.getText());
    			this.u.level = 11;
    			new Customs(this.u);
    		} 
			if (r1 == JOptionPane.CANCEL_OPTION) {
				if (debug) 
					System.out.println(" L'utilisateur a annlé");
			}
    	}
    	if (source == lookWithContents) {
    		JLabel jLabelC = new JLabel("Contenu : ");
    		JTextField content = new JTextField();
    		Object e4 = new Object[]{jLabelC, content};
    		this.setVisible(false);
    		int r2 = JOptionPane.showOptionDialog(this, e4, "", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
    		if (r2 == JOptionPane.OK_OPTION) {
    			this.u.history.add(content.getText());
    			this.u.level = 12;
    			new Customs(this.u);
    		}
			if (r2 == JOptionPane.CANCEL_OPTION) {
				if (debug) 
					System.out.println(" L'utilisateur a annlé");
			}
    	}

		if (source == changeLogin) {
			JLabel labelLogin = new JLabel("Login :");
			JLabel labelMdp = new JLabel("Mot de passe :");
			JTextField login = new JTextField();
			JPasswordField password = new JPasswordField();

			Object[] elements = new Object[]{labelLogin, login, labelMdp, password, ""};

			int rep = JOptionPane.showOptionDialog(this, elements, "Connection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);


			if (rep == JOptionPane.OK_OPTION) {
				if (debug ) {
					System.out.println("login : " + login.getText());
					System.out.println(password.getPassword());
				}
				String pwd = Share.Tools.string_of_tabChar(password.getPassword());
				try {
					// login et mot de passe correct
					new User(login.getText(), pwd);
				} catch (IdentificationException e1) {
					if (debug)
						e1.printStackTrace();
					System.exit(1);
				} catch (SQLException e1) {
					if (debug)
						e1.printStackTrace();
					this.showInformationMessage("Problème avec la base de donnée", "Erreur");
					this.setVisible(false);
					gui.NewAccountWindow.main(null);
				} catch (ClassNotFoundException e1) {
					if (debug)
						e1.printStackTrace();
					this.showInformationMessage("Avec l'installation", "Erreur");
					System.exit(1);
				} catch (FiredException ek) {
					showInformationMessage("Vous avez été licencié", "Accès interdit");
					System.exit(0);
				}
				changeLogin();
			}

			if (rep == JOptionPane.CANCEL_OPTION) {
				if (debug) 
					System.out.println(" L'utilisateur a annlé");

			}

		}

	}

	private void changeLogin() {

		JLabel labelLogin = new JLabel("Login :");
		JLabel labelMdp = new JLabel("Mot de passe :");
		JTextField login = new JTextField();
		JPasswordField password = new JPasswordField();

		Object[] elements = new Object[]{labelLogin, login, labelMdp, password, ""};

		int rep = JOptionPane.showOptionDialog(this, elements, "Connection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);


		if (rep == JOptionPane.OK_OPTION) {
			if (debug ) {
				System.out.println("login : " + login.getText());
				System.out.println(password.getPassword());
			}
			String pwd = Share.Tools.string_of_tabChar(password.getPassword());
			try {
				this.u.q.changeLoginOrPassword(this.u.getId()+"", login.getText(), pwd);
			} catch (SQLException e) {
				if (debug)
					e.printStackTrace();
				this.showInformationMessage("Problème avec la base de donnée", "Erreur");
				this.setVisible(false);
				gui.NewAccountWindow.main(null);
			}
		}
		if (rep == JOptionPane.CANCEL_OPTION) {
			if (debug) 
				System.out.println(" L'utilisateur a annlé");

		}
	}



}





