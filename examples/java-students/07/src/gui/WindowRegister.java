package gui;

public class WindowRegister extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4145635141897808012L;

	public WindowRegister(String name, int width, int length) {
		super(name, width, length);

	}
	
	public void init() {
		Inscription inscription = new Inscription();
		
		this.add(inscription);
		
		setBounds(450, 300, 500, 300);
		inscription.check();
	}

}
