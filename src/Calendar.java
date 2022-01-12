import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Summary: Implements a calendar system which holds bookings
 */
public class Calendar {

    int totalrooms = 10;    //The total number of rooms supported by the calendar (can be scaled)
    private ArrayList<Booking> bookings = new ArrayList<Booking>();

    /**
     *  Initilizes the calendar by loading calendar text file into the bookings array,
     *  and deletes old items upon creation
     */
    public Calendar() {
        load_calendar();
        update_calendar();
    }


    /**
     *
     * @param first : The persons first name
     * @param last : The persons last name
     * @param phone : The persons phone number
     * @param sDay, sMonth, sYear: the start year/month/day of a persons booking
     **@param eDay, eMonth, eYear: the end year/month/day of persons booking
     * @return: Returns True if the booking was created successfuly, false otherwise.
     */
    public boolean add_booking(String first, String last, String phone, int sDay, int sMonth, int sYear,
            int eDay, int eMonth, int eYear) {

        Booking new_booking = new Booking();
        new_booking = fillBooking(first,  last,  phone,  sDay,  sMonth,  sYear,  eDay,  eMonth,  eYear);
        boolean good = makeBooking(new_booking);

        if (good) {

            bookings.add(new_booking);  //only adds the new booking if it's valid
        }

        return good;
    }

    /*
     * Summary: Helper function, creates a booking by filling in the private fields.
     */
    private Booking fillBooking(String first, String last, String phone, int sDay, int sMonth, int sYear, int eDay, int eMonth, int eYear)
    {

        Booking newBooking = new Booking();

        newBooking.name = first + " " + last;       /* could be a bug here due to indentation */
        newBooking.phoneNumber = phone;
        newBooking.dateStart.day = sDay;
        newBooking.dateStart.month = sMonth;
        newBooking.dateStart.year = sYear;

        newBooking.dateEnd.day = eDay;
        newBooking.dateEnd.month = eMonth;
        newBooking.dateEnd.year = eYear;

        newBooking.room = 0;

        return newBooking;
    }

    /*
     * Summary: Helper function of add booking, finds if a person can be accomadated with a room or not.
     * Works by checking if the persons dates conflict with anyone elses in the system.
     * If so, then that room is ruled out as a possible room.
     */
    private boolean makeBooking(Booking person) {

        int i = 0;
        boolean freespace = true;
        boolean exit = false;
        person.room = 1;


        while(i < bookings.size() && exit == false) //Checks if any room can accomadate the guest or not.
        {

            if(((bookings.get(i).dateStart.day <= person.dateStart.day) && (bookings.get(i).dateStart.month <= person.dateStart.month) && (bookings.get(i).dateStart.year <= person.dateStart.year))&&
                    ((bookings.get(i).dateEnd.day >= person.dateStart.day) && (bookings.get(i).dateEnd.month >= person.dateEnd.month) && (bookings.get(i).dateEnd.year >= person.dateEnd.year)))
            {

                if(bookings.get(i).room == person.room) //If the room does not work, tries the next room.
                {
                    person.room += 1;

                }
                if(person.room > totalrooms)    //If no rooms work, returns false
                {
                    exit = true;
                    freespace = false;
                }
            }

            i++;

        }
        return freespace;

    }


    /**
     * Purpose: checks each element in the calendar, to see if the bookings end date
     * has already occured, and therefor can be deleted from the calendar.
     */
    private void update_calendar() {

        int day;
        int month;
        int year;


        Booking current = new Booking();

        LocalDate currentdate = LocalDate.now();    /* gets the current date */

        day = currentdate.getDayOfMonth();
        month = currentdate.getMonthValue();
        year = currentdate.getYear();


        for (int i = bookings.size() -1; i >= 0; i--) {     //deletes any old items

            current = bookings.get(i);

            if ((current.dateEnd.year <  year) || ((current.dateEnd.year == year) && (current.dateEnd.month < month)) ||
                    ((current.dateEnd.year ==  year) && (current.dateEnd.month ==  month) && (current.dateEnd.day < day))) {
                bookings.remove(i); //remove the booking

            }
        }

        return;
    }

    /**
     * Purpose: Loads the calendar by opening and reading the contents
     *          of the text file calendar.
     */
    private void load_calendar() {

        //Loads the text file into the booking[]


        try {
            File file = new File("Calendar.txt");   //creates a new calendar file if one does not exist
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                //       System.out.println("File already exists.");
            }
            Scanner scan = new Scanner(file);

            while (scan.hasNextLine()) {


                Booking current = new Booking();
                String hold_data = new String();


                hold_data = scan.nextLine();                //read a line, split it up, add it to the arrayList
                String[] parts = hold_data.split(" ", 10);

                current.name = parts[0] + " " + parts[1];

                current.phoneNumber = parts[2];

                current.dateStart.year = Integer.parseInt(parts[3]);
                current.dateStart.month = Integer.parseInt(parts[4]);

                current.dateStart.day = Integer.parseInt(parts[5]);
                current.dateEnd.year = Integer.parseInt(parts[6]);
                current.dateEnd.month = Integer.parseInt(parts[7]);
                current.dateEnd.day = Integer.parseInt(parts[8]);
                current.room = Integer.parseInt(parts[9]);

                bookings.add(current);
            }

            scan.close();
        }

        catch(IOException e) {
            //catch exception
        }

        return;
    }

    /**
     * Purpose: when the program is to exit, saves all bookings into the calendar
     *          text file.
     */
    public void saveBookings() {

        Booking current = new Booking();
        //  String line = new String();

        try {
            Writer fileWriter = new FileWriter("Calendar.txt", false);  //remakes the file, wiping it clean

            for (int i = 0; i < bookings.size(); i++) {

                current = bookings.get(i);

                String line = new String();

                line += current.name;           //Saves information in the text file,
                line += " ";                    //seperating fields with spaces

                line += current.phoneNumber;
                line += " ";

                line += current.dateStart.year;
                line += " ";

                line += current.dateStart.month;
                line += " ";

                line += current.dateStart.day;
                line += " ";

                line += current.dateEnd.year;
                line += " ";

                line += current.dateEnd.month;
                line += " ";

                line += current.dateEnd.day;
                line += " ";

                line += current.room;
                line += '\n';

                fileWriter.write(line);

            }

            fileWriter.close();


        } catch (IOException e) {

            //catch error
        }

        return;
    }


    /**
     *
     * @param name : the name of the person you are searching for.
     * @return : A string of all the people who match the name provided.
     */
    public String getBookings(String name) {


        String output = new String();

        for(int i = 0; i < bookings.size(); i++) {  //traverses all bookings, looking for matches
            if(bookings.get(i).name != null && bookings.get(i).name.contains(name)) {

                output += bookings.get(i).name;     //if the name provided is even just a substring of
                output += " Phone ";                //a name in the calendar system it is printed
                output += bookings.get(i).phoneNumber;
                output += " Start date ";

                output += String.valueOf(bookings.get(i).dateStart.year);
                output += "-";

                output += String.valueOf(bookings.get(i).dateStart.month);
                output += "-";

                output += String.valueOf(bookings.get(i).dateStart.day);    //formats the persons information
                output += " End date ";


                output += String.valueOf(bookings.get(i).dateEnd.year);
                output += "-";

                output += String.valueOf(bookings.get(i).dateEnd.month);
                output += "-";

                output += String.valueOf(bookings.get(i).dateEnd.day);
                output += " room ";

                output += String.valueOf(bookings.get(i).room);

                output += "\n";


            }
        }
        return output;
    }


    /**
     *
     * @param name : the name of the person, whoes booking is to be deleted.
     * @param sDay : the start day of the booking to be deleted.
     * @return : True if the booking specified was deleted.
     */
    public boolean removeBooking(String name, int sDay) {

        boolean removed = false;

        Booking remove = new Booking();
        remove.dateStart.day = sDay;
        remove.name = name;

        removed = removeBookingHelper(remove);

        return removed;

    }

    /**
     *
     * Purpose: Helper function for removeBooking.
     * Summary: Goes through each booking, deletes all bookings that satisfy
     *          the search constraints.
     *
     */
    private boolean removeBookingHelper(Booking person) {

        boolean removed = false;

        for (int i = bookings.size()-1; i>=0; i--) {

            Booking current = bookings.get(i);

            if((current.name.contains(person.name)) && (current.dateStart.day == person.dateStart.day)) {

                bookings.remove(i); //i counts downwards because this operation automatically shifts the array
                removed = true;     //to the left.
            }
        }

        return removed;
    }

    /**
     *
     * @author kkoiv
     * Summary: A class which holds the credentials required for a booking.
     *
     */
    private class Booking {

        String name = new String();
        String phoneNumber = new String();
        Date dateStart = new Date();
        Date dateEnd = new Date();
        int room;

        /**
         *
         * @author kkoiv
         * Summary: A class which holds the credentials of a date.
         *
         */
        private class Date {

            int day;
            int month;
            int year;

        }
    }

}