// $Id: jfmt.java,v 1.2 2013-09-24 14:38:16-07 - - $
// NAME: ALEX VINCENT
// USER: AVINCENT

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import static java.lang.System.*;

//The only error is that this program prints the entire file as one paragraph

class jfmt {
   // Static variables keeping the general status of the program.
   public static final String JAR_NAME = get_jarname();
   public static final int EXIT_SUCCESS = 0;
   public static final int EXIT_FAILURE = 1;
   public static int exit_status = EXIT_SUCCESS;

   // A basename is the final component of a pathname.
   // If a java program is run from a jar, the classpath is the
   // pathname of the jar.
   static String get_jarname() {
      String jarpath = getProperty ("java.class.path");
      int lastslash = jarpath.lastIndexOf ('/');
      if (lastslash < 0) return jarpath;
      return jarpath.substring (lastslash + 1);
   }

// Formats a single file.
   static void format (Scanner infile, int widthGiven) {
     List<String> paragraph = new LinkedList<String>();
     // Read each line from the opened file, one after the other.
     // Stop the loop at end of file.
     for (int linenr = 1; infile.hasNextLine(); ++linenr) {
       String line = infile.nextLine();
         
       // Create a LinkedList of Strings.
       List<String> words = new LinkedList<String>();

       // Split the line into words around white space and iterate
       // over the words.
       for (String word: line.split ("\\s+")) {

         // Skip an empty word if such is found.
         if (word.length() == 0) continue;
         // Append the word to the end of the linked list.
         words.add (word);
         paragraph.add(word);
       }
     }
     //print last paragraph at the end of the file.
     print_paragraph(paragraph, widthGiven);
   }

   
//Prints the formatted paragraph with width restrictions
   static void print_paragraph(List<String> paragraph, int width){
     int charCount = 0;
     for(String word: paragraph){
       if (charCount == 0){
         out.printf("%s",word);
         charCount = word.length();
       } else{
         charCount = charCount + word.length()+1;
         if(charCount > width){
           out.printf("%n");
           out.printf("%s",word);
           charCount = word.length();
         }else{
           out.printf(" %s",word);
         }
       }
     }
     if(charCount > 0) out.printf("%n");
   }
 
// Main function scans arguments and opens/closes files.
   public static void main (String[] args) {
     int width = 65;
     if (args.length == 0) {
       // There are no filenames given on the command line.
       out.printf ("No files read");
       //format (new Scanner (in));
     }else{
       //Iterate over each filename given on the command line.
       for (int argix = 0; argix < args.length; ++argix) {
         String filename = args[argix];
         //Find and save the width parameter.
         if (filename.contains ("-")) {
           if (args[argix].matches ("-\\d+")){
             String widthStr = args[argix];
             try{
               width = Integer.parseInt(widthStr);
               //negate the "-" sign to make positive
               if(width < 0) width = width * -1;
             } catch (NumberFormatException error) {
               err.printf("NumberFormatException: %s%n", error.getMessage());
             }
           }
         }
         if (filename.equals("-")){
           // Treat a filename of "-" to mean System.in
           format (new Scanner (in), width);
         }
         else if(filename.contains("-") == false) {
           // Open the file and read it, or error out.
           try {
             Scanner infile = new Scanner (new File (filename));
             out.printf ("FILE: %s%n", filename);
             format (infile,width);
             infile.close();
           }catch (IOException error) {
             exit_status = EXIT_FAILURE;
             err.printf ("%s: %s%n", JAR_NAME,
                         error.getMessage());
           }
         }
       }
     }
     exit (exit_status);
   }
}
 
