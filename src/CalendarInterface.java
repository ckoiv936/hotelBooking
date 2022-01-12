import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Reader;
import java.io.StringReader;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CalendarInterface {

    private JFrame calendarInterface;
    private Calendar calendar = new Calendar();
    private JTextField firstName;
    private JTextField lastName;
    private JTextField startYear;
    private JTextField endYear;
    private JTextField phoneNumber;
    private JTextField bookingsText;
    private String username;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    CalendarInterface window = new CalendarInterface(args[0]);
                    window.calendarInterface.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public CalendarInterface(String name) {
        username = name;
        initialize();
    }
    /**
     * Appends  string to the end of the file, using the username's current value as the text file.
     *
     * @param append
     */
    private void appendFile(String append) {

        BufferedWriter writeFile = null;
        try {
            writeFile = new BufferedWriter(new FileWriter(username + ".txt", true));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(new JFrame("Error"), "Unable to create logs");
        }
        try {
            writeFile.newLine();
            writeFile.write(append);
            writeFile.close();
        } catch (Exception e) {
            System.exit(0);
        }

    }
    /**
     * Converts a string into an int
     * @param month
     * @return
     */
    private int monthToInt(String month) {
        int monthInt;
        switch (month) {
            case "January":
                monthInt = 1;
                break;
            case "Feburary":
                monthInt = 2;
                break;
            case "March":
                monthInt = 3;
                break;
            case "April":
                monthInt = 4;
                break;
            case "May":
                monthInt = 5;
                break;
            case "June":
                monthInt = 6;
                break;
            case "July":
                monthInt = 7;
                break;
            case "August":
                monthInt = 8;
                break;
            case "September":
                monthInt = 9;
                break;
            case "October":
                monthInt = 10;
                break;
            case "November":
                monthInt = 11;
                break;
            case "December":
                monthInt = 12;
                break;
            default:
                monthInt = 1; //
        }
        return monthInt;
    }
    /**
     * Method that checks ifa  string contains only digits, returns true if it does.
     * @param str
     * @return returns true if strign contains only digits, false if otherwise.
     */
    private boolean noNonDigits(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        String[] months = { "January", "Feburary", "March", "April", "May", "June", "July", "August", "September",
                "October", "November", "December" };
        //Contains ints from 1 to 31, used for JComboBox elements.
        Integer[] days = new Integer[31];
        /*
         * Initalizes integer array
         */
        for (int i = 0; i < 31; i++) {
            days[i] = i + 1;
        }
        calendarInterface = new JFrame();
        calendarInterface.setTitle("Hotel Reservation");
        calendarInterface.setBounds(100, 100, 1015, 587);
        calendarInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calendarInterface.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("First Name");
        lblNewLabel.setBounds(31, 26, 87, 14);
        calendarInterface.getContentPane().add(lblNewLabel);

        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setBounds(31, 57, 87, 14);
        calendarInterface.getContentPane().add(lblLastName);

        JComboBox startDay = new JComboBox(days);
        startDay.setBounds(130, 126, 121, 22);
        calendarInterface.getContentPane().add(startDay);

        JComboBox startMonth = new JComboBox(months);
        startMonth.setBounds(128, 157, 121, 22);
        calendarInterface.getContentPane().add(startMonth);

        JComboBox endDay = new JComboBox(days);
        endDay.setBounds(340, 126, 121, 22);
        calendarInterface.getContentPane().add(endDay);

        JComboBox endMonth = new JComboBox(months);
        endMonth.setBounds(340, 157, 121, 22);
        calendarInterface.getContentPane().add(endMonth);

        firstName = new JTextField();
        firstName.setBounds(128, 23, 121, 20);
        calendarInterface.getContentPane().add(firstName);
        firstName.setColumns(10);

        lastName = new JTextField();
        lastName.setBounds(128, 54, 121, 20);
        calendarInterface.getContentPane().add(lastName);
        lastName.setColumns(10);

        startYear = new JTextField();
        startYear.setBounds(128, 189, 121, 20);
        calendarInterface.getContentPane().add(startYear);
        startYear.setColumns(10);

        endYear = new JTextField();
        endYear.setBounds(340, 189, 121, 20);
        calendarInterface.getContentPane().add(endYear);
        endYear.setColumns(10);

        phoneNumber = new JTextField();
        phoneNumber.setBounds(128, 85, 121, 20);
        calendarInterface.getContentPane().add(phoneNumber);
        phoneNumber.setColumns(10);

        bookingsText = new JTextField();
        bookingsText.setBounds(400, 272, 586, 257);
        calendarInterface.getContentPane().add(bookingsText);
        bookingsText.setColumns(10);

        JLabel lblDay = new JLabel("Start Day");
        lblDay.setBounds(31, 130, 87, 14);
        calendarInterface.getContentPane().add(lblDay);

        JLabel lblEndDay = new JLabel("End Day");
        lblEndDay.setBounds(268, 130, 87, 14);
        calendarInterface.getContentPane().add(lblEndDay);

        JLabel lblEndDay_1 = new JLabel("End Month");
        lblEndDay_1.setBounds(268, 161, 87, 14);
        calendarInterface.getContentPane().add(lblEndDay_1);

        JLabel lblDay_1 = new JLabel("Start Month");
        lblDay_1.setBounds(31, 161, 87, 14);
        calendarInterface.getContentPane().add(lblDay_1);

        JLabel lblDay_2 = new JLabel("Start Year");
        lblDay_2.setBounds(31, 192, 87, 14);
        calendarInterface.getContentPane().add(lblDay_2);
        JLabel lblDay_2_1 = new JLabel("End Year");
        lblDay_2_1.setBounds(268, 192, 87, 14);
        calendarInterface.getContentPane().add(lblDay_2_1);
        JButton btnNewButton = new JButton("Add Booking");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Checks if fields are valid, otherwise initalizes all elements to be added to a booking.
                if (firstName.getText().isEmpty() || lastName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame("Error"), "Name fields invalid, please try again");
                } else if (phoneNumber.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame("Error"), "Phone fields invalid, please try again");
                } else if (!noNonDigits(phoneNumber.getText())) {
                    JOptionPane.showMessageDialog(new JFrame("Error"), "Phone fields invalid, please try again");
                } else if ((!noNonDigits(startYear.getText()) || !noNonDigits(endYear.getText())) || startYear.getText().isEmpty() || endYear.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(new JFrame("Error"), "Year fields invalid, please try again");
                } else {
                    String firstNameString = firstName.getText().toLowerCase();
                    String lastNameString = lastName.getText().toLowerCase();
                    String phoneNumberString = phoneNumber.getText();
                    int startDayInt = Integer.parseInt(startDay.getSelectedItem().toString());
                    int startMonthInt = monthToInt(startMonth.getSelectedItem().toString());
                    int startYearInt = Integer.parseInt(startYear.getText());
                    int endDayInt = Integer.parseInt(endDay.getSelectedItem().toString());
                    int endMonthInt = monthToInt(endMonth.getSelectedItem().toString());
                    int endYearInt = Integer.parseInt(endYear.getText());
                    if (!calendar.add_booking(firstNameString, lastNameString, phoneNumberString, startDayInt,
                            startMonthInt, startYearInt, endDayInt, endMonthInt, endYearInt)) {
                        JOptionPane.showMessageDialog(new JFrame("Error"),
                                "Unable to book at that time frame, rooms are full.");
                    }
                    else {
                        LocalDate currentdate = LocalDate.now();
                        int day = currentdate.getDayOfMonth();
                        String month = currentdate.getMonth().toString();
                        int year = currentdate.getYear();
                        appendFile("Added booking for " + firstNameString + " " + lastNameString + " at " + day + '/' + month + '/' + year);
                        appendFile("from " + startDayInt + '/' + startMonth.getSelectedItem().toString()  + '/' + startYearInt + " to " +
                                endDayInt + '/' + endMonth.getSelectedItem().toString() + '/' + endYearInt);

                    }
                }
            }
        });
        btnNewButton.setBounds(31, 220, 127, 23);
        calendarInterface.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Find Bookings of Customer");
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookings;
                bookings = calendar.getBookings(firstName.getText().toLowerCase());
                Reader inputString = new StringReader(bookings);
                BufferedReader reader = new BufferedReader(inputString);
                try {
                    bookingsText.read(reader, null);
                    reader.close();
                    bookingsText.requestFocus();
                } catch (Exception E) {

                }

            }
        });
        btnNewButton_1.setBounds(268, 42, 193, 23);
        calendarInterface.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Remove Booking");
        btnNewButton_2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Gets current text in name boxes and starting day and removes booking based on them.
                calendar.removeBooking((firstName.getText().toLowerCase() + " " + lastName.getText().toLowerCase()),
                        Integer.parseInt(startDay.getSelectedItem().toString()));
                LocalDate currentdate = LocalDate.now();
                int day = currentdate.getDayOfMonth();
                String month = currentdate.getMonth().toString();
                int year = currentdate.getYear();
                appendFile("Removed bookings with name: " + firstName.getText() + " " + lastName.getText() + " at " + day + '/' + month + '/' + year);
            }
        });
        btnNewButton_2.setBounds(268, 220, 145, 23);
        calendarInterface.getContentPane().add(btnNewButton_2);

        JLabel lblNewLabel_1 = new JLabel("Phone Number");
        lblNewLabel_1.setBounds(29, 88, 89, 14);
        calendarInterface.getContentPane().add(lblNewLabel_1);
        JButton btnNewButton_3 = new JButton("Save and Quit");
        btnNewButton_3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.saveBookings();
                LoginInterface.main(null);
                calendarInterface.dispose();
            }
        });
        btnNewButton_3.setBounds(31, 300, 127, 23);
        calendarInterface.getContentPane().add(btnNewButton_3);

    }
}
