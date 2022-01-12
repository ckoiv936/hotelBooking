import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FileInterface {

    private JFrame frame;
    private JTextField textField;
    private final JButton exit = new JButton("Return to supervisor Menu");

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    FileInterface window = new FileInterface(args[0]);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public FileInterface(String fileName) {
        initialize(fileName);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize(String fileName) {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        textField = new JTextField();
        textField.setBounds(21, 11, 386, 190);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        exit.setBounds(31, 212, 213, 31);
        frame.getContentPane().add(exit);
        try {
            FileReader readFile = new FileReader(fileName);
            BufferedReader bufferedRead = new BufferedReader(readFile);
            textField.read(bufferedRead, null);
            bufferedRead.close();
            textField.requestFocus();

        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
