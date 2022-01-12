import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SupervisorMenu {

    private JFrame supervisorMenu;
    private JTextField passwordField;
    private JTextField userField;
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
                    SupervisorMenu window = new SupervisorMenu();
                    window.supervisorMenu.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public SupervisorMenu() {
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
        HashMap<String, String> users = new HashMap<String, String>();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(new JFrame("Error"), "Missing or invalid user file.");
            System.exit(0);
        }

        // Add user details in file to hashmap.
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
            JOptionPane.showMessageDialog(new JFrame("Error"), "Unable to read file.");
            System.exit(0);
        }
        return users;
    }

    /**
     * Saves a hashmap to a text file in the format key + " " + key value
     *
     * @param fileName Name of the file to save to.
     * @param details  Hashmap of the user details to save to the file.
     */
    private void save(String fileName, HashMap<String, String> details) {
        File file = new File(fileName);

        FileWriter writeFile = null;

        try {

            // create new fileWriter for the file.
            writeFile = new FileWriter(file, false);

            // iterate map entries
            for (Map.Entry<String, String> entry : details.entrySet()) {

                // write username and password seperated by space.
                writeFile.write(entry.getKey() + " " + entry.getValue());

                writeFile.write('\n');
            }
            writeFile.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                writeFile.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Initialize the contents of the supervisorMenu.
     */
    private void initialize() {
        String userFile = "userDetails.txt";
        String supervisorFile = "supervisorDetails.txt";
        userDetails = readInFile(userFile);
        /*
         * Setting up initial JFrame for supervisor menu.
         */
        supervisorDetails = readInFile(supervisorFile);
        supervisorMenu = new JFrame();
        supervisorMenu.setBounds(100, 100, 447, 359);
        supervisorMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        supervisorMenu.getContentPane().setLayout(null);
        /*
         * Button for registering user.
         */
        JButton btnNewButton = new JButton("Register User");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userDetails.containsKey(userField.getText())) {
                    JOptionPane.showMessageDialog(new JFrame("Error"), "User already exists.");
                } else {
                    userDetails.put(userField.getText(), passwordField.getText());
                }
            }
        });
        btnNewButton.setBounds(25, 133, 119, 23);
        supervisorMenu.getContentPane().add(btnNewButton);
        /*
         * Adding text fields for username and password.
         */
        passwordField = new JTextField();
        passwordField.setBounds(25, 93, 277, 20);
        supervisorMenu.getContentPane().add(passwordField);
        passwordField.setColumns(10);

        userField = new JTextField();
        userField.setBounds(25, 44, 277, 20);
        supervisorMenu.getContentPane().add(userField);
        userField.setColumns(10);
        /*
         * Button for registering user.
         */

        JButton btnNewButton_1 = new JButton("Register Supervisor");
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (supervisorDetails.containsKey(userField.getText())) {
                    JOptionPane.showMessageDialog(new JFrame("Error"), "User already exists.");
                } else {
                    supervisorDetails.put(userField.getText(), passwordField.getText());
                    if (!userDetails.containsKey(userField.getText())) {
                        userDetails.put(userField.getText(), passwordField.getText());
                    }
                }
            }
        });
        btnNewButton_1.setBounds(164, 133, 138, 23);
        supervisorMenu.getContentPane().add(btnNewButton_1);

        /*
         * Button for saving and quitting.
         */
        JButton btnNewButton_2 = new JButton("Save and Quit");
        btnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save(userFile, userDetails);
                save(supervisorFile, supervisorDetails);
                supervisorMenu.dispose();
                LoginInterface.main(null);
            }
        });
        /*
         * Button for deleting user.
         */
        btnNewButton_2.setBounds(187, 246, 145, 23);
        supervisorMenu.getContentPane().add(btnNewButton_2);

        JButton btnNewButton_3 = new JButton("Delete User");
        btnNewButton_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supervisorDetails.remove(userField.getText());
                userDetails.remove(userField.getText());
                userField.setText(null);
                passwordField.setText(null);
            }
        });
        btnNewButton_3.setBounds(25, 167, 119, 23);
        supervisorMenu.getContentPane().add(btnNewButton_3);

        /*
         * Button for deleting supervisors.
         */
        JButton btnNewButton_3_1 = new JButton("Delete Supervisor");
        btnNewButton_3_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                supervisorDetails.remove(userField.getText());
                userField.setText(null);
                passwordField.setText(null);
            }
        });
        btnNewButton_3_1.setBounds(164, 167, 138, 23);
        supervisorMenu.getContentPane().add(btnNewButton_3_1);

        JButton btnNewButton_4 = new JButton("View Users");
        btnNewButton_4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileInterface.main((new String[] { userFile }));

            }
        });
        btnNewButton_4.setBounds(25, 196, 119, 23);
        supervisorMenu.getContentPane().add(btnNewButton_4);

        JButton btnNewButton_5 = new JButton("View Supervisors");
        btnNewButton_5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileInterface.main((new String[] { supervisorFile }));
            }
        });
        btnNewButton_5.setBounds(164, 196, 138, 23);
        supervisorMenu.getContentPane().add(btnNewButton_5);

        JButton btnNewButton_6 = new JButton("View user's logs");
        btnNewButton_6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String fileName = userField.getText() + ".txt";
                File file = new File(fileName);
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    FileInterface.main(new String[] {fileName});
                }

            }
        });
        btnNewButton_6.setBounds(25, 246, 138, 23);
        supervisorMenu.getContentPane().add(btnNewButton_6);
    }
}
