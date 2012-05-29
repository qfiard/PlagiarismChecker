/**
 * Contains the login and the country of a client
 * 
 * @author Joossen & Mathou
 *
 */
public class Packer {

	private String login;
	private int errorRate;
	
	public Packer(String l, int e) {
		this.login = l;
		this.errorRate = e;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public int getErrorRate() {
		return this.errorRate;
	}
	
}
