package bd6;

import bd6.data.User;
import bd6.gui.LoginFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class Main {
    public static Connection connection;
    public static User currentUser;
    public static String userName;
    public static String password;
    protected static Properties configuration = new Properties();

    public static void main(String[] args) throws SQLException {
        try {
            FileInputStream fileStream = new FileInputStream("bd6.conf");

            configuration.load(fileStream);
        } catch (FileNotFoundException exception) {
            System.err.println("File ``bd6.conf'' not found.");
            configuration.setProperty("server", "localhost");
            configuration.setProperty("dbname", "bd6");
            configuration.setProperty("username", "gregoire");
            configuration.setProperty("password", "");
        } catch (IOException exception) {
            System.err.println("Error while reading configuration file:");
            exception.printStackTrace();
            configuration.setProperty("server", "localhost");
            configuration.setProperty("dbname", "bd6");
            configuration.setProperty("username", "gregoire");
            configuration.setProperty("password", "");
        }

        String url = "jdbc:postgresql://" + configuration.getProperty("server")
                     + '/' + configuration.getProperty("dbname");

        connection = DriverManager.getConnection(url, configuration.getProperty("username"),
                                                 configuration.getProperty("password"));
        UIManager.put("swing.boldMetal", false);

        /*
         * Below is a list of predefined usernames / password for quicker access
         * during the test phase.
         */
        if (args[0].equals("-customs")) {
            userName = "SO78210";
            password = "IWT87FKG6HB";
        } else if (args[0].equals("-manager")) {
            userName = "PV738";
            password = "UQO89JLE9JI";
        } else if (args[0].equals("-client")) {
            userName = "SEGZE03368";
            password = "IVW24HJB2RU";
        }

        LoginFrame loginFrame = new LoginFrame();

        centerFrame(loginFrame);
        loginFrame.setVisible(true);
    }

    public static void centerFrame(JFrame frame) {
        Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        int width = frame.getWidth();
        int height = frame.getHeight();
        int x = (dimensions.width - width) / 2;
        int y = (dimensions.height - height) / 2;

        frame.setLocation(x, y);
    }
}
