import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

/**
 * Removes the quotes and the spaces from the official nyt source code Wordle word list.
 * 
 * @author Kergan Sanderson
 */
public class WordListFormat {

    /**
     * Starts the program.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {

        Scanner input = null;

        // Create the input Scanner from the input file
        try {
            input = new Scanner(new FileInputStream("data/nyt-wordle-wordlist.csv"));
            input.useDelimiter(",");
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found.");
            System.exit(1);
        }

        PrintWriter output = null;

        // Create the PrintWriter to the output file
        try {
            output = new PrintWriter(new FileOutputStream("data/wordle-wordlist.csv"));
        } catch (FileNotFoundException e) {
            System.out.println("Cannot create output file.");
            System.exit(1);
        }

        // Create a temporary string
        String temp = "";

        // Create a counter for the total number of words
        int count = 0;

        // Add the first word to the output file, since it doesn't have a space in front of it
        temp = input.next();
        output.print(temp.substring(1, temp.length() - 1) + ",");
        count++;

        temp = input.next();

        // remove space and quotes from around all tokens except the last one
        while (input.hasNext()) {
            output.print(temp.substring(2,temp.length() - 1) + ",");
            count++;
            temp = input.next();
        }

        // Add the last word to the output file, not adding a comma after it
        output.print(temp.substring(2,temp.length() - 2));
        count++;
        
        // Report the total number of words
        System.out.println("Number of words: " + count);

        // Close input Scanner and output PrintWriter to finish the procedure
        input.close();
        output.close();
    }
}
