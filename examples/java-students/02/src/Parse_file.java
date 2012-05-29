import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;


public class Parse_file {

	File f;
	BufferedReader b;
	Connection conn;
	Statement st;
	
	public Parse_file(String file,Connection c) throws FileNotFoundException, ClassNotFoundException, SQLException{
		this.f = new File(file);
		this.b= new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		
		Class.forName("org.postgresql.Driver");
		this.conn= c;
		this.st=conn.createStatement();
		
	}
	
	public void input_10(String[] s) throws SQLException{
		this.st.execute("insert into utilisateur values (" +
				"\'"+s[1]+"\'," +
				"\'"+s[5]+"\'," +
				"\'emballeur\'," +
				"null," +
				"null," +
				"null," +
				"null," +
				"\'"+s[4]+"\'," +
				"null," +
				"\'"+s[2]+"\'," +
				"\'"+s[3]+"\'" +
				");");
	}
	
	public void input_20(String[] s) throws SQLException{
		System.out.println(s[6]);
		this.st.execute("insert into utilisateur values (" +
				"\'"+s[1]+"\'," +
				"\'"+s[9]+"\'," +
				"\'client\'," +
				"\'"+s[7]+"\'," +
				"\'"+s[4]+"\'," +
				"\'"+s[5]+"\'," +
				"\'"+s[6]+"\'," +
				"null," +
				"null," +
				"null," +
				"null" +
				");");
	}
	
	public void input_30(String[] s) throws SQLException{
		this.st.execute("insert into produit values (" +
				"\'"+s[1]+"\'," +
				"\'"+s[2]+"\'," +
				s[3]+"," +
				s[4]+"," +
				"\'"+s[5]+"\'," +
				s[6]+"," +
				s[8]+"," +
				s[9]+
				");");
	}
	
	public void input_40(String[] s) throws SQLException{
		this.st.execute("insert into utilisateur values(" +
				"\'"+s[1]+"\'," +
				"\'"+s[3]+"\'," +
				"\'transporteur\'," +
				"null," +
				"null," +
				"null," +
				"null," +
				"null," +
				"null," +
				"\'"+s[2]+"\'," +
				"null" +
				");");
	}
	
	public void input_50(String[] s) throws SQLException{
		this.st.execute("insert into utilisateur values (" +
				"\'"+s[3]+"\'," +
				"\'"+s[4]+"\'," +
				"\'douane\'," +
				"\'"+s[1]+"\'," +
				"null," +
				"null," +
				"null," +
				"null," +
				"\'"+s[2]+"\'," +
				"null," +
				"null" +
				");");
	}
	
	public void input_60(String[] s) throws SQLException{
		this.st.execute("insert into utilisateur values (" +
				"\'"+s[3]+"\'," +
				"\'"+s[4]+"\'," +
				"\'gerant\'," +
				"null," +
				"null," +
				"null," +
				"null," +
				"null," +
				"null," +
				"\'"+s[2]+"\'," +
				"\'"+s[1]+"\'"+
				");");
	}
	
	public void input_string(String[] s) throws SQLException{

		if(s[0].matches("10")){
			input_10(s);
		}
		else if(s[0].matches("20")){
			input_20(s);
		}
		else if(s[0].matches("30")){
			input_30(s);
		}
		else if(s[0].matches("40")){
			input_40(s);
		}
		else if(s[0].matches("50")){
			input_50(s);
		}
		else if(s[0].matches("60")){
			input_60(s);
		}
		else {
			System.err.println("Erreur dans input_string, String non vailde");
		}
	}
	public void close() throws SQLException{
		this.conn.close();
	}
	
	public void parse() throws IOException, SQLException{
		String s="";
		String [] t;
		s=this.b.readLine();
		t=s.split("\\|");
		
		
		while(s!=null){
			System.out.println(s);
			this.input_string(t);
			try {
				s=this.b.readLine();
				t=s.split("\\|");
			} catch (NullPointerException e) {
				this.close();
				System.exit(1);
			}
		}
	}
	

}
