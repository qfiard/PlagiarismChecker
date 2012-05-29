import java.sql.*;
import java.util.Arrays;

class PersonneConnexion {
    private Connection conn;

    public PersonneConnexion(String login, String motPasse)
	throws SQLException, ClassNotFoundException {
	Class.forName("org.postgresql.Driver");
	conn = DriverManager.getConnection("jdbc:postgresql://localhost/" 
					   + login, login, motPasse);
    }

    public Connection getConnection() {
	return this.conn;
    }

    public void close() throws SQLException {
	conn.close();
    }

    public char[] passwordToMd5(char[] password) {
	char[] goodpassword = null;
	String passwordStr = new String(password);
	try {
	    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
	    ResultSet rs = null;
	    rs = st.executeQuery("SELECT md5('" + passwordStr + "');");
	    while (rs.next()) {
		goodpassword = rs.getString("md5").toCharArray();
	    }
	    rs.close();
	} catch(SQLException ex) {
	    System.out.println("Erreur " + ex.getMessage());
	}
	return goodpassword;
    }

    public String connexionValide(String login, char[] password) {
	String typePersonne = "";
	try {
	    Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
	    ResultSet rs = null;
	    rs = st.executeQuery("SELECT password, type_personne "+ 
			    "FROM identification WHERE login='" + login + "';");
	    while (rs.next()) {
		char[] goodpassword = rs.getString("password").toCharArray();
		if (Arrays.equals(passwordToMd5(password), goodpassword))
		    typePersonne = rs.getString("type_personne");
	    }
	    rs.close();
	    st.close();
	} catch(SQLException ex) {
	    System.out.println("Erreur " + ex.getMessage());
	}
	return typePersonne;
    }

}
