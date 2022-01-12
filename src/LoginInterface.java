/**
 *
 * @author  Matthew Pham <mpham815@mtroyal.ca>
 *
 */

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginInterface {

    private JFrame hotelLogin;
    private JTextField usernameInput;
    private JPasswordField passwordInput;
    /*
     * Hashmap that contains the login details of a user, with the usernames being
     * the keys which map to the passwords.
     */
    private HashMap<String, String> userDetails;
    private HashMap<String, String> supervisorDetails;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    LoginInterface window = new LoginInterface();
                    window.hotelLogin.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public LoginInterface() {
        initialize();
    }

    /**
     * Reads in a file containing details of users, then stores the username and
     * passwords in a hashmap.
     *
     * @param fileName name of the file the user details are contained in
     * @return HashMap with usernames as the key that map to the passwords.
     */
    private HashMap<String, String> readInFile(String fileName) {

        // Current line of the user details file that is being processed.
        String currentLine;
        boolean successfulOpen = false;
        HashMap<String, String> users = new HashMap<String, String>();
        BufferedReader reader = null;
        while (!successfulOpen) {
            try {
                reader = new BufferedReader(new FileReader(fileName));
                successfulOpen = true;

            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(new JFrame("Error"), "Missing or invalid " + fileName
                        + " file, placeholder file will be created. Initial login details are as follows: Supervisor1 password1.");
                initalizeFile(fileName);
            }
        }
        try {
            while ((currentLine = reader.readLine()) != null) {
                String[] parts = currentLine.split(" ", 2);
                if (parts.length >= 2) {
                    String key = parts[0];
                    key = key.toLowerCase();
                    String value = parts[1];
                    users.put(key, value);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame("Error"), "Issue reading in user details, closing.");
            System.exit(0);
        }
        if (users.isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame("Error"), "Empty " + fileName
                    + " file, placeholder file will be created. Initial login details are as follows: Supervisor1 password1.");
            users.put("supervisor1", "password1");
        }
        return users;
    }

    /**
     * Initalizes a user details file with a provided file name
     *
     * @param fileName name of the user details file to be created.
     */
    private void initalizeFile(String fileName) {

        File newUserDetails = new File(fileName);
        try {
            newUserDetails.createNewFile();
        } catch (IOException failedWrite) {
            JOptionPane.showMessageDialog(new JFrame("Error"), "Unable to make new user file.");
        }
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        String userFile = "userDetails.txt";
        String supervisorFile = "supervisorDetails.txt";
        userDetails = readInFile(userFile);
        supervisorDetails = readInFile(supervisorFile);
        /*
         * New JFrame for login system
         */
        hotelLogin = new JFrame("Hotel Login");
        hotelLogin.setBounds(200, 200, 500, 300);
        hotelLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        hotelLogin.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Hotel Login");
        lblNewLabel.setBounds(187, 11, 141, 14);
        hotelLogin.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Username");
        lblNewLabel_1.setBounds(92, 51, 73, 14);
        hotelLogin.getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Password");
        lblNewLabel_2.setBounds(92, 109, 73, 14);
        hotelLogin.getContentPane().add(lblNewLabel_2);

        /*
         * Adding fields for username and password.
         */
        usernameInput = new JTextField();
        usernameInput.setBounds(175, 48, 240, 20);
        hotelLogin.getContentPane().add(usernameInput);
        usernameInput.setColumns(10);

        passwordInput = new JPasswordField();
        passwordInput.setBounds(175, 106, 240, 20);
        hotelLogin.getContentPane().add(passwordInput);
        /**
         * Button for opening supervisor menu, checks if username and password fields belong to a user OR supervisor.
         */
        JButton btnNewButton = new JButton("User Login");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = usernameInput.getText();
                userName = userName.toLowerCase();
                String password = String.valueOf(passwordInput.getPassword());
                if ((supervisorDetails.containsKey(userName) && password.equals(supervisorDetails.get(userName)))
                        || ((userDetails.containsKey(userName) && password.equals(userDetails.get(userName))))) {
                    CalendarInterface.main(new String[]{userName});
                    usernameInput.setText(null);
                    passwordInput.setText(null);
                    hotelLogin.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "User details incorrect", "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                    usernameInput.setText(null);
                    passwordInput.setText(null);
                }
            }
        });
        btnNewButton.setBounds(24, 212, 128, 23);
        hotelLogin.getContentPane().add(btnNewButton);
        /**
         * Button for exiting the program, prompts the user for double checking.
         */
        JButton btnNewButton_1 = new JButton("Exit");
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hotelLogin = new JFrame("Exit");
                if (JOptionPane.showConfirmDialog(hotelLogin, "Please confirm if you would like to exit",
                        "Hotel Login System", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                    System.exit(0);
                }
            }
        });
        btnNewButton_1.setBounds(360, 212, 114, 23);
        hotelLogin.getContentPane().add(btnNewButton_1);
        /**
         * Button for opening supervisor menu, checks if username and password fields belong to a supervisor.
         */
        JButton btnNewButton_2 = new JButton("Supervisor Menu");
        btnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = usernameInput.getText();
                userName = userName.toLowerCase();
                String password = String.valueOf(passwordInput.getPassword());
                if (supervisorDetails.containsKey(userName) && password.equals(supervisorDetails.get(userName))) {
                    SupervisorMenu.main(null);
                    hotelLogin.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient permissions", "Login Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        btnNewButton_2.setBounds(187, 212, 141, 23);
        hotelLogin.getContentPane().add(btnNewButton_2);
    }
}
