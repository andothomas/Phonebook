import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;

/* Questions for Professor:
 * 1) Entry[]. Is this supposed to be non-static, and how do we make the program work if so? (currently line #17)
 * 2) If the input is greater than 8 or less than 1 character(s), should we display an "error"?? A: no. not within the scope of the project
 * 3) Do the characters we use to separate each parameter matter? (We used "::") A: no. given code was modified as such.
 */

public class Phonebook {


    public static Entry[] entryList = new Entry[200]; // fixed size of 200 entries
    private static int num_entries = 0;


    public static void readPhoneBook(String FileName) throws Exception{
        File file = new File("/Users/ajthomas/Desktop/Phonebook",FileName);
        Scanner sc = new Scanner(file);

        int i = 0;
        while(sc.hasNextLine()){
            String currentLine = sc.nextLine();
            //System.out.println(currentLine);
            String[] tokenArray = currentLine.split("::",0); // Splits the lines into tokens separated by "::"
           // System.out.println(Arrays.toString(tokenArray));
           // System.out.println(tokenArray[0]);

            /* Enters the generated tokens into an array with index 0, 1, and 2
             * corresponding to name, phone number, and notes (respectively)
             */
            entryList[i] = new Entry(tokenArray[0], tokenArray[1], tokenArray[2]);
            num_entries ++; // go to the next phonebook entry
            i++; // increment the number of imported entries by 1


        }
        System.out.printf("%d entries were loaded from the phonebook.%n%n", num_entries);
        //System.out.println(entryList[0].name);
    }

    public static void storePhoneBook(String FileName) throws Exception{ //importing contacts from the file. Taken directly from assignment.
        PrintStream P = new PrintStream(FileName);

        for(int i = 0; i < num_entries; i++){
            P.println(entryList[i].name + "::" + entryList[i].number + "::" + entryList[i].notes); // prints the contents of entryList to the file
        }
        P.close();
        System.out.println("Phonebook Stored.");
    }
    public static void listAllEntries(){
        for (int i = 0; i < num_entries; i++){
            System.out.printf("%s, %s, %s%n", entryList[i].name, entryList[i].number, entryList[i].notes); // print all attributes of each Entry object in entryList
        }
    }


    public static void main(String[]args) throws Exception {

        Scanner keyInput = new Scanner(System.in);
        String name, number, notes;
        String inputString = " ";
        readPhoneBook("phonebook_info"); // reads the entries from the phonebook and stores them as parameters for a new entry object in the entryList array
        System.out.printf("Codes are entered as 1 to 8 characters.%nUse \"e\" for enter, \"f\" for find, \"l\" for list, \"q\" for quit.%n%n");

        while (inputString.charAt(0) != 'q') { // run the loop as long as the input is not "q"
            System.out.print("Command: ");
            inputString = keyInput.nextLine();
            if (inputString.charAt(0) == 'e') { // if "e" is the first character...
                name = (String) inputString.subSequence(2, inputString.length()); // store the remaining characters as the name for the entry

                System.out.print("Enter number: ");
                number = keyInput.nextLine();

                System.out.print("Enter notes: ");
                notes = keyInput.nextLine();
                if (notes.equals("")){
                    notes = " ";
                }
                entryList[num_entries] = new Entry(name, number, notes); // creates the new object with the given parameters
                num_entries++;
            } else if (inputString.charAt(0) == 'f') {
                for (int i = 0; i < num_entries; i++) {
                    if (inputString.toLowerCase().subSequence(2, inputString.length()).equals(entryList[i].name.toLowerCase())) { // if the input after "f " is a name in the phonebook (ignoring case)...
                        System.out.printf("-- %s%n-- %s%n-- %s%n", entryList[i].name, entryList[i].number, entryList[i].notes); //print the contact in a specified format
                        break;
                    } else if (i == num_entries - 1) { //if the name is not found in the last iteration of the loop...
                        System.out.printf("** No entry found with code %s%n", inputString.subSequence(2, inputString.length())); //print this.
                    }
                }
            } else if (inputString.charAt(0) == 'l') { // calls list method
                listAllEntries();
            }
        }
        System.out.println("Saving phonebook...");
        storePhoneBook("phonebook_info"); // calls the storePhoneBook method (saves entries to a file called "phonebook_info")

        System.out.println("Goodbye.");
    }

}
