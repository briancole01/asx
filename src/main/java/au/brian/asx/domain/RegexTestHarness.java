package au.brian.asx.domain;

import java.io.Console;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexTestHarness {

    public static void main(String[] args){
//        Console console = System.console();
//        if (console == null) {
//            System.err.println("No console.");
//            System.exit(1);
//        }
        //while (true) {

            Pattern pattern = 
            //Pattern.compile(console.readLine("%nEnter your regex: "));
            Pattern.compile("Change of Director.s Interest Notice");


            Matcher matcher = 
            //pattern.matcher(console.readLine("Enter input string to search: "));
            pattern.matcher("Change of Director s Interest Notice");

            boolean found = false;
            while (matcher.find()) {
                System.out.println("I found the text " + matcher.group() + " starting at " +
                   "index " +  matcher.start() + "and ending at index " + matcher.end());
                found = true;
            }
            if(!found){
            	System.out.println("No match found");
            }
        //}
    }
}
