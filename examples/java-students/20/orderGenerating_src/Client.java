/**
 * Contains the login and the country of a client
 * 
 * @author Joossen & Mathou
 *
 */
public class Client {

	private String login;
	private String country;
	
	public Client(String l, String c) {
		this.login = l;
		this.country = c;
	}
	
	public String getLogin() {
		return this.login;
	}
	
	public String getCountry() {
		return this.country;
	}
	
}
